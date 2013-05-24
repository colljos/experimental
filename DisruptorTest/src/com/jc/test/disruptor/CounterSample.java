package com.jc.test.disruptor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SingleThreadedClaimStrategy;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;

public class CounterSample {

	private final static int RING_SIZE = 1024 * 8;

	private static long handleCount = 0;

	private final static long ITERATIONS = 1000L * 1000L * 300L;
	private final static int NUM_EVENT_PROCESSORS = 8;

	private final static EventHandler<ValueEvent> handler = new EventHandler<ValueEvent>() {
		public void onEvent(final ValueEvent event, final long sequence,
				final boolean endOfBatch) throws Exception {
			handleCount++;
		}
	};

	public static void main(String[] args) {
	
		System.out.println("Starting disruptor app.");

		ExecutorService executor = Executors.newFixedThreadPool(NUM_EVENT_PROCESSORS);

		Disruptor<ValueEvent> disruptor = new Disruptor<ValueEvent>(
				ValueEvent.EVENT_FACTORY, 
				executor,
				new SingleThreadedClaimStrategy(RING_SIZE),
				new SleepingWaitStrategy());
		disruptor.handleEventsWith(handler);
		RingBuffer<ValueEvent> ringBuffer = disruptor.start();

		long start = System.currentTimeMillis();

		long sequence;
		ValueEvent event;
		for (long x = 0; x < ITERATIONS; x++) {
			sequence = ringBuffer.next();
			event = ringBuffer.get(sequence);
			event.setValue(x);
			ringBuffer.publish(sequence);
		}
		final long expectedSequence = ringBuffer.getCursor();

		while (handleCount < expectedSequence) {
		}

		long opsPerSecond = (ITERATIONS * 1000L)
				/ (System.currentTimeMillis() - start);
		System.out.printf("op/s: %d, handled: %d", opsPerSecond, handleCount);
	}
}
