package com.books.pcjvm.chapt2;

import static org.junit.Assert.*;

import org.junit.Test;

public class AbstractPrimeFinderTest {

	AbstractPrimeFinderTestImpl primeFinder = new AbstractPrimeFinderTestImpl();
	
	@Test
	public void testIsPrime() {
		
		assertFalse(primeFinder.isPrime(-5));
		assertFalse(primeFinder.isPrime(0));
		assertFalse(primeFinder.isPrime(1));
		assertFalse(primeFinder.isPrime(4));
		assertFalse(primeFinder.isPrime(6));
		assertFalse(primeFinder.isPrime(8));
		assertFalse(primeFinder.isPrime(9));
		
		assertTrue(primeFinder.isPrime(997));
		assertTrue(primeFinder.isPrime(881));
		assertTrue(primeFinder.isPrime(757));
		assertTrue(primeFinder.isPrime(641));
		assertTrue(primeFinder.isPrime(521));
		assertTrue(primeFinder.isPrime(409));
		assertTrue(primeFinder.isPrime(293));
		assertTrue(primeFinder.isPrime(193));
		assertTrue(primeFinder.isPrime(101));
	}

	@Test
	public void testCountPrimesInRange() {
		
		assertTrue(primeFinder.countPrimesInRange(0, 10) == 4);
		assertTrue(primeFinder.countPrimesInRange(4, 10) == 2);
		assertTrue(primeFinder.countPrimesInRange(0, 100) == 25);
		assertTrue(primeFinder.countPrimesInRange(0, 1000) == 168);
	}	
	
	private static class AbstractPrimeFinderTestImpl extends AbstractPrimeFinder {

		@Override
		public long countPrimes(int number) {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}

}
