package model;

/**
 *
 * @author Marek
 */
public interface CardDeck {
  /**Velikost zdroj. balíčku*/
  public static final int STD_DECK_SIZE = 52;
  /**Velikost kaskády karet jedné barvy*/
  public static final int SAME_COLORED_MAX = 13;
  /**
   * Vrátí kartu z vrcholu zásobníku (karta zůstává na zásobníku). Pokud je balíček prázdný, vrací null.
   * @return Karta z vrcholu balíčku, nebo null
   */
  Card get();
  /**
   * Vrátí kartu na uvedenem indexu.
   * Spodni karta je na indexu 0, vrchol je na indexu size()-1.
   * Pokud je balíček prázdný, nebo index mimo rozsah, vrací null.
   * @param index - Pozice karty v balíčku
   * @return Karta z vrcholu balíčku
   */
  Card get(int index);
  /**
   * Test, zda je balíček karet prázdný.
   * @return Vrací true, pokud je balíček prázdný, jinak false
   */
  boolean	isEmpty();
  /**
   * Odebere kartu z vrcholu balíčku. Pokud je balíček prázdný, vrací null.
   * @return Karta z vrcholu balíčku, nebo null
   */
  Card pop();
  /**
   * Odebere náhodnou kartu z balíčku. Pokud je balíček prázdný, vrací null.
   * @return Náhodná karta z balíčku, nebo null
   */
  Card popRandom();
  /**
   * Vloží kartu na vrchol balíčku.
   * @param card - Vkládaná karta
   * @return Úspěšnost akce
   */
  boolean put(Card card);
  /**
   * Zjišťuje aktuální počet karet v balíčku.
   * @return aktuální počet karet v balíčku
   */
  int size();
  /**
   * Promíchá karty v balíčku.
   */
  void shuffle();
}
