package com.books.pcjvm.chapt4;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

public class NaivelyConcurrentTotalFileSizeTest {
	
	private static final NaivelyConcurrentTotalFileSize fsProcessor = new NaivelyConcurrentTotalFileSize();

	@Test
	public void testGetTotalSizeOfFile() throws InterruptedException, ExecutionException, TimeoutException {

		long size = fsProcessor.getTotalSizeOfFile("C:\\temp");
		assertTrue(size > 0);
	}

}
