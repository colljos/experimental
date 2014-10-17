package com.java7developer.chapter11.listing_11_5;

import org.junit.Test;
import java.math.BigDecimal;

import org.junit.Test;

import static org.junit.Assert.*;

public class TicketTest {

  BigDecimal expectedRevenue;
  
  @Test
  public void testTenPercentDiscount() {
    String dummyName = "Riley";
    Ticket ticket = new Ticket(dummyName, new BigDecimal("10"));
    assertEquals(new BigDecimal("9.0"), ticket.getDiscountPrice());
  }

}