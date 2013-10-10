package com.jc.ticketing.reservation;

import java.util.List;

import com.jc.gig.Gig;

public class ReservationServiceSequentialImpl extends AbstractReservationService {

	@Override
	public Booking bookSeats(BookingRequest bookingRequest) {
	
		if (bookingRequest.isSectionBooking()) {
			return bookSeats(bookingRequest.getGig(),
					  		 bookingRequest.getSeats());			
		}
		else {
			return bookSeatsSection(bookingRequest.getGig(),
							 		bookingRequest.getSection(),
							 		bookingRequest.getNumSeats());
		}
	}
	
	private Booking bookSeats(Gig gig, List<Seat> seats) {

		Booking booking = new Booking();

		if (validateBooking(gig, seats.size())) {	
			TicketingInfo gigTicketingInfo = gigsOnSale.get(gig);
			for (Seat seat : seats) {
				Seat booked = gigTicketingInfo.bookTicket(seat);
				if (booked != null)
					booking.confirmSeat(seat);
				else 
					booking.unavailableSeat(seat);
			}
		}
		
		return booking;
	}

	private Booking bookSeatsSection(Gig gig, String section, int numSeats) {
		
		Booking booking = new Booking();
		
		if (numSeats > MAX_BOOKABLE_SEATS)
				return booking;
		
		return booking;
	}
}
