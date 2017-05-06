/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package swingtest;

import game.KlondikeGame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;


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
    //private JButton replayGame;
    private JButton quitGame;
    private JFileChooser chooseLoad;
    private FileNameExtensionFilter filter;
    private JLabel header;
    private JPanel menu;
    private JPanel gameMenu;
    private JPanel menuExit;
    private JPanel topPanel;
    private JPanel topPanelSpace;
    public JLabel statusbar;
    private ButtonHandler bHandler;
    private MouseHandler mHandler;
    private Gameboard gameb;
    //private int gameNumber = 0;



    Color blueBack = new Color(10, 75, 130);
    Color greyBack = new Color(229, 229, 229);
    Dimension min = new Dimension(5, 5);
    Dimension pref = new Dimension(900, 5);
    Dimension max = new Dimension(1000, 5);
    
    public Frame() {
        super("Klondike Solitaire");

        gameb = new Gameboard();
        add(gameb);

        this.setFrameSize();
        //gameNumber = 0;

        bHandler = new ButtonHandler();
        mHandler = new MouseHandler();
        this.addMouseListener(mHandler);
        this.addMouseMotionListener(mHandler);
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
                    saveGame.setEnabled(false);
                    loadGame = new JButton("Load Game");
                    loadGame.addActionListener(bHandler);

                menu.add(newGame);
                menu.add(saveGame);
                menu.add(loadGame);

                gameMenu = new JPanel();
                gameMenu.setBackground(new Color(240,240,240));
                gameMenu.setLayout(new FlowLayout());

                    undoGame = new JButton("Undo");
                    undoGame.setEnabled(false);
                    undoGame.addActionListener(bHandler);
                    /*replayGame = new JButton("Replay");
                    replayGame.setEnabled(false);
                    replayGame.addActionListener(bHandler);*/
                    quitGame = new JButton("Quit");
                    quitGame.setEnabled(false);
                    quitGame.addActionListener(bHandler);

                gameMenu.add(undoGame);
                //gameMenu.add(replayGame);
                gameMenu.add(quitGame);


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
            topPanel.add(Box.createRigidArea(new Dimension(5,0))); // gab after exit menu
            topPanel.add(gameMenu);
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

    public void setFrameSize() {
        gameb.gameNumber = gameb.gameNum1 + gameb.gameNum2 + gameb.gameNum3 + gameb.gameNum4;
        if (gameb.gameNumber == 0 || gameb.gameNumber == 1) {
            this.setSize(720, 405);
            this.setLocationRelativeTo(null);
        } else if (gameb.gameNumber > 1) {
            this.setSize(1425, 880);
            this.setLocationRelativeTo(null);
        }
    }

    public void resolveState(){
        gameb.gameNumber = gameb.gameNum1 + gameb.gameNum2 + gameb.gameNum3 + gameb.gameNum4;
        if (gameb.gameNumber == 0) {
            undoGame.setEnabled(false);
            quitGame.setEnabled(false);
            saveGame.setEnabled(false);
        } else {
            undoGame.setEnabled(true);
            quitGame.setEnabled(true);
            saveGame.setEnabled(true);
        }
        if (gameb.gameMotion) {
            if (gameb.gameNumber>1){
                newGame.setEnabled(false);
                loadGame.setEnabled(false);
            }
            saveGame.setEnabled(false);
            undoGame.setEnabled(false);
            quitGame.setEnabled(false);
        } else if (!gameb.gameMotion) {
            loadGame.setEnabled(true);
            newGame.setEnabled(true);
            if (gameb.gameNumber>0) {
                undoGame.setEnabled(true);
                quitGame.setEnabled(true);
                saveGame.setEnabled(true);
            }
        }
        setFrameSize();
    }

    // listener for buttons
    private class ButtonHandler extends Component implements ActionListener {
            //private KlondikeGame game1;
            public void actionPerformed(ActionEvent event) {
                if(event.getSource()==newGame) {
                    gameb.gameNumber = gameb.gameNum1 + gameb.gameNum2 + gameb.gameNum3 + gameb.gameNum4;
                    statusbar.setText("New Game");

                    if (gameb.gameNumber == 0) {
                        gameb.newProvide(1);
                    } else if (gameb.gameNumber == 1){
                        if (gameb.gameNum1 == 1) {
                            gameb.newProvide(2);
                        } else {
                            gameb.newProvide(1);
                        }
                    } else {
                        gameb.newProvide(gameb.gameActive);
                    }

                    resolveState();
                    //gameb.newProvide(gameNumber);
                    gameb.repaint();
                    gameb.gameMotion = true;
                } else if(event.getSource()==saveGame) {
                    statusbar.setText("Game saved."); // TODO  přidat do výpisu string, jak se jmenuje ten uložený soubor
                    gameb.saveProvide(gameb.gameActive);
                    gameb.gameMotion = true;
                } else if(event.getSource()==loadGame) {
                    gameb.gameNumber = gameb.gameNum1 + gameb.gameNum2 + gameb.gameNum3 + gameb.gameNum4;
                    statusbar.setText("Load Game.");
                    saveGame.setEnabled(true);
                    resolveState();

                    chooseLoad = new JFileChooser();
                    filter = new FileNameExtensionFilter("*.save", "save");
                    chooseLoad.setFileFilter(filter);
                    chooseLoad.setAcceptAllFileFilterUsed(false);
                    int result = chooseLoad.showOpenDialog(this);

                    if(result == JFileChooser.APPROVE_OPTION)
                    {
                        System.out.println("Closed chooser opened.");
                        String filename=chooseLoad.getSelectedFile().getAbsolutePath();
                        //String filename=chooseLoad.getSelectedFile().getName();
                        System.out.println(filename);
                        if (gameb.gameNumber == 0) {
                            gameb.loadProvide(1, filename);
                        } else if (gameb.gameNumber == 1){
                            if (gameb.gameNum1 == 1) {
                                gameb.loadProvide(2, filename);
                            } else {
                                gameb.loadProvide(1, filename);
                            }
                        } else {
                            gameb.loadProvide(gameb.gameActive, filename);
                        }

                        resolveState();
                        statusbar.setText("Loaded game ");
                    } else {
                        System.out.println("Closed file chooser.");
                    }
                    gameb.repaint();
                    gameb.gameMotion = true;
                } else if(event.getSource()==undoGame) {
                    statusbar.setText("Undo");
                } else if(event.getSource()==quitGame) {
                    statusbar.setText("Quit");
                    if (!gameb.quitProvide(gameb.gameActive)) {
                        statusbar.setText("You cant quit this game!");
                    }
                    resolveState();
                    gameb.repaint();
                    gameb.gameMotion = true;
                } else if(event.getSource()==exitGame) {
                    statusbar.setText("Exit Game.");
                    System.exit(0);
                }
        }
    }
    
    private class MouseHandler implements MouseListener, MouseMotionListener {
        private int xevent;
        private int yevent;
        String saveStr = "";
        
        public void mouseClicked(MouseEvent event) {
        }
        public void mousePressed(MouseEvent event) {
            xevent = event.getX();
            yevent = event.getY();
            int xtmp = 0;
            int ytmp = 0;
            if (SwingUtilities.isLeftMouseButton(event)) {
                if (gameb.gameMotion) {
                    /*
                    if ((xevent > 15 && xevent < 88) && (yevent > 71 && yevent < 168)) {
                        statusbar.setText("Clicked Card Deck");
                        //gameb.drawBorder(15, 30)
                    } */
                    statusbar.setText(String.format("Click!! %s %s", event.getX(), event.getY()));

                    if ((xevent > 11 && xevent < 712) && (yevent > 47 && yevent < 452)) { // game 1
                        xtmp = 15;
                        ytmp = 52;
                        if (yevent > ytmp && yevent < ytmp + gameb.CHEIGHT) {
                            if (xevent > xtmp && xevent < xtmp + gameb.CWIDTH) { // stock1
                                statusbar.setText("game1: stock clicked");
                                gameb.actionProvide(1, 1);
                            } else if ((xevent > xtmp + gameb.workgap + gameb.CWIDTH) && (xevent < xtmp + gameb.workgap + 2*gameb.CWIDTH)) { // waste1
                                statusbar.setText("game1: waste clicked");
                                gameb.actionProvide(1, 2);
                            } else if ((xevent > xtmp + 3*gameb.workgap + 3*gameb.CWIDTH) && (xevent < xtmp + 6*gameb.workgap + 7*gameb.CWIDTH)) { // targetPacks1
                                for (int i = 0; i<7; i++) {
                                    if ((xevent > xtmp + (i+3)*gameb.workgap + (i+3)*gameb.CWIDTH) && (xevent < xtmp + (i+3)*gameb.workgap + (i+4)*gameb.CWIDTH)) {
                                        statusbar.setText("game1: target pack " + (i + 1) + " clicked");
                                        gameb.actionProvide(1, i + 3);
                                        break;
                                    } else {
                                        statusbar.setText("game1: else clicked");
                                        gameb.actionProvide(1, 0);
                                    }
                                }
                            } else {
                                statusbar.setText("game1: else clicked");
                                gameb.actionProvide(1,0);
                            }
                        } else if (yevent > ytmp + gameb.CHEIGHT + 10 && yevent < ytmp + 2*gameb.CHEIGHT + 10) { // workingPacks1
                            for (int i = 0; i<7; i++) {
                                if ((xevent > xtmp + i*gameb.workgap + i*gameb.CWIDTH) && (xevent < xtmp + i*gameb.workgap + (i+1)*gameb.CWIDTH)) {
                                    statusbar.setText("game1: working pack " + (i + 1) + " clicked");
                                    gameb.actionProvide(1, i + 7);
                                    break;
                                } else {
                                    statusbar.setText("game1: else clicked");
                                    gameb.actionProvide(1,0);
                                }
                            }
                        } else {
                            statusbar.setText("game1: else clicked");
                            gameb.actionProvide(1,0);
                        }
                        gameb.repaint();
                    } else if ((xevent > 717 && xevent < 1418) && (yevent > 47 && yevent < 452)) { // game 2
                        xtmp = 719;
                        ytmp = 52;
                        if (yevent > ytmp && yevent < ytmp + gameb.CHEIGHT) {
                            if (xevent > xtmp && xevent < xtmp + gameb.CWIDTH) { // stock2
                                statusbar.setText("game2: stock clicked");
                                gameb.actionProvide(2, 1);
                            } else if ((xevent > xtmp + gameb.workgap + gameb.CWIDTH) && (xevent < xtmp + gameb.workgap + 2*gameb.CWIDTH)) { // waste2
                                statusbar.setText("game2: waste clicked");
                                gameb.actionProvide(2, 2);
                            } else if ((xevent > xtmp + 3*gameb.workgap + 3*gameb.CWIDTH) && (xevent < xtmp + 6*gameb.workgap + 7*gameb.CWIDTH)) { // targetPacks2
                                for (int i = 0; i<7; i++) {
                                    if ((xevent > xtmp + (i+3)*gameb.workgap + (i+3)*gameb.CWIDTH) && (xevent < xtmp + (i+3)*gameb.workgap + (i+4)*gameb.CWIDTH)) {
                                        statusbar.setText("game2: target pack " + (i + 1) + " clicked");
                                        gameb.actionProvide(2, 2 + 3);
                                        break;
                                    } else {
                                        statusbar.setText("game2: else clicked");
                                        gameb.actionProvide(2, 0);
                                    }
                                }
                            } else {
                                statusbar.setText("game2: else clicked");
                                gameb.actionProvide(2,0);
                            }
                        } else if (yevent > ytmp + gameb.CHEIGHT + 10 && yevent < ytmp + 2*gameb.CHEIGHT + 10) { // workingPacks2
                            for (int i = 0; i<7; i++) {
                                if ((xevent > xtmp + i*gameb.workgap + i*gameb.CWIDTH) && (xevent < xtmp + i*gameb.workgap + (i+1)*gameb.CWIDTH)) {
                                    statusbar.setText("game2: working pack " + (i + 1) + " clicked");
                                    gameb.actionProvide(2, i + 7);
                                    break;
                                } else {
                                    statusbar.setText("game2: else clicked");
                                    gameb.actionProvide(2,0);
                                }
                            }
                        } else {
                            statusbar.setText("game2: else clicked");
                            gameb.actionProvide(2,0);
                        }
                        gameb.repaint();
                    } else if ((xevent > 11 && xevent < 712) && (yevent > 457 && yevent < 860)) { // game 3
                        xtmp = 15;
                        ytmp = 462;
                        if (yevent > ytmp && yevent < ytmp + gameb.CHEIGHT) {
                            if (xevent > xtmp && xevent < xtmp + gameb.CWIDTH) { // stock3
                                statusbar.setText("game3: stock clicked");
                                gameb.actionProvide(3, 1);
                            } else if ((xevent > xtmp + gameb.workgap + gameb.CWIDTH) && (xevent < xtmp + gameb.workgap + 2*gameb.CWIDTH)) { // waste3
                                statusbar.setText("game3: waste clicked");
                                gameb.actionProvide(3, 2);
                            } else if ((xevent > xtmp + 3*gameb.workgap + 3*gameb.CWIDTH) && (xevent < xtmp + 6*gameb.workgap + 7*gameb.CWIDTH)) { // targetPacks3
                                for (int i = 0; i<7; i++) {
                                    if ((xevent > xtmp + (i+3)*gameb.workgap + (i+3)*gameb.CWIDTH) && (xevent < xtmp + (i+3)*gameb.workgap + (i+4)*gameb.CWIDTH)) {
                                        statusbar.setText("game3: target pack " + (i + 1) + " clicked");
                                        gameb.actionProvide(3, 2 + 3);
                                        break;
                                    } else {
                                        statusbar.setText("game3: else clicked");
                                        gameb.actionProvide(3, 0);
                                    }
                                }
                            } else {
                                statusbar.setText("game3: else clicked");
                                gameb.actionProvide(3,0);
                            }
                        } else if (yevent > ytmp + gameb.CHEIGHT + 10 && yevent < ytmp + 2*gameb.CHEIGHT + 10) { // workingPacks3
                            for (int i = 0; i<7; i++) {
                                if ((xevent > xtmp + i*gameb.workgap + i*gameb.CWIDTH) && (xevent < xtmp + i*gameb.workgap + (i+1)*gameb.CWIDTH)) {
                                    statusbar.setText("game3: working pack " + (i + 1) + " clicked");
                                    gameb.actionProvide(3, i + 7);
                                    break;
                                } else {
                                    statusbar.setText("game3: else clicked");
                                    gameb.actionProvide(3,0);
                                }
                            }
                        } else {
                            statusbar.setText("game3: else clicked");
                            gameb.actionProvide(3,0);
                        }
                        gameb.repaint();
                    } else if ((xevent > 717 && xevent < 1418) && (yevent > 457 && yevent < 860)) { // game 4
                        xtmp = 719;
                        ytmp = 462;
                        if (yevent > ytmp && yevent < ytmp + gameb.CHEIGHT) {
                            if (xevent > xtmp && xevent < xtmp + gameb.CWIDTH) { // stock4
                                statusbar.setText("game4: stock clicked");
                                gameb.actionProvide(4, 1);
                            } else if ((xevent > xtmp + gameb.workgap + gameb.CWIDTH) && (xevent < xtmp + gameb.workgap + 2*gameb.CWIDTH)) { // waste4
                                statusbar.setText("game4: waste clicked");
                                gameb.actionProvide(4, 2);
                            } else if ((xevent > xtmp + 3*gameb.workgap + 3*gameb.CWIDTH) && (xevent < xtmp + 6*gameb.workgap + 7*gameb.CWIDTH)) { // targetPacks4
                                for (int i = 0; i<7; i++) {
                                    if ((xevent > xtmp + (i+3)*gameb.workgap + (i+3)*gameb.CWIDTH) && (xevent < xtmp + (i+3)*gameb.workgap + (i+4)*gameb.CWIDTH)) {
                                        statusbar.setText("game4: target pack " + (i + 1) + " clicked");
                                        gameb.actionProvide(4, 2 + 3);
                                        break;
                                    } else {
                                        statusbar.setText("game4: else clicked");
                                        gameb.actionProvide(4, 0);
                                    }
                                }
                            } else {
                                statusbar.setText("game4: else clicked");
                                gameb.actionProvide(4,0);
                            }
                        } else if (yevent > ytmp + gameb.CHEIGHT + 10 && yevent < ytmp + 2*gameb.CHEIGHT + 10) { // workingPacks4
                            for (int i = 0; i<7; i++) {
                                if ((xevent > xtmp + i*gameb.workgap + i*gameb.CWIDTH) && (xevent < xtmp + i*gameb.workgap + (i+1)*gameb.CWIDTH)) {
                                    statusbar.setText("game4: working pack " + (i + 1) + " clicked");
                                    gameb.actionProvide(4, i + 7);
                                    break;
                                } else {
                                    statusbar.setText("game4: else clicked");
                                    gameb.actionProvide(4,0);
                                }
                            }
                        } else {
                            statusbar.setText("game4: else clicked");
                            gameb.actionProvide(4,0);
                        }
                        gameb.repaint();
                    } else {
                        //gameb.gameActive = 0;
                        gameb.repaint();
                    }
                }
            } else if (SwingUtilities.isRightMouseButton(event)) {
                gameb.gameMotion = false;
                if ((xevent > 11 && xevent < 712) && (yevent > 47 && yevent < 452)) { // game 1
                    gameb.gameActive = 1;
                    statusbar.setText("Selected game 1.");
                } else if ((xevent > 717 && xevent < 1418) && (yevent > 47 && yevent < 452)) { // game 2
                    gameb.gameActive = 2;
                    statusbar.setText("Selected game 2.");
                } else if ((xevent > 11 && xevent < 712) && (yevent > 457 && yevent < 860)) { // game 3
                    statusbar.setText("Selected game 3.");
                    gameb.gameActive = 3;
                } else if ((xevent > 717 && xevent < 1418) && (yevent > 457 && yevent < 860)) { // game 4
                    statusbar.setText("Selected game 4.");
                    gameb.gameActive = 4;
                } else {
                    gameb.gameActive=0;
                    gameb.gameMotion=true;
                    statusbar.setText("Unselected games.");
                }
            }
            resolveState();
            gameb.repaint();
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
            if (gameb.gameMotion) {
                xevent = event.getX();
                yevent = event.getY();
                if ((xevent > 11 && xevent < 712) && (yevent > 47 && yevent < 452)) {
                    gameb.gameActive = 1;
                } else if ((xevent > 717 && xevent < 1418) && (yevent > 47 && yevent < 452)) {
                    gameb.gameActive = 2;
                } else if ((xevent > 11 && xevent < 712) && (yevent > 457 && yevent < 860)) {
                    gameb.gameActive = 3;
                } else if ((xevent > 717 && xevent < 1418) && (yevent > 457 && yevent < 860)) {
                    gameb.gameActive = 4;
                } else {
                    gameb.gameActive = 0;
                }
                repaint();
            }
            //statusbar.setText("Mouse moved");
        }
        public void mouseDragged(MouseEvent event) {
            //statusbar.setText("Mouse dragged!");
        }
    
    }
}


 /*
                        Object[] options = {"Game 1", "Game 2", "Game 3", "Game 4"};
                        int choice = JOptionPane.showOptionDialog(null, //Component parentComponent
                                "Choose where you want to create game, or which game u want to replay.",
                                "New game.",
                                JOptionPane.YES_NO_OPTION, //int optionType
                                JOptionPane.INFORMATION_MESSAGE, //int messageType
                                null, //Icon icon,
                                options, null);
                        if (choice == 0) {
                            System.out.println("choice 1");
                            gameb.newProvide(1);
                            //if (gameb.game1 == null)
                        } else if (choice == 1) {
                            System.out.println("choice 2");
                            gameb.newProvide(2);
                        } else if (choice == 2) {
                            gameb.newProvide(3);
                            System.out.println("choice 3");
                        } else if (choice == 3) {
                            gameb.newProvide(4);
                            System.out.println("choice 4");
                        } */