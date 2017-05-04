package game;

import java.io.Serializable;
import model.*;

/**
 *
 * @author Marek Jankech, Jan Morávek
 */
public class KlondikeFactory implements Serializable {
  
  public Card createCard(Card.Color color, int value) {
    if (value >= Card.ACE && value <= Card.KING && containsColor(color.name())) {
      return new KlondikeCard(color, value);
    }
    return null;
  }
  
  public CardDeck createCardDeck() {
    KlondikeCardDeck deck = new KlondikeCardDeck();
    
    for (int i = 1; i <= CardDeck.SAME_COLORED_MAX; i++) {
      deck.put(this.createCard(Card.Color.CLUBS, i));
      deck.put(this.createCard(Card.Color.DIAMONDS, i));
      deck.put(this.createCard(Card.Color.HEARTS, i));
      deck.put(this.createCard(Card.Color.SPADES, i));
      deck.shuffle();
    }
    return deck;
  }
  
  public KlondikeStock createStock(CardDeck deck) {
    KlondikeStock stock = new KlondikeStock();
    
    for (int i = 0; i < KlondikeStock.STD_STOCK_SIZE; i++) {
      if (deck.isEmpty()) {
        return null;
      }
      stock.put(deck.popRandom());
    }
    return stock;
  }
  
  public KlondikeWaste createWaste() {
    KlondikeWaste waste = new KlondikeWaste();
    return waste;
  }
  
  public TargetPack createTargetPack() {
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
