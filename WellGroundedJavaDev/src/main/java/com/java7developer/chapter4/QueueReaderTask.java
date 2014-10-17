package com.java7developer.chapter4;

public abstract class QueueReaderTask extends AbstractQueueTask {

    public QueueReaderTask(int pause_) {
    	super(pause_);
    }	
	
    public void doAction() {
		  String nextMsg = lbq.poll().getWork();
		  doAction(nextMsg);
    }
	
    public abstract void doAction(String msg_);
	
}
