package model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Stack;

/**
 * Kopka karet obrácených rubem nahoru.
 * @author Marek Jankech, Jan Morávek
 */
public class KlondikeStock extends AbstractKlondikeStacker implements Serializable {
  protected static final int STD_STOCK_SIZE = 24;
  
  protected Stack<Card> cards;
  //protected KlondikeWaste waste;
  
  public KlondikeStock() {
    this.cards = new Stack<>();
    //this.waste = new KlondikeWaste();
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
   * Vybere kartu z vrcholu zásobníku.
   * @return karta
   */
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
  public boolean put(Card card) {
    if (this.size() < STD_STOCK_SIZE) {
      card.turnFaceDown();
      this.cards.push(card);
      return true;
    }
    return false;
  }
  /**
   * Vrací zásobník karet v Stock balíčku.
   * @return zásobník karet
   */
  public Stack<Card> getCards() {
    if (this.isEmpty()) {
      return null;
    }
    else {
      return this.cards;
    }
  }
  /**
   * 
   * @return 
   */
  public int size() {
    return this.cards.size();
  }
}
