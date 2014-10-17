package com.java7developer.chapter4;

import java.util.Iterator;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.locks.ReentrantLock;

// From Listing 4.12
public class MicroBlogUpdatesManagerLTQ {
	private final TransferQueue<Update> updates;
	private final ReentrantLock lock;
	private final String name;
	private final int pauseTime;

	private Iterator<Update> it;

	public MicroBlogUpdatesManagerLTQ(String name_, TransferQueue<Update> l_, ReentrantLock lock_, int pause_) {
		name = name_;
		updates = l_;
		lock = lock_;
		pauseTime = pause_;
	}

	public void addName(Update update_) {
		updates.add(update_);
	}

	public void prep() {
		it = updates.iterator();
	}

	public void printCow() {
		lock.lock();

		try {
			if (it != null) {
				System.out.print(name+ ": ");
				while (it.hasNext()) {
					Update s = it.next();
					System.out.print(s+ ", ");
				}

				System.out.println();
			}			
		} finally {
			lock.unlock();
		}
	}
}