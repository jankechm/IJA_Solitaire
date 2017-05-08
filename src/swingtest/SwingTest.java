
package swingtest;

import javax.swing.*;

/**
 * Hlavní třída aplikace. Slouží pouze pro vytvoření okna aplikace a nastavení několika parametrů.
 * @author Jan Morávek, Marek Jankech
 */
public class SwingTest {
  public static void main(String[] a) {
    Frame frame = new Frame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setUndecorated(true);
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);
    }
}
