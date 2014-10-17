package com.java7developer.chapter11.listing_11_6;

import java.math.BigDecimal;

public class Ticket {
	  public static final int BASIC_TICKET_PRICE = 30;

	  private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.9");
	 
	  private final BigDecimal price;
	  private final String clientName;
	  
	  public Ticket(String name, BigDecimal price) {
	    this.clientName = name;
	    this.price = price;
	  }
		
	  public Ticket(String name) {
		this.clientName = name;
	    price = new BigDecimal(BASIC_TICKET_PRICE);
	  }

	  public BigDecimal getPrice() {
	    return price;
	  }
	  
	  public BigDecimal getDiscountPrice() {
		return price.multiply(DISCOUNT_RATE);
	  }
	}