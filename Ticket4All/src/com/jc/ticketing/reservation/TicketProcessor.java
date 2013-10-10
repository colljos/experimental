package com.jc.ticketing.reservation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TransferQueue;

public class TicketProcessor extends Thread {

	private final TransferQueue<BookingRequest> bookingRequestQ;
	private volatile boolean shutdown = false;

	final int numCores = Runtime.getRuntime().availableProcessors();
	final double blockingCoefficient = 0.9;
	final int poolSize = (int) (numCores / (1 - blockingCoefficient));
	private ExecutorService executorPool = Executors.newFixedThreadPool(poolSize);	
	
	public TicketProcessor(TransferQueue<BookingRequest> bookingRequestQ) {
		this.bookingRequestQ = bookingRequestQ;
	}
	
	@Override
	public void run() {

		while(!shutdown ) {
			try {
				final BookingRequest request = bookingRequestQ.take();
				
				executorPool.submit(new Runnable() {
					@Override
					public void run() {
						System.out.println("Seats Requested: " + request.getSeats());
					}});
			}
			catch (InterruptedException e) {
				System.err.println(e);
			}
		}
	}
	
	public void shutdown() {
		shutdown = true; 
	}

	public void submit(BookingRequest bookingRequest) {
		bookingRequestQ.tryTransfer(bookingRequest); 
	}
}
