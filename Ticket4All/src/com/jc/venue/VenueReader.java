package com.jc.venue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class VenueReader {

	public static Venue readVenueInfo(String venueName) throws IOException {
		
		final BufferedReader reader = new BufferedReader(new FileReader(venueName + ".txt")); 
		
		Venue venue = new Venue(venueName);
		
		String venueInfo = null;
		while((venueInfo = reader.readLine()) != null) {
			final String[] venueInfoData = venueInfo.split(",");
			if (venueInfoData == null || venueInfoData.length < 4)
				continue;
			try {
				final String block = venueInfoData[0];
				final Integer rows = Integer.valueOf(venueInfoData[1]);
				final Integer seats = Integer.valueOf(venueInfoData[2]);
				final String tier = venueInfoData[3];
				
				venue.add(block, rows, seats, tier);
			}
			catch (NumberFormatException e) {
				System.out.println("Skipping row: " + Arrays.toString(venueInfoData));
			}
		}
		
		return venue;
	}
}
