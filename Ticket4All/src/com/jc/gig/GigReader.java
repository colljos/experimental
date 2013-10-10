package com.jc.gig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class GigReader {

	private final static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	public List<Gig> readGigInfo() throws IOException {
		
		final BufferedReader reader = new BufferedReader(new FileReader("gigs.txt")); 
		
		List<Gig> gigs = new ArrayList<>();
		
		String gigInfo = null;
		while((gigInfo = reader.readLine()) != null) {
			final String[] gigInfoData = gigInfo.split(",");
			if (gigInfoData == null || gigInfoData.length < 3)
				continue;
			try {
				final Date date = df.parse(gigInfoData[0]);
				final String act = gigInfoData[1];
				final String venue = gigInfoData[2];
				
				gigs.add(new Gig(date,act,venue));
			}
			catch (ParseException e) {
				System.out.println("Skipping row: " + Arrays.toString(gigInfoData));
			}
		}
		
		return gigs;
	}
}
