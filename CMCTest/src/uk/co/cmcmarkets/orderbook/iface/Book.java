package uk.co.cmcmarkets.orderbook.iface;

import uk.co.cmcmarkets.orderbook.book.OrderBookException;

public interface Book {
	
	void add(Order order) throws OrderBookException;

	void edit(Order order) throws OrderBookException;

	void remove(Order order) throws OrderBookException;

	void write(Log log);

	int orderCount();

	Order getOrder(long orderId);

	void attach(BookView bookView);

	void detach(BookView bookView);

}
