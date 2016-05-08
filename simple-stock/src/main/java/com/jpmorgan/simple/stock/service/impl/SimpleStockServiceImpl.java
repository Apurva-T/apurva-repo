package com.jpmorgan.simple.stock.service.impl;

import static com.jpmorgan.utils.CommonUtils.isNotNull;
import static com.jpmorgan.utils.CommonUtils.isZeroVal;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.validator.routines.DoubleValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpmorgan.exception.JPMDataException;
import com.jpmorgan.exception.JPMServiceException;
import com.jpmorgan.simple.stock.calculator.StockCalculator;
import com.jpmorgan.simple.stock.entity.Stock;
import com.jpmorgan.simple.stock.entity.Trade;
import com.jpmorgan.simple.stock.manager.StockEntityManager;
import com.jpmorgan.simple.stock.service.SimpleStockService;
import com.jpmorgan.utils.DateUtils;

@Service("simpleStockService")
/**
 * Business Service Implementation to be called by the controller or test class to 
 * cater to functions like calculation of div yield, PE ration, etc for stocks and associated trades.
 * @author Apurva T
 *
 */
public class SimpleStockServiceImpl implements SimpleStockService {

	@Autowired
	private  StockCalculator stockCalculator;
	
	@Autowired
	private StockEntityManager stockEntityManager;
	
	private Logger logger = LoggerFactory.getLogger(SimpleStockServiceImpl.class);
	private DoubleValidator doubleValidator = new DoubleValidator();
	
	@Override
	public BigDecimal calculateDividendYield(String stockSymbol, String priceStr) throws JPMServiceException {
	
		BigDecimal dividendYield = BigDecimal.ZERO;
		
		if(isNotNull(stockSymbol) && isNotNull(priceStr)) {
			
			Double price = doubleValidator.validate(priceStr); 
			if(isNotNull(price)) {
				Stock stock = this.retrieveStock(stockSymbol);
				if(isNotNull(stock)) {
					stock.setPrice(new BigDecimal(price));
					dividendYield = stockCalculator.calculateDivYield(stock);
				}else {
					logger.error("Sorry, Stock not found for the input stock symbol", stockSymbol);
				}
			}else {
				logger.error("Sorry, Input price is not valid");
			}
		}else {
			logger.error("Sorry, Stock symbol not valid");
		}
		
		return dividendYield;
	}

	
	/**
	 * The method retrieves stock object from the given input stock symbol.
	 * @param stockSymbol
	 * @return associated stock object if present for the input stock symbol. If not available, returns null. 
	 */
	public Stock retrieveStock(String stockSymbol) throws JPMServiceException {
		Stock stock = null;
		if(isNotNull(stockSymbol) && stockSymbol.length() >0) {
			try {
				stock = this.stockEntityManager.getStock(stockSymbol) ;
			}catch(JPMDataException dataException) {
				logger.error("Exception occured during retrieval of stock via stockSymbol", dataException);
				throw new JPMServiceException("Exception occured during retrieval of stock via stockSymbol", dataException);
			}
		}
		
		return stock;
	}

	@Override
	public BigDecimal calculatePERatio(String stockSymbol, String priceStr) throws JPMServiceException {
		BigDecimal peRatio = BigDecimal.ZERO;
		
		if(isNotNull(stockSymbol) && isNotNull(priceStr)) {
			
			Double price = doubleValidator.validate(priceStr); 
			if(isNotNull(price)) {
				Stock stock = this.retrieveStock(stockSymbol);
				if(isNotNull(stock)) {
					stock.setPrice(new BigDecimal(price));
					peRatio = stockCalculator.calculatePERatio(stock);
				}else {
					logger.error("Sorry, Stock not found for the input stock symbol", stockSymbol);
				}
			}else {
				logger.error("Sorry, Input price is not valid");
			}
		}else {
				logger.error("Sorry, Stock symbol not valid");
		}
		return peRatio;
	}

	@Override
	public Boolean recordTrade(Trade trade) throws JPMServiceException {
		boolean isAdded = false;
		// Check for validity of trade object
		if(isNotNull(trade) && isNotNull(trade.getStock()))
				if(!isZeroVal(trade.getPrice()) && trade.getSharesQuantity() !=0 ) {
					try {
						isAdded = this.stockEntityManager.recordTrade(trade);
					}catch(JPMDataException e) {
						logger.error("An error has occurred while adding the trade.");
						throw new JPMServiceException("An error has occurred while adding the trade.", e);
					}
			
		}
		return isAdded;
	}

	@Override
	public BigDecimal calculateStockPrice(String stockSymbol,int durationInMins) throws JPMServiceException {
		
		BigDecimal stockPrice = BigDecimal.ZERO;
		if(isNotNull(stockSymbol)) {
			
			Stock stock = this.retrieveStock(stockSymbol);
			if(isNotNull(stock)) {
				LocalDateTime currentDateTime = DateUtils.getCurrentDateTime();
				List<Trade> allTrades = this.retrieveTrades(stock);
				List<Trade> pastSelectedTrades = this.getPastTrades(allTrades,durationInMins,currentDateTime);
				
				if(!pastSelectedTrades.isEmpty()) {
					stockPrice = this.stockCalculator.calculateStockPrice(pastSelectedTrades);
				}
			}else {
				logger.error("Stock not available for the input stock symbol");
			}
		
		}
		return stockPrice;
	}

	private List<Trade> getPastTrades(List<Trade> allTrades, int durationInMins, LocalDateTime currentTime) {
		
		List<Trade> pastTrades = new ArrayList<Trade>(allTrades.size());
		
		for(Trade trade: allTrades) {
			if(DateUtils.isDurationInRange(trade.getTimeStamp(),currentTime,durationInMins)) {
				pastTrades.add(trade);
			}
		}
		
		return pastTrades;
	}


	@Override
	public BigDecimal calculateGBCEAllShareIndex() throws JPMServiceException {
		
		BigDecimal sharesIndx = BigDecimal.ZERO;
		try {
			Map<String,Stock> stockMap = this.stockEntityManager.getAllStocks();
			if(isNotNull(stockMap) && stockMap.size() >0) {
//				Calculate GM for those stocks whose price is non-zero else GM will become zero overall.
				sharesIndx = this.stockCalculator.calculateGeoMetricMean(stockMap.values().stream().filter(stock ->stock.getPrice().compareTo(BigDecimal.ZERO) != 0).collect(Collectors.toList()));
			}
		}catch(JPMDataException e) {
			logger.error("An error has occurred while retrieving stocks / calculating All Shares Index using Geometric mean.");
			throw new JPMServiceException("An error has occurred while retrieving stocks/ calculating All Shares Index using Geometric mean.", e);
		}
		
		return sharesIndx;
	}


	@Override
	public List<Trade> retrieveTrades(Stock stock) throws JPMServiceException {
		
		List<Trade> allTrades = null;
		try {
			if(isNotNull(stock) && isNotNull(stock.getSymbol())) {
				allTrades = this.stockEntityManager.getTrades(stock.getSymbol());
			}
		} catch (JPMDataException e) {
			logger.error("Exception occured during retrieval of trades ",e);
			throw new JPMServiceException("Exception occured during retrieval of trades ",e);
		}
		return allTrades;
	}

}
