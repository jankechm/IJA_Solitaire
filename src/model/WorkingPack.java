package model;

import java.util.Stack;

/**
 * Rozhraní reprezentující pracovní balíček.
 * @author Marek Jankech, Jan Morávek
 */
public interface WorkingPack extends TargetPack {
  /**
   * Vrátí kartu na uvedenem indexu.
   * Spodni karta je na indexu 0, vrchol je na indexu size() - 1.
   * Pokud je balíček prázdný, nebo index mimo rozsah, vrací null.
   * @param index - pozice karty v balíčku
   * @return karta z vrcholu balíčku
   */
  Card	get(int index);
  /**
   * Metoda odebere ze zásobníku sekvenci karet od zadané karty až po vrchol zásobníku.
   * Pokud je hledaná karta na vrcholu, bude v sekvenci pouze jedna karta.
   * @param card - hledaná karta
   * @return Zásobník karet obsahující odebranou sekvenci. Pokud hledaná karta v zásobníku není, vrací null.
   */
  Stack<Card> pop(Card card);
  /**
   * Vloží karty z kolekce Stack na vrchol zásobníku.
   * Karty vkládá ve stejném pořadí, v jakém jsou uvedeny v zásobníku cards.
   * @param cards - Zásobník vkládaných karet
   * @return Uspěšnost akce
   */
  boolean put(Stack<Card> cards);
  /**
   * Vloží kartu na vrchol balíčku.
   * @param card - karta ke vložení
   * @return - úspěšnost operace
   */
  boolean pushInit(Card card);
  /**
   * Otočí kartu na vrcholu lícem nahoru.
   * @return informace, zda došlo k otočení
   */
  boolean turnFaceUp();
}
