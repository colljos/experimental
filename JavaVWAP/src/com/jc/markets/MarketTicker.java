package com.jc.markets;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.jc.markets.data.Tick;

public class MarketTicker {

	private static final ScheduledExecutorService tickTimer = Executors.newScheduledThreadPool(10);
	private List<MarketTickListener> tickListeners = new ArrayList<MarketTickListener>();
	private ScheduledFuture<?> tickTask; 
	
	private final String marketName; 
	private final List<String> instrumentList;
	private final long initialDelay;
	private final long intervalDelay;
	
	private MarketTicker(String marketName, List<String> instrumentList, long initialDelay, long intervalDelay) {	
		this.marketName = marketName;
		this.instrumentList = instrumentList;
		this.initialDelay = initialDelay;
		this.intervalDelay = intervalDelay;
	}
	
	public static MarketTicker create(String marketName, List<String> instrumentList, long intervalDelay) {
		
		final MarketTicker marketTicker = new MarketTicker(marketName, instrumentList, intervalDelay, intervalDelay);
		return marketTicker;
	}
	
	public synchronized void init() {

		tickTask = tickTimer.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				generateTick();
			}
		}, initialDelay, intervalDelay, TimeUnit.MILLISECONDS);
	}
	
	public synchronized void stopTimer() {
		tickTask.cancel(true);
	}

	public synchronized static void shutdown() {
		tickTimer.shutdown();
	}

	private void generateTick() {
		
		Random random = new Random();
	
		int index = random.nextInt(instrumentList.size());
		long quantity = Math.abs((random.nextInt(10)+1) * 10000);
		double price = random.nextFloat() * 100;
		
		Tick tick = new Tick(marketName, instrumentList.get(index), price, quantity);
		
		for (MarketTickListener tickListener : tickListeners) {
			tickListener.onMarketTick(tick);
		}
	}

	public synchronized void subscribe(MarketTickListener subsriber) {
		tickListeners.add(subsriber);
	}

	public synchronized void unsubscribe(MarketTickListener subsriber) {
		tickListeners.remove(subsriber);
	}	
}
