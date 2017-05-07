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
import model.KlondikeCardDeck;
import model.KlondikeStock;
import model.KlondikeTargetPack;
import model.KlondikeWaste;
import model.KlondikeWorkingPack;

/**
 * Třída reprezentující hru Solitaire Klondike.
 * @author Marek Jankech, Jan Morávek
 */
public class KlondikeGame implements Serializable {
  protected static final int WORKING_P_NUM = 7;
  protected static final int TARGET_P_NUM = 4;
  protected static final int MAX_GAMES = 4;
  /**Počítadlo všech spuštěných her*/
  protected static int gamesCnt = 0;
  /**Statické pole indikující obsazenost ID slotů pro nové hry*/
  protected static boolean[] startedGames = new boolean[MAX_GAMES];
  
  protected KlondikeFactory factory;
  protected KlondikeCardDeck deck;
  protected KlondikeStock stock;
  protected KlondikeWaste waste;
  protected ArrayList<KlondikeWorkingPack> workingP;
  protected ArrayList<KlondikeTargetPack> targetP;
  protected int selSrcIndex;
  protected Selected selSrc = Selected.NOTHING;
  /**Operace typu Command nad kartami*/
  protected Command command;
  /**Stack operací undo*/
  protected UndoStack undoStack;
  /**Instance třídy pro nápovědu*/
  protected Hint hint;
  /**ID současné hry*/
  protected int gameId;
  
  /**
   * Výčtový typ reprezentující typ vybratého balíčku.
   */
  public static enum Selected {
    NOTHING,
    STOCK,
    WASTE,
    WORKING_PACK,
    TARGET_PACK;
  }
  
  public KlondikeGame() {
    this.setGameId();
  }
  /**
   * Nastaví ID pro novou hru a zvýší počítadlo spuštěných her.
   * Pokud počítadlo spuštěných her dosáhlo hodnotu MAX_GAMES, nebo se nenajde
   * pro hru volný slot, nenastavuje nic a vrací false.
   * @return true v případě nalezení volného slotu pro hru
   */
  protected final boolean setGameId() {
    for (int i = 0; i < MAX_GAMES; i++) {
      if (!startedGames[i]) {
        this.gameId = i;
        startedGames[i] = true;
        gamesCnt++;
        return true;
      }
    }
    this.gameId = MAX_GAMES;
    gamesCnt++;
    return false;
  }
  /**
   * Založí novou hru.
   * Vytvoří objekty reprezentované hrou a inicializuje je.
   * Pokud počet vytvořených her dosáhl 4, další nová hra se nevytvoří.
   * @return true, pokud se úspěšně vytvořila nová hra, jinak false
   */
  public boolean newGame() {
    if (this.gameId < MAX_GAMES) {
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
      this.waste = (KlondikeWaste)factory.createWaste();
      this.hint = new Hint(this);
      this.undoStack = new UndoStack();
      return true;
    }
    return false;
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
    gamesCnt--;
    if (this.gameId < MAX_GAMES) {
      startedGames[this.gameId] = false;
    }
  }
  /**
   * Uloží stav hry na disk.
   * Jako název souboru s uloženým stavem hry se použije aktuální datum a čas.
   * @return název souboru s uloženou hrou
   */
  public String saveGame() {
    //Aktuální datum a čas jako název souboru pro uložení
    LocalDateTime dateTime = LocalDateTime.now();
    String fileName = dateTime.format(DateTimeFormatter.ofPattern("dd-MM-y_HH-mm-ss")) + ".save";
    
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
  @SuppressWarnings("unchecked")
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
   * Akce po kliknutí na balíček stock.
   */
  public void selectedStock() {
    System.out.println("STOCK CLICKED");
    this.command = new CardFromStockToWasteCmd(this.stock, this.waste);
    if (this.command.execute()) {
      this.undoStack.push(command);
    }
  }
  /**
   * Akce po kliknutí na balíček waste.
   */
  public void selectedWaste() {
    //označení balíčku waste
    if (this.selSrc == Selected.NOTHING) {
      System.out.println("WASTE SELECTED");
      this.selSrc = Selected.WASTE;
    }
    //zrušení označení
    else {
      System.out.println("WASTE UNSELECTED");
      this.selSrc = Selected.NOTHING;
    }
  }
  /**
   * Akce po kliknutí na pracovní balíček.
   * @param index - index prac. balíčku
   */
  public void selectedWorkingPack(int index) {
    switch (this.selSrc) {
      case NOTHING:
        System.out.println("NOTHING -> WORKING PACK");
        //označení prac. balíčku
        this.selSrc = Selected.WORKING_PACK;
        this.selSrcIndex = index;
        break;
      case WASTE:
        System.out.println("WASTE -> WORKING PACK" + index);
        //operace přesunu karty z balíčku waste do prac. balíčku
        this.command = new CardFromWasteToWPackCmd(this.waste, this.getWorkingPack(index));
        if (this.command.execute()) {
          this.undoStack.push(command);
        }
        this.selSrc = Selected.NOTHING;
        break;
      case TARGET_PACK:
        System.out.println("TARGET PACK" + this.selSrcIndex + " -> WORKING PACK" + index);
        //operace přesunu karty z cíl. balíčku do prac. balíčku
        this.command = new CardFromTPackToWPackCmd(this.getTargetPack(this.selSrcIndex), this.getWorkingPack(index));
        if (this.command.execute()) {
          this.undoStack.push(command);
        }
        this.selSrc = Selected.NOTHING;
        break;
      case WORKING_PACK:
        System.out.println("WORKING PACK" + this.selSrcIndex + " -> WORKING PACK" + index);
        if (this.selSrcIndex != index) {
          //operace přesunu karet z prac. balíčku do jiného prac. balíčku
          this.command = new CardsFromWPackToWPackCmd(this.getWorkingPack(this.selSrcIndex), this.getWorkingPack(index));
          if (this.command.execute()) {
            this.undoStack.push(command);
          }
        }
        this.selSrc = Selected.NOTHING;
        break;
      default:
        System.out.println("WORKING PACK -> NOTHING");
        //zrušení označení
        this.selSrc = Selected.NOTHING;
        break;
    }
  }
  /**
   * Akce po kliknutí na cílový balíček.
   * @param index - index cíl. balíčku
   */
  public void selectedTargetPack(int index) {
    switch (this.selSrc) {
      case NOTHING:
        System.out.println("NOTHING -> TARGET PACK");
        //označení cíl. balíčku
        this.selSrc = Selected.TARGET_PACK;
        this.selSrcIndex = index;
        break;
      case WASTE:
        System.out.println("WASTE -> TARGET PACK" + index);
        //operace přesunu karty z balíčku waste do cíl. balíčku
        this.command = new CardFromWasteToTPackCmd(this.waste, this.getTargetPack(index));
        if (this.command.execute()) {
          this.undoStack.push(command);
        }
        this.selSrc = Selected.NOTHING;
        break;
      case TARGET_PACK:
        System.out.println("TARGET PACK" + this.selSrcIndex + " -> TARGET PACK" + index);
        if (this.selSrcIndex != index){
          //operace přesunu karty z cíl. balíčku do jíného cíl. balíčku
          this.command = new CardFromTPackToTPackCmd(this.getTargetPack(this.selSrcIndex), this.getTargetPack(index));
          if (this.command.execute()) {
            this.undoStack.push(command);
          }
        }
        this.selSrc = Selected.NOTHING;
        break;
      case WORKING_PACK:
        System.out.println("WORKING PACK" + this.selSrcIndex + " -> TARGET PACK" + index);
        //operace přesunu karty z prac. balíčku do cíl. balíčku
        this.command = new CardFromWPackToTPackCmd(this.getWorkingPack(this.selSrcIndex), this.getTargetPack(index));
        if (this.command.execute()) {
          this.undoStack.push(command);
        }
        this.selSrc = Selected.NOTHING;
        break;
      default:
        System.out.println("TARGET PACK -> NOTHING");
        //zrušení označení
        this.selSrc = Selected.NOTHING;
        break;
    }
  }
  /**
   * Akce po kliknutí na prázdné místo.
   */
  public void selectedNothing() {
    System.out.println("JUST NOTHING");
    //zrušení označení
    this.selSrc = Selected.NOTHING;
  }
  /**
   * Akce po kliknutí na tlačítko Undo.
   * @return úspěch operace Undo
   */
  public boolean undo() {
    if ((this.command = this.undoStack.pop()) != null) {
      return this.command.undo();
    }
    return false;
  }
  /**
   * Hledání možných tahů karet.
   * @return instance třídy Hint, pokud najde možný tah, jinak null
   */
  public Hint hint() {
    if (this.hint.giveHint()) {
      return this.hint;
    }
    return null;
  }
  /**
   * Kontrola, jestli už uživatel zvítězil.
   * @return true, pokud zvítězil; false pokud ještě ne
   */
  public boolean checkIfVictory() {
    return this.targetP.stream().allMatch((KlondikeTargetPack tp) -> (tp.isFull()));
  }
  /**
   * Vrací ID aktuální hry.
   * @return ID aktuální hry
   */
  public int getGameId() {
    return this.gameId;
  }
  /**
   * Vrací počet rozehratých her.
   * @return počet rozehratých her
   */
  public static int getGamesCnt() {
    return gamesCnt;
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
    if (index < 0 || index >= WORKING_P_NUM) {
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
    if (index < 0 || index >= TARGET_P_NUM) {
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
  /**
   * Vrací označený zdrojový balíček.
   * @return označený zdrojový balíček
   */
  public Selected getSelectedSource() {
    return this.selSrc;
  }
  /**
   * Vrací index označeného zdrojového balíčku.
   * @return index označeného zdrojového balíčku
   */
  public int getSelectedSourceIndex() {
    return this.selSrcIndex;
  }
}
