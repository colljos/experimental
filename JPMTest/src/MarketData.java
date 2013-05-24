import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

public class MarketData implements IMarketData {
	
	public static final int MAX_SYMBOLS = 10000;
	public static final int MAX_SUBSCRIBERS_PER_SYMBOL = 16;	
	public static final int MAX_FEEDHANDLER_THREADS = 10;
	
	private Map<String, SymbolData> symbols = new ConcurrentHashMap<String, SymbolData>();

	// Singleton instance
	private static MarketData instance = new MarketData();
	
	private MarketData() {
	}

	public synchronized static MarketData getInstance() {
		return instance;
	}	
	
	@Override
	public void subscribe(String symbol, IQuoteListener listener) {
		
		if (!symbols.containsKey(symbol)) {
			throw new RuntimeException("Invalid symbol: " + symbol);
		}
		
		SymbolData symbolData = symbols.get(symbol);
		if (symbolData.countSubscribers() >= MAX_SUBSCRIBERS_PER_SYMBOL) {
			throw new RuntimeException("Reached MAX subscribers <" + symbolData.countSubscribers() + "> for symbol: " + symbol);
		}
		
		symbolData.addSubscriber(listener);
	}

	@Override
	public void unsubscribe(String symbol, IQuoteListener listener) {

		if (!symbols.containsKey(symbol)) {
			throw new RuntimeException("Invalid symbol: " + symbol);
		}

		SymbolData symbolData = symbols.get(symbol);
		if (!symbolData.containsSubscriber(listener)) {
			throw new RuntimeException("Listener <" + listener + "> not registered for symbol: " + symbol);
		}
		
		symbolData.removeSubscriber(listener);
		
	}

	private ExecutorService QUOTE_EXECUTOR = Executors.newFixedThreadPool(MAX_FEEDHANDLER_THREADS); 
	
	@Override
	public void onQuote(final String symbol, final double price, final double qty) {

		for(final IQuoteListener listener : symbols.get(symbol).getSubscribers()) {
			try {
				QUOTE_EXECUTOR.execute(new Runnable() {
					@Override
					public void run() {
						listener.onQuote(symbol, price, qty);
					}
				});
			}
			catch (RejectedExecutionException e) {
				e.printStackTrace();
			};
		}
		
	}

	/*
	 * 	Added public methods
	 */
	
	public int countSubscriptions(String symbol) {
		
		if (!symbols.containsKey(symbol)) {
			throw new RuntimeException("Invalid symbol: " + symbol);
		}
		
		return symbols.get(symbol).countSubscribers();
	}

	public boolean containsSubscription(String symbol, IQuoteListener listener) {

		if (!symbols.containsKey(symbol)) {
			throw new RuntimeException("Invalid symbol: " + symbol);
		}
		
		return symbols.get(symbol).containsSubscriber(listener);
	}

	public void addSymbol(String symbol) {
		
		if (symbols.size() >= MAX_SYMBOLS) {
			throw new RuntimeException("Maximum number of symbols exceeded [" + symbols.size() + "]"); 
		}
		symbols.put(symbol, new SymbolData());
	}

	public void removeSymbol(String symbol) {
		symbols.remove(symbol); 
	}
	
	public int countSymbols() {
		return symbols.size();
	}

	/*
	 * 	Feeder methods
	 */
	
	private static final long EXECUTOR_TERMINATION_TIMEOUT_MSECS = 1000;	
	private volatile boolean feed = true;

	private ExecutorService FEED_EXECUTOR = Executors.newFixedThreadPool(MAX_FEEDHANDLER_THREADS);  
	
	public synchronized void startFeeder() throws InterruptedException {
		
		try {
			FEED_EXECUTOR.execute(new Runnable() {
				@Override
				public void run() {					
					while(feed) {						

						final CountDownLatch latch = new CountDownLatch(symbols.keySet().size());
						
						for(final String symbol : symbols.keySet()) {
							
							try {
							FEED_EXECUTOR.execute(new Runnable() {
								@Override
									public void run() {
										for(Quote quote : symbols.get(symbol).getQuotes()) {
											onQuote(symbol, quote.getPrice(), quote.getQty());
										}
										symbols.get(symbol).flushQuotes();
										latch.countDown();	// ensure Quotes are flushed before re-entrance to loop
									}
								});
							}
							catch (RejectedExecutionException e) {};
						}
	
						try {
							latch.await();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}
		catch (RejectedExecutionException e) {};
	}

	public synchronized void stopFeeder() {
		try {
			FEED_EXECUTOR.shutdown();
			FEED_EXECUTOR.awaitTermination(EXECUTOR_TERMINATION_TIMEOUT_MSECS, TimeUnit.MILLISECONDS);
			QUOTE_EXECUTOR.shutdown();  
			QUOTE_EXECUTOR.awaitTermination(EXECUTOR_TERMINATION_TIMEOUT_MSECS, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			feed = false;
		}
	}
	
	public void feedSymbol(String symbol, double price, double qty) {
		if (!symbols.containsKey(symbol)) {
			throw new RuntimeException("Invalid symbol: " + symbol);
		}

		SymbolData symbolData = symbols.get(symbol);
		symbolData.addQuote(price, qty);	
	}
	
	public int countSymbolQuotes(String symbol) {
		if (!symbols.containsKey(symbol)) {
			throw new RuntimeException("Invalid symbol: " + symbol);
		}

		SymbolData symbolData = symbols.get(symbol);
		return symbolData.countQuotes();		
	}

	
	/*
	 * 	Data Holders / Helpers
	 */
	private class SymbolData {

		// Symbol subscriber set
		private Set<IQuoteListener> symbolSubs = Collections.synchronizedSet(new HashSet<IQuoteListener>());
		
		// Symbol quote queue
		private Queue<Quote> symbolQuotes = new ConcurrentLinkedQueue<Quote>();
		
		public int countSubscribers() {
			return symbolSubs.size();
		}

		public boolean containsSubscriber(IQuoteListener listener) {
			return symbolSubs.contains(listener);
		}

		public void addSubscriber(IQuoteListener listener) {
			symbolSubs.add(listener);			
		}

		public void removeSubscriber(IQuoteListener listener) {
			symbolSubs.remove(listener);
		}

		public Set<IQuoteListener> getSubscribers() {
			return symbolSubs;			
		}

		public void addQuote(double price, double qty) {			
			symbolQuotes.add(new Quote(price, qty));
		}

		public int countQuotes() {
			return symbolQuotes.size();
		}
		
		public void flushQuotes() {
			symbolQuotes.clear();			
		}
		
		public Queue<Quote> getQuotes() {
			return symbolQuotes;
		}

		public void reset() {
			symbolQuotes.clear();
			symbolSubs.clear();
		}
	}
	
	private class Quote {

		private final double price;
		private final double qty;

		public Quote(final double price, final double qty) {
			this.price = price;
			this.qty = qty;
		}
		
		public double getPrice() {
			return price;
		}
		
		public double getQty() {
			return qty;
		}
	}

	public void reset() {
				
		Set<Entry<String, SymbolData>> entries = symbols.entrySet();
		for (Entry<String, SymbolData> entry : entries) {
			entry.getValue().reset();
		}
		symbols.clear();
		
		FEED_EXECUTOR = Executors.newFixedThreadPool(MAX_FEEDHANDLER_THREADS); 
		QUOTE_EXECUTOR = Executors.newFixedThreadPool(MAX_FEEDHANDLER_THREADS);
		feed = true;
	}

}
