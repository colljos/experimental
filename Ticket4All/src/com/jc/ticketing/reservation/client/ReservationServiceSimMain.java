package com.jc.ticketing.reservation.client;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.jc.gig.Gig;
import com.jc.ticketing.reservation.ReservationService;
import com.jc.ticketing.reservation.ReservationServiceSequentialImpl;

public class ReservationServiceSimMain {

	protected final static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	private final ReservationService reserveSvc = new ReservationServiceSequentialImpl();
	
	private void bootstrap() throws Exception {
		
		List<Gig> gigs = reserveSvc.getAvailableGigs();
		for (Gig gig : gigs) {
			BookingRequestClientSimulator gigSim = BookingRequestClientSimulator.create(reserveSvc, gig, 1);
			gigSim.init();
		}
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		new ReservationServiceSimMain().bootstrap();
	}

}
