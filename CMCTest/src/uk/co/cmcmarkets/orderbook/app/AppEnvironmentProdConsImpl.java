package uk.co.cmcmarkets.orderbook.app;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import uk.co.cmcmarkets.orderbook.iface.Action;
import uk.co.cmcmarkets.orderbook.iface.AppEnvironment;
import uk.co.cmcmarkets.orderbook.iface.Command;
import uk.co.cmcmarkets.orderbook.iface.Log;
import uk.co.cmcmarkets.orderbook.iface.LogLevel;
import uk.co.cmcmarkets.orderbook.iface.Order;
import uk.co.cmcmarkets.orderbook.iface.OrderConsumer;
import uk.co.cmcmarkets.orderbook.iface.OrderProducer;


public class AppEnvironmentProdConsImpl implements AppEnvironment
{
    private final Set<OrderConsumer> consumers = new LinkedHashSet<OrderConsumer>();
    private final Set<OrderProducer> producers = new LinkedHashSet<OrderProducer>();
    
    private final LogLevel logLevel;
    /**
     * Implementation of the {@link Log} facade which uses the standard out.
     */
    protected final Log log = new Log()
    {
        @Override
        public void log(LogLevel logLevel, String msg)
        {
            if(!isEnabled(logLevel))
            {
            }
            System.out.println(logLevel + ": " + msg);
        }

        private boolean isEnabled(LogLevel logLevel)
        {
            return logLevel.compareTo(AppEnvironmentProdConsImpl.this.logLevel) >= 0;
        }
    };

    public AppEnvironmentProdConsImpl(LogLevel logLevel)
    {
        this.logLevel = logLevel;
    }

    @Override
    public void registerHandler(OrderConsumer handler)
    {
        consumers.add(handler);
    }

    @Override
    public void registerHandler(OrderProducer handler)
    {
        producers.add(handler);
    }

    @Override
    public final void run()
    {
        notifyStart();
        try
        {
            feedOrders();
        }
        catch(Exception e)
        {
            log.log(LogLevel.ERROR, e.getMessage());
        }
        finally
        {
            notifyFinish();
        }
    }

    /**
     * Sends a stream of orders to the {@link OrderConsumer}s.
     * 
     * @see #notifyOrder(Action, Order)
     * @throws Exception if there is an error.
     */
    protected void feedOrders() throws Exception
    {
        for (OrderProducer orderProducer : producers) {
        	List<Command> commands = orderProducer.pull();
        	for(Command command : commands)
        	{
        		notifyOrder(command.getAction(), command.getOrder());
        	}
		}
    }

    /**
     * Invokes {@link OrderConsumer#handleEvent(Action, Order)} for every registered consumer with
     * specified <code>action</code> and <code>order</code>.
     * 
     */
    protected final void notifyOrder(Action action, Order order)
    {
        for(OrderConsumer consumer : consumers)
        {
            consumer.handleEvent(action, order);
        }
    }

    private final void notifyStart()
    {
        for(OrderConsumer consumer : consumers)
        {
            consumer.startProcessing(log);
        }
    }

    private final void notifyFinish()
    {
        for(OrderConsumer consumer : consumers)
        {
            consumer.finishProcessing();
        }
    }
}
