import static org.junit.Assert.fail;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.Test;


public class JCBQueueTest {

	/*
	 * Insert Operations
	 */
	
	@Test
	public void testAdd() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public void testOfferE() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public void testOfferELongTimeUnit() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public void testPut() {
		fail("Not yet implemented"); // TODO
	}

	/*
	 * Remove Operations
	 */
	
	@Test
	public void testRemove() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public void testPoll() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public void testPollLongTimeUnit() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public void testTake() {
		fail("Not yet implemented"); // TODO
	}
	
	/*
	 * Examine Operations
	 */
	
	@Test
	public void testElement() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testPeek() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testClear() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testIsEmpty() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testSize() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testContains() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public void testBlockingQueue() throws InterruptedException {
//		BlockingQueue queue = new ArrayBlockingQueue<>(5);
		BlockingQueue queue = new JCBQueue<>(5);
		
		Producer p = new Producer(queue);
		Consumer c1 = new Consumer(queue);
		Consumer c2 = new Consumer(queue);
		
		new Thread(p).start();
		Thread.sleep(1000);
		
		new Thread(c1).start();
		new Thread(c2).start();
		Thread.sleep(3000);
		
	}	

	class Producer implements Runnable {

		private final BlockingQueue queue;

		public Producer(BlockingQueue queue) {
			this.queue = queue;
		}
		
		@Override
		public void run() {
			
			while (true) {
				try {
					queue.put(produce());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}			
		}

		private double produce() {
			return Math.random();
		}
	}
	
	class Consumer implements Runnable {

		private final BlockingQueue queue;

		public Consumer(BlockingQueue queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			
			while(true) {
				try {
					consume(queue.take());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		private void consume(Object take) {
			System.out.println(Thread.currentThread()+ ": " + take);
		}
				
	}
}
