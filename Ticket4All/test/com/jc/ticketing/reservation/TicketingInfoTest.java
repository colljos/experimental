package com.jc.ticketing.reservation;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class TicketingInfoTest extends BaseTest {

	@Test
	public void testGenerateTickets() throws IOException {

		TicketingInfo info = new TicketingInfo(gigMuseEmirates);
		assertEquals(64838, info.getSeatCount());
		
		info = new TicketingInfo(gigSnowPatrolO2Arena);
		assertEquals(21938, info.getSeatCount());		
	}

}
