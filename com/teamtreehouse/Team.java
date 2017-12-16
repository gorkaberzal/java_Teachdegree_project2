package com.teamtreehouse;

/*import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.HashMap;
import java.util.TreeSet;*/

public class Team implements Comparable<Team>{
  private String mTeamName;
  private String mCoach;
//  private Team mTeam;
//  private Teams mTeams;
  
  //Prompter prompter = new Prompter();
  
  //private BufferedReader mReader;
  
 // private Map<String, String> mMenu;
  
  
  public Team (String teamName, String coach) {
    mTeamName = teamName;
    mCoach = coach;
  }

  
  public String getTeamName() {
    return mTeamName; 
  }
  
  public String getCoach() {
    return mCoach; 
  }

  
 @Override
  public int compareTo(Team other) {

    return getTeamName().compareTo(other.getTeamName());

  }

  @Override
  public String toString() {
    return String.format("Team:  %s coached by %s", mTeamName, mCoach); 
  }
  
}