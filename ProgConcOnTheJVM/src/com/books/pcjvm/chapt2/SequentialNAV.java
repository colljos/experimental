package com.books.pcjvm.chapt2;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class SequentialNAV extends AbstractNAV {

	@Override
	public double computeNetAssetValue(Map<String, Integer> stocks) throws IOException {
		
		double nav = 0.0;
		for (String ticker : stocks.keySet()) {
			nav += stocks.get(ticker) * YahooFinance.getPrice(ticker);
		}
		
		return nav;
	}

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		new SequentialNAV().timeAndComputeValue();
	}
}
