package com.java7developer.chapter11.listing_11_10;

import org.junit.Test;
import java.math.BigDecimal;
import static org.junit.Assert.*;

public class TicketTest {
  
  @Test
  public void testTenPercentDiscount() {
    Price price = new StubPrice();
    Ticket ticket = new Ticket(price, new BigDecimal("0.9"));
    assertEquals(9.0, ticket.getDiscountPrice().doubleValue(), 0.0001);
  }
}