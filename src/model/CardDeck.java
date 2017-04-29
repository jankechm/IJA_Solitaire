package model;

/**
 *
 * @author Marek
 */
public interface CardDeck {
  Card get();
  Card get(int index);
  boolean	isEmpty();
  Card pop();
  public Card popRandom();
  boolean pushInit(Card card);
  int size();
}
