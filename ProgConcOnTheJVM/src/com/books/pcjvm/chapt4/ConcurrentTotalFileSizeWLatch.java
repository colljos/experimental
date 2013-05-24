package com.books.pcjvm.chapt4;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

public class ConcurrentTotalFileSizeWLatch {

	private ExecutorService service;
	final private AtomicLong pendingFileVisits = new AtomicLong();
	final private AtomicLong totalSize = new AtomicLong();
	final private CountDownLatch latch = new CountDownLatch(1);
	
	protected void updateTotalSizeOfFilesInDir(final File file) {

		long fileSize = 0;

		if (file.isFile()) {
			fileSize = file.length();
		}
		else  {
			final File[] children = file.listFiles();
			if (children != null) {
				for (final File child : children) {
					if (child.isFile()) {
						fileSize += child.length();
					} else {
						pendingFileVisits.incrementAndGet();
						service.execute(new Runnable() {
							@Override
							public void run() {
								updateTotalSizeOfFilesInDir(child);
							}
						});
					}
				}
			}
		}
		totalSize.addAndGet(fileSize);
		if (pendingFileVisits.decrementAndGet() == 0) {
			latch.countDown();
		}

	}

	protected long getTotalSizeOfFilesInDir(final File file) throws InterruptedException, ExecutionException, TimeoutException {

		service = Executors.newFixedThreadPool(100);
		pendingFileVisits.incrementAndGet();
		
		try {
			updateTotalSizeOfFilesInDir(file);
			latch.await(100, TimeUnit.SECONDS);
			return totalSize.longValue();
		} finally {
			service.shutdown();
		}
	}

	/**
	 * @param args
	 * @throws TimeoutException 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
		
		final long start = System.nanoTime();
		final long total = new ConcurrentTotalFileSizeWLatch().getTotalSizeOfFilesInDir(new File(args[0]));
		final long end = System.nanoTime();
		
		System.out.println("Total Size: " + total);
		System.out.println("Time taken: " + (end-start)/1.0e9);
	}

}
