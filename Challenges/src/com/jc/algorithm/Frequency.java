package com.jc.algorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Frequency {

	public static List<Integer> max(List<Integer> list) {
		
		Map<Integer,Integer> countItems = new HashMap<>();
		
		for (Integer item : list) {
			if (countItems.containsKey(item))
				countItems.put(item, countItems.get(item) + 1);
			else
				countItems.put(item, 1);
		}

		List<Integer> results = new LinkedList<>();
		Integer maxFreq = 0;
		for (Entry<Integer,Integer> entry : countItems.entrySet()) {
			if (entry.getValue() > maxFreq) {
				results.clear();
				results.add(entry.getKey());
				maxFreq = entry.getValue();
			}
			else if (entry.getValue() == maxFreq) {
				results.add(entry.getKey());
			}
		}
		
		return results; 
	}

}
