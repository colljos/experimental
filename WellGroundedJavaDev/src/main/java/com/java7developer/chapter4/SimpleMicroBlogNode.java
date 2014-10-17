package com.java7developer.chapter4;

public interface SimpleMicroBlogNode {
	void propagateUpdate(Update update_);
	boolean confirmUpdateReceived(Update update_);
}
