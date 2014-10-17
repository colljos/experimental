package com.java7developer.chapter4.listing_4_1;

import java.util.Map;
import java.util.HashMap;

import com.java7developer.chapter4.SimpleMicroBlogNode;
import com.java7developer.chapter4.Update;

public class ExampleTimingNode implements SimpleMicroBlogNode {

    private final String identifier;

    private final Map<Update, Long> arrivalTime = new HashMap<>();
	
    public ExampleTimingNode(String identifier_) {
      identifier = identifier_;
    }

    public String getIdentifier() {
      return identifier;
    }

    public synchronized void propagateUpdate(Update update_) {
      long currentTime = System.currentTimeMillis();
      arrivalTime.put(update_, currentTime);
    }

    public synchronized boolean confirmUpdateReceived(Update update_) {
      Long timeRecvd = arrivalTime.get(update_);
   return timeRecvd != null;
    }

  }