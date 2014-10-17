package com.java7developer.chapter3;

import com.java7developer.chapter3.listing_3_8.AgentFinderModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Code for listing 3_7 - AgentFinder interface
 * 
 * @author Ben Evans and Martijn Verburg
 */
public class HollyWoodServiceClient {

	public static void main(String[] args) {

		Injector injector = Guice.createInjector(new AgentFinderModule());
	    HollywoodServiceGuice hollyWoodService = injector.getInstance(HollywoodServiceGuice.class);
	    hollyWoodService.emailFriendlyAgents();
	  }
	
}
