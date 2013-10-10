package com.jc.ticketing.reservation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

public class TicketingProcessor {

	private TransferQueue<BookingRequest> updates; 
	private final ExecutorService dispatchThread = Executors.newSingleThreadExecutor();
	
	public TicketingProcessor(TransferQueue<BookingRequest> updateQ) {
		this.updates = updateQ; 
	}

	public void onBookingRequest(final BookingRequest request) {
		
		dispatchThread.submit(new Runnable() {
			@Override
			public void run() {
				try {
					updates.tryTransfer(request, 1000, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					System.err.println(e);
				}
			}});
	}
}
