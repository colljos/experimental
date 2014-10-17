package com.java7developer.chapter4.listing_4_2;

import com.java7developer.chapter4.PairedMicroBlogNode;
import com.java7developer.chapter4.Update;

public class DeadlockingMicroBlogNode implements PairedMicroBlogNode {
    private final String ident;

    public DeadlockingMicroBlogNode(String ident_) {
      ident = ident_;
    }

    public String getIdent() {
      return ident;
    }

    public synchronized void propagateUpdate(Update upd_, PairedMicroBlogNode backup_) {
      System.out.println(ident +": recvd: "+ upd_.getUpdateText() +" ; backup: "+backup_.getIdent());
      backup_.confirmUpdate(this, upd_);
    }

    public synchronized void confirmUpdate(PairedMicroBlogNode other_, Update update_) {
      System.out.println(ident +": recvd confirm: "+ update_.getUpdateText() +" from "+other_.getIdent());
    }
  
    public static void main(String[] args) {
    	final DeadlockingMicroBlogNode local = new DeadlockingMicroBlogNode("localhost:8888");
    	final DeadlockingMicroBlogNode other = new DeadlockingMicroBlogNode("localhost:8988");
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