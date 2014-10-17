package com.java7developer.chapter11.listing_11_1;

import java.math.BigDecimal;
import org.junit.*;
import static org.junit.Assert.*;

public class BigDecimalTest {

  BigDecimal x;
    
  @Before
  public void setUp() { x = new BigDecimal("1.5"); }
    
  @After
  public void tearDown() { x = null; }
    
  @Test
  public void testAddingTwoBigDecimals() {
    assertEquals(new BigDecimal("3.0"), x.add(x));
  }
    
  @Test(expected=NumberFormatException.class)
  public void NumberFormatExceptionIfNotANumber() {
    x = new BigDecimal("Not a number");
  }

}