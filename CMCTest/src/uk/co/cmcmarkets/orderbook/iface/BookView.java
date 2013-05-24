package uk.co.cmcmarkets.orderbook.iface;

public interface BookView {

	void add(Order order);

	void edit(Order order, Order origOrder);

	void remove(Order order);

	void write(Log log);

	int countOrders();
}
