package com.jpmorgan.simple.stock.entity;

import java.math.BigDecimal;

/**
 * Entity to represent Stock.
 * @author Apurva T.
 *
 */
public class Stock  {
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Stock stockSymbol=");
		builder.append(symbol);
		builder.append(", stockType=");
		builder.append(type);
		builder.append(", lastDividend=");
		builder.append(lastDividend);
		builder.append(", fixedDividend=");
		builder.append(fixedDividend);
		builder.append(", parValue=");
		builder.append(parValue);
		builder.append(", price=");
		builder.append(price);
		builder.append("]");
		return builder.toString();
	}

	
	/* The stock symbol.
	 */
	private String symbol = null;
	
	/*
	 * The type of Stock - either common or preferred.
	 */
	private StockType type = StockType.COMMON;
	
	/*
	 * The last dividend paid. 
	 */
	private BigDecimal lastDividend = BigDecimal.ZERO;
	
	/*
	 * Fixed dividend - for preferred stock.
	 */
	private BigDecimal fixedDividend = BigDecimal.ZERO;
	
	/*
	 * Par value.
	 */
	private BigDecimal parValue = BigDecimal.ZERO;
		
	/*
	 * Current Price.
	 */
	private BigDecimal price = BigDecimal.ZERO;
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Stock(){
		
	}

	public Stock(String symbol, StockType type, BigDecimal lastDividend, BigDecimal fixedDividend,
			BigDecimal parValue) {
		super();
		this.symbol = symbol;
		this.type = type;
		this.lastDividend = lastDividend;
		this.fixedDividend = fixedDividend;
		this.parValue = parValue;
	}

	/**
	 * 
	 * @return
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * 
	 * @param symbol
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * 
	 * @return
	 */
	public StockType getType() {
		return type;
	}

	/**
	 * 
	 * @param type
	 */
	public void setType(StockType type) {
		this.type = type;
	}

	/**
	 * 
	 * @return
	 */
	public BigDecimal getLastDividend() {
		return lastDividend;
	}

	/**
	 * 
	 * @param lastDividend
	 */
	public void setLastDividend(BigDecimal lastDividend) {
		this.lastDividend = lastDividend;
	}

	/**
	 * 
	 * @return
	 */
	public BigDecimal getFixedDividend() {
		return fixedDividend;
	}

	/**
	 * 
	 * @param fixedDividend
	 */
	public void setFixedDividend(BigDecimal fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	/**
	 * 
	 * @return
	 */
	public BigDecimal getParValue() {
		return parValue;
	}

	/**
	 * 
	 * @param parValue
	 */
	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}
	
	@Override
    public int hashCode() {
        int hash = 256;
        if (this.symbol != null) {
            hash *= this.symbol.hashCode();
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Stock)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        Stock stock = (Stock) obj;
        boolean equals = true;
        if (this.symbol != null) {
            equals &= this.symbol.equals(stock.getSymbol());
        }
      
        return equals;
    }



}
