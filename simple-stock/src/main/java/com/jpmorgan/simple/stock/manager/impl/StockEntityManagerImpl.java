package com.jpmorgan.simple.stock.manager.impl;

import static com.jpmorgan.utils.CommonUtils.isNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.jpmorgan.exception.JPMDataException;
import com.jpmorgan.simple.stock.entity.Stock;
import com.jpmorgan.simple.stock.entity.Trade;
import com.jpmorgan.simple.stock.manager.SimpleStockDataLoader;
import com.jpmorgan.simple.stock.manager.StockEntityManager;

@Service("stockEntityManager")
/**
 * The class to manage stock related entities. - Stock and Trade.
 * @author Apurva T
 *
 */
public class StockEntityManagerImpl implements StockEntityManager {

	private Logger logger = LoggerFactory.getLogger(StockEntityManagerImpl.class);
	
	private Map<String, Stock> stocks = null;

	
	private List<Trade> trades = null;

	
	public StockEntityManagerImpl(){
		trades = new ArrayList<Trade>();
		stocks = new HashMap<String, Stock>();
	}
	
	public void initialiseData(Map<String,Stock> stocksMap,List<Trade> tradeList) {
		stocks = stocksMap;
		trades = tradeList;
	}

	@Override
	public Map<String, Stock> getAllStocks() {
		return stocks;
	}


	public void setStocks(Map<String, Stock> stocks) {
		this.stocks = stocks;
	}

	@Override
	public List<Trade> getAllTrades() {
		return trades;
	}
	
	@Override
	public List<Trade> getTrades(final String stockSymbol) throws JPMDataException{
	
		List<Trade> tradeList = null;
		try{
			if(isNotNull(stockSymbol)){
				Stock stock = stocks.get(stockSymbol); // Just to check whether we have stock existing or not. 
//				 Else we wont stream and filter trades.
				if(isNotNull(stock)) {
					tradeList = trades.stream().filter(t -> t.getStock().getSymbol().equals(stockSymbol)).collect(Collectors.toList());
				}
			}
			else{
				logger.error("Not able to retrieve stock. Invalid input stock symbol.");
			}
		}catch(Exception exception){
				logger.error("An error has occurred recovering the stock object for the stock symbol: "+stockSymbol+".", exception);
				throw new JPMDataException("An error has occurred recovering the stock object for the stock symbol: "+stockSymbol+".", exception);
		}
		
		return tradeList;
	}

	
	public void setTrades(List<Trade> trades) {
		this.trades = trades;
	}

	@Override
	public boolean recordTrade(Trade trade) throws JPMDataException{
		boolean result = false;
		try{
			result = trades.add(trade);
		}catch(Exception exception){
			throw new JPMDataException("An error has occurred recording a trade in the system's backend.", exception);
		}
		return result;
	}

	
	public int getTradesNumber() {
		return trades.size();
	}

	@Override
	public Stock getStock(String stockSymbol) throws JPMDataException {
		Stock stock = null;
		try{
			if(isNotNull(stockSymbol)){
				stock = stocks.get(stockSymbol);
			}
			else{
				logger.error("Not able to retrieve stock. Invalid input stock symbol.");
			}
		}catch(Exception exception){
				//logger.error("An error has occurred recovering the stock object for the stock symbol: "+stockSymbol+".", exception);
				throw new JPMDataException("An error has occurred recovering the stock object for the stock symbol: "+stockSymbol+".", exception);
		}
		
		return stock;
	}
	
	@Override
	public Boolean addStock(Stock stock) throws JPMDataException {
		Boolean isAdded = false;
		try{
			if(isNotNull(stock)){
				 if(!stocks.containsKey(stock.getSymbol())) {
					 stocks.put(stock.getSymbol(),stock);
				 }else {
					 logger.error("Not able to add stock with symbol {}. Its already present", stock.getSymbol());
				 }
			}
			else{
				logger.error("Stock not valid ");
			}
		}catch(Exception exception){
				logger.error("An error has occurred during addition operation ",exception);
				throw new JPMDataException("An error has occurred during addition operation ",exception);
		}
		
		return isAdded;
	}

}
