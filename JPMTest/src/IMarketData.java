/*
 * Assumptions:
 * - Max number of subscribers per symbol: 16 
 * - Number of symbols: 10000 
 * - Price Feed handler threads: 10 
 * - Subscriber threads: 16 Subscriber’s 
 * - onQuote() callback can take >100ms (a long time)
 */

public interface IMarketData {

	// Subscribe / unsubscribe functions. Can be called from the different threads at any moment.
    void subscribe(String symbol, IQuoteListener listener);
    void unsubscribe(String symbol, IQuoteListener listener);

    // This function gets called by Price feed handler. It can be called from the different threads.
    // The implementation should notify all subscribers for that symbol
    void onQuote(String symbol, double price, double qty);

}
