package com.books.pcjvm.chapt2;

public class SequentialPrimeFinder extends AbstractPrimeFinder {

	@Override
	public long countPrimes(int number) {
		return countPrimesInRange(1, number);
	}

	public static void main(String[] args) throws NumberFormatException, InterruptedException {
		new SequentialPrimeFinder().timeAndCompute(Integer.parseInt(args[0]));
	}
}
