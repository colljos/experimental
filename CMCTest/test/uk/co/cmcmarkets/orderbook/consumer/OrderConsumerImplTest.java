package uk.co.cmcmarkets.orderbook.consumer;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.co.cmcmarkets.orderbook.iface.Command;
import uk.co.cmcmarkets.orderbook.iface.Log;
import uk.co.cmcmarkets.orderbook.iface.LogLevel;
import uk.co.cmcmarkets.orderbook.iface.OrderConsumer;
import uk.co.cmcmarkets.orderbook.iface.OrderProducer;
import uk.co.cmcmarkets.orderbook.producer.OrderProducerHardCoded;

public class OrderConsumerImplTest {

	private OrderConsumer consumer;
	private OrderProducer producer;
	
	private Log log = new Log() {
		public void log(LogLevel logLevel, String msg) {
			System.out.println(msg);
		}};

	@Before
	public void setUp() throws Exception {
		
		consumer = new OrderConsumerImpl();
		producer = new OrderProducerHardCoded();
		consumer.startProcessing(log);
	}

	@Test
	public void testStartProcessing() {
		
	}

	@Test
	public void testFinishProcessing() {
		
		consumer.finishProcessing();
	}

	@Test
	public void testHandleEvent() {
	
		consumer.startProcessing(log);
		
		List<Command> orders = producer.pull();
		for (Command command : orders) {			
			consumer.handleEvent(command.getAction(), command.getOrder());
		}
		
		consumer.finishProcessing();
	}

}
