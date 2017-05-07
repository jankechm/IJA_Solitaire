package game;

import model.Card;
import model.KlondikeTargetPack;
import model.KlondikeWorkingPack;

/**
 * Třída reprezentuje operaci přesunu karty z prac. balíčku do cíl. balíčku.
 * @author Marek Jankech, Jan Morávek
 */
public class CardFromWPackToTPackCmd implements Command {
  protected KlondikeWorkingPack wP;
  protected KlondikeTargetPack tP;
  protected Card card, cardUnder;
  protected boolean wasFaceDown = false;
  
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
    if ((this.cardUnder = this.wP.get()) != null && this.cardUnder.turnFaceUp()) {
      this.wasFaceDown = true;
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
    if (this.wasFaceDown) {
      this.wP.get().turnFaceDown();
    }
    this.wP.pushBack(this.card);
    return true;
  }
}
