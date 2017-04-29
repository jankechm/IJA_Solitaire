package game;

import java.util.ArrayList;
import java.util.Collections;
import model.KlondikeCardDeck;
import model.KlondikeStock;
import model.KlondikeTargetPack;
import model.KlondikeWaste;
import model.KlondikeWorkingPack;

/**
 *
 * @author Marek Jankech, Jan Morávek
 */
public class KlondikeGame {
  protected static final int WORKING_P_NUM = 7;
  protected static final int TARGET_P_NUM = 4;
  /**Počítadlo her*/
  public static int gameCnt = 0;
  
  public int gameId;
  public KlondikeFactory factory;
  public KlondikeCardDeck deck;
  public KlondikeStock stock;
  public KlondikeWaste waste;
  public ArrayList<KlondikeWorkingPack> workingP;
  public ArrayList<KlondikeTargetPack> targetP;
  
  /**
   * Založí novou hru.
   * Vytvoří objekty reprezentované hrou a inicializuje je.
   * Pokud počet vytvořených her dosáhl 4, další nová hra se nevytvoří.
   * @return true, pokud se úspěšně vytvořila nová hra, jinak false
   */
  public boolean newGame() {
    if (gameCnt < 4) {
      factory = new KlondikeFactory();
      deck = (KlondikeCardDeck)factory.createCardDeck();
      workingP = new ArrayList<>();
      for (int i = 0; i < WORKING_P_NUM; i++) {
        workingP.add(i, (KlondikeWorkingPack)factory.createWorkingPack(deck, i+1));
      }
      targetP = new ArrayList<>();
      for (int i = 0; i < TARGET_P_NUM; i++) {
        targetP.add(i, (KlondikeTargetPack)factory.createTargetPack());
      }
      stock = (KlondikeStock)factory.createStock(deck);
      waste = stock.getWaste();
      gameCnt++;
      this.gameId = gameCnt;
      return true;
    }
    else {
      return false;
    }
  }
  /**
   * Ukončí hru.
   */
  public void quitGame() {
    factory = null;
    deck = null;
    workingP = null;
    targetP = null;
    stock = null;
    waste = null;
    gameCnt--;
  }
  /**
   * Uloží hru.
   */
  public void saveGame() {
    
  }
  /**
   * Vrací počet rozehratých her.
   * @return počet rozehratých her
   */
  public static int getGameCnt() {
    return gameCnt;
  }
}
