package com.java7developer.chapter4.listing_4_5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.java7developer.chapter4.PairedMicroBlogNode;
import com.java7developer.chapter4.Update;

public class FlawedMicroBlogNode implements PairedMicroBlogNode {
    private final String ident;
    private final Lock lock = new ReentrantLock();

    public FlawedMicroBlogNode(String ident_) {
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
  
    public void propagateUpdate(Update update_, PairedMicroBlogNode backup_) {
        boolean acquired = false;

        while (!acquired) {
          try {
            int wait = (int)(Math.random() * 10);
            acquired = lock.tryLock(wait, TimeUnit.MILLISECONDS);
            if (acquired) {

              System.out.println(ident +": recvd: "+ update_.getUpdateText() +" ; backup: "+backup_.getIdent());
              backup_.confirmUpdate(this, update_);

            } else {

              Thread.sleep(wait);

            }

          } catch (InterruptedException e) {

          } finally {

            if (acquired) lock.unlock();

          }
        }
      }
    
    public static void main(String[] args) {
    	final FlawedMicroBlogNode local = new FlawedMicroBlogNode("localhost:8888");
    	final FlawedMicroBlogNode other = new FlawedMicroBlogNode("localhost:8988");
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