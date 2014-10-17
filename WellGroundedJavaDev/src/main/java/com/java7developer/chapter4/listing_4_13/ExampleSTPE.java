package com.java7developer.chapter4.listing_4_13;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.java7developer.chapter4.WorkUnit;

public class ExampleSTPE {

	  private ScheduledExecutorService stpe;
	  private ScheduledFuture<?> hndl;
	  private BlockingQueue<WorkUnit<String>> lbq = new LinkedBlockingQueue<>();
	
	  private void run() {
	    stpe = Executors.newScheduledThreadPool(2);

	    final Runnable msgReader = new Runnable() {
	      public void run() {
	        String nextMsg = lbq.poll().getWork();

	        if (nextMsg != null) System.out.println("Msg recvd: "+ nextMsg);
	      }

	    };

	    hndl = stpe.scheduleAtFixedRate(msgReader, 10, 10, TimeUnit.MILLISECONDS);
	  }


	  public void cancel() {
	    final ScheduledFuture<?> myHndl = hndl;

	    stpe.schedule(new Runnable() {
	      public void run() { myHndl.cancel(true); }
	    }, 10, TimeUnit.MILLISECONDS);

	  }
	
	  /**
	   * @param args
	   */
	  public static void main(String[] args) {
		  // TODO Auto-generated method stub

	  }	
}
