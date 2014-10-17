package com.java7developer.chapter11.listing_11_7;

import org.junit.Test;
import java.math.BigDecimal;
import static org.junit.Assert.*;

public class TicketTest {

  @Test
  public void testTenPercentDiscount() {
    Price price = new HttpPrice();
    Ticket ticket = new Ticket(price);
    assertEquals(new BigDecimal("9.0"), ticket.getDiscountPrice());
  }
}