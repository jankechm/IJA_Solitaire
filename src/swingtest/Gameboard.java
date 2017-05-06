/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package swingtest;

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
    public int gameNumber = 0;
    public int gameNum1 = 0;
    public int gameNum2 = 0;
    public int gameNum3 = 0;
    public int gameNum4 = 0;
    public int gameActive = 0;
    public boolean gameMotion = true;
    private String namerino = "";


    Color blueBack = new Color(10, 75, 130);
    
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
            drawGame(g, game1, 15, 10);
            drawGame(g, game2, 719, 10);
            drawGame(g, game3, 15, 420);
            drawGame(g, game4, 719, 420);
        }
    }

    private void drawGame (Graphics g, KlondikeGame gameTmp, int xgap, int ygap) {
        String dir = "src/swingtest/cards/";
        String back = "src/swingtest/cards/back2.jpg";

            workgap = 30;
           /*xgap = 65;
            ygap = 10;*/
            // draw deck
            xd = xgap;
            yd = ygap;
            namerino = back;
            try {
                gameTmp.getDeck();
            } catch (Exception e){
                namerino = "";
            }
            drawCard(g, namerino, xd, yd);

            // draw stack
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

            // target packs
            xd = xgap + 3 * workgap + 3 * CWIDTH;
            yd = ygap;

            for (i = 1; i <= 4; i++) {
                xd = xgap + (i + 2) * workgap + (i + 2) * CWIDTH;
                yd = ygap;
                try {
                    namerino = gameTmp.getTargetPack(i).get().toString();
                    namerino = dir + namerino + ".gif";
                } catch (Exception e) {
                    namerino = "";
                }
                drawCard(g, namerino, xd, yd);
            }
            /*
            xgap = 65;*/
            ygap = ygap +10;
            // working packs
            xd = xgap;
            yd = ygap + CHEIGHT;
            for (i = 1; i < 8; i++) {
                xd = xgap + (i - 1) * workgap + (i - 1) * CWIDTH;
                try {
                    k = gameTmp.getWorkingPack(i - 1).size();
                    //                System.out.println("size " + k);
                    /*
                    for (j = 1; j <= 13; j++) {
                        namerino = back;
                        yd = ygap + CHEIGHT + 17 * (j - 1);
                        drawCard(g, namerino, xd, yd);
                    }*/
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
                    }
                } catch (Exception ee) {
                    namerino = "";
                    drawCard(g, namerino, xd, yd);
                }
            }

    }

    private void drawNoGame (Graphics g, int xgap, int ygap) {
        namerino = "";
        workgap = 30;
        /*xgap = 65;
        ygap = 10;*/

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

        /*xgap = 65;*/
        ygap = ygap + 10;
        // working packs
        xd = xgap;
        yd = ygap + CHEIGHT;

        for (i = 1; i < 8; i++) {
            xd = xgap + (i - 1) * workgap + (i - 1) * CWIDTH;
            drawCard(g, namerino, xd, yd);
        }
    }

    public void drawGameBorder (Graphics g) {
        if (gameActive == 0) {
            g.drawRect(10,5,1407,815);
            g.drawRect(11,6,1405,813);
            /*
            g.drawRect(715,5,701,405);
            g.drawRect(716,6,699,403);

            g.drawRect(10,415,701,405);
            g.drawRect(11,416,699,403);

            g.drawRect(715,415,701,405);
            g.drawRect(716,416,699,403);
            */
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
        //repaint();
    }

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

    public void actionProvide (int index, int action) {
        if (index == 1 && gameNum1 == 1) {
            if (action == 0) {
                game1.selectedNothing();
            }else if (action == 1) {
                game1.selectedStock();
            } else if (action == 2) {
                game1.selectedWaste();
            } else if (action >= 3 && action <= 6) {
                game1.selectedTargetPack(index-3);
            } else if (action >= 7) {
                game1.selectedWorkingPack(index-7);
            }
            System.out.println(index);
            System.out.println(action);
        } else if (index == 2 && gameNum2 == 1) {
            if (action == 0) {
                game2.selectedNothing();
            }else if (action == 1) {
                game2.selectedStock();
            } else if (action == 2) {
                game2.selectedWaste();
            } else if (action >= 3 && action <= 6) {
                game2.selectedTargetPack(index-3);
            } else if (action >= 7) {
                game2.selectedWorkingPack(index-7);
            }
            System.out.println(index);
            System.out.println(action);
        } else if (index == 3 && gameNum3 == 1) {
            if (action == 0) {
                game3.selectedNothing();
            }else if (action == 1) {
                game3.selectedStock();
            } else if (action == 2) {
                game3.selectedWaste();
            } else if (action >= 3 && action <= 6) {
                game3.selectedTargetPack(index-3);
            } else if (action >= 7) {
                game3.selectedWorkingPack(index-7);
            }
            System.out.println(index);
            System.out.println(action);
        } else if (index == 4 && gameNum4 == 1) {
            if (action == 0) {
                game4.selectedNothing();
            }else if (action == 1) {
                game4.selectedStock();
            } else if (action == 2) {
                game4.selectedWaste();
            } else if (action >= 3 && action <= 6) {
                game4.selectedTargetPack(index-3);
            } else if (action >= 7) {
                game4.selectedWorkingPack(index-7);
            }
            System.out.println(index);
            System.out.println(action);
        }
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
                System.out.println("Could not find file '" + fileName + "' with picture");
            }
			Image image = new ImageIcon(fileName).getImage();
			g.drawImage(image, x, y, CWIDTH, CHEIGHT, null);
		}
	}

    private void drawBorder(Graphics g, int x, int y)
    {
        g.setColor(Color.YELLOW);
        g.drawRect(x, y, CWIDTH, CHEIGHT);
        g.drawRect(x + 1, y + 1, CWIDTH - 2, CHEIGHT - 2);
        g.drawRect(x + 2, y + 2, CWIDTH - 4, CHEIGHT - 4);
    }
    
}
