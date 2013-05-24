package uk.co.cmcmarkets.orderbook;

import uk.co.cmcmarkets.orderbook.app.AppEnvironmentProdConsImpl;
import uk.co.cmcmarkets.orderbook.consumer.OrderConsumerImpl;
import uk.co.cmcmarkets.orderbook.iface.AppEnvironment;
import uk.co.cmcmarkets.orderbook.iface.LogLevel;
import uk.co.cmcmarkets.orderbook.producer.OrderProducerFromXml;

public class AppRunnerXml
{
    /**
     * @param args
     */
    public static void main(String[] args)
    {
    	if (args.length < 1) {
    		System.out.println("Syntax: AppRunnerXml <XML command file>");
    		System.out.println("eg. AppRunnerXml resources/orders1.xml");	
    		System.exit(-1);
    	} 
    	
        AppEnvironment environment = new AppEnvironmentProdConsImpl(LogLevel.INFO);
        
        // Producers
        environment.registerHandler(new OrderProducerFromXml(args[0]));
        
        // Consumers
        environment.registerHandler(new OrderConsumerImpl());
        
        environment.run();
    }
}
