package com.jc.ticketing.reservation;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.jc.gig.Gig;
import com.jc.venue.Venue.VenueEntry;

public class TicketingInfo {

	private final Gig gig;
	private final Map<String, Set<Seat>> sections = new ConcurrentHashMap<>();
	private int seatCount;

	public TicketingInfo(Gig gig) {
		this.gig = gig;
		generateTickets();
	}

	private void generateTickets() {
		
		this.seatCount = 0;
		List<VenueEntry> venueEntries;
		try {
			venueEntries = gig.getVenue().getVenueEntries();
			for (VenueEntry venueEntry : venueEntries) {
				String block = venueEntry.getBlock();			
				Set<Seat> sectionSeats = new HashSet<Seat>();
				for (int i = 1; i <= venueEntry.getRows(); i++) {
					for (int j = 1; j <= venueEntry.getSeats(); j++) {
						sectionSeats.add(new Seat(block, i, j));
						this.seatCount += 1;
					}
				}
				sections.put(block, sectionSeats);
			}
		} catch (IOException e) {
			System.err.println("Unable to load venue information for <" + gig.getVenueName() + ">");
		}
	}

	public int getSeatCount() {
		return seatCount;
	}
	
	public boolean isAvailable() {
		return seatCount > 0;
	}

	synchronized public Seat bookTicket(Seat seat) {
		
		Set<Seat> sectionSeats = sections.get(seat.getSection());
		if (sectionSeats != null) {
			if (sectionSeats.contains(seat)) {
				sectionSeats.remove(seat);
				return seat;
			}
		}
		return null;
	}
	
	synchronized public Booking bookSection(String section, int numSeats) {

		Booking booking = new Booking();
		Set<Seat> sectionSeats = sections.get(section);
		if (sectionSeats.size() >= numSeats) {
			Iterator<Seat> seats = sectionSeats.iterator();
			for (int i = 0; i < numSeats; i++) {
				Seat nextSeat = seats.next();
				sectionSeats.remove(nextSeat);
				booking.confirmSeat(nextSeat);
			}
		}
		
		return booking;
	}	

	public Gig getGig() {
		return gig;
	}

}
