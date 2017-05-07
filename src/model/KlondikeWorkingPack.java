package model;

import java.io.Serializable;
import java.util.Stack;

/**
 * Třída reprezentující pracovní balíček.
 * @author Marek Jankech, Jan Morávek
 */
public class KlondikeWorkingPack extends AbstractKlondikeStacker implements WorkingPack, Serializable {
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
  
  @Override
  public Card	get(int index) {
    if (this.isEmpty() || index >= this.cards.size())
      return null;
    else {
      return this.cards.elementAt(this.cards.size() - index - 1);
    }
  }
  /**
   * Vybere kartu z vrcholu zásobníku.
   * @return karta
   */
  @Override
  public Card pop() {
    if (this.isEmpty())
      return null;
    else {
      return this.cards.pop();
    }
  }
  
  @Override
  public Stack<Card> pop(Card card) {
    int cardIndex = this.size() - this.cards.indexOf(card) - 1;
    Stack<Card> removedCards;
    Card c;
    
    if (cardIndex >= 0 && cardIndex < this.size()) {
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
   * Metoda odebere ze zásobníku sekvenci karet od zadaného indexu až po vrchol zásobníku.
   * Pokud se jedná o index vrcholu, bude v sekvenci pouze jedna karta.
   * Pokud je balíček prázdný, nebo index mimo rozsah, vrací null.
   * @param index - index hledané karty
   * @return zásobník karet obsahující odebranou sekvenci nebo null
   */
  public Stack<Card> pop(int index) {
    Stack<Card> removedCards;
    Card c;
    
    if (index >= 0 && index < this.size()) {
      removedCards = new Stack<>();
      for (int i = 0; i <= index; i++) {
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
    if ((this.isEmpty() && card.value() == Card.KING) ||
        (!this.isEmpty() && !this.get().similarColorTo(card) && this.get().value() - 1 == card.value())) {
      this.cards.push(card);
      return true;
    }
    return false;
  }
  /**
   * Zjišťuje, zda je možné do pracovního balíčku vložit danou kartu.
   * @param card - karta ke vložení
   * @return úspěšnost možného vložení dané karty
   */
  public boolean canPut(Card card) {
    return (this.isEmpty() && card.value() == Card.KING) ||
      (!this.isEmpty() && !this.get().similarColorTo(card) && this.get().value() - 1 == card.value());
  }
  /**
   * 
   * @param cards
   * @return 
   */
  @Override
  public boolean put(Stack<Card> cards) {
    if ((this.isEmpty() && cards.firstElement().value() == Card.KING) ||
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
   * Vrací kartu nazpět do tohoto balíčku, po neúspěšném přesunu.
   * @param card - vrácená karta
   */
  public void pushBack(Card card) {
    this.cards.push(card);
  }
  /**
   * Vrací karty nazpět do tohoto balíčku, po neúspěšném přesunu.
   * @param cards - vrácené karty
   */
  public void pushBack(Stack<Card> cards) {
    this.cards.addAll(cards);
  }
  /**
   * Otočí kartu na vrcholu lícem nahoru.
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
  @Override
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
  /**
   * Vrací zásobník karet v pracovním balíčku.
   * @return zásobník karet
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
}
