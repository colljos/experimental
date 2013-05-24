package uk.co.cmcmarkets.orderbook.book.views;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import uk.co.cmcmarkets.orderbook.iface.BookView;
import uk.co.cmcmarkets.orderbook.iface.Log;
import uk.co.cmcmarkets.orderbook.iface.LogLevel;
import uk.co.cmcmarkets.orderbook.iface.Order;

public class BookViewByLevelImplTest {

	private BookView bookViewByLevel;

	private final Order order1 = new Order(1L, "MSFT.L", true, 5, 200);
	private final Order order1e = new Order(1L, null, true, 7, 400);
	private final Order order2 = new Order(2L, "VOD.L", true, 15, 100);
	private final Order order2e = new Order(2L, null, true, 15, 200);
	private final Order order3 = new Order(3L, "MSFT.L", false, 5, 300);
	private final Order order3e = new Order(3L, null, true, 7, 200);
	private final Order order4 = new Order(4L, "MSFT.L", true, 7, 150);
	private final Order order4a = new Order(4L, "MSFT.L", true, 7, 350);
	private final Order order41 = new Order(41L, "MSFT.L", true, 6, 350);
	private final Order order42 = new Order(42L, "MSFT.L", true, 7, 350);

	private final Order order5 = new Order(5L, "VOD.L", false, 17, 300);
	private final Order order6 = new Order(6L, "VOD.L", true, 12, 150);
	private final Order order7 = new Order(7L, "VOD.L", false, 16, 100);
	private final Order order8 = new Order(8L, "VOD.L", false, 19, 100);
	private final Order order9 = new Order(9L, "VOD.L", false, 21, 112);
	
	private final Order	order18 = new Order(18L, "VWLYBWQQTO", false, 2, 351);
	private final Order	order18e1 = new Order(18L, null, false, 76, 495);
	private final Order	order18a = new Order(18L, "VWLYBWQQTO", false, 76, 495);
	private final Order	order18e2 = new Order(18L, null, false, 77, 233);
		
	private final Order order10a = new Order(9L, "ORCL.L", true, 30, 333);
	private final Order order10b = new Order(9L, "ORCL.L", true, 20, 222);
	private final Order order10c = new Order(9L, "ORCL.L", true, 10, 111);
	
	private final Order order11a = new Order(9L, "ORCL.L", false, 10, 111);
	private final Order order11b = new Order(9L, "ORCL.L", false, 20, 222);
	private final Order order11c = new Order(9L, "ORCL.L", false, 30, 333);
	
	
	private final Log log = new Log() {
		public void log(LogLevel logLevel, String msg) {
			System.out.println(msg);
		}};

	@Before
	public void setUp() throws Exception {
		
		bookViewByLevel = new BookViewByLevelImpl();
	}

	@Test
	public void testAddNewInstrument() {
		
		System.out.println("testAddNewInstrument ==========================================");
		bookViewByLevel.add(order1);
		bookViewByLevel.write(log);
		assertEquals(1, bookViewByLevel.countOrders());
	}

	@Test
	public void testAddNewInstrumentDup() {
		
		System.out.println("testAddNewInstrumentDup ==========================================");
		bookViewByLevel.add(order1);
		bookViewByLevel.write(log);
	}

	@Test
	public void testAddNewInstrumentSeveralPrices() {
		
		System.out.println("testAddNewInstrumentSeveralPrices ==========================================");
		bookViewByLevel.add(order1);
		bookViewByLevel.add(order4);
		bookViewByLevel.write(log);
	}

	@Test
	public void testAddNewInstrumentSamePriceSeveralQuantities() {
		
		System.out.println("testAddNewInstrumentSamePriceSeveralQuantities ==========================================");
		bookViewByLevel.add(order4);
		bookViewByLevel.add(order4a);
		bookViewByLevel.write(log);
	}

	@Test
	public void testAddNewBidOfferInstrumentSamePrice() {
		
		System.out.println("testAddNewBidOfferInstrumentSamePrice ==========================================");
		bookViewByLevel.add(order1);
		bookViewByLevel.add(order3);
		bookViewByLevel.write(log);
	}

	@Test
	public void testEditSellToBuy() {

		System.out.println("testEditSellToBuy ==========================================");
		bookViewByLevel.add(order3);
		bookViewByLevel.edit(order3e, order3);
		bookViewByLevel.write(log);
	}

	@Test
	public void testEditDiffPrice() {

		System.out.println("testEditDiffPrice ==========================================");
		bookViewByLevel.add(order1);
		bookViewByLevel.edit(order1e, order1);
		bookViewByLevel.write(log);
	}

	@Test
	public void testEditSamePriceDiffQuantity() {

		System.out.println("testEditSamePriceDiffQuantity ==========================================");
		bookViewByLevel.add(order2);
		bookViewByLevel.edit(order2e, order2);
		bookViewByLevel.write(log);
	}
	
	@Test
	public void testEditTwice() {

		System.out.println("testEditTwice ==========================================");
		bookViewByLevel.add(order18);
		bookViewByLevel.edit(order18e1, order18);
		bookViewByLevel.edit(order18e2, order18a);
		bookViewByLevel.write(log);
	}

	@Test
	public void testRemoveInstrument() {

		System.out.println("testRemoveInstrument ==========================================");
		bookViewByLevel.add(order1);
		bookViewByLevel.remove(order1);
		bookViewByLevel.write(log);
	}

	@Test
	public void testRemovePrice() {

		System.out.println("testRemovePrice ==========================================");
		bookViewByLevel.add(order4);
		bookViewByLevel.add(order41);
		bookViewByLevel.remove(order41);
		bookViewByLevel.write(log);
	}

	@Test
	public void testRemoveQuantity() {

		System.out.println("testRemoveQuantity ==========================================");
		bookViewByLevel.add(order4);
		bookViewByLevel.add(order42);
		bookViewByLevel.remove(order42);
		bookViewByLevel.write(log);
	}

	@Test
	public void testWrite() {
				
		System.out.println("testWrite ==========================================");
		bookViewByLevel.add(order1);
		bookViewByLevel.add(order2);
		bookViewByLevel.add(order3);
		bookViewByLevel.add(order4);
		bookViewByLevel.remove(order1);
		bookViewByLevel.add(order5);
		bookViewByLevel.add(order6);
		bookViewByLevel.edit(order3e, order3);
		bookViewByLevel.add(order7);
		bookViewByLevel.add(order8);
		bookViewByLevel.add(order9);
		bookViewByLevel.remove(order5);
		bookViewByLevel.write(log);
	}

	@Test
	public void testWriteBid() {
				
		System.out.println("testWriteBid ==========================================");
		bookViewByLevel.add(order10a);
		bookViewByLevel.add(order10b);
		bookViewByLevel.add(order10c);
		bookViewByLevel.write(log);		
	}
	
	@Test
	public void testWriteAsk() {
				
		System.out.println("testWriteAsk ==========================================");
		bookViewByLevel.add(order11a);
		bookViewByLevel.add(order11b);
		bookViewByLevel.add(order11c);
		bookViewByLevel.write(log);		
	}	
}
