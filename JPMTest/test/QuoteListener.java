import java.util.concurrent.CountDownLatch;


public class QuoteListener implements IQuoteListener {
	
	private long msecWait = 0L;
	private final CountDownLatch doneSignal;

	public QuoteListener(final long msecWait, CountDownLatch doneSignal) {
		this.msecWait = msecWait;
		this.doneSignal = doneSignal;
	}

	public QuoteListener(final long msecWait) {
		this(msecWait, new CountDownLatch(1));
	}

	public QuoteListener() {
		this(0, new CountDownLatch(1)); 
	}

	@Override
	public void onQuote(String symbol, double price, double qty) {
		
		try {
			System.out.println("SYMBOL <" + symbol + ">, PRICE <" + price + ">, QTY " +qty +">, wait: " + msecWait);
			Thread.sleep(msecWait);
			doneSignal.countDown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}

}
