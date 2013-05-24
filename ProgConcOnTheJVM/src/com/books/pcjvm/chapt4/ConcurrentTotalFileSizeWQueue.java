package com.books.pcjvm.chapt4;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

public class ConcurrentTotalFileSizeWQueue {

	private ExecutorService service;
	final private AtomicLong pendingFileVisits = new AtomicLong();
	final private BlockingQueue<Long> fileSizes = new ArrayBlockingQueue<Long>(500);
	
	
	protected void exploreDir(final File file) {

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
						startExploreDir(child);
					}
				}
			}
		}

		try {
			fileSizes.put(fileSize);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
		pendingFileVisits.decrementAndGet();
	}

	private void startExploreDir(final File child) {
		pendingFileVisits.incrementAndGet();
		service.execute(new Runnable() {
			@Override
			public void run() {
				exploreDir(child);
			}
		});
	}

	protected long getTotalSizeOfFilesInDir(final File file) throws InterruptedException, ExecutionException, TimeoutException {

		service = Executors.newFixedThreadPool(100);
		
		try {
			startExploreDir(file);
			long totalSize = 0;
			while (pendingFileVisits.get() > 0 || fileSizes.size() > 0) {
				final Long size = fileSizes.poll(10, TimeUnit.SECONDS);
				totalSize += size;
			}
			return totalSize;
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
		final long total = new ConcurrentTotalFileSizeWQueue().getTotalSizeOfFilesInDir(new File(args[0]));
		final long end = System.nanoTime();
		
		System.out.println("Total Size: " + total);
		System.out.println("Time taken: " + (end-start)/1.0e9);
	}

}
