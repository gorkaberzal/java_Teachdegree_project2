package com.teamtreehouse;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;

import java.util.Comparator;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.NumberFormatException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.HashSet;

public class Prompter implements Comparator<Player>{
  public String mTeamName;
  private String mCoach;
  private Team mTeam;
  private Teams mTeams;
  private Player mPlayer;
  private Players mPlayers;
  private int MAX_PLAYERS;
  
  
  private List<Player> mListPlayers;  
  
  private List<Player> mTotalPlayers;
  
  private BufferedReader mReader;
  
  private Map<String, String> mMenu;
  
  private Map<Team, List<Player>> byTeam;
  
  private Map<String, Set<Player>> byHeight;
  
  
  public Prompter() {
    mReader = new BufferedReader(new InputStreamReader (System.in));
    mMenu = new HashMap<>();
    mMenu.put("create", "Create a new team for the new season");
    mMenu.put("add", "Add a player to a choosen a team");
    mMenu.put("remove", "Remove a player from a team");
    mMenu.put("report", "View a report of a team by height");
    mMenu.put("balance", "View the League Balance Report");
    mMenu.put("roster", "View roster");
    mMenu.put("quit", "Exiting the League Manager"); 
    byTeam = new TreeMap<>();
    byHeight = new TreeMap<>();
    mListPlayers = new ArrayList<>(Arrays.asList(mPlayers.load()));
    mTotalPlayers = new ArrayList<>(Arrays.asList(mPlayers.load()));
    Collections.sort(mListPlayers);
    MAX_PLAYERS = 11;
  }
  
  private String promptAction() throws IOException {
    for (Map.Entry<String, String> option : mMenu.entrySet()) {
      System.out.printf("%s - %s %n",
                        option.getKey(),
                        option.getValue());
    }
    System.out.println();
    System.out.print("What do you want to do:  ");
    System.out.println();
    String choice = mReader.readLine();
    return choice.trim().toLowerCase();
  }
  
    public void run() {
    String choice = "";
    do {
      try {
        choice = promptAction();
        switch(choice) {
          case "create":
            if (mTeams == null) {
              mTeams = new Teams();
            }
            if(mTotalPlayers.size() / MAX_PLAYERS > mTeams.getTeamsCount()){
              Team team = promptNewTeam();
              mTeams.addTeam(team);
              System.out.printf("%s was crated!  %n%n", team);
            } else {
              System.out.printf(" Sorry, there are not enough players to create another team. %n%n"); 
            }
            break;
          case "add":
            Team promptTeam = promptTeam();
            System.out.printf("You chose:  %s %n", promptTeam);
            Player promptPlayer;
          //Avoid adding players when reached the maximun MAX_PLAYERS
            if (!byTeam.isEmpty() && byTeam.get(promptTeam) != null && byTeam.get(promptTeam).size() > MAX_PLAYERS -1) {
              System.out.printf("Sorry the team has the maximum number of players assigned! %n%n");
            } else {
            promptPlayer = promptPlayerFromPlayers();
            System.out.printf("You chose: %s %n%n", promptPlayer);            
            addPlayerToTeam(promptTeam, promptPlayer); 
            }
            break;
          case "remove":
              promptTeam = promptTeam();   
              System.out.printf("You chose:  %s %n", promptTeam);
              List<Player> listPromptPlayer = new ArrayList<>();
              listPromptPlayer = byTeam.get(promptTeam);
              Collections.sort(listPromptPlayer);
              promptPlayer = promptPlayerByTeam(listPromptPlayer);
              mListPlayers.add(promptPlayer);
              Collections.sort(mListPlayers);
              listPromptPlayer.remove(promptPlayer);
              
            break;  
          case "report":  
            promptTeam = promptTeam();
            System.out.printf("You chose:  %s %n", promptTeam);
            byHeight = new TreeMap<>();
            listPromptPlayer = new ArrayList<>();
            listPromptPlayer = byTeam.get(promptTeam);
            int count35_40 = 0;
            int count41_46 = 0;
            int count47_50 = 0;
            for (Player p : listPromptPlayer) {
              if(p.getHeightInInches() < 41 ) {
                addPlayerByHeight("35-40", p);
                count35_40 ++;
              }
              if(p.getHeightInInches() < 47 && p.getHeightInInches() > 40 ) {
                addPlayerByHeight("41-46", p);
                count41_46 ++;
              }
              if(p.getHeightInInches() > 46) {
                addPlayerByHeight("47-50", p);
                count47_50 ++;
              }
            }
            System.out.printf("Height ranges: %s%n  ", byHeight.entrySet());
            System.out.printf("%d players in the height range 35-40 %n",  count35_40);
            System.out.printf("%d players in the height range 41-46 %n",  count41_46);          
            System.out.printf("%d players in the height range 47-50 %n%n",  count47_50);          

            
            break;          
          case "roster":
            promptTeam = promptTeam();
            System.out.printf("You chose:  %s %n", promptTeam);
            listPromptPlayer = byTeam.get(promptTeam);
            Collections.sort(listPromptPlayer);
            for(Player p : listPromptPlayer) {
              System.out.printf(String.format("%s ", p));  
            }
            System.out.println();
            break;
          
          case "balance":
            
            promptTeam = promptTeam();
            System.out.printf("You chose:  %s %n", promptTeam);
            listPromptPlayer = byTeam.get(promptTeam);
            Collections.sort(listPromptPlayer, new Prompter());
            int count = 0;
            for(Player p : listPromptPlayer) {
              System.out.printf(String.format("%s ", p)); 
              if(p.isPreviousExperience()) {
                count ++;
              }
            }
            System.out.printf("The count of experienced players in this team is:  %d%n", count);
            System.out.printf("The count of inexperienced players in this team is:  %d%n", listPromptPlayer.size() - count);
            System.out.printf("The average experience level for this team is %d%% %n%n", count * 100 / listPromptPlayer.size());
          
            break;          
          case "quit":
            System.out.println("Thanks for having used the League Manager");
            break;
          default:
            System.out.printf("Uknown choice: '%s' Try again. %n%n%n",
                              choice);
        }
      } catch (IOException ioe) {
        System.out.println("Problem with input");
        ioe.printStackTrace();
      } catch (NumberFormatException nfe) {
        System.out.println("Problem with input");
        nfe.printStackTrace();
      }
    } while (!choice.equals("quit"));
  }
  
  private Team promptNewTeam() throws IOException {
    System.out.println("Enter the team's name:  ");
    String team = mReader.readLine();
    System.out.println("Enter the coach:  ");
    String coach = mReader.readLine();
    mTeamName = team;
    mCoach = coach;
    return new Team(team, coach);
  } 
  
  public void setTeamName(String teamName) {
    mTeamName = teamName;
  }
  
  public String getTeamName() {
    return mTeamName; 
  }
  
  public String getCoach() {
    return mCoach; 
  }
  
 
  public Team promptTeam() throws IOException, NumberFormatException {
    List<Team> teams = new ArrayList<>(mTeams.getTeams());
    int index = promptForIndexTeam(teams);
    return teams.get(index);
  }
  
  private int promptForIndexTeam(List<Team> options) throws IOException, NumberFormatException {
    int counter = 1;
    for (Team option : options) {
      System.out.printf("%d.)  %s %n", counter, option);
      counter++;
    }
    System.out.print("Your choice:  ");
    String optionAsString = mReader.readLine();
    int choice = Integer.parseInt(optionAsString.trim());
    return choice -1;
  }
  
  private Player promptPlayerFromPlayers() throws IOException, NumberFormatException {
    int index = promptForIndexPlayer(mListPlayers);
    Player promptPlayer = mListPlayers.get(index);
    mListPlayers.remove(index);    
    return promptPlayer;
  }
  
  private Player promptPlayerByTeam(List<Player> listPromptPlayer) throws IOException {
    int index = promptForIndexPlayer(listPromptPlayer);
    Player promptPlayer = listPromptPlayer.get(index);
    return promptPlayer;
  }
  
  private int promptForIndexPlayer(List<Player> options) throws IOException {
    int counter = 1;
    for (Player option : options) {
      System.out.printf("%d.) %s ", counter, option);
      counter++;
    }
    System.out.print("Your choice:  ");
    String optionAsString = mReader.readLine();
    int choice = Integer.parseInt(optionAsString.trim());
    return choice -1;
  }
  
  public void addPlayerToTeam(Team promptTeam, Player promptPlayer) {
    List<Player> teamPlayers = byTeam.get(promptTeam);
    if (teamPlayers == null) {
      teamPlayers = new ArrayList<>(); 
      byTeam.put(promptTeam, teamPlayers);
    }
      teamPlayers.add(promptPlayer);
      Collections.sort(teamPlayers);
  }
  
  public void addPlayerByHeight(String groupHeight, Player heightPlayer) {
    Set<Player> setHeightPlayer = byHeight.get(groupHeight);
    if(setHeightPlayer == null) {
      setHeightPlayer = new HashSet<>();
    }
      byHeight.put(groupHeight, setHeightPlayer);
      setHeightPlayer.add(heightPlayer);
  }
  
  @Override
  public int compare(Player other, Player another) {
    int i = other.getHeightInInches();
    int j = another.getHeightInInches();
    if(i < j) {
      return -1; 
    }
    if(i == j) {
      return 0; 
    }
    if(i > j) {
      return 1; 
    }
    return 0;
  }
/*
  @Override
  public String toString() {
    return String.format("Team:  %s coached by %s", mTeamName, mCoach); 
  }*/
  
}