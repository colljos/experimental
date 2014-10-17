package com.java7developer.chapter3;

/**
 * Code for listing 3_3 - AgentFinder interface
 * 
 * @author Ben Evans and Martijn Verburg
 */
public class AgentFinderFactory {

	private static AgentFinderFactory singleton;
	
	private AgentFinderFactory(){}
	
	public static AgentFinderFactory getInstance()
	{
		if (singleton == null)
		{
			singleton = new AgentFinderFactory();
		}
		return singleton;
	}
	
	// Turn into a Java 7 case statements later
	public AgentFinder getAgentFinder(String agentType)
	{
		AgentFinder finder = null;
				
		if (agentType.equals("spreadsheet"))
		{
			finder = new SpreadsheetAgentFinder();
		}
		else if (agentType.equals("webservice"))
		{
			finder = new WebServiceAgentFinder(); 
		}
		else
		{
			finder = new WebServiceAgentFinder();
		}
		return finder;
	}
}
