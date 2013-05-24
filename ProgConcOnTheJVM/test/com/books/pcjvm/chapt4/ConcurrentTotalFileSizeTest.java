package com.books.pcjvm.chapt4;

import static org.junit.Assert.*;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

import com.books.pcjvm.chapt4.ConcurrentTotalFileSize.SubDirectoriesAndSize;

public class ConcurrentTotalFileSizeTest {

	ConcurrentTotalFileSize fsProcessor = new ConcurrentTotalFileSize();
	private File file = new File("C:\\temp");
	
	@Test
	public void testGetTotalAndSubDirs() {
		
		SubDirectoriesAndSize res = fsProcessor.getTotalAndSubDirs(file);
		assertTrue(res.size > 0);
		assertTrue(res.subDirectories.size() > 0);
		System.out.println("SubDirectories : " + res.subDirectories);
		System.out.println("CurrentDir size: " + res.size);
	}

	@Test
	public void testGetTotalSizeOfFilesInDir() throws InterruptedException, ExecutionException, TimeoutException {
		
		long totalSize = fsProcessor.getTotalSizeOfFilesInDir(file);
		assertTrue(totalSize > 0);
		System.out.println("Total size: " + totalSize);
	}

}
