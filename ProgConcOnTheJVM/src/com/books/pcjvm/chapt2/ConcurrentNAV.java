package com.books.pcjvm.chapt2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ConcurrentNAV extends AbstractNAV {

	@Override
	public double computeNetAssetValue(final Map<String, Integer> stocks) throws IOException, InterruptedException, ExecutionException {
		
		final int numberOfCores = Runtime.getRuntime().availableProcessors();
		final double blockingCoefficient = 0.9;
		final int poolSize = (int) (numberOfCores / (1 - blockingCoefficient));
		
		System.out.println("Number of Core available is: "+numberOfCores);
		System.out.println("Pool size is "+poolSize);
		
		final List<Callable<Double>> partitions = new ArrayList<Callable<Double>>();

		for(final String ticker: stocks.keySet()) {
			partitions.add(new Callable<Double>() {
				@Override
				public Double call() throws Exception {
					return stocks.get(ticker) * YahooFinance.getPrice(ticker);
				}
			});
		}
		
		final ExecutorService executorPool = Executors.newFixedThreadPool(poolSize);
		List<Future<Double>> valueOfStocks = executorPool.invokeAll(partitions, 10000, TimeUnit.SECONDS);
		
		double nav = 0.0;
		for (Future<Double> valueOfStock : valueOfStocks) {
			nav += valueOfStock.get();
		}
		
		executorPool.shutdown();
		return nav;
	}

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		new ConcurrentNAV().timeAndComputeValue();
	}
}
