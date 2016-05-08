package com.jpmorgan.simple.stock.calculator;

import static com.jpmorgan.utils.CommonUtils.isNotNull;
import static com.jpmorgan.utils.CommonUtils.isZeroVal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.commons.math3.stat.StatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jpmorgan.exception.JPMServiceException;
import com.jpmorgan.simple.stock.entity.Stock;
import com.jpmorgan.simple.stock.entity.Trade;

/**
 * The Concrete Calculator class to calculate Dividend Yield, P/E ratio, GM and Volume Weighted Stock Price. 
 * @author Apurva T
 *
 */
@Service("stockCalculator")
public class StockCalculator {
	
	private static Integer SCALE = 2;
	private static RoundingMode ROUND_HALF_EVEN=RoundingMode.HALF_EVEN;
	
	private Logger logger = LoggerFactory.getLogger(StockCalculator.class);

	
	/**
	 * The method calculates dividend yield for the input stock. The calculation is based upon the stock type; 
	 * whether its common or preferred type.
	 * @param stock
	 * @return calculated dividend yield. Zero if input is not valid.
	 */
	public  BigDecimal calculateDivYield(Stock stock) throws JPMServiceException{
		
		BigDecimal divYield = BigDecimal.ZERO;
		
		if(isNotNull(stock) && isNotNull(stock.getPrice())) {
		
			if(BigDecimal.ZERO.compareTo(stock.getPrice()) != 0) {
				
				switch (stock.getType()){
					
					case COMMON : divYield = calcCommonDivYield(stock.getLastDividend(), stock.getPrice());
									break;
					
					case PREFERRED : divYield = calcPrefDivYield (stock.getFixedDividend(),stock.getParValue(),stock.getPrice());
									break;
						
				}
			}else {
				logger.error("Ticker Price is Zero. Cant calculate Div Yield");
				throw new JPMServiceException("Ticker Price is Zero. Cant calculate Div Yield" );
			}
		}else {
			logger.error("Stock not valid. Cant calculate Div Yield");
			throw new JPMServiceException("Stock not valid. Cant calculate Div Yield");
		}
		return divYield; 
	}
	
	
	/**
	 * The method calculates Dividend Yield for Common Stock type by using formula; Dividend Yield = Last Dividend / Price.
	 * @param lastDividend
	 * @param price
	 * @return calculated Dividend Yield
	 */
	private  BigDecimal calcCommonDivYield(BigDecimal lastDividend, BigDecimal price) {
		
		BigDecimal divYield = lastDividend.divide(price, SCALE, ROUND_HALF_EVEN);
		
		return divYield;
	}
	
	
	/**
	 * The method calculates Dividend Yield for Preferred Stock type by using formula; Dividend Yield = Fixed Dividend * Par Value / Price.
	 * @param fixedDividend
	 * @param parValue
	 * @param price
	 * @return calculated Dividend Yield
	 */
	private  BigDecimal calcPrefDivYield(BigDecimal fixedDividend, BigDecimal parValue, BigDecimal price) {
		
		BigDecimal divYield = (fixedDividend.multiply(parValue)).divide(price, SCALE, ROUND_HALF_EVEN);
		
		return divYield;
	}

	/**
	 * The method calculates PE ratio for the given input stock using below formula :
	 * 		PE Ratio = Price/Dividend.
	 * 
	 *  @return calculated PE Ratio. For invalid values; returns 0.
	 */
	public  BigDecimal calculatePERatio(Stock stock){
			
		BigDecimal peRatio = BigDecimal.ZERO;
		
		if(isNotNull(stock) && isNotNull(stock.getLastDividend()) && isNotNull(stock.getPrice()) ) {
			if(!isZeroVal(stock.getLastDividend())) {
				peRatio = stock.getPrice().divide(stock.getLastDividend(), SCALE,ROUND_HALF_EVEN);
			}
		}
		
		return peRatio; 
	}
	
	
	/** 
	 * The method calculates geometric mean of the input stocks prices using below formula ;
	 * 				GM = nth root of prices of stocks..
	 * @param stocks
	 * @return Geometric mean for valid input; else ZERO.
	 */
	public  BigDecimal calculateGeoMetricMean(List<Stock> stocks){
		
		BigDecimal geometricMean = BigDecimal.ZERO;
		double calcGM = 0.0d;
		
		if (isNotNull(stocks) ) {
			double[] stockPrices = new double[stocks.size()];
			int i =0;
			for(Stock stock : stocks) {
				stockPrices[i++] = stock.getPrice().doubleValue();
			}
			calcGM = StatUtils.geometricMean(stockPrices);
			geometricMean = new BigDecimal(calcGM).setScale(SCALE, ROUND_HALF_EVEN);;
		}
		return geometricMean; 
	}
	
	
	/**
	 * The method calculates Stock Prices for the input trade list using below formula;
	 * 		SumAll (Trades Prices * Trades Qty)/ Sum of All trade Qty.
	 * @param trades
	 * @return calculated stock price. Returns Zero for invalid input.
	 */
	public  BigDecimal calculateStockPrice(List<Trade> trades){
		
		BigDecimal stockPrice = BigDecimal.ZERO;
		
		long totalTradeQty = 0L;
		BigDecimal sumOfQuantities = BigDecimal.ZERO;
		BigDecimal summation = BigDecimal.ZERO;
		
		
		if (isNotNull(trades) ) {
			for(Trade trade : trades) {
				totalTradeQty +=trade.getSharesQuantity();
				summation = summation.add((trade.getPrice().multiply(new BigDecimal(trade.getSharesQuantity()))));
			}
			sumOfQuantities = new BigDecimal(totalTradeQty);
			
			stockPrice = summation.divide(sumOfQuantities, SCALE, ROUND_HALF_EVEN);
		}
		
		
		return stockPrice; 
	}
		
}
