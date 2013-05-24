package com.books.pcjvm.chapt4;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ConcurrentTotalFileSizeWForkJoin {
	
	private static class FileSizeFinder extends RecursiveTask<Long> {

		private static final long serialVersionUID = 4171832960165645706L;
		
		private final File file;

		public FileSizeFinder(File thefile) {
			this.file = thefile;
			// TODO Auto-generated constructor stub
		}

		@Override
		public Long compute() {
			long size = 0;
			if (file.isFile()) {
				size = file.length();
			}
			else {
				final File[] children = file.listFiles();
				if (children != null) {
					List<ForkJoinTask<Long>> tasks = new ArrayList<ForkJoinTask<Long>>();
					for (File child : children) {
						if (child.isFile()) {
							size += child.length();
						}
						else {
							tasks.add(new FileSizeFinder(child));
						}
					}
				
					for (ForkJoinTask<Long> task : invokeAll(tasks)) {
						size += task.join();
					}
				}
			}
			return size;
		}

	}


	private final static ForkJoinPool forkJoinPool = new ForkJoinPool(); 
	

	public static void main(String[] args) {
		
		final long start = System.nanoTime();
		final long total = forkJoinPool.invoke(new FileSizeFinder(new File(args[0])));
		final long end = System.nanoTime();
		
		System.out.println("Total Size: " + total);
		System.out.println("Time taken: " + (end-start)/1.0e9);
	}

}
