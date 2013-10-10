package com.jc.gig;

import java.io.IOException;
import java.util.Date;

import com.jc.venue.Venue;
import com.jc.venue.VenueReader;

public class Gig {

	private Date date;
	private String act;
	private String venueName;
	
	private Venue venue = null;

	public Gig(Date date, String act, String venueName) {
		this.date = date;
		this.act = act;
		this.venueName = venueName;
	}

	public String getVenueName() {
		return venueName;
	}
	
	public Venue getVenue() throws IOException {
		if (venue == null) {
			venue = VenueReader.readVenueInfo(venueName);
		}
		return venue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((act == null) ? 0 : act.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((venue == null) ? 0 : venue.hashCode());
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
		Gig other = (Gig) obj;
		if (act == null) {
			if (other.act != null)
				return false;
		} else if (!act.equals(other.act))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (venue == null) {
			if (other.venue != null)
				return false;
		} else if (!venue.equals(other.venue))
			return false;
		return true;
	}
}