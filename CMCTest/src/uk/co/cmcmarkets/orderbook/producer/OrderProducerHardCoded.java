package uk.co.cmcmarkets.orderbook.producer;

import java.util.Arrays;
import java.util.List;

import uk.co.cmcmarkets.orderbook.iface.Action;
import uk.co.cmcmarkets.orderbook.iface.Command;
import uk.co.cmcmarkets.orderbook.iface.OrderProducer;

public class OrderProducerHardCoded implements OrderProducer {

	static final Command[] commands =
        new Command[] { new Command(Action.ADD, 1L, "MSFT.L", true, 5, 200),
                        new Command(Action.ADD, 2L, "VOD.L", true, 15, 100),
                        new Command(Action.ADD, 3L, "MSFT.L", false, 5, 300),
                        new Command(Action.ADD, 4L, "MSFT.L", true, 7, 150),
                        new Command(Action.REMOVE, 1L, null, true, -1, -1),
                        new Command(Action.ADD, 5L, "VOD.L", false, 17, 300),
                        new Command(Action.ADD, 6L, "VOD.L", true, 12, 150),
                        new Command(Action.EDIT, 3L, null, true, 7, 200),
                        new Command(Action.ADD, 7L, "VOD.L", false, 16, 100),
                        new Command(Action.ADD, 8L, "VOD.L", false, 19, 100),
                        new Command(Action.ADD, 9L, "VOD.L", false, 21, 112),
                        new Command(Action.REMOVE, 5L, null, false, -1, -1) };
	
	@Override
	public void startProcessing() {
		System.out.println("OrderProducerHardCoded started processing...");
	}

	@Override
	public void finishProcessing() {
		System.out.println("OrderProducerHardCoded finished processing.");
	}

	@Override
	public List<Command> pull() {
		return Arrays.asList(commands);
	}
}
