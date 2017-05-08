/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package swingtest;

import game.Hint;
import game.KlondikeGame;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Jan Morávek (xmorav33), Marek Jankech (xjanke01)
 */
public class Gameboard extends JComponent{
    
    public static final int CWIDTH = 73;
	public static final int CHEIGHT = 97;
    private int xd = 0;
    private int yd = 0;
    public int workgap = 30;
    private int i;
    private int j;
    private int k;
    private KlondikeGame game1;
    private KlondikeGame game2;
    private KlondikeGame game3;
    private KlondikeGame game4;
    public boolean showHint = false;
    public int saveHintSrc = -1;
    public int saveHintDest = -1;
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

    /** Metoda volaná pomocí repaint(). Zajišťuje volání ostatních metod pro vykreslení hry*/
    public void paint(Graphics g) { // drawRect(x, y, width, height);
        g.setColor(blueBack);
        gameNumber = gameNum1 + gameNum2 + gameNum3 + gameNum4;
        //System.out.println(gameNumber);
        if (gameNumber == 0){
            g.setColor(new Color(240,240,240));
            g.fillRect(0, 0, getWidth(), getHeight());
            drawNoGame(g, 15, 10);
        } else if (gameNumber == 1) {
            if (gameNum2==1) {
                game1 = game2;
                gameNum1 = 1;
                gameNum2 = 0;
            } else if (gameNum3==1) {
                game1 = game3;
                gameNum1 = 1;
                gameNum3 = 0;
            } else if (gameNum4==1) {
                game1 = game4;
                gameNum1 = 1;
                gameNum4 = 0;
            }
            drawGame(g, game1, 15, 10);
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
     * Metody pro vykreslení celé hry. Obsahuje metody pro vykreslení hrací plochy, vykreslení karet, vykreslení
     * ohraničení a nápovědy.
     ******************************************************************************************************************/
    /** Metoda vykreslující celou hrací plochu.*/
    private void drawGame (Graphics g, KlondikeGame gameTmp, int xgap, int ygap) {
        String dir = "lib/cards/";
        String back = "lib/cards/back2.jpg";
        resolveBorder(gameTmp);
        resolveHint(gameTmp);

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
            if (showHint && saveHintSrc == 1) {
                drawHintBorder(g, xd, yd);
            }

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
            if (showHint && saveHintSrc == 2) {
                drawHintBorder(g, xd, yd);
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
                if (showHint && (saveHintSrc == 2 || saveHintSrc >6)) {
                    if (saveHintDest == i + 2) {
                        drawHintBorder(g, xd, yd);
                    }
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
                        if (showHint && saveHintSrc == 2) {
                            if (saveHintDest == i + 6 && j==k) {
                                drawHintBorder(g, xd, yd);
                            }
                        } else if (showHint && saveHintSrc > 6) {
                            if (saveHintSrc == i + 6 && j==k) {
                                drawHintBorder(g, xd, yd);
                            }
                            if (saveHintDest == i + 6 && j==k) {
                                drawHintBorder(g, xd, yd);
                            }
                        }
                    }
                } catch (Exception ee) {
                    namerino = "";
                    drawCard(g, namerino, xd, yd);
                }
            }
    }

    /** Metoda vykresluje žádnou hru.*/
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

    /** Metoda vykresluje jednotlivé karty na zadané souřadnice. */
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

    /** Metoda pro vykreslení ohraničení her.*/
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

    /** Metody vykreslující ohraničení karty*/
    private void drawCardBorder (Graphics g, int x, int y) {
        g.setColor(blueBack);
        g.drawRect(x, y, CWIDTH, CHEIGHT);

        g.drawRect(x + 1, y + 1, CWIDTH - 2, CHEIGHT - 2);
        g.drawRect(x + 2, y + 2, CWIDTH - 4, CHEIGHT - 4);
    }
    private void drawHintBorder (Graphics g, int x, int y) {
        g.setColor(Color.yellow);
        //g.setColor(new Color (240,240,240));
        g.drawRect(x, y, CWIDTH, CHEIGHT);

        g.drawRect(x + 1, y + 1, CWIDTH - 2, CHEIGHT - 2);
        g.drawRect(x + 2, y + 2, CWIDTH - 4, CHEIGHT - 4);
    }

    /*******************************************************************************************************************
     * Metody pro přístup k instancím třídy KlondikeGame. Zároveň část problému při operacích s těmito instancemi.
     ******************************************************************************************************************/
    /** Metoda, která zajistí vytvoření nové hry.*/
    public void newProvide(int index) {
        switch (index) {
            case 1:
                if (gameNum1==0) {
                    game1 = new KlondikeGame();
                }
                game1.newGame();
                gameNum1 = 1;
                break;
            case 2:
                if (gameNum2==0) {
                    game2 = new KlondikeGame();
                }
                game2.newGame();
                gameNum2 = 1;
                break;
            case 3:
                if (gameNum3==0) {
                    game3 = new KlondikeGame();
                }
                game3.newGame();
                gameNum3 = 1;
                break;
            case 4:
                if (gameNum4==0) {
                    game4 = new KlondikeGame();
                }
                game4.newGame();
                gameNum4 = 1;
                break;
        }
    }

    /** Metoda zajišťující nahrání hry.*/
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

    /** Metoda zajišťující novou hru.*/
    public String saveProvide(int index) {
        String fileName = "";
        switch (index) {
            case 1:
                fileName = game1.saveGame();
                break;
            case 2:
                fileName = game2.saveGame();
                break;
            case 3:
                fileName = game3.saveGame();
                break;
            case 4:
                fileName = game4.saveGame();
                break;
        }
        return fileName;
    }

    /** Metoda zajišťující ukončení hry*/
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

    /** Metoda využívaná pro získání velikosti working packu ke změně plochy pro kliknutí. */
    public int workSizeProvide(int index, int i) {
        if (index == 1 && gameNum1 == 1) {
            return game1.getWorkingPack(i).size();
        } else if (index == 2 && gameNum2 == 1) {
            return game2.getWorkingPack(i).size();
        } else if (index == 3 && gameNum3 == 1) {
            return game3.getWorkingPack(i).size();
        } else if (index == 4 && gameNum4 == 1) {
            return game4.getWorkingPack(i).size();
        } else {
            return -1;
        }
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

    /** Metoda zajišťující provedení operací nad instancemi her.*/
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
                if (game1.checkIfVictory()) {
                    winGame(1);
                    game1.quitGame();
                    gameNum1 = 0;
                }
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
                if (game2.checkIfVictory()) {
                    winGame(2);
                    game2.quitGame();
                    gameNum2 = 0;
                }
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
                if (game3.checkIfVictory()) {
                    winGame(3);
                    game3.quitGame();
                    gameNum3 = 0;
                }
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
                if (game4.checkIfVictory()) {
                    winGame(4);
                    game4.quitGame();
                    gameNum4 = 0;
                }
            } else if (action >= 7) {
                game4.selectedWorkingPack(action-7);
            }
        }
    }

    /*******************************************************************************************************************
     * Metody pro vyhodnocení vybraných karet ve hře a nápovědy.
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

    /** Metoda, která zprostředkuje akci undo. */
    public void resolveHint(KlondikeGame gameTmp) {  // TODO smazat nápovědy
        try {
            Hint hint1 = gameTmp.hint();
            if (hint1 == null) {
                //System.out.println("game1: no possible hint");
            } else if (hint1.getSrc() == KlondikeGame.Selected.STOCK) {
                //System.out.println("STOCK SELECTED");
                saveHintSrc = 1;
            } else if (hint1.getSrc() == KlondikeGame.Selected.WASTE || hint1.getSrc() == KlondikeGame.Selected.WORKING_PACK) {
                if (hint1.getSrc() == KlondikeGame.Selected.WASTE) {
                    saveHintSrc = 2;
                    //System.out.println("WASTE " + hint1.getDest().toString() + "" + hint1.getDestIndex());
                } else if (hint1.getSrc() == KlondikeGame.Selected.WORKING_PACK) {
                    saveHintSrc = hint1.getSrcIndex() + 7;
                    //System.out.println("WORKING PACK" + hint1.getSrcIndex() + " " + hint1.getDest().toString() + hint1.getDestIndex());
                }
                if (hint1.getDest() == KlondikeGame.Selected.TARGET_PACK) {
                    saveHintDest = hint1.getDestIndex() + 3;
                } else if (hint1.getDest() == KlondikeGame.Selected.WORKING_PACK) {
                    saveHintDest = hint1.getDestIndex() + 7;
                }
            }
        } catch (Exception e) {
            saveHintSrc = -1;
            saveHintDest = -1;
        }
    }

    public static void winGame(int index)
    {
        String titleBar = "You win the game " + index + " !";
        String infoMessage = "Congratulations! \n You are the winner!";

        JLabel win = new JLabel(infoMessage);
        win.setFont(new java.awt.Font("Verdana", 1, 20));
        win.setForeground(new Color(10, 75, 130));

        JOptionPane.showMessageDialog(null, win,  titleBar, JOptionPane.PLAIN_MESSAGE);

    }


}
