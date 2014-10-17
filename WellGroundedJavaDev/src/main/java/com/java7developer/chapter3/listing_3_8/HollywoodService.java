package com.java7developer.chapter3.listing_3_8;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.java7developer.chapter3.AgentFinder;

/**
 * Code for listing 3_8
 * 
 * @author Ben Evans and Martijn Verburg
 */
public class HollywoodService {
  
  private AgentFinder finder = null;

  @Inject
  public HollywoodService(@Named("primary") AgentFinder finder) {
    this.finder = finder;
  }
}

