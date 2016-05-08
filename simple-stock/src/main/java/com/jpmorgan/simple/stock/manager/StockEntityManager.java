package com.jpmorgan.simple.stock.manager;

import java.util.List;
import java.util.Map;

import com.jpmorgan.exception.JPMDataException;
import com.jpmorgan.simple.stock.entity.Stock;
import com.jpmorgan.simple.stock.entity.Trade;

/**
 * The class to manage stock related entities. - Stock and Trade.
 * @author Apurva T
 *
 */
public interface StockEntityManager {

	/**
	 * The method records/adds a trade associated to a stock.
	 * 
	 * @param trade
	 *            The trade object to record.
	 * 
	 * @return true when the record is added successfully, else false.
	 * 
	 * @throws JPMDataException
	 *             A exception occurred during the operation.
	 */
	boolean recordTrade(Trade trade) throws JPMDataException;

	/**
	 * The method retrieves all the trades happened in the application.
	 * 
	 * @return The array list that contains all the trades in the Super Simple
	 *         Stocks application.
	 */
	List<Trade> getAllTrades() throws JPMDataException;

	/**
	 * 
	 * @param stockSymbol
	 * @return Stock object associated with the input stock symbol. 
	 */
	Stock getStock(String stockSymbol) throws JPMDataException;

	/**
	 * The method retrieves all the stocks added in the application.
	 * 
	 * @return the map that contains all the stocks supported by the Super
	 *         Simple Stocks application.
	 */
	Map<String, Stock> getAllStocks() throws JPMDataException;

	
	/**
	 * The method retrieves the trades associated with the input stock symbol.
	 * @param stockSymbol
	 * @return list of trades when the input is valid; else null.
	 * @throws JPMDataException
	 */
	List<Trade> getTrades(String stockSymbol) throws JPMDataException;

	/**
	 * The method adds a stock into the application.
	 * @param stock
	 * @return true if added successfully, else false.
	 * @throws JPMDataException
	 */
	Boolean addStock(Stock stock) throws JPMDataException;
	
	public void initialiseData(Map<String,Stock> stockMap, List<Trade> tradeList);

}
