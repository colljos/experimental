package com.java7developer.chapter3;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

/**
 * Code for listing 3_5
 * 
 * @author Ben Evans and Martijn Verburg
 */
public class HollywoodServiceGuice {

	AgentFinder agentFinder = null;
	
	@Inject
	public HollywoodServiceGuice(AgentFinder finder)
	{
		this.agentFinder = finder;
	}
	
	public void emailFriendlyAgents()
	{
		List<Agent> agents = agentFinder.findAllAgents();
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
