package com.teamtreehouse;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;

import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.TreeMap;


public class Teams {
  private Set<Team> mTeams;
  private Prompter prompter;
  
  public Teams () {
    mTeams = new TreeSet<Team>();
  }
  
  public void addTeam(Team team) {
    mTeams.add(team); 
  }
  
  
  public int getTeamsCount() {
    return mTeams.size(); 
  }
  

  public List<Team> getTeams() {
    List<Team> teamList = new ArrayList<Team>(mTeams);
    return teamList; 
  }
  
}