package model;

import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import java.util.Stack;
//import model.cards.CardDeck;
//import model.cards.Card;

/**
 *
 * @author Marek
 */
public class KlondikeCardDeck implements CardDeck {
  protected static final int STD_DECK_SIZE = 52;
  protected static final int SAME_COLORED_MAX = 13;

  protected Stack<Card> cards;
  
  public KlondikeCardDeck() {
    this.cards = new Stack<>();
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 37 * hash + Objects.hashCode(this.cards);
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
    final KlondikeCardDeck other = (KlondikeCardDeck) obj;
    if (!Objects.equals(this.cards, other.cards)) {
      return false;
    }
    return true;
  }
  
  @Override
  public Card	get() {
    if (this.isEmpty())
      return null;
    else {
      return this.cards.lastElement();
    }
  }
  
  @Override
  public Card	get(int index) {
    if (this.isEmpty() || index >= this.cards.size())
      return null;
    else {
      return this.cards.elementAt(this.cards.size() - index - 1);
    }
  }
  
  @Override
  public boolean isEmpty() {
    return this.cards.isEmpty();
  }
  
  @Override
  public Card pop() {
    if (this.cards.isEmpty())
      return null;
    else {
      return this.cards.pop();
    }
  }
  
  @Override
  public Card popRandom() {
    Random random = new Random();
    int index = random.nextInt(this.size());
    Card card;
    
    if (this.cards.isEmpty())
      return null;
    else {
      card = this.cards.get(index);
      this.cards.remove(index);
      return card;
    }
  }
  
  @Override
  public boolean pushInit(Card card) {
    if (this.size() < STD_DECK_SIZE) {
      this.cards.push(card);
      return true;
    }
    return false;
  }
  
  @Override
  public int size() {
    return this.cards.size();
  }
  /**
   * Promíchá karty balíčku
   */
  public void shuffle() {
    Collections.shuffle(this.cards);
    /*int i = 1;
    for (Card c : this.cards) {
      System.out.println(String.valueOf(i) + ' ' + c);
      i++;
    }*/
  }
}
