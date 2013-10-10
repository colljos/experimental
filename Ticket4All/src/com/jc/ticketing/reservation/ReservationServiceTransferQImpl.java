package com.jc.ticketing.reservation;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

import com.jc.gig.Gig;

public class ReservationServiceTransferQImpl extends AbstractReservationService {

	private ConcurrentHashMap<Gig, TicketingInfo> gigsOnSale = new ConcurrentHashMap<>();
	private ConcurrentHashMap<Gig, TicketProcessor> gigTicketProcessors;
	
	public ReservationServiceTransferQImpl() {
		super();
		if (countGigsOnSale() > 0) {
			gigTicketProcessors = new ConcurrentHashMap<>(countGigsOnSale());
			
			Set<Gig> gigSet = gigsOnSale.keySet();
			for (Gig gig : gigSet) {
				TransferQueue<BookingRequest> transferQueue= new LinkedTransferQueue<BookingRequest>();
				gigTicketProcessors.putIfAbsent(gig, new TicketProcessor(transferQueue));
			}
		}
	}

	@Override
	public Booking bookSeats(BookingRequest bookingRequest) {
	
		TicketProcessor gigProcessor = gigTicketProcessors.get(bookingRequest.getGig());
		gigProcessor.submit(bookingRequest);
		
		return new Booking();
	}	
}
