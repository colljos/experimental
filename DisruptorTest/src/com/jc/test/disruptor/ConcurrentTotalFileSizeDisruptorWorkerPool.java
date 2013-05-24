/*
 * 	Version using:
 * 		- WorkerPool (EP)
 * 		- Disruptor util Long types for mutable state (totalSize)
 */

package com.jc.test.disruptor;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import com.lmax.disruptor.FatalExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SingleThreadedClaimStrategy;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.util.PaddedLong;

public class ConcurrentTotalFileSizeDisruptorWorkerPool {

	private static final int NUM_WORKERS = 8;
	private static final int BUFFER_SIZE = 1024 * 8;
	private final ExecutorService EXECUTOR = Executors
			.newFixedThreadPool(NUM_WORKERS);
	

	// private final MutableLong fileSizeCounter = new MutableLong();
	// private final PaddedLong fileSizeCounter = new PaddedLong();
	// private final PaddedAtomicLong fileSizeCounter = new PaddedAtomicLong();

	private final PaddedLong[] counters = new PaddedLong[NUM_WORKERS];
	{
		for (int i = 0; i < NUM_WORKERS; i++) {
			counters[i] = new PaddedLong();
		}
	}

	// ============================

	private final EventDirSizeWorkHandler[] handlers = new EventDirSizeWorkHandler[NUM_WORKERS];
	{
		for (int i = 0; i < NUM_WORKERS; i++) {
			handlers[i] = new EventDirSizeWorkHandler(counters, i);
		}
	}

	private final WorkerPool<FileSizeEvent> workerPool = new WorkerPool<FileSizeEvent>(
					FileSizeEvent.EVENT_FACTORY, 
					new SingleThreadedClaimStrategy(BUFFER_SIZE), 
					new YieldingWaitStrategy(),
					new FatalExceptionHandler(),
					handlers);

	// ==========================

	protected long getTotalSizeOfFilesInDir(final File file)
			throws InterruptedException, ExecutionException, TimeoutException {

		resetCounters();
		RingBuffer<FileSizeEvent> ringBuffer = workerPool.start(EXECUTOR);

		try {
			long sequence = ringBuffer.next();
			ringBuffer.get(sequence).setValue(file, ringBuffer);
			ringBuffer.publish(sequence);
		} finally {
			workerPool.drainAndHalt();
			EXECUTOR.shutdown();
		}

		// Synchronize

		// Final sum
		return sumCounters();

	}

	private void resetCounters() {
		for (int i = 0; i < NUM_WORKERS; i++) {
			counters[i].set(0L);
		}
	}

	private long sumCounters() {
		long sumJobs = 0L;
		for (int i = 0; i < NUM_WORKERS; i++) {
			sumJobs += counters[i].get();
		}

		return sumJobs;
	}

	/**
	 * @param args
	 * @throws TimeoutException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException,
			ExecutionException, TimeoutException {

		final long start = System.nanoTime();
		final long total = new ConcurrentTotalFileSizeDisruptorWorkerPool()
				.getTotalSizeOfFilesInDir(new File(args[0]));
		final long end = System.nanoTime();

		System.out.println("Total Size: " + total);
		System.out.println("Time taken: " + (end - start) / 1.0e9);
	}

}
