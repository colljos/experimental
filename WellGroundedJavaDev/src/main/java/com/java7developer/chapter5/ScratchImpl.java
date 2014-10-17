package com.java7developer.chapter5;

public class ScratchImpl {

	private static ScratchImpl instance = null;

	// Constructor
	private ScratchImpl() {
		super();
	}

	/*
	 * This is where your actual code will go
	 */
	private void run() {
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		instance = new ScratchImpl();
		instance.run();
	}

}
