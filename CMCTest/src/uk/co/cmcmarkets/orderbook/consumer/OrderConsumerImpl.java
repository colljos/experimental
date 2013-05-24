package uk.co.cmcmarkets.orderbook.consumer;

import uk.co.cmcmarkets.orderbook.book.BookImpl;
import uk.co.cmcmarkets.orderbook.book.OrderBookException;
import uk.co.cmcmarkets.orderbook.book.views.BookViewByLevelImpl;
import uk.co.cmcmarkets.orderbook.iface.Action;
import uk.co.cmcmarkets.orderbook.iface.Book;
import uk.co.cmcmarkets.orderbook.iface.Log;
import uk.co.cmcmarkets.orderbook.iface.LogLevel;
import uk.co.cmcmarkets.orderbook.iface.Order;
import uk.co.cmcmarkets.orderbook.iface.OrderConsumer;

public class OrderConsumerImpl implements OrderConsumer
{
    private Book book = new BookImpl(); 
	private Log log;

	@Override
    public void startProcessing(Log log)
    {
		this.log = log;
		book.attach(new BookViewByLevelImpl());
    }

    @Override
    public void finishProcessing()
    {
    	book.write(log);
    }

    @Override
    public void handleEvent(Action action, Order order)
    {
    	assert(action != null);
    	assert(order != null);
    	
    	try {
    		if (Action.ADD == action) {
    			book.add(order);
    		}
    		else if (Action.EDIT == action) {
    			book.edit(order);
    		}
    		else if (Action.REMOVE == action) {
    			book.remove(order);
    		}
    		
		} catch (OrderBookException e) {
			log.log(LogLevel.ERROR, e.getMessage());
		}
    }

}
