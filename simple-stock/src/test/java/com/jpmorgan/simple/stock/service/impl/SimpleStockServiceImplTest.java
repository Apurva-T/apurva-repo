/**
 * 
 */
package com.jpmorgan.simple.stock.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jpmorgan.exception.JPMServiceException;
import com.jpmorgan.simple.stock.entity.Stock;
import com.jpmorgan.simple.stock.entity.Trade;
import com.jpmorgan.simple.stock.entity.TradeBuilder;
import com.jpmorgan.simple.stock.manager.SimpleStockDataLoader;
import com.jpmorgan.simple.stock.manager.StockEntityManager;
import com.jpmorgan.simple.stock.service.SimpleStockService;


/**
 * Test class to test Simple Stock Application.
 * @author Apurva T
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-simple-stock-context.xml")
public class SimpleStockServiceImplTest {

	private String[] stockSymbols = null;
	
	@Autowired
	SimpleStockService simpleStockService;
	
	@Autowired
	StockEntityManager stockEntityManager;
	
	@Autowired
	SimpleStockDataLoader stockDataLoader;
	
	@Before
	public void createStockSymbolArr() {
		stockSymbols = new String[] {"GIN","JOE","ALE","TEA","POP"};
		stockDataLoader.initialiseData();   // Load Data In-memory in Data Loader.
		stockEntityManager.initialiseData(stockDataLoader.getStockMap(), stockDataLoader.getTradeList());  // Copy data from loader to manager.  
		
	}
	
	
	/**
	 * Test method for {@link com.jpmorgan.simple.stock.service.impl.SimpleStockServiceImpl#calculateDividendYield(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void calculateDividendYield()  {
		String tickerPrice = "109";
		try {
			BigDecimal divYield;
			for(String stockSymbol : stockSymbols) {
				divYield = BigDecimal.ZERO;
				divYield = simpleStockService.calculateDividendYield(stockSymbol, tickerPrice) ;
				System.out.println("Dividend Yield for "+ stockSymbol+" with Price : "+tickerPrice+" is "+divYield);
				assertNotEquals("DIV Yield Test UnSuccessful",BigDecimal.ZERO,divYield);
				System.out.println("Div Yield test Successful \n");
			}
			
		}catch(JPMServiceException e) {
			e.printStackTrace();
			fail("Exception !!");
		}
		
	}
	
	/**
	 * Test method for {@link com.jpmorgan.simple.stock.service.impl.SimpleStockServiceImpl#calculateDividendYield(java.lang.String, java.lang.String)}.
	 */
	@Test (expected = JPMServiceException.class)
	public void calcDivYieldZeroPrice() throws JPMServiceException {
		String tickerPrice = "0";
			BigDecimal divYield;
			for(String stockSymbol : stockSymbols) {
				divYield = BigDecimal.ZERO;
				divYield = simpleStockService.calculateDividendYield(stockSymbol, tickerPrice) ;
				System.out.println("Dividend Yield for "+ stockSymbol+" with Price : "+tickerPrice+" is "+divYield);
				assertEquals("DIV Yield Ratio Test Unsuccessful",BigDecimal.ZERO,divYield);
			}
			
		
	}

	/**
	 * Test method for {@link com.jpmorgan.simple.stock.service.impl.SimpleStockServiceImpl#calculatePERatio(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testCalculatePERatio() {
		String tickerPrice = "482";
		try {
			List<BigDecimal> actualPeRatios = new ArrayList<BigDecimal>();
			List<BigDecimal> expectedPeRatios = new ArrayList<BigDecimal>();
			expectedPeRatios.add(new BigDecimal("60.25"));
			expectedPeRatios.add(new BigDecimal("37.08"));
			expectedPeRatios.add(new BigDecimal("20.96"));
			expectedPeRatios.add(BigDecimal.ZERO);
			expectedPeRatios.add(new BigDecimal("60.25"));
			
			BigDecimal peRatio;
			for(String stockSymbol : stockSymbols) {
				peRatio = BigDecimal.ZERO;
				peRatio = simpleStockService.calculatePERatio(stockSymbol, tickerPrice) ;
				actualPeRatios.add(peRatio);
				System.out.println("PE Ratio for "+ stockSymbol+" with Price : "+tickerPrice+" is "+peRatio);
				
			}
			
			if("482".equals(tickerPrice))
				assertEquals("PE Ratio Test UnSuccessful",actualPeRatios,expectedPeRatios);
			else {
				assertNotEquals(BigDecimal.ZERO,expectedPeRatios.get(0));
			}
			
			System.out.println("PE Ratio test Successful \n");
		}catch(JPMServiceException e) {
			e.printStackTrace();
			fail("Exception in PE Ratio test !!");
		}
	}

	/**
	 * Test method for {@link com.jpmorgan.simple.stock.service.impl.SimpleStockServiceImpl#recordTrade(com.jpmorgan.simple.stock.entity.Trade)}.
	 */
	@Test
	public void testRecordTrade() throws JPMServiceException {
		String stockSymbol = "JOE";
		Stock stock = simpleStockService.retrieveStock(stockSymbol);
		List<Trade> tradeList = simpleStockService.retrieveTrades(stock);
		System.out.println("For stock symbol "+stockSymbol+", Existing trade list size "+tradeList.size());
		Trade tradeToAdd = new TradeBuilder().build(stock);
		boolean isAdded = simpleStockService.recordTrade(tradeToAdd);
		List<Trade> updatedTradeList = simpleStockService.retrieveTrades(stock);
		System.out.println("For stock symbol "+stockSymbol+", Updated trade list size "+updatedTradeList.size());
		assertEquals(tradeList.size()+1, updatedTradeList.size());
		System.out.println("Successfully recorded trade. \n");
	}

	/**
	 * Test method for {@link com.jpmorgan.simple.stock.service.impl.SimpleStockServiceImpl#calculateStockPrice(java.lang.String, int)}.
	 */
	@Test
	public void testCalculateStockPrice() {
		try{
			BigDecimal stockPrice ;
			int pastMins = 15;
			for(String stockSymbol : stockSymbols) {
				stockPrice = simpleStockService.calculateStockPrice(stockSymbol, pastMins);
				
				if("ALE".equals(stockSymbol) && pastMins==15) {
					assertEquals(BigDecimal.ZERO, stockPrice);  // Expected 0 as ALE was not traded in past 15 mins.
				}else {
					assertNotEquals(BigDecimal.ZERO, stockPrice);
				}
				System.out.println("Stock Price for stock symbol : "+stockSymbol+ " for the past "+pastMins+" mins is --> "+stockPrice );
				
			}
			System.out.println("Stock Price test Successful \n");
		}catch(JPMServiceException e) {
			e.printStackTrace();
			fail("Exception in Stock Price Calc test!!");
		}
	}

	/**
	 * Test method for {@link com.jpmorgan.simple.stock.service.impl.SimpleStockServiceImpl#calculateGBCEAllShareIndex()}.
	 */
	@Test
	public void testCalculateGBCEAllShareIndex() {
		try {
			BigDecimal sharesIndx = simpleStockService.calculateGBCEAllShareIndex();
			assertNotEquals(BigDecimal.ZERO, sharesIndx);
			System.out.println("Share Index "+sharesIndx);
			System.out.println("Shares Indx test Successful \n");
		}catch(JPMServiceException e) {
			e.printStackTrace();
			fail("Exception in Stock Price Calc test!!");
}
	}

}
