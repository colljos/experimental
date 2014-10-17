package com.java7developer.chapter4.listing_4_11;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.java7developer.chapter4.AbstractQueueTask;
import com.java7developer.chapter4.Author;
import com.java7developer.chapter4.QueueReaderTask;
import com.java7developer.chapter4.Update;
import com.java7developer.chapter4.WorkUnit;

public class BlockingQueueMain {

	/**
	 * Used in Listing 4.11
	 */
	public static void main(String[] args) {
		  final Update.Builder ub = new Update.Builder();
		  final BlockingQueue<WorkUnit<String>> lbq = new LinkedBlockingQueue<>(100);

		  AbstractQueueTask writer = new AbstractQueueTask(10) {
		    public void doAction() {
		    	text = text + "X";
		    	WorkUnit<String> wu = new WorkUnit<String>(text);
		    	boolean handed = false;
		    	try {
		    		handed = lbq.offer(wu, 100, TimeUnit.MILLISECONDS);
		    	} catch (InterruptedException e) {
		    	}
		    	if (!handed) System.out.println("Unable to handoff Update to Queue due to timeout");
		    }
		  };
		  writer.setQueue(lbq);

		  QueueReaderTask reader = new QueueReaderTask(1000) {
		    public void doAction(final String text_) {
		    	System.out.println(text_);
		    }
		  };
		  
		  (new Thread(reader)).start();
		  (new Thread(writer)).start();
	}

}
