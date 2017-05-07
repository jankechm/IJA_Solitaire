package game;

import model.Card;
import model.KlondikeStock;
import model.KlondikeWaste;

/**
 * Třída reprezentuje operaci přesunu karty ze stock balíčku do waste balíčku.
 * @author Marek Jankech, Jan Morávek
 */
public class CardFromStockToWasteCmd implements Command {
  protected KlondikeStock stock;
  protected KlondikeWaste waste;
  protected Card card;
  
  public CardFromStockToWasteCmd(KlondikeStock stock, KlondikeWaste waste) {
    this.stock = stock;
    this.waste = waste;
  }
  /**
   * Vykonání operace přesunu.
   * @return úspěšnost operace
   */
  @Override
  public boolean execute(){
    if (!this.stock.isEmpty()) {
      this.card = this.stock.pop();
      this.waste.put(this.card);
    }
    else if (this.stock.isEmpty() && this.waste.isEmpty()) {
      return false;
    }
    else {
      while (!this.waste.isEmpty()) {
        this.card = this.waste.pop();
        this.stock.put(this.card);
      }
    }
    return true;
  }
  /**
   * Vrácení do stavu před vykonáním operace.
   * @return úspěšnost vrácení operace
   */
  @Override
  public boolean undo() {
    if (!this.waste.isEmpty()) {
      this.card = this.waste.pop();
      this.stock.put(this.card);
    }
    else {
      while (!this.stock.isEmpty()) {
        this.card = this.stock.pop();
        this.waste.put(this.card);
      }
    }
    return true;
  }
}
