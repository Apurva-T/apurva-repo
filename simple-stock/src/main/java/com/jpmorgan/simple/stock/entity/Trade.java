package com.jpmorgan.simple.stock.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * Entity to represent a trade associated with a stock.
 * @author Apurva T
 *
 */
public class Trade {
	public Trade(Stock stock, TradeType type, Long sharesQuantity, BigDecimal price, LocalDateTime timeStamp) {
		super();
		this.stock = stock;
		this.type = type;
		this.sharesQuantity = sharesQuantity;
		this.price = price;
		this.timeStamp = timeStamp;
	}

	/**
	 * Timestamp when the trade happened.
	 */
	private LocalDateTime timeStamp = null;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Trade [timeStamp=");
		builder.append(timeStamp);
		builder.append(", stock=");
		builder.append(stock);
		builder.append(", tradeType=");
		builder.append(type);
		builder.append(", sharesQuantity=");
		builder.append(sharesQuantity);
		builder.append(", price=");
		builder.append(price);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Associated stock.
	 */
	private Stock stock = null;

	/**
	 * Type of trade - Buy or sell.
	 */
	private TradeType type = TradeType.BUY;

	/**
	 * Quantity of shares traded.
	 */
	private Long sharesQuantity = 0L;

	/**
	 * The price at which shares are traded.
	 */
	private BigDecimal price = BigDecimal.ZERO;

	
	public Trade() {
	}

	
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Long getSharesQuantity() {
		return sharesQuantity;
	}

	public void setSharesQuantity(Long sharesQuantity) {
		this.sharesQuantity = sharesQuantity;
	}

	public TradeType getType() {
		return type;
	}

	public void setType(TradeType type) {
		this.type = type;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}
}

