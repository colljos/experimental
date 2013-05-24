package uk.co.cmcmarkets.orderbook.producer;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uk.co.cmcmarkets.orderbook.iface.Action;
import uk.co.cmcmarkets.orderbook.iface.Command;
import uk.co.cmcmarkets.orderbook.iface.OrderProducer;

public class OrderProducerFromXml implements OrderProducer {

	private final String pathname;

	public OrderProducerFromXml(String pathname) {
		this.pathname = pathname;
	}
	
	@Override
	public void startProcessing() {
		System.out.println("OrderProducerFromXml started processing...");
	}

	@Override
	public void finishProcessing() {
		System.out.println("OrderProducerFromXml finished processing.");
	}

	@Override
	public List<Command> pull() {
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder parser = dbf.newDocumentBuilder();
			
			FileInputStream fis = new FileInputStream(pathname);
			Document doc = parser.parse(fis);
			return extractCommands(doc);

		} catch (Exception e) {
			// ParserConfigurationException e
			// FileNotFoundException e
			// SAXException e
			// IOException e
			throw new IllegalStateException(e);	// wrap as run-time
		}
	}

	private List<Command> extractCommands(Document doc) {
		
		List<Command> commands = new ArrayList<Command>();
		
		Element commandsElem = doc.getDocumentElement();
		NodeList nodes = commandsElem.getChildNodes();
	    for (int i = 0; i < nodes.getLength(); i++) {
	    	Node item = nodes.item(i);
	    	if (item.getNodeName() == "add") {
	    		NamedNodeMap attribs = item.getAttributes();
	    		Node orderid = attribs.getNamedItem("order-id");
	    		Node symbol = attribs.getNamedItem("symbol");
	    		Node type = attribs.getNamedItem("type");
	    		Node price = attribs.getNamedItem("price");
	    		Node quantity = attribs.getNamedItem("quantity");
	    		
	    		Command cmd = new Command(Action.ADD, parseLong(orderid), symbol.getNodeValue(), parseBuySell(type), parseInt(price), parseInt(quantity));
	    		commands.add(cmd);
	    	}
	    	else if (item.getNodeName() == "edit") {
	    		NamedNodeMap attribs = item.getAttributes();
	    		Node orderid = attribs.getNamedItem("order-id");
	    		Node price = attribs.getNamedItem("price");
	    		Node quantity = attribs.getNamedItem("quantity");

	    		Command cmd = new Command(Action.EDIT, parseLong(orderid), null, true, parseInt(price), parseInt(quantity));
	    		commands.add(cmd);
	    	}
	    	else if (item.getNodeName() == "remove") {
	    		NamedNodeMap attribs = item.getAttributes();
	    		Node orderid = attribs.getNamedItem("order-id");

	    		Command cmd = new Command(Action.REMOVE, parseLong(orderid), null, true, -1, -1);
	    		commands.add(cmd);
	    	}
		}
		
		return commands;
	}


	private int parseInt(Node value) {
		return Integer.valueOf(value.getNodeValue());	 						
		// catch(NumberFormatException e) {
	}

	private long parseLong(Node value) {
		return Long.valueOf(value.getNodeValue());
		// throws NumberFormatException
	}

	private boolean parseBuySell(Node type) {		
		String value = type.getNodeValue(); 
		if ("buy".equalsIgnoreCase(value))
			return true;
		else if ("sell".equalsIgnoreCase(value))
			return false;

		throw new IllegalArgumentException("Invalid order type [" + value + "]. Must be 'buy' or 'sell'");
	}
}
