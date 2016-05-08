package com.jpmorgan.simple.stock.service;

import java.math.BigDecimal;
import java.util.List;

import com.jpmorgan.exception.JPMServiceException;
import com.jpmorgan.simple.stock.entity.Stock;
import com.jpmorgan.simple.stock.entity.Trade;


/**
 * Business Service to cater to the requirements of the application like calculating PE ratio,
 * Div Yield , etc for stocks and trades.  
 * @author Apurva T
 *
 */
public interface SimpleStockService {

	/**
	 * The method calculates the dividend yield for a given stock with the help of input price.
	 * 
	 * @param stockSymbol
	 * @param inputPrice
	 * 
	 * @return calculated dividend yield for the input stock , if all inputs are valid.
	 * For invalid inputs; null will be returned.
	 * 
	 * @throws Exception
	 *             A exception raised during the execution of the method due to
	 *             an error.
	 */
	public BigDecimal calculateDividendYield(String stockSymbol,String inputPrice) throws JPMServiceException;


	/**
	 * The method calculates PE ratio for the input stock with the help of input price String.
	 * @param stockSymbol
	 * @param inputPrice
	 * @return the calculated PE ratio if inputs are valid. Else null. 
	 * @throws Exception
	 */
	public BigDecimal calculatePERatio(String stockSymbol,String inputPrice) throws JPMServiceException;

	/**
	 * Record a trade in the Super Simple Stocks application.
	 * 
	 * @param trade
	 *            Trade object to record.
	 * 
	 * @return True, when the record is successful. Other case, False.
	 */
	public Boolean recordTrade(Trade trade) throws JPMServiceException;

	/**
	 * 
	 * 
	 * @param stockSymbol
	 * @param durationInMins
	 * @return
	 * @throws Exception
	 */
	public BigDecimal calculateStockPrice(String stockSymbol,int durationInMins) throws JPMServiceException;

	/**
	 * The method calculates the GBCE All Share Index using the geometric mean of prices
	 * for all stocks. where the stock's price is greater than zero. 
	 * @return calculated share index (geometric mean)
	 * @throws Exception
	 */
	public BigDecimal calculateGBCEAllShareIndex() throws JPMServiceException;

	/**
	 * The method retrieves stock associated with the input stock symbol. 
	 * @param stockSymbol
	 * @return the stock associated with the given stock symbol. Returns null, if not present or if the input is invalid.
	 * @throws JPMServiceException
	 */
	public Stock retrieveStock(String stockSymbol) throws JPMServiceException;

	/**
	 * The method retrieves list of trades for associated stock.
	 * @param stock
	 * @return the list of trade associated with the input stock. Returns empty list if no associated trade exist or if stock is null.
	 */
	public List<Trade> retrieveTrades(Stock stock) throws JPMServiceException;

}
