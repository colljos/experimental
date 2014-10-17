package com.java7developer.chapter4.listing_4_10;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

import com.java7developer.chapter4.Author;
import com.java7developer.chapter4.MicroBlogUpdatesManager;
import com.java7developer.chapter4.Update;

public class CopyOnWriteExampleMain {

public static void main(String[] args) {
	  final CountDownLatch firstLatch = new CountDownLatch(1);
	  final CountDownLatch secondLatch = new CountDownLatch(1);
	  final Update.Builder ub = new Update.Builder();


	  final List<Update> l = new CopyOnWriteArrayList<>();
	  l.add(ub.author(new Author("Ben")).updateText("I like pie").build());
	  l.add(ub.author(new Author("Charles")).updateText("I like ham on rye").build());
	               
	  ReentrantLock lock = new ReentrantLock();               
	  final MicroBlogUpdatesManager mbex1 = new MicroBlogUpdatesManager("MBEx1", l, lock);
	  final MicroBlogUpdatesManager mbex2 = new MicroBlogUpdatesManager("MBEx2", l, lock);
	              
	  Thread t1 = new Thread() {

	    public void run() {

	      l.add(ub.author(new Author("Jeffrey")).updateText("I like a lot of things").build());

	      mbex1.prep();

	      firstLatch.countDown();
	      try { secondLatch.await(); } catch (InterruptedException e) {  }
	      mbex1.printCow();
	    }

	  };

	  Thread t2 = new Thread() {

	    public void run() {

	      try {

	        firstLatch.await();

	        l.add(ub.author(new Author("Gavin")).updateText("I like otters").build());

	        mbex2.prep();

	        secondLatch.countDown();

	      } catch (InterruptedException e) { }

	      mbex2.printCow();
	}
	};
	t1.start();
	t2.start();
	
}
	
}
