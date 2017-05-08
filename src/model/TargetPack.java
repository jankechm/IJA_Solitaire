package model;

/**
 * Rozhraní reprezentující cílový balíček.
 * @author Marek Jankech, Jan Morávek
 */
public interface TargetPack {
  /**
   * Vloží kartu na vrchol balíčku.
   * @param card - karta ke vložení
   * @return - úspěšnost operace
   */
  boolean put(Card card);
  /**
   * Odebere kartu z vrcholu zásobníku. Pokud je zásobník prázdný, vrací null.
   * @return karta z vrcholu zásobníku
   */
  Card pop();
  /**
   * Vrátí kartu z vrcholu zásobníku (karta zůstává na zásobníku).
   * @return karta z vrcholu zásobníku
   */
  Card get();
  /**
   * Zjišťuje aktuální počet karet v balíčku.
   * @return aktuální počet karet v balíčku
   */
  int size();
}
