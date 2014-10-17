package com.java7developer.chapter3;

import java.util.ArrayList;
import java.util.List;

public class Traditional {

	AgentFinder finder;
	
	public List<Agent> friendlyAgentsInHollywood(String arg)
	{
		List<Agent> allAgents = finder.findAllAgents();
		List<Agent> friendlyAgents = new ArrayList<>();
		for (Agent agent : allAgents)
		{
			if (agent.getType().equals(arg))
			{
				friendlyAgents.add(agent);
			}
		}
		return friendlyAgents;
	}
	
}
