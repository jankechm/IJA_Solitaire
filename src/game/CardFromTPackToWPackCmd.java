package game;

import model.Card;
import model.KlondikeTargetPack;
import model.KlondikeWorkingPack;

/**
 * Třída reprezentuje operaci přesunu karty z cíl. balíčku do prac. balíčku.
 * @author Marek Jankech, Jan Morávek
 */
public class CardFromTPackToWPackCmd implements Command {
  protected KlondikeTargetPack tP;
  protected KlondikeWorkingPack wP;
  protected Card card;
  
  public CardFromTPackToWPackCmd(KlondikeTargetPack tP, KlondikeWorkingPack wP) {
    this.tP = tP;
    this.wP = wP;
  }
  /**
   * Vykonání operace přesunu.
   * @return úspěšnost operace
   */
  @Override
  public boolean execute(){
    if ((this.card = this.tP.pop()) == null) {
      return false;
    }
    if ((this.wP.put(this.card)) == false) {
      this.tP.put(this.card);
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
    this.tP.put(card);
    return true;
  }
}
