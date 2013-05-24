package uk.co.cmcmarkets.orderbook.iface;

import java.util.List;

public interface OrderProducer {

	// asynchronous
	void startProcessing();
	void finishProcessing();
	
	// synchronous
	List<Command> pull();
}
