package model;

import java.util.Stack;

/**
 *
 * @author Marek Jankech, Jan Morávek
 */
public interface WorkingPack extends TargetPack {
  public Card	get(int index);
  public Stack<Card> pop(Card card);
  public boolean put(Stack<Card> cards);
  public boolean pushInit(Card card);
  public boolean turnFaceUp();
}
