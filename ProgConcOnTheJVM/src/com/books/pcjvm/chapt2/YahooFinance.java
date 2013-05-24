package com.books.pcjvm.chapt2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class YahooFinance {

	public static double getPrice(String ticker) throws IOException {

		final URL url = new URL("http://ichart.finance.yahoo.com/table.csv?s=" + ticker);
		final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		
		// Date, Open, High, Low, Close, Volume, Adj Close
		//final String discardHeader = 
				reader.readLine();
		final String data = reader.readLine();
		final String[] dataItems = data.split(",");
		final double price = Double.valueOf(dataItems[dataItems.length - 1]);
		
		return price;
	}

}
