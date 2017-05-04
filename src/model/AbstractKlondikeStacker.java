package model;

import java.util.Stack;

/**
 * Abstraktní třída zásobníků karet
 * @author Marek Jankech, Jan Morávek
 */
public abstract class AbstractKlondikeStacker {
  public abstract Card get();
  public abstract Card pop();
  public abstract boolean put(Card card);
  public abstract Stack<Card> getCards();
  public abstract int size();
  public abstract boolean isEmpty();
}