package game;

/**
 * Rozhraní reprezentující operaci/transakci.
 * @author Marek Jankech, Jan Morávek
 */
public interface Command {
  /**
   * Provedení dané operace.
   * @return true, v případě úspechu, jinal false
   */
  public boolean execute();
  /**
   * Provedení reverzní operace k dané operaci.
   * Vrácení původního stavu před provedením.
   * @return true, v případě úspechu, jinal false
   */
  public boolean undo();
}
