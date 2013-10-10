package com.jc.ticketing.reservation;

import java.util.ArrayList;
import java.util.List;

public class Booking {

	private List<Seat> confirmedSeats = new ArrayList<>();
	private List<Seat> unavailableSeats = new ArrayList<>();
	
	public void confirmSeat(Seat seat) {
		confirmedSeats.add(seat);
	}

	public void unavailableSeat(Seat seat) {
		unavailableSeats.add(seat);
	}
	
	public int countConfirmed() {
		return confirmedSeats.size();
	}
	
	public int countUnavailable() {
		return unavailableSeats.size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((confirmedSeats == null) ? 0 : confirmedSeats.hashCode());
		result = prime
				* result
				+ ((unavailableSeats == null) ? 0 : unavailableSeats.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Booking other = (Booking) obj;
		if (confirmedSeats == null) {
			if (other.confirmedSeats != null)
				return false;
		} else if (!confirmedSeats.equals(other.confirmedSeats))
			return false;
		if (unavailableSeats == null) {
			if (other.unavailableSeats != null)
				return false;
		} else if (!unavailableSeats.equals(other.unavailableSeats))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Booking [confirmedSeats=" + confirmedSeats
				+ ", unavailableSeats=" + unavailableSeats + "]";
	}
}
