package com.jpmorgan.simple.stock.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * The data builder class for entity Trade.
 * @author Apurva T
 *
 */
public class TradeBuilder {

	private Stock stock = null;

	private TradeType tradeType = TradeType.SELL;

	private Long sharesQuantity = 12L;

	private BigDecimal price = new BigDecimal("10");
	
	private LocalDateTime dateTime = LocalDateTime.now();
	
	public Trade build(Stock stock) {
		this.stock = stock;
		return new Trade(this.stock,this.tradeType,this.sharesQuantity,this.price,dateTime);
	}
	
	
	public TradeBuilder withTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
		return this;
	}
	
	public TradeBuilder withSharesQty(long sharesQty) {
		this.sharesQuantity = sharesQty;
		return this;
	}
	
	public TradeBuilder withPrice(BigDecimal price) {
		this.price = price;
		return this;
	}
	
	public TradeBuilder withTime(LocalDateTime time) {
		this.dateTime = time;
		return this;
	}
}
