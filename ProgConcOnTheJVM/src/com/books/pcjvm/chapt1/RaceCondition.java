package com.books.pcjvm.chapt1;

public class RaceCondition {

	private static boolean done;

	public static void main(String[] args) throws InterruptedException {
		
		new Thread(
			new Runnable() {
				@Override
				public void run() {
					int i = 0;
					while(!done) { i++; }
					System.out.println("Done!");
				}
			}
		).start();
		
		System.out.println("OS: " + System.getProperty("os.name"));
		Thread.sleep(2000);
		done = true;
		System.out.println("flag done set to true");
	}
}
