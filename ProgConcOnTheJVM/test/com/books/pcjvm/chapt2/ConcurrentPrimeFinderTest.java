package com.books.pcjvm.chapt2;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConcurrentPrimeFinderTest {

	ConcurrentPrimeFinder primeFinder = new ConcurrentPrimeFinder(2,2);
	
	@Test
	public void testCountPrimes() {

		assertTrue(primeFinder.countPrimes(10) == 4);
		assertTrue(primeFinder.countPrimes(100) == 25);
		assertTrue(primeFinder.countPrimes(1000) == 168);
	}

}
