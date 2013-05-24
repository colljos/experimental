package com.books.pcjvm.chapt6;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import akka.stm.Atomic;
import akka.stm.Ref;


public class ConcurrentTotalFileSizeWSTM {

	private ExecutorService service;
	final private Ref<Long> pendingFileVisits = new Ref<Long>(0L);
	final private Ref<Long> totalSize = new Ref<Long>(0L);
	final private CountDownLatch latch = new CountDownLatch(1);
	
	protected void findTotalSizeOfFilesInDir(final File file) {

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
					} 
					else {
						updatePendingVisits(1);
						service.execute(new Runnable() {
							@Override
							public void run() {
								findTotalSizeOfFilesInDir(child);
							}
						});
					}
				}
			}
		}
		
		final long totalFizeSize = fileSize;
		new Atomic<Long>() {
			@Override
			public Long atomically() {
				totalSize.swap(totalSize.get() + totalFizeSize);
				return null;
			}
		}.execute();
		
		if (updatePendingVisits(-1) == 0) {
			latch.countDown();
		}
	}

	private long updatePendingVisits(final int value) {
		return new Atomic<Long>() {
			@Override
			public Long atomically() {
				pendingFileVisits.swap(pendingFileVisits.get() + value);
				return pendingFileVisits.get();
			}
		}.execute();
	}

	private long getTotalSizeOfFilesInDir(final File file) throws InterruptedException {

		service = Executors.newFixedThreadPool(100);
		updatePendingVisits(1);
		
		try {
			findTotalSizeOfFilesInDir(file);
			latch.await(100, TimeUnit.SECONDS);
			return totalSize.get();
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
		final long total = new ConcurrentTotalFileSizeWSTM().getTotalSizeOfFilesInDir(new File(args[0]));
		final long end = System.nanoTime();
		
		System.out.println("Total Size: " + total);
		System.out.println("Time taken: " + (end-start)/1.0e9);
	}

}
