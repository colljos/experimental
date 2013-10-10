package com.jc.gig;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class GigReaderTest {

	@Test
	public void testReadGigs() throws IOException {

		GigReader gigReader = new GigReader();
		List<Gig> gigs = gigReader.readGigInfo();
		assertEquals(2, gigs.size());
	}

}
