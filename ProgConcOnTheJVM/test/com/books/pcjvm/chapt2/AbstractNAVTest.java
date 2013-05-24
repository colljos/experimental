package com.books.pcjvm.chapt2;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

public class AbstractNAVTest {
	
	@Test
	public void testReadTickers() throws IOException {
		
		Map<String, Integer> tickerList = AbstractNAV.readTickers();
		assertNotNull(tickerList);
		assertTrue(tickerList.size() > 0);
		
		System.out.println("Total Tickers: "+tickerList.size());
		System.out.println(tickerList);
		
	}

}
