import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class MarketDataTest {

	final private MarketData md = MarketData.getInstance();

	private static final int MAX_PRICES = 100;
		
	@Test
	public void testSubscribeUnsubscribe() {
	
		md.addSymbol("ABC");
		
		IQuoteListener listener = new QuoteListener();
		md.subscribe("ABC", listener); 
		
		assertEquals(md.countSubscriptions("ABC"), 1);
		assertTrue(md.containsSubscription("ABC", listener));	

		md.unsubscribe("ABC", listener);
		
		assertEquals(md.countSubscriptions("ABC"), 0);
		assertFalse(md.containsSubscription("ABC", listener));
		
		md.removeSymbol("ABC");
	}

	// Boundary tests
	@Test
	public void testMaxSymbols() {
		
		for (int i = 0; i < MarketData.MAX_SYMBOLS+1; i++) {
			try {
				md.addSymbol("ABC" + i);
			}
			catch (RuntimeException e) {
				System.out.println("Max Symbols: " + md.countSymbols() + " [" + i + "]");
				assertEquals(md.countSymbols(), MarketData.MAX_SYMBOLS);				
			}
		}
		assertEquals( MarketData.MAX_SYMBOLS, md.countSymbols());				
		
		// Clean-up
		for (int i = 0; i < MarketData.MAX_SYMBOLS; i++) {
			md.removeSymbol("ABC" + i);
		}
		assertEquals(0, md.countSymbols());				
	}
	
	@Test
	public void testMaxSubscribersPerSymbol() {

		md.addSymbol("CDE");
		
		for (int i = 1; i < MarketData.MAX_SUBSCRIBERS_PER_SYMBOL+1; i++) {
			try {
				md.subscribe("CDE", new IQuoteListener() {
					@Override
					public void onQuote(String symbol, double price, double qty) {
						// TODO Auto-generated method stub
					}
				});
			}
			catch (RuntimeException e) {
				System.out.println("Max Subscriptions: " + md.countSubscriptions("CDE") + " [" + i + "]");
				assertEquals(MarketData.MAX_SUBSCRIBERS_PER_SYMBOL, md.countSubscriptions("CDE"));				
			}
		}
		assertEquals(MarketData.MAX_SUBSCRIBERS_PER_SYMBOL, md.countSubscriptions("CDE"));				
	}
	
	/* 
	 *  Price Quotes
	 */
	@Test
	public void testOnQuoteDirect() throws InterruptedException {

		final CountDownLatch latch = new CountDownLatch(1);

		md.reset();
		md.addSymbol("BCD");
		
		md.subscribe("BCD", new IQuoteListener() {	
			@Override
			public void onQuote(String symbol, double price, double qty) {
				
				System.out.println("Symbol <" + symbol + ">, price <" + price + ">, qty <" + qty + ">");
				assertEquals("BCD", symbol);
				assertEquals(123, price, 0.1);
				assertEquals(1000, qty, 0.1);
				latch.countDown();
			}
		}); 

		md.onQuote("BCD", 123, 1000);
		
		System.out.println("LatchCount: " + latch.getCount());
		assertTrue(latch.await(1000, TimeUnit.MILLISECONDS));
	}

	@Test
	public void testOnQuoteDirectManyPrices() throws InterruptedException {

		final CountDownLatch latch = new CountDownLatch(MAX_PRICES);
		final AtomicInteger counter = new AtomicInteger(0);
		
		md.reset();
		md.addSymbol("BCD");
		
		md.subscribe("BCD", new IQuoteListener() {	
			@Override
			public void onQuote(String symbol, double price, double qty) {
				
				System.out.println("Symbol <" + symbol + ">, price <" + price + ">, qty <" + qty + ">");
				counter.incrementAndGet(); 
				latch.countDown();
			}
		}); 

		for (int i = 0; i < MAX_PRICES; i++) {
			md.onQuote("BCD", i, 1000+i);			
		}
		
		System.out.println("LatchCount: " + latch.getCount());
		assertTrue(latch.await(1000, TimeUnit.MILLISECONDS));
		assertEquals(MAX_PRICES, counter.intValue());
	}
	
	@Test
	public void testOnQuoteViaFeeder() throws InterruptedException {

		final CountDownLatch latch = new CountDownLatch(1);
		
		md.reset();
		md.addSymbol("XYZ");

		Thread subscriber = new Thread() {
			public void run() {
				md.subscribe("XYZ", new IQuoteListener() {	
					@Override
					public void onQuote(String symbol, double price, double qty) {
						
						System.out.println("Symbol <" + symbol + ">, price <" + price + ">, qty <" + qty + ">");
						assertEquals("XYZ", symbol);
						assertEquals(123, price, 0.1);
						assertEquals(2222, qty, 0.1);
						latch.countDown();
					}
				}); 
			}
		};
		subscriber.start();
		
		md.feedSymbol("XYZ", 123, 2222);
		assertTrue(md.countSymbolQuotes("XYZ") > 0);
		
		md.startFeeder();
		assertTrue(md.countSymbolQuotes("XYZ") > 0);

		try {
			System.out.println("LatchCount: " + latch.getCount());
			assertTrue(latch.await(1000, TimeUnit.MILLISECONDS));
		}
		finally {
			md.stopFeeder();
			md.removeSymbol("XYZ");
		}
	}

	@Test
	public void testOnQuoteViaFeederManyPrices() throws InterruptedException {

		final CountDownLatch latch = new CountDownLatch(MAX_PRICES);
		final AtomicInteger counter = new AtomicInteger(0);

		md.reset();
		md.addSymbol("BCD");
		
		md.subscribe("BCD", new IQuoteListener() {	
			@Override
			public void onQuote(String symbol, double price, double qty) {
				
				System.out.println("Symbol <" + symbol + ">, price <" + price + ">, qty <" + qty + ">");
				counter.incrementAndGet(); 
				latch.countDown();
			}
		}); 

		for (int i = 0; i < MAX_PRICES; i++) {
			md.feedSymbol("BCD", i, 2000+i);			
		}

		md.startFeeder();
		
		try {
			System.out.println("LatchCount: " + latch.getCount());
			assertTrue(latch.await(10000, TimeUnit.MILLISECONDS));
			assertEquals(MAX_PRICES, counter.intValue());
		}
		finally {
			md.stopFeeder();
			md.removeSymbol("BCD");
		}
	}
	
}
