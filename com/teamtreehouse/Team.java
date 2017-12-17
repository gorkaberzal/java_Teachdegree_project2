package com.teamtreehouse;

public class Team implements Comparable<Team>{
  private String mTeamName;
  private String mCoach;
  
  
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
