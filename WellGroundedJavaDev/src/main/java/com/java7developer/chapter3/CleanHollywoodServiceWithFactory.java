package com.java7developer.chapter3;

import java.util.ArrayList;
import java.util.List;

/**
 * Code for listing 3_4
 * 
 * @author Ben Evans and Martijn Verburg
 */
public class CleanHollywoodServiceWithFactory {

	public static void main(String[] args)
	{
		CleanHollywoodServiceWithFactory service = new CleanHollywoodServiceWithFactory();
		AgentFinderFactory factory = AgentFinderFactory.getInstance();
		AgentFinder finder = factory.getAgentFinder(args[0]);
		service.emailFriendlyAgents(finder);
	}

	public void emailFriendlyAgents(AgentFinder finder)
	{
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
