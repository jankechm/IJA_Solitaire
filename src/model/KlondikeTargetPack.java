package model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Stack;

/**
 * Třída reprezentující cílový balíček.
 * @author Marek Jankech, Jan Morávek
 */
public class KlondikeTargetPack extends AbstractKlondikeStacker implements Serializable, TargetPack {
  /**Velikost zaplněného cílového balíčku*/
  public static final int FULL_T_PACK = 13;
  protected Stack<Card> cards;
  
  public KlondikeTargetPack() {
    this.cards = new Stack<>();
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 97 * hash + Objects.hashCode(this.cards);
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
    final KlondikeTargetPack other = (KlondikeTargetPack) obj;
    if (!Objects.equals(this.cards, other.cards)) {
      return false;
    }
    return true;
  }

  @Override
  public Card get() {
    if (this.isEmpty())
      return null;
    else {
      return this.cards.lastElement();
    }
  }

  @Override
  public Card pop() {
    if (this.isEmpty())
      return null;
    else {
      return this.cards.pop();
    }
  }

  @Override
  public boolean put(Card card) {
    if ((this.isEmpty() && card.value() == Card.ACE) ||
        (!this.isEmpty() && this.get().color() == card.color() && this.get().value() == card.value() - 1)) {
      this.cards.push(card);
      return true;
    }
    return false;
  }
  /**
   * Test, zda může vložit kartu na vrchol balíčku.
   * @param card - karta ke vložení
   * @return - úspěšnost operace
   */
  public boolean canPut(Card card) {
    return (this.isEmpty() && card.value() == Card.ACE) ||
      (!this.isEmpty() && this.get().color() == card.color() && this.get().value() == card.value() - 1);
  }

  @Override
  public boolean isEmpty() {
    return this.cards.isEmpty();
  }
  /**
   * Test plnosti cíl. balíčku.
   * @return true, pokud je plný, jinak false
   */
  public boolean isFull() {
    return this.size() >= FULL_T_PACK;
  }

  @Override
  public int size() {
    return this.cards.size();
  }

  @Override
  public Stack<Card> getCards() {
    if (this.isEmpty()) {
      return null;
    }
    else {
      return this.cards;
    }
  }
}
