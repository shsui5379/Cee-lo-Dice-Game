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
  private static final Scanner SCANNER = new Scanner(System.in);

  /**
   * Launching point of this game
   */
  public void play() {
    //setting up the game
    Banker banker = new Banker(BANKER_INITIAL_CHIPS, DICE_SIDES);
    String input;

    System.out.print("Player 1, what is your name?  ");
    input = SCANNER.nextLine();
    Player player1 = new Player(input, PLAYER_INITIAL_CHIPS, DICE_SIDES);

    System.out.print("Player 2, what is your name?  ");
    input = SCANNER.nextLine();
    Player player2 = new Player(input, PLAYER_INITIAL_CHIPS, DICE_SIDES);

    System.out.print("Player 3, what is your name?  ");
    input = SCANNER.nextLine();
    Player player3 = new Player(input, PLAYER_INITIAL_CHIPS, DICE_SIDES);

    System.out.println("Welcome to the Cee-lo Dice Game, " + player1.getName() + ", " + player2.getName() + " and " + player3.getName() + "!\n");


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

    System.out.println();

    //gameplay

    bankerTurn(banker, player1, player2, player3);

    playerTurn(player1, banker);
    playerTurn(player2, banker);
    playerTurn(player3, banker);

    System.out.println();
  }

  /**
   * Logic for the banker's turn <br />
   * If the banker wins, the players lose their wager to the banker <br />
   * If the banker loses, the players win their wager from the banker <br />
   * If the banker scores, the players roll
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
    System.out.println("The banker rolled a " + banker.getDice1Value() + ", " + banker.getDice2Value() + ", " + banker.getDice3Value());

    if (outcome == OUTCOME_WIN) System.out.print("The banker won this round");
    else if (outcome == OUTCOME_LOSE) System.out.print("The banker lost this round");
    else System.out.print("The banker scored a " + banker.getScore());

    System.out.println(" and has " + banker.getChips() + " chips left");

    System.out.println(player1.getName() + " has " + player1.getChips() + " chips left");
    System.out.println(player2.getName() + " has " + player2.getChips() + " chips left");
    System.out.println(player3.getName() + " has " + player3.getChips() + " chips left");

    System.out.println();
  }

  /**
   * Logic for a player's turn <br />
   * If the player wins, they win their wager from the banker <br />
   * If the player loses, they lose their wager to the banker
   * @param player The player playing this turn
   * @param banker The banker in this game
   */
  private static void playerTurn(Player player, Banker banker) {
    if (player.stillInGame() && banker.notBroken() && banker.getScore() > 0) {

      //rolling
      int outcome = OUTCOME_REROLL;
      while (outcome == OUTCOME_REROLL) {
        player.roll();
        outcome = determineOutcome(player.getDice1Value(), player.getDice2Value(), player.getDice3Value());
      }

      //action
      int multiplier;
      int wager = player.getWager();
      if (outcome == OUTCOME_LOSE || outcome < banker.getScore()) multiplier = 1; //player loses - banker wins
      else multiplier = -1; //player wins - banker loses

      banker.updateBalance(multiplier * player.getWager());
      player.adjustBalance(outcome == OUTCOME_WIN || outcome >= banker.getScore());

      //print
      System.out.println(player.getName() + " rolled a " + player.getDice1Value() + ", " + player.getDice2Value() + ", " + player.getDice3Value());

      if (outcome != OUTCOME_LOSE && outcome != OUTCOME_WIN) System.out.println(player.getName() + " scored a " + outcome);

      if (outcome == OUTCOME_WIN || outcome >= banker.getScore()) System.out.print(player.getName() + " won " + wager + " chips");
      else System.out.print(player.getName() + " lost " + wager + " chips");

      System.out.println(" and has " + player.getChips() + " chips left");
      System.out.println("The bank now has " + banker.getChips() + " chips");

    } else if (!player.stillInGame() && banker.getScore() > 0) //out of game
      System.out.println(player.getName() + " ran out of chips and is out of the game");
    else if (!banker.notBroken() && banker.getScore() > 0) //bank broke
      System.out.println("The bank broke and the game is over");

    System.out.println();
  }

  /**
   * Output a integer value signalling what a player should do based on dice rolls <br />
   * Wins are for triples and 4, 5, 6 <br />
   * Loses are 1, 2, 3 <br />
   * Scores are the third dice when a double occurs
   * @param roll1 the player's 1st dice value
   * @param roll2 the player's 2nd dice value
   * @param roll3 the player's 3rd dice value
   * @return 0 for lose round, 10 for win round, -1 for reroll, int from 1-6 for score
   */
  private static int determineOutcome(int roll1, int roll2, int roll3) {
    if ((roll1 == roll2 && roll2 == roll3) ||
            ((roll1 == 4 || roll2 == 4 || roll3 == 4) &&
            (roll1 == 5 || roll2 == 5 || roll3 == 5) &&
            (roll1 == 6 || roll2 == 6 || roll3 == 6))
    ) {
      return OUTCOME_WIN;
    } else if ((roll1 == 1 || roll2 == 1 || roll3 == 1) &&
            (roll1 == 2 || roll2 == 2 || roll3 == 2) &&
            (roll1 == 3 || roll2 == 3 || roll3 == 3)) {
      return OUTCOME_LOSE;
    } else if (roll1 == roll2 && roll1 != roll3) {
      return roll3;
    } else if (roll1 == roll3 && roll1 != roll2) {
      return roll2;
    } else if (roll2 == roll3 && roll1 != roll2) {
      return roll1;
    }
    return OUTCOME_REROLL;
  }


  /**
   * Asks the player for a valid amount of chips to wager, and set their wager
   * @param player the player wagering
   */
  private static void setPlayerWager(Player player) {
    if (player.stillInGame()) {
      int input = -1;

      while (input <= 0 || input > player.getChips()) {
        System.out.print(player.getName() + ", you have " + player.getChips() + " chips.  How many chips would you like to wager?  ");
        input = SCANNER.nextInt();
        SCANNER.nextLine();
      }

      player.setWager(input);
    } else {
      System.out.println(player.getName() + ", you have no chips left and cannot wager");
    }
  }


  /**
   * Gives the user options at the end of the game to play a new game, view high score or exit
   */
  private void printMenu() {
    //menu
    System.out.println();
    System.out.println("Press any key to play a new game");
    System.out.println("Press x to exit");
    System.out.println("Press h to view high score");

    //processing

    String input = SCANNER.nextLine().toLowerCase();

    if (input.equals("h")) {
      printTopScore();
      printMenu();
    } else if (!input.equals("x")) {
      play();
    }
  }

  /**
   * updates the tracked top score and scorer
   * @param player player with new top score
   */
  private static void updateTopScore(Player player) {
    if (player.getChips() > topScore) {
      topScore = player.getChips();
      topScorer = player.getName();
    }
  }

  /**
   * Prints the top score of the game and who got that score
   */
  private static void printTopScore() {
    System.out.println();
    System.out.println(topScore + " - " + topScorer);
    System.out.println();
  }
}
