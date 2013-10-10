package com.jc.ticketing.reservation;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.jc.gig.Gig;
import com.jc.gig.GigReader;

public abstract class AbstractReservationService implements ReservationService {

	protected ConcurrentHashMap<Gig, TicketingInfo> gigsOnSale = new ConcurrentHashMap<>();
	
	public AbstractReservationService() {
		loadGigs();
	}

	@Override
	public abstract Booking bookSeats(BookingRequest bookingRequest);
	
	@Override
	public List<Gig> getAvailableGigs() {
		return Collections.list(gigsOnSale.keys());
	}

	private int loadGigs() {
		
		GigReader gigReader = new GigReader();
		try {
			List<Gig> gigs = gigReader.readGigInfo();
			for (Gig gig : gigs) {
				TicketingInfo ticketingInfo = new TicketingInfo(gig);
				gigsOnSale.put(gig, ticketingInfo);
			}
			return gigsOnSale.size();
		} 
		catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	protected int countGigsOnSale() {
		return gigsOnSale.size();
	}
	
	protected boolean validateBooking(Gig gig, int numSeatsRequested) {
		
		if (numSeatsRequested > MAX_BOOKABLE_SEATS) {
			System.err.println("Too many seats requested <" + numSeatsRequested +"> for Gig: " + gig);
			return false;
		}
		
		if (!gigsOnSale.containsKey(gig)) {
			System.err.println("Unable to locate Gig Ticketing Information: "  + gig);
			return false;
		}
		
		return true;
	}
}
