package com.jc.ticketing.reservation;

public class Seat {

	private final String section;
	private final int row;
	private final int seatNumber;

	public Seat(String section, int row, int seatNumber) {
		this.section = section;
		this.row = row;
		this.seatNumber = seatNumber;
	}

	public String getSection() {
		return section;
	}

	public int getRow() {
		return row;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + row;
		result = prime * result + seatNumber;
		result = prime * result + ((section == null) ? 0 : section.hashCode());
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
		Seat other = (Seat) obj;
		if (row != other.row)
			return false;
		if (seatNumber != other.seatNumber)
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}
}
