package uk.co.cmcmarkets.orderbook.book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import uk.co.cmcmarkets.orderbook.iface.Book;
import uk.co.cmcmarkets.orderbook.iface.BookView;
import uk.co.cmcmarkets.orderbook.iface.Log;
import uk.co.cmcmarkets.orderbook.iface.LogLevel;
import uk.co.cmcmarkets.orderbook.iface.Order;

public class BookImpl implements Book {

	private Map<Long, Order> book = new HashMap<Long, Order>();
	private List<BookView> views = new ArrayList<BookView>();
	
	@Override
	public void add(Order order) throws OrderBookException {
		
		// validate input
		if (book.containsKey(order.getOrderId())) {
			throw new OrderBookException("ADD: Order [" + order.getOrderId() + "] already in order book");
		}
		
		// add order
		book.put(order.getOrderId(), order);
		
		// notify views
		for (BookView view : views) {
			view.add(order);
		}
	}

	@Override
	public void edit(Order order) throws OrderBookException {

		// locate original order
		if (!book.containsKey(order.getOrderId())) {
			throw new OrderBookException("EDIT: Invalid Order Id [" + order.getOrderId() + "]");	
		}
		
		Order origOrder = book.get(order.getOrderId());	
		
		// replace original order with amend
		Order amendedOrder = new Order(order.getOrderId(), origOrder.getSymbol(), origOrder.isBuy(), order.getPrice(), order.getQuantity());
		book.put(order.getOrderId(), amendedOrder);	

		// notify views
		for (BookView view : views) {
			view.edit(order, origOrder);
		}
	}

	@Override
	public void remove(Order order) throws OrderBookException {

		// locate original order
		if (!book.containsKey(order.getOrderId())) {
			throw new OrderBookException("REMOVE: Invalid Order Id [" + order.getOrderId() + "]");	
		}
		Order origOrder = book.get(order.getOrderId());
		
		// remove order
		book.remove(order.getOrderId());
		
		// notify views
		for (BookView view : views) {
			view.remove(origOrder);
		}
	}

	@Override
	public void write(Log log) {

		log.log(LogLevel.INFO, "ORDER BOOK");
		log.log(LogLevel.INFO, "=================================================");
		Set<Entry<Long, Order>> entries = book.entrySet();
		for (Entry<Long, Order> entry : entries) {
			log.log(LogLevel.INFO, entry.getKey() + ", " + entry.getValue());
		}
		
		log.log(LogLevel.INFO, "BOOK VIEWS");
		log.log(LogLevel.INFO, "=================================================");
		for (BookView bookView : views) {
			bookView.write(log);
		}
	}

	@Override
	public int orderCount() {
		return book.size();
	}

	@Override
	public Order getOrder(long orderId) {
		return book.get(orderId);
	}
	
	/*
	 * 	Observer for Book Views
	 */
	public void attach(BookView bookView) {
		if (!views.contains(bookView))
			views.add(bookView);
	}

	public void detach(BookView bookView) {
		if (views.contains(bookView))
			views.remove(bookView);
	}

	
}
