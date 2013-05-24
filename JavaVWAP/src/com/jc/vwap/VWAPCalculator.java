package com.jc.vwap;

import com.jc.markets.data.Tick;

public class VWAPCalculator {

	private double vwapTickTotal;
	private long vwapQuantityTotal;
	private final String instrument;
	private long tickCount = 0;

	public VWAPCalculator(final String instrument) {
		this.instrument = instrument;
	}

	synchronized public double calculate(Tick tick) {
		
		double vwapTick = tick.getPrice() * tick.getQuantity();
		
		// LOCK-ACQUIRE (for finer-grained synchronization)
		vwapTickTotal += vwapTick;
		vwapQuantityTotal += tick.getQuantity();
		tickCount++;
		// LOCK-RELEASE (for finer-grained synchronization)
		
		System.out.println("Calculator processing: " + tick + ", Statistics: " + toString());
		return vwapTickTotal/vwapQuantityTotal;
	}

	public String getInstrument() {
		return instrument;
	}
	
	public String toString() {
		return "[tickCount <" + tickCount + ">, totalTick <" + vwapTickTotal + ">, totalQuantity <" + vwapQuantityTotal + "]";
	}


}
