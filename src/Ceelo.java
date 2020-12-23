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

  /** outcome status representing a lost */
  private static final int OUTCOME_LOSE = 0;
  /** outcome status representing a win */
  private static final int OUTCOME_WIN = 10;
  /** outcome status representing an invalid roll that needs to be rerolled */
  private static final int OUTCOME_REROLL = -1;

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
      if (player1.getChips() > player2.getChips() && player1.getChips() > player3.getChips()) {
        System.out.println(player1.getName() + " won the game with " + player1.getChips() + " chips!");
        updateTopScore(player1);
      } else if (player2.getChips() > player1.getChips() && player2.getChips() > player3.getChips()) {
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

    //gameplay

    bankerTurn(banker, player1, player2, player3);

    playerTurn(player1, banker);
    playerTurn(player2, banker);
    playerTurn(player3, banker);
  }

  /**
   * Logic for the banker's turn
   * @param banker The banker playing this turn
   * @param player1 Player 1 involved in the game
   * @param player2 Player 2 involved in the game
   * @param player3 Player 3 involved in the game
   */
  private static void bankerTurn(Banker banker, Player player1, Player player2, Player player3) {
    //determine what the banker does based on dice rolls
    int outcome = OUTCOME_REROLL;
    while (outcome == OUTCOME_REROLL) {
      banker.roll();
      outcome = determineOutcome(banker.getDice1Value(), banker.getDice2Value(), banker.getDice3Value());
    }

    //carry out what the outcome is
    if (outcome == OUTCOME_LOSE || outcome == OUTCOME_WIN) { //cases where the banker wither loses or wins

      int multiplier;
      if (outcome == OUTCOME_LOSE) multiplier = -1; //banker loses round - player wins round
      else multiplier = 1; //banker wins round - player loses round

      banker.updateBalance(multiplier * player1.getWager());
      player1.adjustBalance(outcome == OUTCOME_LOSE);

      banker.updateBalance(multiplier * player2.getWager());
      player2.adjustBalance(outcome == OUTCOME_LOSE);

      banker.updateBalance(multiplier * player3.getWager());
      player3.adjustBalance(outcome == OUTCOME_LOSE);

      banker.setScore(0);

    } else { //banker "scores" instead of win/lose
      banker.setScore(outcome);
    }

    //print outcome
    System.out.println("The banker rolled a " + banker.getDice1Value() + ", " + banker.getDice2Value() + " and a " + banker.getDice3Value());

    if (outcome == OUTCOME_WIN) System.out.print("The banker won this round");
    else if (outcome == OUTCOME_LOSE) System.out.print("The banker lost this round");
    else System.out.println("The banker scored a " + banker.getScore());

    System.out.println(" and has " + banker.getChips() + " chips left");
  }

  private static void playerTurn(Player player, Banker banker) {
    if (player.stillInGame() && banker.notBroken()) {



    } else if (!player.stillInGame())
      System.out.println(player.getName() + " ran out of chips and is out of the game");
  }

  /**
   *
   * @param roll1
   * @param roll2
   * @param roll3
   * @return 0 for lose round, 10 for win round, -1 for reroll, int from 1-6 for score
   */
  private static int determineOutcome(int roll1, int roll2, int roll3) {

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
