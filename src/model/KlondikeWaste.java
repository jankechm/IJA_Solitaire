package model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Stack;

/**
 * Zásobník karet potáhnutých z kopky.
 * @author Marek Jankech, Jan Morávek
 */
public class KlondikeWaste extends AbstractKlondikeStacker implements Serializable {
  protected Stack<Card> cards;
  
  public KlondikeWaste() {
    this.cards = new Stack<>();
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 67 * hash + Objects.hashCode(this.cards);
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
    final KlondikeWaste other = (KlondikeWaste) obj;
    if (!Objects.equals(this.cards, other.cards)) {
      return false;
    }
    return true;
  }
  /**
   *
   * @return
   */
  public boolean isEmpty() {
    return this.cards.isEmpty();
  }
  /**
   *
   * @return Karta na vrcholu zásobníku
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
   *
   * @return
   */
  //@Override
  public Card pop() {
    if (this.isEmpty())
      return null;
    else {
      return this.cards.pop();
    }
  }
  /**
   * 
   * @param card
   */
  public void put(Card card) {
    card.turnFaceUp();
    this.cards.push(card);
  }
  /**
   * 
   * @return 
   */
  public int size() {
    return this.cards.size();
  }
}

