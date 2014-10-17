package com.java7developer.chapter4.listing_4_4;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.java7developer.chapter4.PairedMicroBlogNode;
import com.java7developer.chapter4.Update;

public class LockingMicroBlogNode implements PairedMicroBlogNode {
    private final String ident;
    private final Lock lock = new ReentrantLock();

    public LockingMicroBlogNode(String ident_) {
      ident = ident_;
    }

    public String getIdent() {
      return ident;
    }

    public synchronized void confirmUpdate(PairedMicroBlogNode other_, Update upd_) {
        lock.lock();

        try{
          System.out.println(ident +": recvd confirm: "+ upd_.getUpdateText() +" from "+ other_.getIdent());
        } finally {
          lock.unlock();
        }
    }
  
    public void propagateUpdate(Update upd_, PairedMicroBlogNode backup_) {
      lock.lock();
      try {
        System.out.println(ident +": recvd: "+ upd_.getUpdateText() +" ; backup: "+backup_.getIdent());
        backup_.confirmUpdate(this, upd_);
      } finally {
        lock.unlock();
      }

    }


    public static void main(String[] args) {
    	final LockingMicroBlogNode local = new LockingMicroBlogNode("localhost:8888");
    	final LockingMicroBlogNode other = new LockingMicroBlogNode("localhost:8988");
    	final Update first = getUpdate("1");
    	final Update second = getUpdate("2");


    	new Thread(new Runnable() {
    		public void run() {
    			local.propagateUpdate(first, other);
    		}
    	}).start();

    	new Thread(new Runnable() {
    		public void run() {
    			other.propagateUpdate(second, local);
    		}
    	}).start();
  
    }

	private static Update getUpdate(String string) {
		Update.Builder ub = new Update.Builder();
		ub.updateText(string).createTime(System.currentTimeMillis());
		
		return ub.build();
	}

}