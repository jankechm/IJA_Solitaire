package game;

/**
 * Rozhraní reprezentující operaci s kartami.
 * @author Marek Jankech, Jan Morávek
 */
public interface Command {
  public boolean execute();
  public boolean undo();
}
