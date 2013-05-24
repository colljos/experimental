package com.jc.algorithm;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FrequencyTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testMaxFrequency() {
		
		List<Integer> list = Arrays.asList(1,2,3,4,5,3,4,6,7,8);
		List<Integer> expectedResult = Arrays.asList(3,4);
		
		assertEquals(expectedResult, Frequency.max(list));
	}

}
