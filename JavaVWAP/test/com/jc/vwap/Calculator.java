package com.jc.vwap;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.jc.markets.data.Tick;

public class Calculator {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testCalculateVWAP() {
	
		VWAPCalculator calc = new VWAPCalculator("BBVA");		
		double vwap = calc.calculate(new Tick("BLOOMBERG", "BBVA", 25, 100));
		assertEquals(25, vwap, 0.01d);
		
		vwap = calc.calculate(new Tick("BLOOMBERG", "BBVA", 50, 100));
		assertEquals(37.5, vwap, 0.01d);

		vwap = calc.calculate(new Tick("BLOOMBERG", "BBVA", 25, 100));
		assertEquals(33.33, vwap, 0.01d);
	}
}
