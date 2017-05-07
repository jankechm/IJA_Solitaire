/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package swingtest;

import game.Hint;
import game.KlondikeGame;
import model.KlondikeTargetPack;
import model.KlondikeWorkingPack;
import model.TargetPack;
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
    
    public static final int CWIDTH = 73;
	public static final int CHEIGHT = 97;
    //private int xgap = 20;
    //private int ygap = 20;
    private int xd = 0;
    private int yd = 0;
    public int workgap = 30;
    private int i;
    private int j;
    private int k;
    private int m;
    private KlondikeGame game1;
    private KlondikeGame game2;
    private KlondikeGame game3;
    private KlondikeGame game4;
    /*private Hint hint1;
    private Hint hint2;
    private Hint hint3;
    private Hint hint4; */
    public int gameNumber = 0;
    public int gameNum1 = 0;
    public int gameNum2 = 0;
    public int gameNum3 = 0;
    public int gameNum4 = 0;
    public int gameActive = 0;
    public boolean gameMotion = true;
    private int selectedCard = -1;
    private String namerino = "";
    Color blueBack = new Color(10, 75, 130);

    /** Method is called by repaint() method and is calling necessary methods for game board drawing*/
    public void paint(Graphics g) { // drawRect(x, y, width, height);
        g.setColor(blueBack);
        gameNumber = gameNum1 + gameNum2 + gameNum3 + gameNum4;
        //System.out.println(gameNumber);
        if (gameNumber == 0){
            g.setColor(new Color(240,240,240));
            g.fillRect(0, 0, getWidth(), getHeight());
            drawNoGame(g, 15, 10);
        } else if (gameNumber == 1) {
            if (gameNum1==1) {
                drawGame(g, game1, 15, 10);
            } else if (gameNum2==1) {
                drawGame(g, game2, 15, 10);
            } else if (gameNum3==1) {
                drawGame(g, game3, 15, 10);
            } else if (gameNum4==1) {
                drawGame(g, game4, 15, 10);
            }
        } else if (gameNumber > 1) {
            drawGameBorder(g);
            //if (gameNum1 == 1)
                drawGame(g, game1, 15, 10);
            //if (gameNum2 == 1)
                drawGame(g, game2, 719, 10);
            //if (gameNum3 == 1)
                drawGame(g, game3, 15, 420);
            //if (gameNum4 == 1)
                drawGame(g, game4, 719, 420);
        }
    }


    /*******************************************************************************************************************
     * Methods for drawing game board, game cards, and borders of selected games and cards.
     ******************************************************************************************************************/
    /** Method draws whole game board.*/
    private void drawGame (Graphics g, KlondikeGame gameTmp, int xgap, int ygap) {
        String dir = "lib/cards/";
        String back = "lib/cards/back2.jpg";
        resolveBorder(gameTmp);

            workgap = 30;
            // draw stock
            xd = xgap;
            yd = ygap;
            namerino = back;
            try {
                gameTmp.getDeck();
            } catch (Exception e){
                namerino = "";
            }
            drawCard(g, namerino, xd, yd);

            // draw waste
            xd = xgap + workgap + CWIDTH;
            yd = ygap;
            try {
                namerino = gameTmp.getWaste().get().toString();
                //System.out.println(namerino);
                namerino = dir + namerino + ".gif";
                drawCard(g, namerino, xd, yd);
            } catch (Exception e) {
                namerino = "";
            }
            if (selectedCard == 2) {
                    drawCardBorder(g, xd, yd);
            }

            // target packs
            xd = xgap + 3 * workgap + 3 * CWIDTH;
            yd = ygap;

            for (i = 1; i <= 4; i++) {
                xd = xgap + (i + 2) * workgap + (i + 2) * CWIDTH;
                yd = ygap;
                try {
                    namerino = gameTmp.getTargetPack(i-1).get().toString();
                    namerino = dir + namerino + ".gif";
                } catch (Exception e) {
                    namerino = "";
                }
                drawCard(g, namerino, xd, yd);
                if (selectedCard == i+2) {
                    drawCardBorder(g, xd, yd);
                }
            }

            ygap = ygap +10;
            // working packs
            xd = xgap;
            yd = ygap + CHEIGHT;
            for (i = 1; i < 8; i++) {
                xd = xgap + (i - 1) * workgap + (i - 1) * CWIDTH;
                try {
                    k = gameTmp.getWorkingPack(i - 1).size();
                    //System.out.println(k);
                    for (j = 1; j <= k; j++) {
                        try {
                            if (!gameTmp.getWorkingPack(i - 1).getCards().get(j - 1).isTurnedFaceUp()) {
                                namerino = back;
                                yd = ygap + CHEIGHT + 17 * (j - 1);
                            } else {
                                namerino = gameTmp.getWorkingPack(i - 1).getCards().get(j - 1).toString();
                                yd = ygap + CHEIGHT + 17 * (j - 1);
                                namerino = dir + namerino + ".gif";
                                //                            System.out.println(namerino);
                            }
                            //namerino = "";
                        } catch (Exception e) {
                            namerino = "";
                            yd = ygap + CHEIGHT;
                        }
                        drawCard(g, namerino, xd, yd);
                        if (selectedCard == i+6 && j==k) {
                            drawCardBorder(g, xd, yd);
                        }
                    }
                } catch (Exception ee) {
                    namerino = "";
                    drawCard(g, namerino, xd, yd);
                }
            }
    }

    /** Method draws no game.*/
    private void drawNoGame (Graphics g, int xgap, int ygap) {
        namerino = "";
        workgap = 30;

        // draw deck
        xd = xgap;
        yd = ygap;
        drawCard(g, namerino, xd, yd);

        // target packs
        xd = xgap + 3 * workgap + 3 * CWIDTH;
        yd = ygap;

        for (i = 1; i <= 4; i++) {
            xd = xgap + (i + 2) * workgap + (i + 2) * CWIDTH;
            yd = ygap;
            drawCard(g, namerino, xd, yd);
        }

        ygap = ygap + 10;
        // working packs
        xd = xgap;
        yd = ygap + CHEIGHT;

        for (i = 1; i < 8; i++) {
            xd = xgap + (i - 1) * workgap + (i - 1) * CWIDTH;
            drawCard(g, namerino, xd, yd);
        }
    }

    /** Method draws cards on game boards.*/
    private void drawCard(Graphics g, String fileName, int x, int y)
    {
        if (fileName == "")
        {
            g.setColor(blueBack);
            g.drawRect(x, y, CWIDTH, CHEIGHT);
        }
        else
        {
            try {
                Scanner sc = new Scanner(new File(fileName));
            }
            catch (Exception e) {
                System.out.println("Could not find file '" + fileName + "' with picture");
            }
            Image image = new ImageIcon(fileName).getImage();
            g.drawImage(image, x, y, CWIDTH, CHEIGHT, null);
        }
    }

    /** Method draws game border of selected game.*/
    public void drawGameBorder (Graphics g) {
        if (gameActive == 0) {
            g.drawRect(10,5,1407,815);
            g.drawRect(11,6,1405,813);
        } else if (gameActive == 1) {
            g.drawRect(10,5,701,405);
            g.drawRect(11,6,699,403);
        } else if (gameActive == 2){
            g.drawRect(715,5,701,405);
            g.drawRect(716,6,699,403);
        } else if (gameActive == 3) {
            g.drawRect(10,415,701,405);
            g.drawRect(11,416,699,403);
        } else if (gameActive == 4) {
            g.drawRect(715,415,701,405);
            g.drawRect(716,416,699,403);
        }
    }

    /** Method draws border of selected card in game.*/
    private void drawCardBorder (Graphics g, int x, int y) {
        g.setColor(blueBack);
        g.drawRect(x, y, CWIDTH, CHEIGHT);

        g.drawRect(x + 1, y + 1, CWIDTH - 2, CHEIGHT - 2);
        g.drawRect(x + 2, y + 2, CWIDTH - 4, CHEIGHT - 4);
    }

    /*******************************************************************************************************************
     * Methods providing acces to Klondike game objects and apllying requested operations from Frame to KlondikeGame
     * objects
     ******************************************************************************************************************/
    /** Method providing creating of new game.*/
    public void newProvide(int index) {
        if (index == 1) {
            if (gameNum1==0) {
                game1 = new KlondikeGame();
            }
            game1.newGame();
            gameNum1 = 1;
        } else if (index == 2) {
            if (gameNum2==0) {
                game2 = new KlondikeGame();
            }
            game2.newGame();
            gameNum2 = 1;
        } else if (index == 3) {
            if (gameNum3==0) {
                game3 = new KlondikeGame();
            }
            game3.newGame();
            gameNum3 = 1;
        } else if (index == 4) {
            if (gameNum4==0) {
                game4 = new KlondikeGame();
            }
            game4.newGame();
            gameNum4 = 1;
        }
    }

    /** Method providing load of game.*/
    public void loadProvide(int index, String nameFile) {
        quitProvide(index);
        newProvide(index);
        switch (index) {
            case 1:
                game1.loadGame(nameFile);
                break;
            case 2:
                game2.loadGame(nameFile);
                break;
            case 3:
                game3.loadGame(nameFile);
                break;
            case 4:
                game4.loadGame(nameFile);
                break;
        }
    }

    /** Method providing save of game.*/
    public void saveProvide(int index) {
        switch (index) {
            case 1:
                game1.saveGame();
                break;
            case 2:
                game2.saveGame();
                break;
            case 3:
                game3.saveGame();
                break;
            case 4:
                game4.saveGame();
                break;
        }
    }

    /** Method providing quit of game.*/
    public boolean quitProvide(int index) {
        if (index == 1 && gameNum1 == 1) {
            game1.quitGame();
            game1 = null;
            gameNum1 = 0;
        } else if (index == 2 && gameNum2 == 1) {
            game2.quitGame();
            game2 = null;
            gameNum2 = 0;
        } else if (index == 3 && gameNum3 == 1) {
            game3.quitGame();
            game3 = null;
            gameNum3 = 0;
        } else if (index == 4 && gameNum4 == 1) {
            game4.quitGame();
            game4 = null;
            gameNum4 = 0;
        } else {
            return false;
        }
        return true;
    }

    /** Metoda, která zprostředkuje akci undo. */
    public void undoProvide(int index) {
        if (index == 1 && gameNum1 == 1) {
            game1.undo();
        } else if (index == 2 && gameNum2 == 1) {
            game2.undo();
        } else if (index == 3 && gameNum3 == 1) {
            game3.undo();
        } else if (index == 4 && gameNum4 == 1) {
            game4.undo();
        }
    }

    /** Metoda, která zprostředkuje akci undo. */
    public void hintProvide(int index) {  // TODO smazat nápovědy
        if (index == 1 && gameNum1 == 1) {
            Hint hint1 = game1.hint();
            if (hint1 == null) {
                System.out.println("game1: no possible hint");
            } else if (hint1.getSrc() == KlondikeGame.Selected.STOCK) {
                System.out.println("STOCK SELECTED");
            } else if (hint1.getSrc() == KlondikeGame.Selected.WASTE) {
                hint1.getDest().toString();
                hint1.getDestIndex();
                System.out.println("WASTE " + hint1.getDest().toString()+ "" + hint1.getDestIndex());
            } else if (hint1.getSrc() == KlondikeGame.Selected.WORKING_PACK) {
                hint1.getDest();
                hint1.getDestIndex();
                hint1.getSrcIndex();
                System.out.println("WORKING PACK" + hint1.getSrcIndex() + " " + hint1.getDest().toString() + hint1.getDestIndex());
            }
        } else if (index == 2 && gameNum2 == 1) {
            Hint hint2 = game2.hint();
        } else if (index == 3 && gameNum3 == 1) {
            Hint hint3 = game3.hint();
        } else if (index == 4 && gameNum4 == 1) {
            Hint hint4 = game4.hint();
        }
        System.out.println("hint provide" + index);
    }

    /** Method apllies requested operations over selected game.*/
    public void actionProvide (int index, int action) {
        System.out.printf("GAME%d: action: %d\n", index, action);
        if (index == 1 && gameNum1 == 1) {
            if (action == 0) {
                game1.selectedNothing();
            }else if (action == 1) {
                game1.selectedStock();
            } else if (action == 2) {
                game1.selectedWaste();
            } else if (action >= 3 && action <= 6) {
                game1.selectedTargetPack(action-3);
            } else if (action >= 7) {
                game1.selectedWorkingPack(action-7);
            }
        } else if (index == 2 && gameNum2 == 1) {
            if (action == 0) {
                game2.selectedNothing();
            }else if (action == 1) {
                game2.selectedStock();
            } else if (action == 2) {
                game2.selectedWaste();
            } else if (action >= 3 && action <= 6) {
                game2.selectedTargetPack(action-3);
            } else if (action >= 7) {
                game2.selectedWorkingPack(action-7);
            }
        } else if (index == 3 && gameNum3 == 1) {
            if (action == 0) {
                game3.selectedNothing();
            }else if (action == 1) {
                game3.selectedStock();
            } else if (action == 2) {
                game3.selectedWaste();
            } else if (action >= 3 && action <= 6) {
                game3.selectedTargetPack(action-3);
            } else if (action >= 7) {
                game3.selectedWorkingPack(action-7);
            }
        } else if (index == 4 && gameNum4 == 1) {
            if (action == 0) {
                game4.selectedNothing();
            }else if (action == 1) {
                game4.selectedStock();
            } else if (action == 2) {
                game4.selectedWaste();
            } else if (action >= 3 && action <= 6) {
                game4.selectedTargetPack(action-3);
            } else if (action >= 7) {
                game4.selectedWorkingPack(action-7);
            }
        }
    }

    /*******************************************************************************************************************
     * This Method is only for resolving selected cards from KlondikeGame objects
     ******************************************************************************************************************/
    private void resolveBorder(KlondikeGame gameTmp) {
        try {
            int index = gameTmp.getSelectedSourceIndex();
            if (gameTmp.getSelectedSource() == KlondikeGame.Selected.WASTE) {
                selectedCard = 2; // waste
            } else if (gameTmp.getSelectedSource() == KlondikeGame.Selected.TARGET_PACK) {
                selectedCard = 3 + index; // target pack
            } else if (gameTmp.getSelectedSource() == KlondikeGame.Selected.WORKING_PACK) {
                selectedCard = 7 + index; // working pack
            } else {
                selectedCard = 0; // nothing
            }
        } catch (Exception e) {
            selectedCard = -1;
        }
    }

}
