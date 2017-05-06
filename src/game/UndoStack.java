package game;

import java.util.Stack;

/**
 * Zásobník operací Undo.
 * @author Marek Jankech, Jan Morávek
 */
public class UndoStack {
  protected static final int MAX_UNDO_OPERATIONS = 5;
  protected Stack<Command> uStack;
  
  public UndoStack() {
    this.uStack = new Stack<>();
  }
  
  public void push(Command cmd) {
    if (this.isFull()) {
      this.uStack.remove(0);
    }
    this.uStack.push(cmd);
  }
  
  public Command pop() {
    if (!this.uStack.isEmpty()) {
      return this.uStack.pop();
    }
    return null;
  }
  
  protected boolean isFull() {
    return this.uStack.size() >= MAX_UNDO_OPERATIONS;
  }
}
