package game;

/**
 * Rozhraní reprezentující operaci s kartou/kartami.
 * @author Marek Jankech, Jan Morávek
 */
public interface Command {
  public boolean execute();
  public boolean undo();
}
