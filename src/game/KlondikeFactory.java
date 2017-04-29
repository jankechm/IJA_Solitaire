package game;

import java.util.Collections;
import java.util.Stack;
import model.*;

/**
 *
 * @author Marek Jankech, Jan Morávek
 */
public class KlondikeFactory {
  protected static final int STD_DECK_SIZE = 52;
  protected static final int CASCADE_SIZE = 13;
  protected static final int STD_STOCK_SIZE = 24;
  protected static final int ACE = 1;
  protected static final int KING = 13;
  
  public Card createCard(Card.Color color, int value) {
    if (value >= ACE && value <= KING && containsColor(color.name())) {
      return new KlondikeCard(color, value);
    }
    return null;
  }
  
  public CardDeck createCardDeck() {
    KlondikeCardDeck deck = new KlondikeCardDeck();
    
    for (int i = 1; i <= CASCADE_SIZE; i++) {
      deck.pushInit(this.createCard(Card.Color.CLUBS, i));
      deck.pushInit(this.createCard(Card.Color.DIAMONDS, i));
      deck.pushInit(this.createCard(Card.Color.HEARTS, i));
      deck.pushInit(this.createCard(Card.Color.SPADES, i));
      deck.shuffle();
    }
    return deck;
  }
  
  public KlondikeStock createStock(CardDeck deck) {
    KlondikeStock stock = new KlondikeStock();
    
    for (int i = 0; i < STD_STOCK_SIZE; i++) {
      if (deck.isEmpty()) {
        return null;
      }
      stock.pushInit(deck.popRandom());
    }
    return stock;
  }
  
  public TargetPack createTargetPack() {
    /*KlondikeCardDeck deck = new KlondikeCardDeck();
    KlondikeCard virtualCard = new KlondikeCard(color, 1);
    deck.setVirtualCard(virtualCard);
    return deck;*/
    
    KlondikeTargetPack tP = new KlondikeTargetPack();
    return tP;
  }
  
  public WorkingPack createWorkingPack(CardDeck deck, int size) {
    KlondikeWorkingPack wP = new KlondikeWorkingPack(size);
    
    for (int i = 0; i < size; i++) {
      if (deck.isEmpty()) {
        return null;
      }
      wP.pushInit(deck.popRandom());
    }
    wP.get().turnFaceUp();
    return wP;
  }
  
  public static boolean containsColor(String color) {
    for (Card.Color c : Card.Color.values()) {
      if (c.name().equals(color)) {
          return true;
      }
    }
    return false;
  }
}
