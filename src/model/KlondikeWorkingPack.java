package model;

import java.io.Serializable;
import java.util.Stack;

/**
 *
 * @author Marek Jankech, Jan Morávek
 */
public class KlondikeWorkingPack implements WorkingPack, Serializable {
  protected int initSize;
  protected Stack<Card> cards;
  
  public KlondikeWorkingPack(int size) {
    this.initSize = size;
    this.cards = new Stack<>();
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
   * 
   * @param index
   * @return 
   */
  @Override
  public Card	get(int index) {
    if (this.isEmpty() || index >= this.cards.size())
      return null;
    else {
      return this.cards.elementAt(this.cards.size() - index - 1);
    }
  }
  /**
   * Vytáhne kartu z pracovního balíčku.
   * @return Karta z prac. balíčku, nebo null, pokud je prázdný.
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
  public Stack<Card> pop(Card card) {
    int cardIndex = this.cards.indexOf(card);
    Stack<Card> removedCards;
    Card c;
    
    if (cardIndex >= 0) {
      removedCards = new Stack<>();
      for (int i = 0; i <= cardIndex; i++) {
        c = this.cards.pop();
        removedCards.add(0, c);
      }
      return removedCards;
    }
    else
      return null;
  }
  /**
   * 
   * @param card
   * @return 
   */
  @Override
  public boolean put(Card card) {
    if ((this.isEmpty() && card.value() == 13) ||
        (!this.isEmpty() && !this.get().similarColorTo(card) && this.get().value() - 1 == card.value())) {
      this.cards.push(card);
      return true;
    }
    return false;
  }
  /**
   * 
   * @param cards
   * @return 
   */
  @Override
  public boolean put(Stack<Card> cards) {
    if ((this.isEmpty() && cards.firstElement().value() == 13) ||
       (!this.isEmpty() && this.get().value() == cards.firstElement().value() + 1 &&
       !this.get().similarColorTo(cards.firstElement()))) {
      this.cards.addAll(cards);
      return true;
    }
    return false;
  }
  /**
   * 
   * @param card
   * @return 
   */
  @Override
  public boolean pushInit(Card card) {
    if (this.size() < this.initSize) {
      this.cards.push(card);
      return true;
    }
    return false;
  }
  /**
   * 
   * @return 
   */
  @Override
  public boolean turnFaceUp() {
    Card card = this.get();
    
    if (card != null)
      return card.turnFaceUp();
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
