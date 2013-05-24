package com.jc.processor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

import com.jc.markets.MarketTickListener;
import com.jc.markets.data.Tick;

public class MarketTickProcessor implements MarketTickListener {

	private TransferQueue<Tick> updates; 
	private final ExecutorService dispatchThread = Executors.newSingleThreadExecutor();
	
	public MarketTickProcessor(TransferQueue<Tick> updateQ) {
		this.updates = updateQ; 
	}

	@Override
	public void onMarketTick(final Tick tick) {
		
		dispatchThread.submit(new Runnable() {
			@Override
			public void run() {
				try {
					updates.tryTransfer(tick, 1000, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					System.err.println(e);
				}
			}});
	}
}
