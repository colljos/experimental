package com.books.pcjvm.chapt2;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

public class ConcurrentNAVTest {

	ConcurrentNAV cnav = new ConcurrentNAV();
	
	@Test
	public void testTimeAndComputeValue() throws IOException, InterruptedException, ExecutionException {
		
		Map<String, Integer> stocks = ConcurrentNAV.readTickers();
		double nav = cnav.computeNetAssetValue(stocks);
		assertTrue(nav > 0d);
		System.out.println("Net Asset Value of portfolio: "+nav);
	}

}
