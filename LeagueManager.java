import com.teamtreehouse.Team;
import com.teamtreehouse.Teams;
import com.teamtreehouse.Prompter;
import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;

public class LeagueManager {

  public static void main(String[] args) {
    Player[] players = Players.load();
    System.out.printf("There are currently %d registered players.%n%n", players.length);
    // Your code here!

    Prompter prompter = new Prompter();
    prompter.run();
  }

}
