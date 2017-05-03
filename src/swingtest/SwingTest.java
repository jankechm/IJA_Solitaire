
package swingtest;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Marlliciuss
 */


public class SwingTest {
  public static void main(String[] a) {
    Frame frame = new Frame();
    Gameboard gameb = new Gameboard();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(720,405);
    //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setLocationRelativeTo(null);
    frame.setUndecorated(true);
    frame.add(gameb);
    frame.setVisible(true);
    
    
    
    /*
    Gameboard gameb1 = new Gameboard();
    JPanel panelm = new JPanel();
    panelm.setLayout(new FlowLayout());
    //panelm.setPreferredSize(new Dimension(640, 480));
    //panelm.setBackground(Color.PINK);
    JPanel panel1 = new JPanel();    
    panel1.setPreferredSize(new Dimension(720, 405));
    panel1.add(gameb1);
    panel1.setBackground(Color.GREEN);
    JPanel panel2 = new JPanel();    
    panel2.setPreferredSize(new Dimension(720, 405));
    panel2.setBackground(Color.YELLOW);
    JPanel panel3 = new JPanel();    
    panel3.setPreferredSize(new Dimension(720, 405));
    panel3.setBackground(Color.RED);
    JPanel panel4 = new JPanel();    
    panel4.setPreferredSize(new Dimension(720, 405)); //(720, 405)
    panel4.setBackground(Color.BLUE);
    
    
    Frame frame1 = new Frame();
    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame1.setSize(1450,880);
    //frame1.setLayout(new FlowLayout());
    frame1.add(panelm, BorderLayout.CENTER);
    panelm.add(panel1);
    panelm.add(panel2);
    panelm.add(panel3);
    panelm.add(panel4);
    
    //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame1.setLocationRelativeTo(null);
    frame1.setUndecorated(true);
    frame1.setVisible(true);
    */
    }
}
