package com.jc.test.disruptor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.ClaimStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.FatalExceptionHandler;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.MultiThreadedClaimStrategy;
import com.lmax.disruptor.MultiThreadedLowContentionClaimStrategy;
import com.lmax.disruptor.NoOpEventProcessor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.Sequencer;
import com.lmax.disruptor.SingleThreadedClaimStrategy;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.WorkProcessor;
import com.lmax.disruptor.YieldingWaitStrategy;

public class Snippet1 {

	private static final EventFactory<ValueEvent> EVENT_FACTORY = ValueEvent.EVENT_FACTORY;

	private static final int RING_SIZE = 8; 

	private static final ClaimStrategy CLAIM_STRATEGY = new SingleThreadedClaimStrategy(RING_SIZE);
	private static final ClaimStrategy CLAIM_STRATEGY_MT = new MultiThreadedClaimStrategy(RING_SIZE);
	private static final ClaimStrategy CLAIM_STRATEGY_MTLC = new MultiThreadedLowContentionClaimStrategy(RING_SIZE);
	private static final ClaimStrategy CLAIM_STRATEGY_ST = new SingleThreadedClaimStrategy(RING_SIZE); 
			
	private static final WaitStrategy WAIT_STRATEGY = new SleepingWaitStrategy();
	private static final WaitStrategy WAIT_STRATEGY_B = new BlockingWaitStrategy();
	private static final WaitStrategy WAIT_STRATEGY_BS = new BusySpinWaitStrategy();
	private static final WaitStrategy WAIT_STRATEGY_S = new SleepingWaitStrategy();
	private static final WaitStrategy WAIT_STRATEGY_Y = new YieldingWaitStrategy();

	private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Explicit setup:
		
		// Event Processor
		final EventHandler<ValueEvent> handler = new EventHandler<ValueEvent>() {
		    public void onEvent(final ValueEvent event, final long sequence, final boolean endOfBatch) throws Exception
		    {
		        // process a new event.
		    	System.out.println("ValueEvent: " + event.getValue() + ", sequence: " + sequence + ", endOfBatch: " + endOfBatch);
		    }
		};
		
		
		// Setup the RingBuffer and barriers.
		RingBuffer<ValueEvent> ringBuffer = new RingBuffer<ValueEvent>(EVENT_FACTORY, CLAIM_STRATEGY, WAIT_STRATEGY); 

		SequenceBarrier barrier = ringBuffer.newBarrier();       
		BatchEventProcessor<ValueEvent> eventProcessor = new BatchEventProcessor<ValueEvent>(ringBuffer, barrier, handler);
		ringBuffer.setGatingSequences(eventProcessor.getSequence());  

		// Other processors
//		BatchEventProcessor<ValueEvent> batchEventProcessor = new BatchEventProcessor<>(ringBuffer, barrier, handler);
//		batchEventProcessor.run();
//		Sequence seq = batchEventProcessor.getSequence();
//		ExceptionHandler fatalExceptionHandler = new FatalExceptionHandler();
//		ExceptionHandler ignoreExceptionHandler = new IgnoreExceptionHandler(); 
//		batchEventProcessor.setExceptionHandler(ignoreExceptionHandler);
		
		Sequencer sequencer = new Sequencer(CLAIM_STRATEGY, WAIT_STRATEGY);
		NoOpEventProcessor noopEventProcessor = new NoOpEventProcessor(sequencer);
		noopEventProcessor.run();
		
//		ExceptionHandler exceptionHandler = new IgnoreExceptionHandler();
//		AtomicLong workSequence = new AtomicLong();
//		WorkHandler<ValueEvent> workHandler = new WorkHandler<ValueEvent>() {
//			@Override
//			public void onEvent(ValueEvent event) throws Exception {
//		        // process a new event.
//		    	System.out.println("WorkHandler ValueEvent: " + event.getValue());
//			}
//		};
//		WorkProcessor<ValueEvent> workProcessor = new WorkProcessor<ValueEvent>(ringBuffer, barrier, workHandler, exceptionHandler, workSequence); 
//		workProcessor.run();
		
		// Each EventProcessor can run on a separate thread
		EXECUTOR.submit(eventProcessor);		
		
		// Publishers claim events in sequence
		
		for (int i = 0; i < 100; i++) {
			
			long sequence = ringBuffer.next();
			ValueEvent event = ringBuffer.get(sequence);
	
			event.setValue(1234 + i); // this could be more complex with multiple fields
	
			// make the event available to EventProcessors
			ringBuffer.publish(sequence); 
		}

//		BatchHandler handler1 = new MyBatchHandler1();
//		BatchHandler handler2 = new MyBatchHandler2();
//		BatchHandler handler3 = new MyBatchHandler3()
//		
//		RingBuffer ringBuffer = new RingBuffer(EVENT_FACTORY, CLAIM_STRATEGY, WAIT_STRATEGY);
//		
//		ConsumerBarrier consumerBarrier1 = ringBuffer.createConsumerBarrier();
//		
//		BatchConsumer consumer1 = new BatchConsumer(consumerBarrier1, handler1);
//		BatchConsumer consumer2 = new BatchConsumer(consumerBarrier1, handler2);
//		
//		ConsumerBarrier consumerBarrier2 = ringBuffer.createConsumerBarrier(consumer1, consumer2);
//		BatchConsumer consumer3 = new BatchConsumer(consumerBarrier2, handler3);
//		
//		executor.execute(consumer1);
//		executor.execute(consumer2);
//		executor.execute(consumer3);
//		
//		ProducerBarrier producerBarrier = ringBuffer.createProducerBarrier(consumer3);

	}

}
