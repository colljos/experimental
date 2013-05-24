package com.books.pcjvm.chapt4;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ConcurrentTotalFileSize {

	class SubDirectoriesAndSize {

		final long size;
		final List<File> subDirectories;

		public SubDirectoriesAndSize(final long totalSize,
				final List<File> theSubDirs) {
			this.size = totalSize;
			this.subDirectories = theSubDirs;
		}
	}

	protected SubDirectoriesAndSize getTotalAndSubDirs(final File file) {

		final List<File> subDirectories = new ArrayList<File>();
		long total = 0;

		if (file.isDirectory()) {
			final File[] children = file.listFiles();
			if (children != null) {
				for (File child : children) {
					if (child.isFile()) {
						total += child.length();
					} else {
						subDirectories.add(child);
					}
				}
			}
		}

		return new SubDirectoriesAndSize(total, subDirectories);
	}

	protected long getTotalSizeOfFilesInDir(final File file) throws InterruptedException, ExecutionException, TimeoutException {

		long total = 0;

		final ExecutorService service = Executors.newFixedThreadPool(100);

		try {
			List<File> directories = new ArrayList<File>();
			directories.add(file);
			while (!directories.isEmpty()) {
				final List<Future<SubDirectoriesAndSize>> partialResults = new ArrayList<Future<SubDirectoriesAndSize>>();
				for (final File directory : directories) {
					partialResults.add(service
							.submit(new Callable<SubDirectoriesAndSize>() {
								@Override
								public SubDirectoriesAndSize call()
										throws Exception {
									return getTotalAndSubDirs(directory);
								}
							}));
				}
				directories.clear();
				for (Future<SubDirectoriesAndSize> partialResult : partialResults) {
					SubDirectoriesAndSize subDirectoriesAndSize = partialResult.get(100, TimeUnit.SECONDS);
					directories.addAll(subDirectoriesAndSize.subDirectories);
					total += subDirectoriesAndSize.size;
				}
			}
		} finally {
			service.shutdown();
		}

		return total;
	}

	/**
	 * @param args
	 * @throws TimeoutException 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
		
		final long start = System.nanoTime();
		final long total = new ConcurrentTotalFileSize().getTotalSizeOfFilesInDir(new File(args[0]));
		final long end = System.nanoTime();
		
		System.out.println("Total Size: " + total);
		System.out.println("Time taken: " + (end-start)/1.0e9);
	}

}
