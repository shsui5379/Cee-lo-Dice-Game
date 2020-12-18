import java.util.Scanner;

/**
 * The Cee-lo Dice Game
 *
 * @author
 */
public class Ceelo {
  /** Tracks the highest score through the runtime of this program */
  private static int topScore;
  /** Name of the player who holds topScore */
  private static String topScorer = "";

  /** The number of chips a banker starts with in this game */
  private static final int BANKER_INITIAL_CHIPS = 1000;
  /** The number of chips a player stars with in this game */
  private static final int PLAYER_INITIAL_CHIPS = 100;

  /** The number of sides a dice has in this game */
  private static final int DICE_SIDES = 6;


  /** Scanner for getting user input */
  Scanner scanner = new Scanner(System.in);

  /**
   * Launching point of this game
   */
  public void play() {
    //setting up the game
    Banker banker = new Banker(BANKER_INITIAL_CHIPS, DICE_SIDES);
    String input;

    System.out.print("Player 1, what is your name?  ");
    input = scanner.nextLine();
    Player player1 = new Player(input, PLAYER_INITIAL_CHIPS, DICE_SIDES);

    System.out.print("Player 2, what is your name?  ");
    input = scanner.nextLine();
    Player player2 = new Player(input, PLAYER_INITIAL_CHIPS, DICE_SIDES);

    System.out.print("Player 3, what is your name?  ");
    input = scanner.nextLine();
    Player player3 = new Player(input, PLAYER_INITIAL_CHIPS, DICE_SIDES);

    System.out.println("Welcome to the Cee-lo Dice Game, " + player1.getName() + ", " + player2.getName() + " and " + player3.getName() + "!");


    //launching rounds if possible
    while ((player1.stillInGame() || player2.stillInGame() || player3.stillInGame()) && banker.notBroken())
      playRound(banker, player1, player2, player3);


    //end game
    if (banker.notBroken()) System.out.println("The banker won the game with " + banker.getChips() + " chips!");
    else {
      if (player1.stillInGame()) {
        System.out.println(player1.getName() + " won the game with " + player1.getChips() + " chips!");
        updateTopScore(player1);
      } else if (player2.stillInGame()) {
        System.out.println(player2.getName() + " won the game with " + player2.getChips() + " chips!");
        updateTopScore(player2);
      } else {
        System.out.println(player3.getName() + " won the game with " + player3.getChips() + " chips!");
        updateTopScore(player3);
      }
    }

    printMenu();
  }


  /**
   * Makes the players play during this round
   * @param banker the banker in this game
   * @param player1 player 1 of this game
   * @param player2 player 2 of this game
   * @param player3 player 3 of this game
   */
  private static void playRound(Banker banker, Player player1, Player player2, Player player3) {
    //wagers
    setPlayerWager(player1);
    setPlayerWager(player2);
    setPlayerWager(player3);

    bankerTurn(banker);

    playerTurn(player1);
    playerTurn(player2);
    playerTurn(player3);
  }

  private static void bankerTurn(Banker banker) {

  }

  private static void playerTurn(Player player) {

  }


  private static void determineOutcome() {

  }


  private static void printPlayerOptions() {

  }

  private static void setPlayerWager(Player player) {

  }


  private void printMenu() {

  }

  private static void updateTopScore(Player player) {

  }

  private static void printTopScore() {

  }
}
