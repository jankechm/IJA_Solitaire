package game;

import java.util.Stack;
import model.Card;
import model.KlondikeWorkingPack;

/**
 * Třída reprezentuje operaci přesunu karet z jednoho prac. balíčku do jiného prac. balíčku.
 * @author Marek Jankech, Jan Morávek
 */
public class CardsFromWPackToWPackCmd implements Command {
  protected KlondikeWorkingPack wP1;
  protected KlondikeWorkingPack wP2;
  protected Card card, cardUnder;
  protected Stack<Card> cards;
  protected boolean wasFaceDown = false;
  
  public CardsFromWPackToWPackCmd(KlondikeWorkingPack wP1, KlondikeWorkingPack wP2) {
    this.wP1 = wP1;
    this.wP2 = wP2;
  }
  /**
   * Vykonání operace přesunu.
   * @return úspěšnost operace
   */
  @Override
  public boolean execute(){
    for (int i = 0; i < this.wP1.size(); i++) {
      this.card = this.wP1.get(i);
      if (this.card != null && this.card.isTurnedFaceUp() && this.wP2.canPut(this.card)) {
        this.cards = this.wP1.pop(i);
        this.wP2.put(this.cards);
        if ((this.cardUnder = this.wP1.get()) != null && this.cardUnder.turnFaceUp()) {
          this.wasFaceDown = true;
        }
        return true;
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
    if (this.wasFaceDown) {
      this.wP1.get().turnFaceDown();
    }
    this.cards = this.wP2.pop(this.card);
    this.wP1.put(this.cards);
    return true;
  }
}

