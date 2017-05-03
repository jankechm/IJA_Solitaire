package game;

import model.Card;
import model.KlondikeTargetPack;

/**
 * Třída reprezentuje operaci přesunu karty z cílového balíčku do cílového balíčku.
 * @author Marek Jankech, Jan Morávek
 */
public class CardFromTPackToTPackCmd implements Command {
  KlondikeTargetPack tP1;
  KlondikeTargetPack tP2;
  Card card;
  
  public CardFromTPackToTPackCmd(KlondikeTargetPack tP1, KlondikeTargetPack tP2) {
    this.tP1 = tP1;
    this.tP2 = tP2;
  }
  /**
   * Vykonání operace přesunu.
   * @return úspěšnost operace
   */
  @Override
  public boolean execute(){
    if ((this.card = this.tP1.pop()) != null) {
      if (this.tP2.put(this.card)) {
        return true;
      }
      else {
        this.tP1.put(this.card);
      }
    }
    return false;
  }
  /**
   * Vrácení do stavu před vykonáním operace.
   * @return úspěšnost vrácení operace
   */
  @Override
  public boolean undo() {
    this.card = this.tP2.pop();
    this.tP1.put(this.card);
    return true;
  }
}
