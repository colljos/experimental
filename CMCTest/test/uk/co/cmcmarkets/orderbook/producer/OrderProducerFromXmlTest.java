package uk.co.cmcmarkets.orderbook.producer;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.co.cmcmarkets.orderbook.iface.Command;

public class OrderProducerFromXmlTest {

	private final String resource1 = "resources/orders1.xml"; 
	private final String resource2 = "resources/orders2.xml";
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testPull() {
		
		OrderProducerFromXml producer = new OrderProducerFromXml(resource1);
		List<Command> commands = producer.pull();
		assertEquals(commands.size(), 13, commands.size()); 
	}
	
	@Test
	public void testPull2() {
		
		OrderProducerFromXml producer = new OrderProducerFromXml(resource2);
		List<Command> commands = producer.pull();
		assertEquals(10000, commands.size()); 
	}	
}
