package model;

import java.util.Stack;

/**
 * Abstraktní třída zásobníků karet
 * @author Marek Jankech, Jan Morávek
 */
public abstract class AbstractKlondikeStacker {
  /**
   * Vrátí kartu z vrcholu zásobníku (karta zůstává na zásobníku).
   * @return karta z vrcholu zásobníku
   */
  public abstract Card get();
  /**
   * Odebere kartu z vrcholu zásobníku. Pokud je zásobník prázdný, vrací null.
   * @return karta z vrcholu zásobníku
   */
  public abstract Card pop();
  /**
   * Vloží kartu na vrchol balíčku.
   * @param card - karta ke vložení
   * @return - úspěšnost operace
   */
  public abstract boolean put(Card card);
  /**
   * Vrací karty v kolekci Stack.
   * @return karty v kolekci Stack
   */
  public abstract Stack<Card> getCards();
  /**
   * Zjišťuje aktuální počet karet v balíčku.
   * @return aktuální počet karet v balíčku
   */
  public abstract int size();
  /**
   * Test, zda je balíček karet prázdný.
   * @return true, pokud je balíček prázdný, jinak false
   */
  public abstract boolean isEmpty();
}