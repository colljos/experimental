package com.java7developer.chapter4.listing_4_6;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.java7developer.chapter4.PairedMicroBlogNode;
import com.java7developer.chapter4.SaferMicroBlogNode;
import com.java7developer.chapter4.Update;

public class FixedMicroBlogNode implements SaferMicroBlogNode {
    private final String ident;
    private final Lock lock = new ReentrantLock();
	private long startTime = System.currentTimeMillis();

    public FixedMicroBlogNode(String ident_) {
      ident = ident_;
    }

    public String getIdent() {
      return ident;
    }

    public void propagateUpdate(Update upd_, SaferMicroBlogNode backup_) {

        boolean acquired = false;

        boolean done = false;

        while (!done) {
          int wait = (int)(Math.random() * 10);

          try {
            acquired = lock.tryLock(wait, TimeUnit.MILLISECONDS);

            if (acquired) {

              System.out.println(ident +": recvd: "+ upd_.getUpdateText() +" ; backup: "+backup_.getIdent());

              done = backup_.tryConfirmUpdate(this, upd_);
            }

          } catch (InterruptedException e) {

          } finally {

            if (acquired) lock.unlock();

          }

          if (!done) try { 
            Thread.sleep(wait); 
          } catch (InterruptedException e) { }
        }
      }


      public boolean tryConfirmUpdate(SaferMicroBlogNode other_, Update upd_) {
        boolean acquired = false;

        try {
          int wait = (int)(Math.random() * 10);
          acquired = lock.tryLock(wait, TimeUnit.MILLISECONDS);

          if (acquired) {
            long elapsed = System.currentTimeMillis() - startTime ;
            System.out.println(ident +": recvd confirm: "+ upd_.getUpdateText() +" from "+other_.getIdent() +" - took "+ elapsed +" millis");

            return true;

          }

        } catch (InterruptedException e) {

        } finally {

          if (acquired) lock.unlock();

        }


        return false;

      }    
    public static void main(String[] args) {
    	final FixedMicroBlogNode local = new FixedMicroBlogNode("localhost:8888");
    	final FixedMicroBlogNode other = new FixedMicroBlogNode("localhost:8988");
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