package uk.co.cmcmarkets.orderbook.book;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import uk.co.cmcmarkets.orderbook.iface.Book;
import uk.co.cmcmarkets.orderbook.iface.Log;
import uk.co.cmcmarkets.orderbook.iface.LogLevel;
import uk.co.cmcmarkets.orderbook.iface.Order;

public class BookImplTest {

	private Book book;
	
	private final Order orderNew = new Order(1L, "MSFT.L", true, 5, 200);
	private final Order orderAmend = new Order(1L, null, true, 7, 300);
	private final Order orderAmendAgain = new Order(1L, null, false, 9, 500);	// buySell should relate to original 
	private final Order orderAmendNonExistent = new Order(2L, null, true, 7, 200);
	private final Order orderDelete = new Order(1L, null, true, -1, -1);
	private final Order orderDeleteNonExistent = new Order(3L, null, true, -1, -1);

	@Before
	public void setUp() throws Exception {
		
		book = new BookImpl();		
	}

	@Test
	public void testAdd() throws Exception {
		
		book.add(orderNew);
		assertEquals(1, book.orderCount());				
	}

	@Test(expected=OrderBookException.class)
	public void testAddDuplicate() throws Exception {

		try {
			book.add(orderNew);
			book.add(orderNew);			
		}
		catch (OrderBookException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	@Test
	public void testEdit() throws Exception {
		
		book.add(orderNew);
		book.edit(orderAmend);
		
		assertEquals(1, book.orderCount());
		assertEquals(7, book.getOrder(1L).getPrice());
		assertEquals(300, book.getOrder(1L).getQuantity());
	}
	
	@Test
	public void testEditTwice() throws Exception {
		
		book.add(orderNew);
		book.edit(orderAmend);
		book.edit(orderAmendAgain);
		
		assertEquals(1, book.orderCount());
		assertEquals("MSFT.L", book.getOrder(1L).getSymbol());
		assertEquals(true, book.getOrder(1L).isBuy());
		assertEquals(9, book.getOrder(1L).getPrice());
		assertEquals(500, book.getOrder(1L).getQuantity());
	}

	@Test(expected=OrderBookException.class)
	public void testEditNonExistent() throws Exception {

		try {
			book.add(orderNew);
			book.edit(orderAmendNonExistent);			
		}
		catch (OrderBookException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@Test
	public void testRemove() throws Exception {

		book.add(orderNew);
		book.remove(orderDelete);
		assertEquals(0, book.orderCount());
	}

	@Test(expected=OrderBookException.class)
	public void testRemoveNonExistent() throws Exception {

		try {
			book.add(orderNew);
			book.remove(orderDeleteNonExistent);			
		}
		catch (OrderBookException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@Test
	public void testWrite() throws Exception {
		
		book.add(new Order(1L, "MSFT.L", true, 5, 200));
		book.add(new Order(2L, "VOD.L", true, 15, 100));
		book.add(new Order(3L, "MSFT.L", false, 5, 300));
		book.add(new Order(4L, "MSFT.L", true, 7, 150));
		book.remove(new Order(1L, null, true, -1, -1));
		book.add(new Order(5L, "VOD.L", false, 17, 300));
		book.add(new Order(6L, "VOD.L", true, 12, 150));
		book.edit(new Order( 3L, null, true, 7, 200));
		book.add(new Order(7L, "VOD.L", false, 16, 100));
		book.add(new Order(8L, "VOD.L", false, 19, 100));
		book.add(new Order(9L, "VOD.L", false, 21, 112));
		book.remove(new Order(5L, null, false, -1, -1));
		
		book.write(new Log() {
			public void log(LogLevel logLevel, String msg) {
				System.out.println(msg);
			}});

		assertEquals(7, book.orderCount());
	}

}
