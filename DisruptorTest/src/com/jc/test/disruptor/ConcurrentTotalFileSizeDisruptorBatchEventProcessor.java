/*
 * 	Version using:
 * 		- BatchEventProcessor
 * 		- AtomicLong for mutable state (totalSize)
 */
package com.jc.test.disruptor;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.SingleThreadedClaimStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;

public class ConcurrentTotalFileSizeDisruptorBatchEventProcessor {

    private static final int NUM_WORKERS = 100;
    private static final int BUFFER_SIZE = 1024 * 4;
    private final ExecutorService EXECUTOR = Executors.newFixedThreadPool(NUM_WORKERS);
      
    private final AtomicLong fileSizeCounter = new AtomicLong(); 
	
    // ==========================
    
    private final RingBuffer<FileSizeEvent> ringBuffer =
            new RingBuffer<FileSizeEvent>(FileSizeEvent.EVENT_FACTORY,
                                       new SingleThreadedClaimStrategy(BUFFER_SIZE),
                                       new YieldingWaitStrategy());
    private final SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
    
    private final FileSizeEventHandler handler = new FileSizeEventHandler(fileSizeCounter);
    private final BatchEventProcessor<FileSizeEvent> batchEventProcessor = new BatchEventProcessor<FileSizeEvent>(ringBuffer, sequenceBarrier, handler);
    {
        ringBuffer.setGatingSequences(batchEventProcessor.getSequence());
    }
    

    // ==========================
    
	protected long getTotalSizeOfFilesInDir(final File file) {

        EXECUTOR.submit(batchEventProcessor);
    
   		long sequence = ringBuffer.next();
		System.out.println("next ringBuffer sequence: "+sequence);
        ringBuffer.get(sequence).setValue(file, ringBuffer);
        ringBuffer.publish(sequence);          
       	 
       	// Synchronize (await completion of Event Processors)

       	try {
			sequenceBarrier.waitFor(10000, 3, TimeUnit.SECONDS);
			System.out.println("BatchEventProcessor sequence: " + batchEventProcessor.getSequence());
			System.out.println("RingBuffer Next     sequence: " + ringBuffer.next());
			System.out.println("SequenceBarrier Cursor      : " + sequenceBarrier.getCursor());

//			while (handleCount < expectedSequence) 
//			{
//				// busy spin
//			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       	finally {
       		batchEventProcessor.halt();
       		EXECUTOR.shutdown();
       	}
       	
       	// Final sum
       	return fileSizeCounter.get();
	
	}
    
	/**
	 * @param args
	 * @throws TimeoutException 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
		
		final long start = System.nanoTime();
		final long total = new ConcurrentTotalFileSizeDisruptorBatchEventProcessor().getTotalSizeOfFilesInDir(new File(args[0]));
		final long end = System.nanoTime();
		
		System.out.println("Total Size: " + total);
		System.out.println("Time taken: " + (end-start)/1.0e9);
	}

}
