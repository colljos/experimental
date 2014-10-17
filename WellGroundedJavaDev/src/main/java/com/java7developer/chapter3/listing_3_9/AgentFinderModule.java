/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java7developer.chapter3.listing_3_9;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.java7developer.chapter3.AgentFinder;
import com.java7developer.chapter3.SpreadsheetAgentFinder;

/**
 * Code for listing 3_9
 * 
 * @author Ben Evans and Martijn Verburg
 */
public class AgentFinderModule extends AbstractModule {

  @Override
  protected void configure() {
  }

  @Provides
  AgentFinder provideAgentFinder() {
    SpreadsheetAgentFinder finder = new SpreadsheetAgentFinder();
    finder.setType("Excel 97");
    finder.setPath("c:/temp/agents.xls");
    return finder;
  }
}