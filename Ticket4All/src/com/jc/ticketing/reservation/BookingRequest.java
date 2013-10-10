package com.jc.ticketing.reservation;

import java.util.ArrayList;
import java.util.List;

import com.jc.gig.Gig;

public class BookingRequest {

	private final Gig gig;
	private int numSeats = 0;
	private String section = null;

	private List<Seat> seats = new ArrayList<>(numSeats);
	
	public BookingRequest(Gig gig) {
		this.gig = gig;
	}

	public void bookSeat(Seat seat) {
		seats.add(seat);
	}
	
	public void bookSeat(List<Seat> asList) {
		seats.addAll(seats);
	}
	
	public void bookSection(String section, int numSeats) {
		this.seats.clear();
		this.section = section;
		this.numSeats = numSeats;
	}
	
	public boolean isSectionBooking() {
		return seats.isEmpty();
	}

	public Gig getGig() {
		return gig;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public String getSection() {
		return section;
	}

	public int getNumSeats() {
		return numSeats;
	}
}
