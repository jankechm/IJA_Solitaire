package game;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
public class KlondikeGame implements Serializable {
  protected static final int WORKING_P_NUM = 7;
  protected static final int TARGET_P_NUM = 4;
  /**Počítadlo všech spuštěných her*/
  protected static int gameCnt = 0;
  /**ID současné hry*/
  protected int gameId;
  protected KlondikeFactory factory;
  protected KlondikeCardDeck deck;
  protected KlondikeStock stock;
  protected KlondikeWaste waste;
  protected ArrayList<KlondikeWorkingPack> workingP;
  protected ArrayList<KlondikeTargetPack> targetP;
  
  /**
   * Založí novou hru.
   * Vytvoří objekty reprezentované hrou a inicializuje je.
   * Pokud počet vytvořených her dosáhl 4, další nová hra se nevytvoří.
   * @return true, pokud se úspěšně vytvořila nová hra, jinak false
   */
  public boolean newGame() {
    if (gameCnt < 4) {
      this.factory = new KlondikeFactory();
      this.deck = (KlondikeCardDeck)factory.createCardDeck();
      this.workingP = new ArrayList<>();
      for (int i = 0; i < WORKING_P_NUM; i++) {
        this.workingP.add(i, (KlondikeWorkingPack)factory.createWorkingPack(deck, i+1));
      }
      this.targetP = new ArrayList<>();
      for (int i = 0; i < TARGET_P_NUM; i++) {
        this.targetP.add(i, (KlondikeTargetPack)factory.createTargetPack());
      }
      this.stock = (KlondikeStock)factory.createStock(deck);
      this.waste = stock.getWaste();
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
    this.factory = null;
    this.deck = null;
    this.workingP = null;
    this.targetP = null;
    this.stock = null;
    this.waste = null;
    gameCnt--;
  }
  /**
   * Uloží stav hry na disk.
   * Jako název souboru s uloženým stavem hry se použije aktuální datum a čas.
   * @return název souboru s uloženou hrou
   */
  public String saveGame() {
    //Aktuální datum a čas jako název souboru pro uložení
    LocalDateTime dateTime = LocalDateTime.now();
    String fileName = dateTime.format(DateTimeFormatter.ofPattern("d-M-y_H-m-ss")) + ".save";
    
		try (FileOutputStream fo = new FileOutputStream(new File(fileName));
         ObjectOutputStream oo = new ObjectOutputStream(fo)) {
      //Uložení stavu hry do souboru
      oo.writeObject(this.deck);
      oo.writeObject(this.stock);
      oo.writeObject(this.waste);
      oo.writeObject(this.workingP);
      oo.writeObject(this.targetP);
		}
    catch (FileNotFoundException e) {
			System.err.println("Výstupní soubor nenalezen");
      return null;
		}
    catch (IOException e) {
			System.err.println("Chyba inicializace výstupního proudu");
      return null;
		}
    return fileName;
  }
  /**
   * Načte uloženou hru z disku.
   * @param fileName - název souboru
   * @return 
   */
  public boolean loadGame(String fileName) {
    try (FileInputStream fi = new FileInputStream(new File(fileName));
         ObjectInputStream oi = new ObjectInputStream(fi)) {
      //Načtení stavu hry ze souboru
      this.deck = (KlondikeCardDeck)oi.readObject();
      this.stock = (KlondikeStock)oi.readObject();
      this.waste = (KlondikeWaste)oi.readObject();
      this.workingP = (ArrayList<KlondikeWorkingPack>)oi.readObject();
      this.targetP = (ArrayList<KlondikeTargetPack>)oi.readObject();
    }
    catch (FileNotFoundException e) {
			System.err.println("Vstupní soubor nenalezen");
      return false;
		}
    catch (IOException e) {
			System.err.println("Chyba inicializace vstupního proudu");
      return false;
		}
    catch (ClassNotFoundException e) {
			System.err.println("Třída nenalezena.");
      return false;
		}
    return true;
  }
  /**
   * Vrací počet rozehratých her.
   * @return počet rozehratých her
   */
  public static int getGameCnt() {
    return gameCnt;
  }
  /**
   * Vrací instanci zdrojového balíčku karet.
   * @return instance balíčku karet.
   */
  public KlondikeCardDeck getDeck() {
    return this.deck;
  }
  /**
   * Vrací instanci pracovního balíčku karet.
   * @param index - index prac. balíčku, číslovaný od 0
   * @return instance prac. balíčku
   */
  public KlondikeWorkingPack getWorkingPack(int index) {
    if (index > this.workingP.size() - 1) {
      return null;
    }
    return this.workingP.get(index);
  }
  /**
   * Vrací instanci zdrojového balíčku karet.
   * @param index - index zdroj. balíčku, číslovaný od 0
   * @return instance zdroj. balíčku
   */
  public KlondikeTargetPack getTargetPack(int index) {
    if (index > this.targetP.size() - 1) {
      return null;
    }
    return this.targetP.get(index);
  }
  /**
   * Vrací instanci stock balíčku.
   * @return instance stock balíčku.
   */
  public KlondikeStock getStock() {
    return this.stock;
  }
  /**
   * Vrací instanci waste stacku.
   * @return 
   */
  public KlondikeWaste getWaste() {
    return this.waste;
  }
}
