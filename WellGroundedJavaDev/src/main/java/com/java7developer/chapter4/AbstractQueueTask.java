package com.java7developer.chapter4;

import java.util.concurrent.BlockingQueue;


public abstract class AbstractQueueTask implements Runnable {
    protected String text = "";
    protected final int pauseTime;
    private boolean shutdown = false;
	protected BlockingQueue<WorkUnit<String>> lbq;

    public AbstractQueueTask(int pause_) {
      pauseTime = pause_;
    }	
	
    public void shutdown() {
    	shutdown = true;
    }
		
    public void run() {
      while (!shutdown) {
		  doAction();
        try {
        	Thread.sleep(pauseTime);
        } catch (InterruptedException e) {
        	shutdown = true;
        }
      }
    }

    public abstract void doAction();

	public void setQueue(BlockingQueue<WorkUnit<String>> lbq_) {
		lbq = lbq_;
	}
}
