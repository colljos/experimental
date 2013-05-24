package com.jc.markets;


import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.jc.markets.data.Tick;

public class MarketTickerTest {

	private static final long SLOW = 1000;		// 1 secs
	private static final long FAST = 100; 		// 100 msecs
	private static final long VERYFAST = 10;	// 10 msecs
	private static final long SLEEPTIME = 10000; // 10 secs
	
	static private int count;
	
	@Before
	public void setUp() throws Exception {
		System.err.println("setUp");
	}

	@After
	public void tearDown() throws Exception {
		System.err.println("tearDown");
	}

	@AfterClass
	static public void tearDownAfterClass() throws Exception {
		System.err.println("tearDownAfterClass");
		MarketTicker.shutdown();
	}
	
	@Test
	public void testSlowMarketTicker() {
			
		System.err.println("SLOW ...");
		List<String> instrumentList = Arrays.asList("APPL", "MSFT", "ORCL");
		MarketTicker ticker = MarketTicker.create("BLOOMBERG", instrumentList, SLOW);
		ticker.init();
		
		count = 0;
		ticker.subscribe(new MarketTickListener() {
			@Override
			public void onMarketTick(Tick tick) {
				System.out.println(tick);
				count++;
			}});
		
		try {
			Thread.sleep(SLEEPTIME);
		} catch (InterruptedException e) {
		}
		
		assertEquals(SLEEPTIME/SLOW, count);
		ticker.stopTimer();
	}

	
	@Test
	public void testFastMarketTicker() {
			
		System.err.println("FAST ...");
		List<String> instrumentList = Arrays.asList("APPL", "MSFT", "ORCL", "AMZN", "PRGS");
		MarketTicker ticker = MarketTicker.create("BLOOMBERG", instrumentList, FAST);
		ticker.init();
		
		count = 0;
		ticker.subscribe(new MarketTickListener() {
			@Override
			public void onMarketTick(Tick tick) {
				System.out.println(tick);
				count++;
			}});
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
		
		assertEquals(SLEEPTIME/FAST, count);
		ticker.stopTimer();
	}
	
	@Test
	public void testVeryFastMarketTicker() {
			
		System.err.println("VERY FAST ...");
		List<String> instrumentList = Arrays.asList("APPL", "MSFT", "ORCL", "AMZN", "PRGS");
		MarketTicker ticker = MarketTicker.create("BLOOMBERG", instrumentList, VERYFAST);
		ticker.init();
		
		count = 0;
		ticker.subscribe(new MarketTickListener() {
			@Override
			public void onMarketTick(Tick tick) {
				System.out.println(tick);
				count++;
			}});
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
		
		assertEquals(SLEEPTIME/VERYFAST, count);
		ticker.stopTimer();
	}
}
