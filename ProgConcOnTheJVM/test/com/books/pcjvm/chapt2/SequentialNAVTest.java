package com.books.pcjvm.chapt2;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

public class SequentialNAVTest {

	SequentialNAV snav = new SequentialNAV();
	
	@Test
	public void testTimeAndComputeValue() throws IOException {
		
		Map<String, Integer> stocks = SequentialNAV.readTickers();
		double nav = snav.computeNetAssetValue(stocks);
		assertTrue(nav > 0d);
		System.out.println("Net Asset Value of portfolio: "+nav);
	}

}
