/**
 * A dice
 *
 * @author
 */
public class Dice {
  /** The outcome of rolling this dice */
  private int result;
  /** The number of sides this dice has */
  private final int SIDES;


  /**
   * Creates a dice with the specified number of sides
   * @param sides The number of sides this dice has
   */
  public Dice(int sides) {
    SIDES = sides;
  }

  /**
   * Rolls this dice
   */
  public void roll() {
      result = (int) (Math.random() * SIDES) + 1;
  }

  /**
   * Returns the result of rolling this dice
   * @return Result of rolling this dice
   */
  public int getResult() {
    return result;
  }
}
