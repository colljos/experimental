package com.java7developer.chapter3;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * Code for listing 3_6 - AgentFinder interface
 * 
 * @author Ben Evans and Martijn Verburg
 */
public class AgentFinderModule extends AbstractModule {

	@Override
	protected void configure()
	{
	    bind(AgentFinder.class).to(WebServiceAgentFinder.class);
	}

	@Provides AgentFinder provideAgentFinder()
	{
		return new SpreadsheetAgentFinder();
	}
	
}
