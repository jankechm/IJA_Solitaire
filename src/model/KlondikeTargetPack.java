package model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Stack;

/**
 * Třída reprezentující cílový balíček.
 * @author Marek Jankech, Jan Morávek
 */
public class KlondikeTargetPack extends AbstractKlondikeStacker implements Serializable, TargetPack {
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
  
  /**
   * 
   * @return 
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
   * Vytáhne kartu z cílového balíčku.
   * @return Karta z cíl. balíčku, nebo null, pokud je prázdný.
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
   * 
   * @param card
   * @return 
   */
  @Override
  public boolean put(Card card) {
    if ((this.isEmpty() && card.value() == 1) ||
        (!this.isEmpty() && this.get().similarColorTo(card) && this.get().value() == card.value() - 1)) {
      this.cards.push(card);
      return true;
    }
    return false;
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
   * @return 
   */
  @Override
  public int size() {
    return this.cards.size();
  }
}
