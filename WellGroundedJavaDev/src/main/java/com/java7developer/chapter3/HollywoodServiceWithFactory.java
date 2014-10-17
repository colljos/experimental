package com.java7developer.chapter3;

import java.util.ArrayList;
import java.util.List;

/**
 * Code for listing 3_3 - AgentFinder interface
 * 
 * @author Ben Evans and Martijn Verburg
 */
public class HollywoodServiceWithFactory {

	public static void main(String[] args)
	{
		HollywoodServiceWithFactory service = new HollywoodServiceWithFactory();
		service.emailFriendlyAgents(args[0]);
	}

	public void emailFriendlyAgents(String agentFinderType)
	{
		AgentFinderFactory factory = AgentFinderFactory.getInstance();
		AgentFinder finder = factory.getAgentFinder(agentFinderType);
		List<Agent> agents = finder.findAllAgents();
		List<Agent> friendlyAgents = filterAgents(agents, "Java Developers");
	}
	
	public static List<Agent> filterAgents(List<Agent> agents, String agentType)
	{
		List<Agent> filteredAgents = new ArrayList<>();
		for (Agent agent:agents)
		{
			if (agent.getType().equals("Java Developers"))
			{
				filteredAgents.add(agent);
			}
		}
		return filteredAgents;
	}
}
