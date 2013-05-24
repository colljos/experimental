package com.jc.processor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TransferQueue;

import com.jc.markets.data.Tick;
import com.jc.vwap.VWAPCalculator;

public class VWAPCalculatorController extends Thread {

	private final TransferQueue<Tick> tickUpdateQ;
	private final ConcurrentHashMap<String, VWAPCalculator> calculators; 
	private volatile boolean shutdown = false;

	final int numCores = Runtime.getRuntime().availableProcessors();
	final double blockingCoefficient = 0.9;
	final int poolSize = (int) (numCores / (1 - blockingCoefficient));
	private ExecutorService executorPool = Executors.newFixedThreadPool(poolSize);	
	
	public VWAPCalculatorController(TransferQueue<Tick> tickUpdateQ, ConcurrentHashMap<String,VWAPCalculator> calculators) {
		this.tickUpdateQ = tickUpdateQ;
		this.calculators = calculators;
	}
	
	@Override
	public void run() {

		while(!shutdown ) {
			try {
				final Tick tick = tickUpdateQ.take();
				calculators.putIfAbsent(tick.getInstrument(), new VWAPCalculator(tick.getInstrument()));
				final VWAPCalculator calculator = calculators.get(tick.getInstrument());
				
				executorPool.submit(new Runnable() {
					@Override
					public void run() {
						double vwap = calculator.calculate(tick); 
						System.out.println(tick.getInstrument() + ": VWAP <" + vwap + ">, Last Price <" + tick.getPrice() +">");
					}});
			}
			catch (InterruptedException e) {
				System.err.println(e);
			}
		}
	}
	
	public void shutdown() {
		shutdown = true; 
	}
	
}
