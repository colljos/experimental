package com.java7developer.chapter11.listing_11_17;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import org.junit.Test;

public class TicketTest {

  @Test
  public void testTenPercentDiscount() {
    Price price = mock(Price.class);
    when(price.getInitialPrice()).thenReturn(new BigDecimal("10"));

    Ticket ticket = new Ticket(price, new BigDecimal("0.9"));
    assertEquals(9.0, ticket.getDiscountPrice().doubleValue(), 0.000001);
  }
}