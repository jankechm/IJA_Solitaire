package model;

/**
 *
 * @author Marek Jankech, Jan Morávek
 */
public interface TargetPack {
  boolean put(Card card);
  Card pop();
  Card get();
  int size();
}
