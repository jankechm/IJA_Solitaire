package model;

/**
 *
 * @author Marek Jankech, Jan Morávek
 */
public interface TargetPack {
  public boolean put(Card card);
  public Card pop();
  public Card get();
  public int size();
}
