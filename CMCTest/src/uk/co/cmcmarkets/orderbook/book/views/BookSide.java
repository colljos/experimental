package uk.co.cmcmarkets.orderbook.book.views;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import uk.co.cmcmarkets.orderbook.iface.Log;
import uk.co.cmcmarkets.orderbook.iface.LogLevel;

public class BookSide {

	private Map<String, Map<Integer, BookLevelEntry>> bookSide;
	
	public BookSide() {
		bookSide = new HashMap<String, Map<Integer,BookLevelEntry>>();
	}

	public Map<Integer, BookLevelEntry> getBookEntries(String symbol) {
		return bookSide.get(symbol);
	}

	public Map<Integer, BookLevelEntry> createBookEntry(String symbol) {
		bookSide.put(symbol, new ConcurrentSkipListMap<Integer, BookLevelEntry>());
		return bookSide.get(symbol);
	}

	public void removeBookEntry(String symbol) {
		bookSide.remove(symbol); 
	}

	public void write(Log log, boolean isBidSide) {
		
		if (count() <= 0)
			return;
		
		if (isBidSide)
			log.log(LogLevel.INFO, "BID");
		else 
			log.log(LogLevel.INFO, "ASK");
		log.log(LogLevel.INFO, "=================================================");
						
		Set<Entry<String, Map<Integer, BookLevelEntry>>> bidEntries = bookSide.entrySet();
		for (Entry<String, Map<Integer, BookLevelEntry>> instrumentEntry : bidEntries) {
			String key = instrumentEntry.getKey();
			log.log(LogLevel.INFO, "Instrument: " + key);
			log.log(LogLevel.INFO, "=================================================");
			
			ConcurrentNavigableMap<Integer, BookLevelEntry> priceEntry = (ConcurrentNavigableMap<Integer, BookLevelEntry>) instrumentEntry.getValue();
			Set<Entry<Integer, BookLevelEntry>> priceEntries;
			// BID: natural ascending order
			if (isBidSide) {	
				priceEntries = priceEntry.entrySet();				
			}
			// ASK: natural descending order
			else {				
				ConcurrentNavigableMap<Integer, BookLevelEntry> descPriceEntries = priceEntry.descendingMap();
				priceEntries = descPriceEntries.entrySet();				
			}
			for (Entry<Integer, BookLevelEntry> entry : priceEntries) {
				BookLevelEntry bookEntry = entry.getValue();
				log.log(LogLevel.INFO, (isBidSide?"Bid ":"Ask ") + bookEntry.toString());
			}
		}		

	}

	public int count() {
		return bookSide.size();
	}	
}
