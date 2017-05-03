package game;

import model.Card;
import model.KlondikeTargetPack;
import model.KlondikeWorkingPack;

/**
 * Třída reprezentuje operaci přesunu karty z prac. balíčku do cíl. balíčku.
 * @author Marek Jankech, Jan Morávek
 */
public class CardFromWPackToTPackCmd implements Command {
  KlondikeWorkingPack wP;
  KlondikeTargetPack tP;
  Card card;
  //UndoStack uStack;
  
  public CardFromWPackToTPackCmd(KlondikeWorkingPack wP, KlondikeTargetPack tP) {
    this.wP = wP;
    this.tP = tP;
  }
  /**
   * Vykonání operace přesunu.
   * @return úspěšnost operace
   */
  @Override
  public boolean execute(){
    if ((this.card = this.wP.pop()) == null) {
      return false;
    }
    if ((this.tP.put(this.card)) == false) {
      this.wP.pushBack(this.card);
      return false;
    }
    //this.uStack.push(this); - bude delat Controller
    return true;
  }
  /**
   * Vrácení do stav před vykonáním operace.
   * @return úspěšnost vrácení operace
   */
  @Override
  public boolean undo() {
    this.tP.pop();
    this.wP.pushBack(card);
    return true;
  }
}
