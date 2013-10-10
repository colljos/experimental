package com.jc.ticketing.reservation.client;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.jc.gig.Gig;
import com.jc.ticketing.reservation.Booking;
import com.jc.ticketing.reservation.BookingRequest;
import com.jc.ticketing.reservation.ReservationService;
import com.jc.ticketing.reservation.Seat;
import com.jc.venue.Venue;

public class BookingRequestClientSimulator {

	private final Random random = new Random();

	private static final ScheduledExecutorService tickTimer = Executors.newScheduledThreadPool(10);
	private ScheduledFuture<?> tickTask; 
	
	private final Gig gig;
	private final int initialDelay;
	private final int intervalDelay;
	private final ReservationService reserveSvc;
	private int blockBookingCount;
	private int seatsBookingCount;

	public BookingRequestClientSimulator(ReservationService reserveSvc, Gig gig, int initialDelay, int intervalDelay) {
		this.reserveSvc = reserveSvc;
		this.gig = gig;
		this.initialDelay = initialDelay;
		this.intervalDelay = intervalDelay;
	}

	public static BookingRequestClientSimulator create(ReservationService reserveSvc, Gig gig, int requestsPerSec) {
		
		final BookingRequestClientSimulator clientSim = new BookingRequestClientSimulator(reserveSvc, gig, requestsPerSec, requestsPerSec);
		return clientSim;
	}

	public synchronized void init() {

		tickTask = tickTimer.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				generateBookingRequest();
			}
		}, initialDelay, intervalDelay, TimeUnit.MILLISECONDS);
	}
	
	public synchronized void stopTimer() {
		tickTask.cancel(true);
	}

	public synchronized static void shutdown() {
		tickTimer.shutdown();
	}

	private void generateBookingRequest() {
		
		try {
			Venue venue = gig.getVenue();

			int blockCount = venue.countBlocks();
			String section = String.valueOf(random.nextInt(blockCount));
			
			int sectionRows = venue.countRows(section);
			int sectionSeatsPerRow = venue.countSeats(section);
				
			int numSeats = random.nextInt(ReservationService.MAX_BOOKABLE_SEATS);
			
			boolean blockBooking = random.nextBoolean();
			Booking bookingResponse;
			BookingRequest bookingRequest = new BookingRequest(gig);
			if (blockBooking) {
				blockBookingCount++;
				bookingRequest.bookSection(section, numSeats);
				bookingResponse = reserveSvc.bookSeats(bookingRequest);			
			}
			else {
				seatsBookingCount++;
				bookingRequest = new BookingRequest(gig);
				
				for (int i = 0; i < numSeats; i++) {
					int row = random.nextInt(sectionRows);
					int seat = random.nextInt(sectionSeatsPerRow);		
					bookingRequest.bookSeat(new Seat(section, row, seat)); 
				}
				bookingResponse = reserveSvc.bookSeats(bookingRequest);
			}
	
			System.out.println("===========================================");
			System.out.println("GIG: " + gig);
			System.out.println("Booking Request: " + bookingRequest);
			System.out.println("Booking Response: " + bookingResponse);
			System.out.println("Seat  Booking count: " + seatsBookingCount);
			System.out.println("Block Booking count: " + blockBookingCount);
		} 
		catch (IOException e) {
			System.err.println("Unable to load venue information for <" + gig.getVenueName() + ">");
		}
	}

}
