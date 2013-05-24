
public class LatchUsingPrimitives {

	private final Object lock = new Object();
	private volatile boolean flag = false;
	
	public void waitTillChange() {
		
		synchronized (lock) {
			
			while(!flag ) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					System.out.println(Thread.currentThread() + " Interrupted");
				}
			}
		}
	}
	
	public void change() {
		
		synchronized (lock) {
			flag = true;
			lock.notifyAll();
		}
	}
}
