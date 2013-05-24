package com.books.pcjvm.chapt2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ConcurrentPrimeFinder extends AbstractPrimeFinder {

	private final int poolSize;
	private final int numberOfParts;

	public ConcurrentPrimeFinder(final int thePoolSize,
			final int theNumberOfParts) {
		this.poolSize = thePoolSize;
		this.numberOfParts = theNumberOfParts;
	}

	@Override
	public long countPrimes(int number) {

		int count = 0;

		try {
			final List<Callable<Integer>> partitions = new ArrayList<Callable<Integer>>();
			final int chunksPerPartition = number / numberOfParts;
			System.out.println("Numbers <" + number + ">, numberOfParts <" + numberOfParts + "> chunksPerPartition <"+ chunksPerPartition +">");

			for (int i = 0; i < numberOfParts; i++) {
				final int lower = (i * chunksPerPartition) + 1;
				final int upper = (i == numberOfParts - 1) ? number : lower
						+ chunksPerPartition - 1;

				System.out.println("Chunking: lower <" + lower + ">, upper <" + upper + ">");
				partitions.add(new Callable<Integer>() {
					@Override
					public Integer call() throws Exception {
						return countPrimesInRange(lower, upper);
					}
				});
			}

			final ExecutorService executorPool = Executors
					.newFixedThreadPool(poolSize);
			List<Future<Integer>> resultFromParts = executorPool.invokeAll(
					partitions, 10000, TimeUnit.SECONDS);
			executorPool.shutdown();

			for (Future<Integer> result : resultFromParts) {
				count += result.get();
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		return count;
	}
	
	public static void main(String[] args) {
		if (args.length < 3) 
			System.out.println("Usage: number poolsize numberOfParts");
		else
			new ConcurrentPrimeFinder(Integer.parseInt(args[1]), Integer.parseInt(args[2])).timeAndCompute(Integer.parseInt(args[0]));
	}
}
