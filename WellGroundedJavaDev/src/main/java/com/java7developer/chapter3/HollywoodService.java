package com.java7developer.chapter3;

import java.util.ArrayList;
import java.util.List;

/**
 * Code for listing 3_2
 * 
 * @author Ben Evans and Martijn Verburg
 */
public class HollywoodService {

	public static void main(String[] args)
	{
		HollywoodService service = new HollywoodService();
		service.emailFriendlyAgents();
	}

	public void emailFriendlyAgents()
	{
		AgentFinder finder = new SpreadsheetAgentFinder();
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
