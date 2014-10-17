package com.java7developer.chapter11.listing_11_9;

import java.math.BigDecimal;

public class HttpPrice implements Price {

  @Override
  public BigDecimal getInitialPrice() {
    return HttpPricingService.getInitialPrice();
  }

  private static class HttpPricingService {
      static BigDecimal getInitialPrice() {return null;}
  }
}