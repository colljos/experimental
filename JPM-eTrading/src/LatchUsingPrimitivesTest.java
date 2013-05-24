import org.junit.Test;


public class LatchUsingPrimitivesTest {


	@Test
	public void testChange() {

		LatchUsingPrimitives latch = new LatchUsingPrimitives();
		
		Worker w1 = new Worker(latch, 1);
		Worker w2 = new Worker(latch, 2);
		
		new Thread(w1).start();
		new Thread(w2).start();
		
		latch.waitTillChange();
	}

	public class Worker implements Runnable {

		private final int id;
		private final LatchUsingPrimitives latch;

		public Worker(LatchUsingPrimitives latch, int id) {
			this.latch = latch;
			this.id = id;
		}

		@Override
		public void run() {
			long sleepTime = (long) (Math.random() * 5000);
			System.out.println("Worker [" + id + "] sleeping " + sleepTime + " msecs ...");
			try {
				Thread.sleep(sleepTime);
				System.out.println("Worker [" + id + "] finished");
				latch.change();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
