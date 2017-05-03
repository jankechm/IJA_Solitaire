package game;

import model.Card;
import model.KlondikeTargetPack;
import model.KlondikeWaste;

/**
 * Třída reprezentuje operaci přesunu karty z waste balíčku do cílového balíčku.
 * @author Marek Jankech, Jan Morávek
 */
public class CardFromWasteToTPackCmd implements Command {
  KlondikeWaste waste;
  KlondikeTargetPack tP;
  Card card;
  
  public CardFromWasteToTPackCmd(KlondikeWaste waste, KlondikeTargetPack tP) {
    this.waste = waste;
    this.tP = tP;
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
    if ((this.tP.put(this.card)) == false) {
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
    this.tP.pop();
    this.waste.put(card);
    return true;
  }
}
