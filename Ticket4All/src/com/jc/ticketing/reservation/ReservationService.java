package com.jc.ticketing.reservation;

import java.util.List;

import com.jc.gig.Gig;


public interface ReservationService {

	public static final int MAX_BOOKABLE_SEATS = 6;
	
	List<Gig> getAvailableGigs();
	Booking bookSeats(BookingRequest bookingRequest);
}
