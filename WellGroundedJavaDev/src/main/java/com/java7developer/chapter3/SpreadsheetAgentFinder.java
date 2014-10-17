package com.java7developer.chapter3;

import java.util.List;

/**
 * Code for listing 3_1
 * 
 * @author Ben Evans and Martijn Verburg
 */
public class SpreadsheetAgentFinder implements AgentFinder {

  /** The type of spreadsheet we are dealing with */
  private String type;
  
  /** The location of the spreadsheet */
  private String path;
  
	@Override
	public List<Agent> findAllAgents() {
		return null;
	}

  public void setType(String type)
  {
    this.type = type;
  }
  
  public void setPath(String path)
  {
    this.path = path;
  }
  
}