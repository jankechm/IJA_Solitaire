package model;

/**
 * Rozhraní reprezentující jednu kartu.
 * Karta obsahuje informaci o své hodnotě (1 až 13) a barvě. Tyto informace jsou
 * nastaveny konstruktorem. Hodnota 1 reprezentuje eso (ace), 11 až 13 postupně 
 * kluk (jack), královna (queen) a král (king). Barvu definuje výčtový typ Color.
 * @author Marek Jankech, Jan Morávek
 */
public interface Card {

  /**
   * Výčtový typ reprezentující barvu karty.
   */
  public static enum Color {

    /**Spides (piky). Textová reprezentace barvy je "S".*/
    CLUBS,
    /**Diamonds (káry). Textová reprezentace barvy je "D".*/
    DIAMONDS,
    /**Hearts (srdce). Textová reprezentace barvy je "H".*/
    HEARTS,
    /**Clubs (kříže). Textová reprezentace barvy je "C".*/
    SPADES;
    
    @Override
    public String toString() {
      return this.name().substring(0, 1);
    }
  }
  
  /**
   * Vrátí barvu karty
   * @return Barva karty.
   */
  Color color();
  /**
   * Porovná hodnotu karty se zadanou kartou c.
   * Pokud jsou stejné, vrací 0. Pokud je karta větší než zadaná c, vrací kladný rozdíl hodnot.
   * @param c - Karta, s kterou se porovnává.
   * @return Rozdíl hodnot karet.
   */
  int	compareValue(Card c);
  /**
   * Testuje, zda je karta otočená lícem nahoru.
   * @return true, pokud je karta je otočená lícem nahoru.
   */
  boolean	isTurnedFaceUp();
  /**
   * Testuje, zda má karta podobnou barvu jako karta zadaná.
   * Podobnou barvou se myslí černá (piky, kříže) a červená (káry a srdce).
   * @param c - Karta pro porovnání.
   * @return Informace o zhodě barev
   */
  boolean	similarColorTo(Card c);
  /**
   * Otočí kartu lícem nahoru.
   * Pokud tak už je, nedělá nic.
   * @return true, pokud došlo k otočení karty
   */
  boolean	turnFaceUp();
  /**
   * Otočí kartu rubem nahoru.
   * Pokud tak už je, nedělá nic.
   * @return true, pokud došlo k otočení karty
   */
  boolean	turnFaceDown();
  /**
   * Vrací hodnotu karty.
   * @return Hodnota karty.
   */
  int value();
}
