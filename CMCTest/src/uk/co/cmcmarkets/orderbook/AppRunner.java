package uk.co.cmcmarkets.orderbook;

import uk.co.cmcmarkets.orderbook.app.AppEnvironmentImpl;
import uk.co.cmcmarkets.orderbook.consumer.OrderConsumerImpl;
import uk.co.cmcmarkets.orderbook.iface.AppEnvironment;
import uk.co.cmcmarkets.orderbook.iface.LogLevel;

public class AppRunner
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        AppEnvironment environment = new AppEnvironmentImpl(LogLevel.INFO);
        environment.registerHandler(new OrderConsumerImpl());
        environment.run();
    }
}
