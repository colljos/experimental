package com.jc.test.disruptor;

import java.io.File;
import java.util.concurrent.atomic.AtomicLong;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;

public class FileSizeEventHandler implements EventHandler<FileSizeEvent> {

	private final AtomicLong fileSizeCounter;

	public FileSizeEventHandler(AtomicLong fileSizeCounter) {
		this.fileSizeCounter = fileSizeCounter;
	}

	@Override
	public void onEvent(FileSizeEvent event, long sequence, boolean endOfBatch)
			throws Exception {

//		System.out.println("onEvent(): " + event.getFilename());
		exploreDir(event.getFilename(), event.getRingBuffer());
	}

	private void exploreDir(final File file, RingBuffer<FileSizeEvent> ringBuffer) {

		long fileSize = 0;
		// pendingFileVisits.incrementAndGet();

		if (file.isFile()) {
			fileSize = file.length();
		} else {
			final File[] children = file.listFiles();
			if (children != null) {
				for (final File child : children) {
					if (child.isFile()) {
						fileSize += child.length();
					} else {
						// Publish further work to ring buffer
						// exploreDir(child);
						long sequence = ringBuffer.next();
						System.out.println("next ringBuffer sequence: "+sequence);
						ringBuffer.get(sequence).setValue(child, ringBuffer);
						System.out.println("onEvent(): exploring new directory [" + child + "]");
						ringBuffer.publish(sequence);
					}
				}
			}
		}

		try {
			fileSizeCounter.getAndAdd(fileSize);
			System.out.println("onEvent(): File Size <" + fileSize + "> for "+file);
//			latch.countDown();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// pendingFileVisits.decrementAndGet();

	}

}
