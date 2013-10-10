package com.jc.ticketing.reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.jc.gig.Gig;

public class ReservationServiceConcurrentImpl extends
		AbstractReservationService {

	final int numberOfCores = Runtime.getRuntime().availableProcessors();
	final double blockingCoefficient = 0.9;
	final int poolSize = (int) (numberOfCores / (1 - blockingCoefficient));
	final ExecutorService executorPool = Executors.newFixedThreadPool(poolSize);

	public ReservationServiceConcurrentImpl() {
		System.out.println("Number of Core available is: " + numberOfCores);
		System.out.println("Pool size is " + poolSize);
	}

	@Override
	public Booking bookSeats(BookingRequest bookingRequest) {

		if (bookingRequest.isSectionBooking()) {
			return bookSeats(bookingRequest.getGig(), bookingRequest.getSeats());
		} else {
			return bookSeatsSection(bookingRequest.getGig(),
					bookingRequest.getSection(), bookingRequest.getNumSeats());
		}

	}

	private Booking bookSeatsSection(Gig gig, String section, int numSeats) {
		final TicketingInfo gigTicketingInfo = gigsOnSale.get(gig);
		Booking booking = gigTicketingInfo.bookSection(section, numSeats);
		return booking;
	}

	private Booking bookSeats(Gig gig, List<Seat> seats) {

		Booking booking = new Booking();

		if (validateBooking(gig, seats.size())) {

			final TicketingInfo gigTicketingInfo = gigsOnSale.get(gig);
			final List<Callable<Seat>> bookingReqs = new ArrayList<Callable<Seat>>();

			for (final Seat seat : seats) {
				bookingReqs.add(new Callable<Seat>() {
					@Override
					public Seat call() throws Exception {
						return gigTicketingInfo.bookTicket(seat);
					}
				});
			}

			try {
				List<Future<Seat>> bookTicketResponses = executorPool
						.invokeAll(bookingReqs, 10000, TimeUnit.SECONDS);

				for (Future<Seat> bookingResponse : bookTicketResponses) {

					try {
						Seat seatBooking = bookingResponse.get();
						if (seatBooking != null)
							booking.confirmSeat(seatBooking);
						else
							booking.unavailableSeat(seatBooking);
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return booking;
	}
}
