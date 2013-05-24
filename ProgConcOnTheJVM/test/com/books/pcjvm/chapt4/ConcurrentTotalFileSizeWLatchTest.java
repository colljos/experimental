package com.books.pcjvm.chapt4;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

public class ConcurrentTotalFileSizeWLatchTest {

	ConcurrentTotalFileSizeWLatch fsProcessor = new ConcurrentTotalFileSizeWLatch();
	private File file = new File("C:\\temp");
	
	@Test
	public void testGetTotalSizeOfFilesInDir() throws InterruptedException, ExecutionException, TimeoutException {
		
		long totalSize = fsProcessor.getTotalSizeOfFilesInDir(file);
		assertTrue(totalSize > 0);
		System.out.println("Total size: " + totalSize);
	}

}
