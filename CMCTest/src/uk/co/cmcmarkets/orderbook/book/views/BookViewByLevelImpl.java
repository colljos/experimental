package uk.co.cmcmarkets.orderbook.book.views;

import java.util.Map;

import uk.co.cmcmarkets.orderbook.iface.BookView;
import uk.co.cmcmarkets.orderbook.iface.Log;
import uk.co.cmcmarkets.orderbook.iface.Order;

public class BookViewByLevelImpl implements BookView {

	private BookSide bookSideBids;
	private BookSide bookSideAsks;

	public BookViewByLevelImpl() {

		bookSideAsks = new BookSide();
		bookSideBids = new BookSide();
	}

	@Override
	public void add(Order order) {

		BookSide bookSide = getBookSide(order.isBuy());

		// get Instrument entries
		Map<Integer, BookLevelEntry> instrumentEntries = bookSide
				.getBookEntries(order.getSymbol());

		// create Instrument entry if none
		if (instrumentEntries == null) {
			instrumentEntries = bookSide.createBookEntry(order.getSymbol());
		}

		// get Price entries (for Instrument)
		BookLevelEntry priceEntry = instrumentEntries.get(order.getPrice());

		// create price entry if none
		if (priceEntry == null) {
			BookLevelEntry entry = new BookLevelEntry(order.getPrice(), order
					.getQuantity(), 1);
			instrumentEntries.put(order.getPrice(), entry);
		}
		// increment Size & order Count
		else {
			priceEntry.update(order.getQuantity());
		}
	}

	@Override
	public void edit(Order order, Order origOrder) {

		BookSide bookSide = getBookSide(origOrder.isBuy());

		Map<Integer, BookLevelEntry> instrEntries = bookSide.getBookEntries(origOrder.getSymbol());
		// same price ?
		if (order.getPrice() == origOrder.getPrice()) {
			BookLevelEntry priceEntry = instrEntries.get(order.getPrice());
			priceEntry.amend(order.getQuantity(), origOrder.getQuantity());
		} else {
			// locate old price entry
			BookLevelEntry priceEntry = instrEntries.get(origOrder.getPrice());
			priceEntry.remove(origOrder.getQuantity());
			if (priceEntry.getCount() == 0)
				instrEntries.remove(origOrder.getPrice());

			// locate new price entry
			BookLevelEntry newPriceEntry = instrEntries.get(order.getPrice());
			if (newPriceEntry == null) {
				BookLevelEntry entry = new BookLevelEntry(order.getPrice(),	order.getQuantity(), 1);
				instrEntries.put(order.getPrice(), entry);
			}
			// increment Size & order Count
			else {
				newPriceEntry.update(order.getQuantity());
			}
		}
	}

	@Override
	public void remove(Order origOrder) {

		BookSide bookSide = getBookSide(origOrder.isBuy());

		Map<Integer, BookLevelEntry> instrumentEntries = bookSide
				.getBookEntries(origOrder.getSymbol());
		BookLevelEntry priceEntry = instrumentEntries.get(origOrder.getPrice());
		priceEntry.remove(origOrder.getQuantity());

		// purge any entries?
		if (priceEntry.getCount() == 0)
			instrumentEntries.remove(origOrder.getPrice());

		if (instrumentEntries.size() == 0)
			bookSide.removeBookEntry(origOrder.getSymbol());
	}

	private BookSide getBookSide(boolean buy) {
		if (buy)
			return bookSideBids;
		else
			return bookSideAsks;
	}

	@Override
	public void write(Log log) {

		// Bid Entries
		bookSideBids.write(log, true);

		// Ask Entries
		bookSideAsks.write(log, false);
	}

	@Override
	public int countOrders() {
		return bookSideAsks.count() + bookSideBids.count();
	}

}
