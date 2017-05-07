package game;

import model.Card;
import model.KlondikeWaste;
import model.KlondikeWorkingPack;

/**
 * Třída reprezentuje operaci přesunu karty z waste balíčku do pracovního balíčku.
 * @author Marek Jankech, Jan Morávek
 */
public class CardFromWasteToWPackCmd implements Command {
  protected KlondikeWaste waste;
  protected KlondikeWorkingPack wP;
  protected Card card;
  
  public CardFromWasteToWPackCmd(KlondikeWaste waste, KlondikeWorkingPack wP) {
    this.waste = waste;
    this.wP = wP;
  }
  /**
   * Vykonání operace přesunu.
   * @return úspěšnost operace
   */
  @Override
  public boolean execute(){
    if ((this.card = this.waste.pop()) == null) {
      return false;
    }
    if ((this.wP.put(this.card)) == false) {
      this.waste.put(this.card);
      return false;
    }
    return true;
  }
  /**
   * Vrácení do stavu před vykonáním operace.
   * @return úspěšnost vrácení operace
   */
  @Override
  public boolean undo() {
    this.wP.pop();
    this.waste.put(card);
    return true;
  }
}
