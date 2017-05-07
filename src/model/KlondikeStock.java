package model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Stack;

/**
 * Třáda reprezentující balíček stock.
 * Kopka karet obrácených rubem nahoru.
 * @author Marek Jankech, Jan Morávek
 */
public class KlondikeStock extends AbstractKlondikeStacker implements Serializable {
  /**Počáteční velikost stock balíčku*/
  public static final int STD_STOCK_SIZE = 24;
  protected Stack<Card> cards;
  
  public KlondikeStock() {
    this.cards = new Stack<>();
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 29 * hash + Objects.hashCode(this.cards);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final KlondikeStock other = (KlondikeStock) obj;
    if (!Objects.equals(this.cards, other.cards)) {
      return false;
    }
    return true;
  }
  /**
   * Test, zda je balíček karet prázdný.
   * @return Vrací true, pokud je balíček prázdný, jinak false
   */
  @Override
  public boolean isEmpty() {
    return this.cards.isEmpty();
  }
  /**
   * Vrátí kartu z vrcholu zásobníku (karta zůstává na zásobníku). Pokud je balíček prázdný, vrací null.
   * @return Karta z vrcholu balíčku, nebo null
   */
  @Override
  public Card get() {
    if (this.isEmpty())
      return null;
    else {
      return this.cards.lastElement();
    }
  }
  /**
   * Odebere kartu z vrcholu balíčku. Pokud je balíček prázdný, vrací null.
   * @return Karta z vrcholu balíčku, nebo null
   */
  @Override
  public Card pop() {
    if (this.isEmpty())
      return null;
    else {
      return this.cards.pop();
    }
  }
  /**
   * Vloží kartu na vrchol balíčku.
   * @param card - Vkládaná karta
   * @return Úspěšnost akce
   */
  @Override
  public boolean put(Card card) {
    if (this.size() < STD_STOCK_SIZE) {
      card.turnFaceDown();
      this.cards.push(card);
      return true;
    }
    return false;
  }
  /**
   * Vrací karty ze stock balíčku v kolekci Stack.
   * @return karty v kolekci Stack
   */
  @Override
  public Stack<Card> getCards() {
    if (this.isEmpty()) {
      return null;
    }
    else {
      return this.cards;
    }
  }
  /**
   * Test, zda je balíček karet prázdný.
   * @return true, pokud je balíček prázdný, jinak false
   */
  @Override
  public int size() {
    return this.cards.size();
  }
}
