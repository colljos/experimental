package com.java7developer.chapter4;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

// From Listing 4.9
public class MicroBlogUpdatesManager {
	private final List<Update> updates;
	private final ReentrantLock lock;
	private final String name;

	private Iterator<Update> it;



	public MicroBlogUpdatesManager(String name_, List<Update> l_, ReentrantLock lock_) {
		name = name_;
		updates = l_;
		lock = lock_;
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