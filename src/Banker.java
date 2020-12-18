/**
 * Banker player in the Cee-lo Dice Game
 *
 * @author
 */
public class Banker {
  /** The number of chips in the bank */
  private int chips;
  /** The score of the banker, if the banker didn't automatically win/lose the round */
  private int score;

  /** A dice for this banker to roll */
  private final Dice DICE1;
  /** A dice for this banker to roll */
  private final Dice DICE2;
  /** A dice for this banker to roll */
  private final Dice DICE3;


  /**
   * Initializes an instance of a banker
   * @param initialChips The amount of chips the bank starts with
   * @param diceSides The number of sides the dice the banker rolls should have
   */
  public Banker(int initialChips, int diceSides) {
    chips = initialChips;

    DICE1 = new Dice(diceSides);
    DICE2 = new Dice(diceSides);
    DICE3 = new Dice(diceSides);
  }


  /**
   * Rolls this banker's dice
   */
  public void roll() {
    DICE1.roll();
    DICE2.roll();
    DICE3.roll();
  }


  /**
   * Returns the value on this banker's first dice
   * @return Result from rolling the first dice
   */
  public int getDice1Value() {
    return DICE1.getResult();
  }

  /**
   * Returns the value on this banker's second dice
   * @return Result from rolling the second dice
   */
  public int getDice2Value() {
    return DICE2.getResult();
  }

  /**
   * Returns the value on this banker's third dice
   * @return Result from rolling the third dice
   */
  public int getDice3Value() {
    return DICE3.getResult();
  }


  /**
   * Gets the score of this banker
   * @return This banker's score
   */
  public int getScore() {
    return score;
  }

  /**
   * Updates this banker's score
   * @param newScore This banker's new score
   */
  public void setScore(int newScore) {
    score = newScore;
  }


  /**
   * Gets the number of chips left in the bank
   * @return Number of chips left in this bank
   */
  public int getChips() {
    return chips;
  }

  /**
   * Updates the amount of chips in this bank
   * @param delta Number of chips to add or subtract from bank
   */
  public void updateBalance(int delta) {
    chips += delta;
  }


  /**
   * Returns whether the bank is broken
   * @return true if the bank is not broken
   */
  public boolean notBroken() {
    return chips > 0;
  }
}
