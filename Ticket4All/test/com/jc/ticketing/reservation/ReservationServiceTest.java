package com.jc.ticketing.reservation;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Arrays;

import org.junit.Test;

public class ReservationServiceTest extends BaseTest {

	private final ReservationService reserveSvc = new ReservationServiceSequentialImpl();
			
	@Test
	public void testBookSeats() throws ParseException {
		
		BookingRequest request = new BookingRequest(gigMuseEmirates);
		request.bookSeat(Arrays.asList(new Seat("1",1,1),
									   new Seat("134",33,29)));
		Booking booking = reserveSvc.bookSeats(request);
		assertEquals(2, booking.countConfirmed());
	}

	@Test
	public void testBookSeatsSeaction() throws ParseException {
		
		BookingRequest request = new BookingRequest(gigMuseEmirates);
		request.bookSection("134", 6);
		Booking booking = reserveSvc.bookSeats(request);
		assertEquals(6, booking.countConfirmed());
	}

}
