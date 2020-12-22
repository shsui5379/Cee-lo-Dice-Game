/**
 * A player in a game
 *
 * @author
 */
public class Player {
  /** The number of chips this player has */
  private int chips;
  /** This player's wager */
  private int wager;

  /** This player's name */
  private final String NAME;

  /** A dice for this banker to roll */
  private final Dice DICE1;
  /** A dice for this banker to roll */
  private final Dice DICE2;
  /** A dice for this banker to roll */
  private final Dice DICE3;

  /**
   * Constructs a player
   * @param name Name of the player
   * @param initialChips Number of chips that player starts with
   * @param diceSides Number of sides the dice this player rolls should have
   */
  public Player(String name, int initialChips, int diceSides) {
    NAME = name;

    chips = initialChips;

    DICE1 = new Dice(diceSides);
    DICE2 = new Dice(diceSides);
    DICE3 = new Dice(diceSides);
  }


  /**
   * Rolls this player's dice
   */
  public void roll() {
    DICE1.roll();
    DICE2.roll();
    DICE3.roll();
  }

  /**
   * Returns the value on this player's first dice
   * @return Result from rolling the first dice
   */
  public int getDice1Value() {
    return DICE1.getResult();
  }

  /**
   * Returns the value on this player's second dice
   * @return Result from rolling the second dice
   */
  public int getDice2Value() {
    return DICE2.getResult();
  }

  /**
   * Returns the value on this player's third dice
   * @return Result from rolling the third dice
   */
  public int getDice3Value() {
    return DICE3.getResult();
  }


  /**
   * Sets this player's wager
   * @param newWager this player's wager
   */
  public void setWager(int newWager) {
    wager = newWager;
  }

  /**
   * Returns this player's wager
   * @return player's wager
   */
  public int getWager() {
    return wager;
  }


  /**
   * Returns the number of chips this player has
   * @return Number of chips this player has
   */
  public int getChips() {
    return chips;
  }

  /**
   * Adjusts this player's balance based on whether the player won their wager or not
   * @param didWinWager Whether this player won their wager
   */
  public void adjustBalance(boolean didWinWager) {
    if (didWinWager) chips += wager;
    else chips -= wager;

    wager = 0;
  }


  /**
   * Returns this player's name
   * @return player's name
   */
  public String getName() {
    return NAME;
  }


  /**
   * Reports if this player is still in the game
   * @return true if player is still in the game
   */
  public boolean stillInGame() {
    return chips > 0;
  }
}
