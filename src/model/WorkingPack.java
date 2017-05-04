package model;

import java.util.Stack;

/**
 *
 * @author Marek Jankech, Jan Morávek
 */
public interface WorkingPack extends TargetPack {
  Card	get(int index);
  Stack<Card> pop(Card card);
  boolean put(Stack<Card> cards);
  boolean pushInit(Card card);
  boolean turnFaceUp();
}
