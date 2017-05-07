package game;

import model.Card;
import model.KlondikeTargetPack;
import model.KlondikeWaste;
import model.KlondikeWorkingPack;

/**
 * Třída reprezentující nápovědu pro hru.
 * @author Marek Jankech, Jan Morávek
 */
public class Hint {
  protected KlondikeGame game;
  protected int srcIndex = -1, destIndex = -1;
  protected KlondikeGame.Selected src = KlondikeGame.Selected.NOTHING;
  protected KlondikeGame.Selected dest = KlondikeGame.Selected.NOTHING;
  
  public Hint(KlondikeGame game) {
    this.game = game;
  }
  /**
   * Hledání možných tahů karet.
   * @return true, pokud najde možný tah, jinak false
   */
  public boolean giveHint() {
    if (this.wPackToWPack()) {
      return true;
    }
    if (this.wPackToTPack()) {
      return true;
    }
    if (this.wasteToTPack()) {
      this.srcIndex = -1;
      return true;
    }
    if (this.wasteToWPack()) {
      this.srcIndex = -1;
      return true;
    }
    if (this.stockClick()) {
      this.srcIndex = -1;
      this.destIndex = -1;
      this.dest = KlondikeGame.Selected.NOTHING;
      return true;
    }
    return false;
  }
  /**
   * Hledání možných tahů mezi prac. balíčky.
   * @return true, pokud najde možný tah, jinak false
   */
  public boolean wPackToWPack() {
    Card card;
    KlondikeWorkingPack srcWP;
    int cardIndex = 0;
    
    //iterace přes všechny prac. balíčky
    for (this.srcIndex = 0; this.srcIndex < KlondikeGame.WORKING_P_NUM; this.srcIndex++) {
      srcWP = this.game.getWorkingPack(this.srcIndex);
      this.destIndex = this.srcIndex + 1;
      if (!srcWP.isEmpty()) {
        card = srcWP.get(this.srcIndex);
        //hledání první karty obrácené lícem nahor v aktuálním zdroj. balíčku
        while (!card.isTurnedFaceUp()) {
          cardIndex++;
          card = srcWP.get(cardIndex);
        }
        //hledání cíle pro vložení karty
        if (this.findDestWP(card)) {
          return true;
        }
      }
    }
    return false;
  }
  /**
   * Hledání možného prac. balíčku pro vložení karty.
   * @param card - karta ke vložení
   * @return true, pokud najde vhodný prac. balíček, jinak false
   */
  protected boolean findDestWP(Card card) {
    KlondikeWorkingPack destWP;
    
    //iterace přes ostatní prac. balíčky kromě zdrojového - hledání cíle pro vložení karty
    while (this.destIndex != this.srcIndex) {
      if (this.destIndex >= KlondikeGame.WORKING_P_NUM) {
        this.destIndex = 0;
      }
      destWP = this.game.getWorkingPack(this.destIndex);
      if (destWP.canPut(card)) {
        this.src = KlondikeGame.Selected.WORKING_PACK;
        this.dest = KlondikeGame.Selected.WORKING_PACK;
        return true;
      }
      this.destIndex++;
    }
    return false;
  }
  /**
   * Hledání možných tahů mezi prac. balíčky a cíl. balíčky.
   * @return true, pokud najde možný tah, jinak false
   */
  protected boolean wPackToTPack() {
    Card card;
    KlondikeWorkingPack srcWP;
    KlondikeTargetPack destTP;
    
    for (this.srcIndex = 0; this.srcIndex < KlondikeGame.WORKING_P_NUM; this.srcIndex++) {
      srcWP = this.game.getWorkingPack(this.srcIndex);
      if (!srcWP.isEmpty()) {
        card = srcWP.get();
        for (this.destIndex = 0; this.destIndex < KlondikeGame.TARGET_P_NUM; this.destIndex++) {
          destTP = this.game.getTargetPack(this.destIndex);
          if (destTP.canPut(card)) {
            this.src = KlondikeGame.Selected.WORKING_PACK;
            this.dest = KlondikeGame.Selected.TARGET_PACK;
            return true;
          }
        }
      }
    }
    return false;
  }
  /**
   * Hledání možného tahu mezi waste balíčkem a cílovými balíčky.
   * @return true, pokud najde možný tah, jinak false
   */
  protected boolean wasteToTPack() {
    KlondikeWaste waste = this.game.getWaste();
    KlondikeTargetPack destTP;
    
    if (!waste.isEmpty()) {
      for (this.destIndex = 0; this.destIndex < KlondikeGame.TARGET_P_NUM; this.destIndex++) {
        destTP = this.game.getTargetPack(this.destIndex);
        if (destTP.canPut(waste.get())) {
            this.src = KlondikeGame.Selected.WASTE;
            this.dest = KlondikeGame.Selected.TARGET_PACK;
            return true;
        }
      }
    }
    return false;
  }
  /**
   * Hledání možného tahu mezi waste balíčkem a prac. balíčky.
   * @return true, pokud najde možný tah, jinak false
   */
  protected boolean wasteToWPack() {
    KlondikeWaste waste = this.game.getWaste();
    KlondikeWorkingPack destTP;
    
    if (!waste.isEmpty()) {
      for (this.destIndex = 0; this.destIndex < KlondikeGame.WORKING_P_NUM; this.destIndex++) {
        destTP = this.game.getWorkingPack(this.destIndex);
        if (destTP.canPut(waste.get())) {
            this.src = KlondikeGame.Selected.WASTE;
            this.dest = KlondikeGame.Selected.WORKING_PACK;
            return true;
        }
      }
    }
    return false;
  }
  /**
   * Kontrola možného tahu klikem na stock balíček.
   * @return true, pokud je možný tah, jinak false
   */
  protected boolean stockClick() {
    return (!this.game.getStock().isEmpty() || !this.game.getWaste().isEmpty());
  }
  /**
   * Getter pro index source balíčku.
   * @return index source balíčku
   */
  public int getSrcIndex() {
    return this.srcIndex;
  }
  /**
   * Getter pro index destination balíčku.
   * @return index source balíčku
   */
  public int getDestIndex() {
    return this.destIndex;
  }
  /**
   * Getter pro druh source balíčku.
   * @return druh source balíčku
   */
  public KlondikeGame.Selected getSrc() {
    return this.src;
  }
  /**
   * Getter pro druh destination balíčku.
   * @return druh destination balíčku
   */
  public KlondikeGame.Selected getDest() {
    return this.dest;
  }
}
