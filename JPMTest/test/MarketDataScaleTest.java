import static org.junit.Assert.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MarketDataScaleTest {

	final private MarketData md = MarketData.getInstance();

	private static final int MAX_PRICES = 100;
	private static final int MAX_TEST_SYMBOLS = 100;
	private static final int MAX_SUBSCRIBER_TRHEADS = 16;
			
	private ExecutorService pool; 
	
	@Before
	public void setUp() throws Exception {
		pool = Executors.newFixedThreadPool(MAX_SUBSCRIBER_TRHEADS);
	}
	
	@After
	public void tearDown() throws Exception {
		pool.shutdown();
		pool.awaitTermination(5000, TimeUnit.SECONDS);
	}

	@Test
	public void testOnQuoteDirectManyPricesMaxSubscribers() throws InterruptedException {

		final CountDownLatch startSignal = new CountDownLatch(MAX_SUBSCRIBER_TRHEADS); 
		final CountDownLatch doneSignal = new CountDownLatch(MAX_PRICES * MAX_SUBSCRIBER_TRHEADS); 

		md.reset();
		md.addSymbol("BCD");
		
		try {
			for (int i = 0; i < MAX_SUBSCRIBER_TRHEADS; i++) {
				pool.execute(new Subscriber(0, startSignal, doneSignal, "BCD"));
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		startSignal.await();
		
		for (int i = 0; i < MAX_PRICES; i++) {
			md.onQuote("BCD", i, 1000+i);			
		}
		
		try {
			assertTrue(doneSignal.await(10000, TimeUnit.MILLISECONDS));
		}
		finally {
			md.removeSymbol("BCD");
		}
	}

	@Test
	public void testOnQuoteDirectManyPricesMaxSubscribersMaxSymbols() throws InterruptedException {

		final CountDownLatch startSignal = new CountDownLatch(MAX_SUBSCRIBER_TRHEADS); 
		final CountDownLatch doneSignal = new CountDownLatch(MAX_SUBSCRIBER_TRHEADS *
																MAX_PRICES *  
																MAX_TEST_SYMBOLS);
		md.reset();

		String[] symbolArray = new String[MAX_TEST_SYMBOLS];
		try {
			for (int i = 0; i < MAX_TEST_SYMBOLS; i++) {
				md.addSymbol("ABC" + i);
				symbolArray[i] = "ABC" + i;
			}
			
			for (int j = 0; j < MAX_SUBSCRIBER_TRHEADS; j++) {			
				pool.execute(new Subscriber(0, startSignal, doneSignal, symbolArray));
			}
			
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		startSignal.await();
		
		for (int i = 0; i < MAX_PRICES; i++) {
			for (int j = 0; j < MAX_TEST_SYMBOLS; j++) {
				md.onQuote("ABC" + j, i, 1000+i);
			}
		}
		
		try {
			assertTrue(doneSignal.await(10000, TimeUnit.MILLISECONDS));
		}
		finally {
			for (int i = 1; i < MAX_TEST_SYMBOLS; i++) {
				md.removeSymbol("ABC" + i);
			}
		}
	}
	
	@Test
	public void testOnQuoteDirectSlowSubscribers() throws InterruptedException {
		
		final CountDownLatch startSignal = new CountDownLatch(MAX_SUBSCRIBER_TRHEADS); 
		final CountDownLatch doneSignal = new CountDownLatch(MAX_SUBSCRIBER_TRHEADS *
																10 * 10);
		md.reset();

		String[] symbolArray = new String[10];
		try {
			for (int i = 0; i < 10; i++) {
				md.addSymbol("ABC" + i);
				symbolArray[i] = "ABC" + i;
			}
			
			int subscriberPauseRateMsecs = 10;
			for (int j = 0; j < MAX_SUBSCRIBER_TRHEADS; j++) {			
				pool.execute(new Subscriber(subscriberPauseRateMsecs * j, startSignal, doneSignal, symbolArray));
			}
			
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		startSignal.await();
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				md.onQuote("ABC" + j, i, 1000+i);
			}
		}
		
		try {
			assertTrue(doneSignal.await(30000, TimeUnit.MILLISECONDS));
		}
		finally {
			for (int i = 1; i < 10; i++) {
				md.removeSymbol("ABC" + i);
			}
		}
	}
	
	
	@Test
	public void testOnQuoteViaFeederManyPricesMaxSubscribers() throws InterruptedException {

		final CountDownLatch startSignal = new CountDownLatch(MAX_SUBSCRIBER_TRHEADS); 
		final CountDownLatch doneSignal = new CountDownLatch(MAX_PRICES * MAX_SUBSCRIBER_TRHEADS); 

		md.reset();
		md.addSymbol("BCD");
		
		try {
			for (int i = 0; i < MarketData.MAX_SUBSCRIBERS_PER_SYMBOL; i++) {
				pool.execute(new Subscriber(0, startSignal, doneSignal, "BCD"));
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		startSignal.await();

		for (int i = 0; i < MAX_PRICES; i++) {
			md.feedSymbol("BCD", i, 1000+i);			
		}
		
		md.startFeeder();

		try {
			assertTrue(doneSignal.await(10000, TimeUnit.MILLISECONDS));
		}
		finally {
			md.stopFeeder();
			md.removeSymbol("BCD");
		}
	}

	@Test
	public void testOnQuoteViaFeederManyPricesMaxSubscribersMaxSymbols() throws InterruptedException {

		final CountDownLatch startSignal = new CountDownLatch(MAX_SUBSCRIBER_TRHEADS); 
		final CountDownLatch doneSignal = new CountDownLatch(MAX_SUBSCRIBER_TRHEADS *
																MAX_PRICES *  
																MAX_TEST_SYMBOLS);
		md.reset();

		String[] symbolArray = new String[MAX_TEST_SYMBOLS];
		try {
			for (int i = 0; i < MAX_TEST_SYMBOLS; i++) {
				md.addSymbol("ABC" + i);
				symbolArray[i] = "ABC" + i;
			}
			
			for (int j = 0; j < MAX_SUBSCRIBER_TRHEADS; j++) {			
				pool.execute(new Subscriber(0, startSignal, doneSignal, symbolArray));
			}
			
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		startSignal.await();
		
		for (int i = 0; i < MAX_PRICES; i++) {
			for (int j = 0; j < MAX_TEST_SYMBOLS; j++) {
				md.feedSymbol("ABC" + j, i, 1000+i);
			}
		}
		
		md.startFeeder();

		try {
			assertTrue(doneSignal.await(10000, TimeUnit.MILLISECONDS));
		}
		finally {
			md.stopFeeder();
			for (int i = 1; i < MAX_TEST_SYMBOLS; i++) {
				md.removeSymbol("ABC" + i);
			}		
		}
	}
	
	@Test
	public void testOnQuoteViaFeederSlowSubscribers() throws InterruptedException {
		
		final CountDownLatch startSignal = new CountDownLatch(MAX_SUBSCRIBER_TRHEADS); 
		final CountDownLatch doneSignal = new CountDownLatch(MAX_SUBSCRIBER_TRHEADS *
																10 * 10);
		md.reset();

		String[] symbolArray = new String[10];
		try {
			for (int i = 0; i < 10; i++) {
				md.addSymbol("ABC" + i);
				symbolArray[i] = "ABC" + i;
			}
			
			int subscriberPauseRateMsecs = 10;
			for (int j = 0; j < MAX_SUBSCRIBER_TRHEADS; j++) {			
				pool.execute(new Subscriber(subscriberPauseRateMsecs * j, startSignal, doneSignal, symbolArray));
			}
			
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		startSignal.await();
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				md.feedSymbol("ABC" + j, i, 1000+i);
			}
		}
		
		md.startFeeder();
		
		try {
			assertTrue(doneSignal.await(30000, TimeUnit.MILLISECONDS));
		}
		finally {
			md.stopFeeder();
			for (int i = 1; i < 10; i++) {
				md.removeSymbol("ABC" + i);
			}
		}
	}

	public class Subscriber implements Runnable {
		
		private final CountDownLatch doneSignal;
		private final long msecWait;
		private final String[] symbols;
		private final CountDownLatch startSignal;
				
		public Subscriber(final long msecWait, CountDownLatch startSignal, CountDownLatch doneSignal, final String... symbols) {
			this.symbols = symbols;
			this.msecWait = msecWait;
			this.doneSignal = doneSignal;
			this.startSignal = startSignal;
		}

		@Override
		public void run() {
			for (int i = 0; i < symbols.length; i++) {
				System.out.println("Adding subscriber for Symbol <" + symbols[i] + ">, msecWait <" + msecWait + ">, startLatchCount <" + startSignal.getCount() +">, doneLatchCount <" + doneSignal.getCount() + ">");
				md.subscribe(symbols[i], new QuoteListener(msecWait, doneSignal));				
			}
			startSignal.countDown();
		}
	}
}
