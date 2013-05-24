package com.books.pcjvm.chapt4;

import java.io.File;

public class TotalFileSizeSequential {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		final long start = System.nanoTime();
		final long total = new TotalFileSizeSequential().getTotalFileSizeOfFilesInDir(new File(args[0]));
		final long end = System.nanoTime();
		
		System.out.println("Total Size: " + total);
		System.out.println("Time taken: " + (end-start)/1.0e9);
		
	}

	private long getTotalFileSizeOfFilesInDir(final File file) {

		if (file.isFile()) return file.length();
		
		final File[] children = file.listFiles();
		long total = 0;
		
		if (children != null) {
			for (final File child : children) {
				total += getTotalFileSizeOfFilesInDir(child);
			}
		}
		
		return total;
	}

}
