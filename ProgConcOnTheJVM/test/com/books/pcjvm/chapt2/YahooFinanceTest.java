package com.books.pcjvm.chapt2;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class YahooFinanceTest {

	@Test
	public void testGetPrice() throws IOException {
		
		double currentPrice = YahooFinance.getPrice("AMZN");
		assertTrue(currentPrice > 0d);
		System.out.println("AMZN price: "+currentPrice);
	}

}
