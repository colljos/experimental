package com.jc.venue;

import java.util.ArrayList;
import java.util.List;

public class Venue {

	private final String venueName;
	final List<VenueEntry> venueEntries = new ArrayList<>();
	
	public Venue(String venueName) {
		this.venueName = venueName;
	}

	public void add(String block, Integer rows, Integer seats, String tier) {
		venueEntries.add(new VenueEntry(block, rows, seats, tier));
	}
	
	public class VenueEntry {

		private final String block;
		private final Integer rows;
		private final Integer seats;
		private final String tier;

		public VenueEntry(String block, Integer rows, Integer seats, String tier) {
			this.block = block;
			this.rows = rows;
			this.seats = seats;
			this.tier = tier;
		}

		public int getRows() {
			return rows;
		}

		public int getSeats() {
			return seats;
		}
		
		public String getBlock() {
			return block;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((block == null) ? 0 : block.hashCode());
			result = prime * result + ((rows == null) ? 0 : rows.hashCode());
			result = prime * result + ((seats == null) ? 0 : seats.hashCode());
			result = prime * result + ((tier == null) ? 0 : tier.hashCode());
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
			VenueEntry other = (VenueEntry) obj;
			if (block == null) {
				if (other.block != null)
					return false;
			} else if (!block.equals(other.block))
				return false;
			if (rows == null) {
				if (other.rows != null)
					return false;
			} else if (!rows.equals(other.rows))
				return false;
			if (seats == null) {
				if (other.seats != null)
					return false;
			} else if (!seats.equals(other.seats))
				return false;
			if (tier == null) {
				if (other.tier != null)
					return false;
			} else if (!tier.equals(other.tier))
				return false;
			return true;
		}
	}

	public boolean validate() {
		if (venueEntries.size() > 0)
			return true;

		return false;
	}

	public int countBlocks() {
		return venueEntries.size();
	}

	public int countSeats() {
		int totalSeats = 0;
		for (VenueEntry entry : venueEntries) {
			totalSeats += entry.getRows() * entry.getSeats();
		}
		return totalSeats;
	}

	public int countRows(String section) {
		if (section != null && section.isEmpty()) {		
			for (VenueEntry entry : venueEntries) {
				if (section.equals(entry.getBlock()))
					return entry.getRows();
			}
		}
		return 0;
	}

	public int countSeats(String section) {
		if (section != null && section.isEmpty()) {		
			for (VenueEntry entry : venueEntries) {
				if (section.equals(entry.getBlock()))
					return entry.getSeats();
			}
		}
		return 0;
	}
	
	public void print() {
		
		System.out.println("Venue Name: " + venueName);
		System.out.println("Venue Block count: " + countBlocks());
		System.out.println("Venue Seats count: " + countSeats());	
	}

	public List<VenueEntry> getVenueEntries() {
		return venueEntries;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((venueEntries == null) ? 0 : venueEntries.hashCode());
		result = prime * result
				+ ((venueName == null) ? 0 : venueName.hashCode());
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
		Venue other = (Venue) obj;
		if (venueEntries == null) {
			if (other.venueEntries != null)
				return false;
		} else if (!venueEntries.equals(other.venueEntries))
			return false;
		if (venueName == null) {
			if (other.venueName != null)
				return false;
		} else if (!venueName.equals(other.venueName))
			return false;
		return true;
	}
}
