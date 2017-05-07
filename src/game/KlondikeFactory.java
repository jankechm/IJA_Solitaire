package game;

import java.io.Serializable;
import model.*;

/**
 * Továrna pro vytváření kartových objektů hry.
 * @author Marek Jankech, Jan Morávek
 */
public class KlondikeFactory implements Serializable {
  /**
   * Vytváří objekt karty.
   * @param color - barva karty
   * @param value - hodnota karty
   * @return objekt karty v případě úspěchu, jinak null
   */
  public Card createCard(Card.Color color, int value) {
    if (value >= Card.ACE && value <= Card.KING && containsColor(color.name())) {
      return new KlondikeCard(color, value);
    }
    return null;
  }
  /**
   * Vytváří standardní balíček pro hru Solitaire Klondike o velikosti 52 karet.
   * Karty v balíčku se zamíchají.
   * @return objekt deck balíčku
   */
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
  /**
   * Vytváří stock balíček.
   * @param deck - zdrojový deck balíček, odkud se vezmou karty pro vytvoření stock balíčku
   * @return objekt stock balíčku v případě úspěchu, jinak null
   */
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
  /**
   * Vytváří waste balíček.
   * @return objekt waste balíčku
   */
  public KlondikeWaste createWaste() {
    KlondikeWaste waste = new KlondikeWaste();
    return waste;
  }
  /**
   * Vytváří cílový balíček.
   * @return cílový balíček
   */
  public TargetPack createTargetPack() {
    KlondikeTargetPack tP = new KlondikeTargetPack();
    return tP;
  }
  /**
   * Vytváří pracovní balíček.
   * @param deck - zdrojový deck balíček, odkud se vezmou karty pro vytvoření prac. balíčku
   * @param size - počet karet v prac. balíčku
   * @return objekt prac. balíčku v případě úspěchu, jinak null
   */
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
  /**
   * Zjišťuje existenci barvy mezi kartami Klondike Solitaire.
   * @param color - hledaná barva
   * @return true, v případě nalezení barvy, jinak false
   */
  public static boolean containsColor(String color) {
    for (Card.Color c : Card.Color.values()) {
      if (c.name().equals(color)) {
          return true;
      }
    }
    return false;
  }
}
