package model;

/**
 *
 * @author Marek
 */
public interface CardDeck {
  public static final int STD_DECK_SIZE = 52;
  public static final int SAME_COLORED_MAX = 13;
  
  Card get();
  Card get(int index);
  boolean	isEmpty();
  Card pop();
  Card popRandom();
  boolean put(Card card);
  int size();
  void shuffle();
}
