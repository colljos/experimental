package com.books.pcjvm.chapt2;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;

import org.junit.Test;

public class SequentialPrimeFinderTest {

	 SequentialPrimeFinder primeFinder = new  SequentialPrimeFinder();
	
	@Test
	public void testCountPrimes() throws InterruptedException, ExecutionException {

		assertTrue(primeFinder.countPrimes(10) == 4);
		assertTrue(primeFinder.countPrimes(100) == 25);
		assertTrue(primeFinder.countPrimes(1000) == 168);
	}

}
