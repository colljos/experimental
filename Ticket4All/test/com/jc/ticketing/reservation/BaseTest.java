package com.jc.ticketing.reservation;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.jc.gig.Gig;
import com.jc.venue.Venue;
import com.jc.venue.VenueReader;

public class BaseTest {

	protected final static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	protected static Gig gigMuseEmirates, gigSnowPatrolO2Arena;
	protected static Venue venueEmirates;
	protected static Venue venueO2arena;
	
	
	static {
		try {
			gigMuseEmirates = new Gig(df.parse("26/05/2013"),"Muse","Emirates");
			gigSnowPatrolO2Arena = new Gig(df.parse("10/02/2012"),"Snow Patrol","O2Arena");

			venueEmirates = VenueReader.readVenueInfo("emirates");
			venueO2arena = VenueReader.readVenueInfo("O2arena");

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
