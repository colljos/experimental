import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LatchUsingExplicitLocksAndCondVars {

	private final Lock lock = new ReentrantLock();
	private final Condition condition = lock.newCondition();
	private volatile boolean flag = false;

	public void waitTillChange() {

		lock.lock();

		try {
			while (!flag) {
				condition.wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			lock.unlock();
		}

	}

	public void change() {

		lock.lock();		
		try {
			flag = true;
			condition.signalAll();
		}
		finally {
			lock.unlock();
		}
	}

}
