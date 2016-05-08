package com.jpmorgan.simple.stock.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.jpmorgan.simple.stock.entity.Stock;
import com.jpmorgan.simple.stock.entity.StockType;
import com.jpmorgan.simple.stock.entity.Trade;
import com.jpmorgan.simple.stock.entity.TradeType;
import com.jpmorgan.utils.DateUtils;

/**
 * The data loader class for loading sample data for this application. 
 * Please note that this is required for testing this application only. 
 * In real-life, individual entity's dao will be used.
 *
 * 
 * @author Apurva T
 *
 */
@Service("simpleStockDataLoader")
public class SimpleStockDataLoader {

	private List<Trade> tradeList;
	private Map<String,Stock> stockMap;
	
	public List<Trade> getTradeList() {
		return tradeList;
	}

	public Map<String, Stock> getStockMap() {
		return stockMap;
	}

	@PostConstruct
	public void initialiseData() {
		createStocks();
		createTrades();		
	}


	private void createStocks() {
		
		stockMap = new HashMap<String,Stock>(10);
		
		Stock stock = new Stock("TEA", StockType.COMMON,BigDecimal.ZERO,BigDecimal.ZERO,new BigDecimal("100"));
		stock.setPrice(new BigDecimal("43"));
		stockMap.put("TEA",stock);
		
		stock = new Stock("POP", StockType.COMMON,new BigDecimal("8"),BigDecimal.ZERO,new BigDecimal("100"));
		stock.setPrice(new BigDecimal("110"));
		stockMap.put("POP",stock);
		
		stock = new Stock("ALE", StockType.COMMON,new BigDecimal("23"),BigDecimal.ZERO,new BigDecimal("60"));
		stock.setPrice(new BigDecimal("39"));
		stockMap.put("ALE",stock);
		
		stock = new Stock("GIN", StockType.PREFERRED,new BigDecimal("8"),new BigDecimal("2"),new BigDecimal("100"));
		stock.setPrice(new BigDecimal("540"));
		stockMap.put("GIN",stock);
		
		stock = new Stock("JOE", StockType.COMMON,new BigDecimal("13"),BigDecimal.ZERO,new BigDecimal("250"));
		stock.setPrice(new BigDecimal("290"));
		stockMap.put("JOE",stock);
		
	}
	
	
	private void createTrades() {
		
		tradeList = new ArrayList<Trade>(10);
		
		Trade trade = new Trade(stockMap.get("TEA"),TradeType.BUY,25L,new BigDecimal(71),DateUtils.getCurrentDateTime().minusMinutes(10L));
		tradeList.add(trade);
		
		trade = new Trade(stockMap.get("POP"),TradeType.BUY,125L,new BigDecimal(156),DateUtils.getCurrentDateTime().minusMinutes(9L));
		tradeList.add(trade);
		
		trade = new Trade(stockMap.get("POP"),TradeType.BUY,2222L,new BigDecimal(349),DateUtils.getCurrentDateTime());
		tradeList.add(trade);
		
		trade = new Trade(stockMap.get("ALE"),TradeType.BUY,890L,new BigDecimal(22),DateUtils.getCurrentDateTime().minusMinutes(20L));
		tradeList.add(trade);
		
		trade = new Trade(stockMap.get("JOE"),TradeType.BUY,12133L,new BigDecimal(457),DateUtils.getCurrentDateTime().minusMinutes(8L));
		tradeList.add(trade);
		
		trade = new Trade(stockMap.get("GIN"),TradeType.BUY,7109309L,new BigDecimal(787),DateUtils.getCurrentDateTime().minusMinutes(1L));
		tradeList.add(trade);
		
		trade = new Trade(stockMap.get("GIN"),TradeType.BUY,678L,new BigDecimal(32),DateUtils.getCurrentDateTime().minusMinutes(7L));
		tradeList.add(trade);
	}
	
}
