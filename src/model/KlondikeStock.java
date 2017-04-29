package model;

import java.util.Objects;
import java.util.Stack;

/**
 * Kopka karet obrácených rubem nahoru.
 * @author Marek Jankech, Jan Morávek
 */
public class KlondikeStock extends AbstractKlondikeStacker {
  protected static final int STD_STOCK_SIZE = 24;
  
  protected Stack<Card> cards;
  protected KlondikeWaste waste;
  
  public KlondikeStock() {
    this.cards = new Stack<>();
    this.waste = new KlondikeWaste();
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 89 * hash + Objects.hashCode(this.cards);
    hash = 89 * hash + Objects.hashCode(this.waste);
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
    if (!Objects.equals(this.waste, other.waste)) {
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
   * Přemístí kartu z kopky do zásobníku (waste).
   * Pokud je halda prázdná, znovu se naplní.
   */
  //@Override
  public void pop() {
    if (this.isEmpty()) {
      this.pushBack();
    }
    else {
      this.waste.put(this.cards.pop());
    }
  }
  /**
   * 
   * @param card
   * @return 
   */
  public boolean pushInit(Card card) {
    if (this.size() < STD_STOCK_SIZE) {
      this.cards.push(card);
      return true;
    }
    return false;
  }
  /**
   * Znovu-naplnění kopky.
   * Karty ze zásobníku (waste) se přemístí do kopky.
   */
  protected void pushBack() {
    Card card;
    
    while (!this.waste.isEmpty()) {
      card = this.waste.pop();
      card.turnFaceDown();
      this.cards.push(card);
    }
  }
  /**
   * 
   * @return 
   */
  public int size() {
    return this.cards.size();
  }
  /**
   * Vrací instanci Waste.
   * @return instance Waste
   */
  public KlondikeWaste getWaste() {
    return this.waste;
  }
}
