/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package swingtest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author "Jan Morávek (xmorav33)"
 */
public class Frame extends JFrame {
    
    private JButton newGame;
    private JButton saveGame;
    private JButton loadGame;
    private JButton exitGame;
    private JButton undoGame;
    private JLabel header;
    private JPanel menu;
    private JPanel menuExit;
    private JPanel topPanel;
    private JPanel topPanelSpace;
    public JLabel statusbar;
    private ButtonHandler bHandler;
    private MouseHandler mHandler;
    
    Color blueBack = new Color(10, 75, 130);
    
    Dimension min = new Dimension(5, 5);
    Dimension pref = new Dimension(900, 5);
    Dimension max = new Dimension(1000, 5);
    
    public Frame() {
        super("Klondike Solitaire");
        
        bHandler = new ButtonHandler();
        mHandler = new MouseHandler();
        this.addMouseListener(mHandler);
        //setSize(1280,720);
        
        // top panel - only for making gap on top
        topPanelSpace = new JPanel();
        topPanelSpace.setLayout(new BoxLayout(topPanelSpace, BoxLayout.PAGE_AXIS));
        topPanelSpace.add(Box.createRigidArea(new Dimension(0,5)));
        
            // top panel - invisible panel
            topPanel = new JPanel();
            topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
            topPanelSpace.add(topPanel);

                // menu panel
                menu = new JPanel();
                menu.setBackground(blueBack);
                menu.setLayout(new FlowLayout());

                    newGame = new JButton("New Game");
                    newGame.addActionListener(bHandler);
                    saveGame = new JButton("Save Game");
                    saveGame.addActionListener(bHandler);
                    loadGame = new JButton("Load Game");
                    loadGame.addActionListener(bHandler);
                    undoGame = new JButton("UNDO");
                    undoGame.addActionListener(bHandler);

                menu.add(newGame);
                menu.add(saveGame);
                menu.add(loadGame);
                menu.add(undoGame);


                // header label
                header = new JLabel("Klondike");
                header.setFont(new java.awt.Font("Verdana", 1, 28));
                header.setForeground(blueBack);

                //menu exit
                menuExit = new JPanel();
                menuExit.setBackground(blueBack);
                menuExit.setLayout(new FlowLayout(FlowLayout.RIGHT));

                    exitGame = new JButton("EXIT");
                    exitGame.addActionListener(bHandler);

                menuExit.add(exitGame);

            topPanel.add(Box.createRigidArea(new Dimension(5,0))); // gap before menu
            topPanel.add(menu);
            topPanel.add(new Box.Filler(min, pref, max)); // gap before header label
            topPanel.add(header);
            topPanel.add(new Box.Filler(min, pref, max)); // gape after header label
            topPanel.add(menuExit);
            topPanel.add(Box.createRigidArea(new Dimension(5,0))); // gab after exit menu
        
            // statusbar - showing events and messages
            statusbar = new JLabel("Welcome to game Klondike Solitaire.");
            statusbar.setFont(new java.awt.Font("Verdana",1, 12));
            statusbar.setOpaque(true);
            statusbar.setBackground(blueBack);
            statusbar.setForeground(Color.WHITE);
        
        add(topPanelSpace, BorderLayout.NORTH);
        add(statusbar, BorderLayout.SOUTH);
        
    }
    
    // listener for buttons
    private class ButtonHandler implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                if(event.getSource()==newGame) {
                    statusbar.setText("New Game");
                }
                else if(event.getSource()==saveGame) {
                    statusbar.setText("Save Game.");
                }
                else if(event.getSource()==loadGame) {
                     statusbar.setText("Load Game.");
                }
                else if(event.getSource()==undoGame) {
                     statusbar.setText("UNDO");
                }
                else if(event.getSource()==exitGame) {
                    statusbar.setText("Exit Game.");
                    System.exit(0);
                }
        }
    }
    
    private class MouseHandler implements MouseListener, MouseMotionListener {
        
        String saveStr = "";
        
        public void mouseClicked(MouseEvent event) {
            int xevent = event.getX();
            int yevent = event.getY();
            if ((xevent>15 && xevent<88) && (yevent>30 && yevent<127) ) {
                statusbar.setText("Clicked Card Deck");
            } else {
                statusbar.setText(String.format("Click!! %s %s", event.getX(), event.getY()));
            }
        }
        public void mousePressed(MouseEvent event) {
            //statusbar.setText("Pressed mouse!");
        }
        public void mouseReleased(MouseEvent event) {
            //statusbar.setText("Released mouse!");
        }
        public void mouseExited(MouseEvent event) {
            //saveStr = statusbar.getText();
            //statusbar.setText("You are out of window!");
        }
        public void mouseEntered(MouseEvent event) {
           //statusbar.setText(saveStr);
        }
        public void mouseMoved(MouseEvent event) {
            //statusbar.setText("Mouse moved");
        }
        public void mouseDragged(MouseEvent event) {
            //statusbar.setText("Mouse dragged!");
        }
    
    }
}


