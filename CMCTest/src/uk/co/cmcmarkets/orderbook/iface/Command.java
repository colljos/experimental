package uk.co.cmcmarkets.orderbook.iface;

public class Command {

    private final Action action;
    private final Order order;

    public Command(Action action, long orderId, String symbol, boolean isBuy, int price,
                   int quantity)
    {
        this.action = action;
        this.order = new Order(orderId, symbol, isBuy, price, quantity);
    }

	public Action getAction() {
		return action;
	}

	public Order getOrder() {
		return order;
	}
}
