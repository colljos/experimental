package com.jc.venue;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class VenueReaderTest {

	@Test
	public void testReadVenue() throws IOException {
		
		Venue venue = VenueReader.readVenueInfo("emirates");
		venue.print();
		assertTrue(venue.validate());
		
		assertEquals(122, venue.countBlocks());
		assertEquals(64838 ,venue.countSeats());
		
		venue = VenueReader.readVenueInfo("O2arena");
		venue.print();
		assertTrue(venue.validate());
		
		assertEquals(49, venue.countBlocks());
		assertEquals(21938 ,venue.countSeats());

	}

}
