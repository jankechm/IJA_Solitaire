/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package swingtest;

import swingtest.Frame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author "Jan Morávek (xmorav33)"
 */
public class Gameboard extends JComponent{
    
    private static final int CWIDTH = 73;
	private static final int CHEIGHT = 97;
    private int xgap = 20;
    private int ygap = 20;
    private int xd = 0;
    private int yd = 0;
    private int workgap = 30;
    Color blueBack = new Color(10, 75, 130);
    
    public void paint(Graphics g) { // drawRect(x, y, width, height);
        
        g.setColor(blueBack);
        
// DELETE PATH 
        String namerino = "X:/IJA/Solitaire-In-Java-master/src/cards/back2.jpg";
        
        workgap = 30;
        xgap = 15;
        ygap = 30;
        // draw deck
        xd = xgap;
        yd = ygap;
        drawCard(g, namerino, xd, yd);
        /*
        xd = 42;
        yd = ygap;
        drawCard(g, namerino, xd, yd);
        */
// DELETE PATH 
        namerino = "";
        
        // draw stack
        xd = xgap + workgap + CWIDTH;
        yd = ygap;
        drawCard(g, namerino, xd, yd);
        
        // target pack 1
        xd = xgap + 3 * workgap + 3 * CWIDTH;
        yd = ygap;
        drawCard(g, namerino, xd, yd);
        
        // target pack 2
        xd = xgap + 4 * workgap + 4 * CWIDTH;
        yd = ygap;
        drawCard(g, namerino, xd, yd);
            
        // target pack 3
        xd = xgap + 5 * workgap + 5 * CWIDTH;
        yd = ygap;
        drawCard(g, namerino, xd, yd);
            
        // target pack 4
        xd = xgap + 6 * workgap + 6 * CWIDTH;
        yd = ygap;
        drawCard(g, namerino, xd, yd);
        
        xgap = 15;
        ygap = 50;
        // working pack 1
        xd = xgap;
        yd = ygap + CHEIGHT;
        drawCard(g, namerino, xd, yd);
            
        // working pack 2
        xd = xgap + workgap + CWIDTH;
        yd = ygap + CHEIGHT;
        drawCard(g, namerino, xd, yd);
            
        // working pack 3
        xd = xgap + 2 * workgap + 2 * CWIDTH;
        yd = ygap + CHEIGHT;
        drawCard(g, namerino, xd, yd);
            
        // working pack 4
        xd = xgap + 3 * workgap + 3 * CWIDTH;
        yd = ygap + CHEIGHT;
        drawCard(g, namerino, xd, yd);
            
        // working pack 5
        xd = xgap + 4 * workgap + 4 * CWIDTH;
        yd = ygap + CHEIGHT;
        drawCard(g, namerino, xd, yd);
            
        // working pack 6
        xd = xgap + 5 * workgap + 5 * CWIDTH;
        yd = ygap + CHEIGHT;
        drawCard(g, namerino, xd, yd);
            
        // working pack 7
        xd = xgap + 6 * workgap + 6 * CWIDTH;
        yd = ygap + CHEIGHT;
        drawCard(g, namerino, xd, yd);
        
        
    }
    
    private void drawCard(Graphics g, String fileName, int x, int y)
	{
		if (fileName == "")
		{
			g.setColor(blueBack);
			g.drawRect(x, y, CWIDTH, CHEIGHT);
            //statusbar.setText("DRAWED");
		}
		else
		{
            try {
                Scanner sc = new Scanner(new File(fileName));
            }
            catch (Exception e) {
                System.out.println("could not find file with picture");
            }
			Image image = new ImageIcon(fileName).getImage();
			g.drawImage(image, x, y, CWIDTH, CHEIGHT, null);
		}
	}
    
}
