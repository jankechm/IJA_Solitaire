/*
 */

package test;
 
import game.*;
import model.Card;
import model.CardDeck;
import java.util.HashSet;
import java.util.Set;
import model.TargetPack;
import model.WorkingPack;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * IJA 2016/2017: Testovaci trida pro ukol c. 2.
 * @author koci
 */
public class SolitaireTest {
    
    protected KlondikeFactory factory;

    @Before
    public void setUp() {
         factory = new KlondikeFactory();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCard() {
        Card c1 = factory.createCard(Card.Color.CLUBS, 0);
        Card c2 = factory.createCard(Card.Color.DIAMONDS, 11);
        Card c3 = factory.createCard(Card.Color.SPADES, 5);
        Card c4 = factory.createCard(Card.Color.CLUBS, 6);
        Card c5 = factory.createCard(Card.Color.CLUBS, 6);
        
        Assert.assertNull("(Card.Color.CLUBS, 0) nelze", c1);
        Assert.assertFalse("Karta c2 nema byt otocena licem nahoru.", c2.isTurnedFaceUp());
        Assert.assertTrue("Otoceni karty c2.", c2.turnFaceUp());
        Assert.assertTrue("Karta c2 ma byt otocena licem nahoru.", c2.isTurnedFaceUp());
        Assert.assertFalse("Otoceni karty c2.", c2.turnFaceUp());
        Assert.assertTrue("Stejny typ barvy u karet", c3.similarColorTo(c4));
        Assert.assertFalse("Stejny typ barvy u karet", c3.similarColorTo(c2));
        Assert.assertEquals("Shoda objektu.", c4, c5);
        Assert.assertTrue("Rozdil hodnot karet.", c2.compareValue(c3) == 6);
    }

    @Test
    public void testCardDeck() {
        CardDeck deck = factory.createCardDeck();

        Assert.assertEquals("Pocet karet je 52", 52, deck.size());

        Set<Card> testCards = new HashSet<>();
        for (int i = 1; i <= 13; i++) { testCards.add(factory.createCard(Card.Color.CLUBS,i)); }
        for (int i = 1; i <= 13; i++) { testCards.add(factory.createCard(Card.Color.DIAMONDS,i)); }
        for (int i = 1; i <= 13; i++) { testCards.add(factory.createCard(Card.Color.HEARTS,i)); }
        for (int i = 1; i <= 13; i++) { testCards.add(factory.createCard(Card.Color.SPADES,i)); }

        for (int i = 0; i < 52; i++) {
            Card c = deck.pop();
            Assert.assertTrue("Spravna karta.", testCards.remove(c));
        }

        Assert.assertTrue("Testova sada musi byt prazdna", testCards.isEmpty());
    }

    
    @Test
    public void testTargetPack() {
        
        TargetPack pack = factory.createTargetPack();
        
        Card c1 = factory.createCard(Card.Color.DIAMONDS, 11);
        Card c2 = factory.createCard(Card.Color.DIAMONDS, 1);
        Card c3 = factory.createCard(Card.Color.CLUBS, 11);
        Card c4 = factory.createCard(Card.Color.CLUBS, 1);
        Card c5 = factory.createCard(Card.Color.CLUBS, 2);
        Card c6 = factory.createCard(Card.Color.DIAMONDS, 2);

        Assert.assertFalse("Nelze vlozit kartu", pack.put(c1));
        Assert.assertEquals("Pocet karet v baliku je 0.", 0, pack.size());
        //Assert.assertFalse("Nelze vlozit kartu", pack.put(c2));
        Assert.assertTrue("Lze vlozit kartu", pack.put(c2));
        //Assert.assertEquals("Pocet karet v baliku je 0.", 0, pack.size());
        Assert.assertEquals("Pocet karet v baliku je 1.", 1, pack.size());
        Assert.assertFalse("Nelze vlozit kartu", pack.put(c3));
        //Assert.assertTrue("Pocet karet v baliku je 0.", pack.isEmpty());
        Assert.assertEquals("Pocet karet v baliku je 1.", 1, pack.size());
        //Assert.assertTrue("Lze vlozit kartu", pack.put(c4));
        Assert.assertFalse("Nelze vlozit kartu", pack.put(c4));
        Assert.assertEquals("Pocet karet v baliku je 1.", 1, pack.size());
        Assert.assertFalse("Nelze vlozit kartu", pack.put(c3));
        Assert.assertEquals("Pocet karet v baliku je 1.", 1, pack.size());
        //Assert.assertTrue("Lze vlozit kartu", pack.put(c5));
        Assert.assertTrue("Lze vlozit kartu", pack.put(c6));
        Assert.assertEquals("Pocet karet v baliku je 2.", 2, pack.size());
        
        Card c55 = factory.createCard(Card.Color.CLUBS, 2);
        Card c44 = factory.createCard(Card.Color.CLUBS, 1);
        Card c20 = factory.createCard(Card.Color.DIAMONDS, 1);
        Card c21 = factory.createCard(Card.Color.DIAMONDS, 2);
        
        //Assert.assertEquals("Na vrcholu je karta c55", c55, pack.get());
        Assert.assertEquals("Na vrcholu je karta c55", c21, pack.get());
        Assert.assertEquals("Pocet karet v baliku je 2.", 2, pack.size());
        //Assert.assertEquals("Vyber karty z vrcholu", c55, pack.pop());
        Assert.assertEquals("Vyber karty z vrcholu", c21, pack.pop());
        Assert.assertEquals("Pocet karet v baliku je 1.", 1, pack.size());
        //Assert.assertEquals("Na vrcholu je karta c44", c44, pack.get());
        Assert.assertEquals("Na vrcholu je karta c44", c20, pack.get());
        Assert.assertEquals("Pocet karet v baliku je 1.", 1, pack.size());
    }
    
    @Test
    public void testWorkingPack() {
        CardDeck deck = factory.createCardDeck();
        WorkingPack pack = factory.createWorkingPack(deck, 7);
           
        Card c1 = factory.createCard(Card.Color.DIAMONDS, 11);
        Card c2 = factory.createCard(Card.Color.DIAMONDS, 13);
        Card c3 = factory.createCard(Card.Color.HEARTS, 12);
        Card c4 = factory.createCard(Card.Color.CLUBS, 12);
        Card c5 = factory.createCard(Card.Color.SPADES, 11);
        Card c6 = factory.createCard(Card.Color.HEARTS, 11);

        /*Assert.assertEquals("Pracovni balicek je prazdny.", 0, pack.size());
        Assert.assertFalse("Na prazdny pracovni balicek lze vlozit pouze krale.", pack.put(c1));
        Assert.assertTrue("Na prazdny pracovni balicek vkladame krale.", pack.put(c2));
        Assert.assertFalse("Na cerveneho krale lze vlozit pouze cernou damu.", pack.put(c3));
        Assert.assertEquals("Pracovni balicek obsahuje 1 kartu.", 1, pack.size());
        Assert.assertTrue("Na cerveneho krale vkladame cernou damu.", pack.put(c4));
        Assert.assertEquals("Pracovni balicek obsahuje 2 karty.", 2, pack.size());

        Assert.assertFalse("Na cernou damu lze vlozit pouze cerveneho kluka.", pack.put(c5));
        Assert.assertEquals("Pracovni balicek obsahuje 2 karty.", 2, pack.size());
        Assert.assertTrue("Na cernou damu vkladame cerveneho kluka.", pack.put(c6));
        Assert.assertEquals("Pracovni balicek obsahuje 3 karty.", 3, pack.size());
        
        Stack<Card> s = pack.pop(factory.createCard(Card.Color.CLUBS, 12));
        Assert.assertEquals("Pracovni balicek obsahuje 1 kartu.", 1, pack.size());
        Assert.assertEquals("Pocet odebranych karet je 2.", 2, s.size());
        
        Assert.assertEquals("Na vrcholu je H(11).", factory.createCard(Card.Color.HEARTS, 11), s.pop());
        Assert.assertEquals("Na vrcholu je C(12).", factory.createCard(Card.Color.CLUBS, 12), s.pop());
        Assert.assertEquals("Odebrany balicek je prazdny.", 0, s.size());  */ 
    }
    
    @Test
    public void testWorkingPack2() {
        CardDeck deck = factory.createCardDeck();
        WorkingPack pack1 = factory.createWorkingPack(deck, 7);
        WorkingPack pack2 = factory.createWorkingPack(deck, 20);
           
        pack1.put(factory.createCard(Card.Color.DIAMONDS, 13));
        pack1.put(factory.createCard(Card.Color.CLUBS, 12));
        pack1.put(factory.createCard(Card.Color.HEARTS, 11));
     
        /*Stack<Card> s = pack1.pop(factory.createCard(Card.Color.CLUBS, 12));
        
        Assert.assertFalse("Nelze vlozit odebranou mnozinu (pracovni balicek je prazdny)", pack2.put(s));
        
        Assert.assertTrue("Vkladame cerveneho krale na prazdny balicek.", 
                pack2.put(factory.createCard(Card.Color.HEARTS, 13)));

        Assert.assertTrue("Vkladame odebranou mnozinu.", pack2.put(s));
        
        Assert.assertEquals("Pracovni balicek c. 2 obsahuje 3 karty.", 3, pack2.size());*/
    }

    @Test
    public void testGame() {
        KlondikeGame game = new KlondikeGame();
        Card c1 = factory.createCard(Card.Color.DIAMONDS, 11);
        int cardNum = 0;
        String fileName;
        
        game.newGame();

        for (int i = 0; i < 7; i++) {
          Assert.assertEquals("Pocet karet v pracovním balíčku " + String.valueOf(i) + " je " + String.valueOf(i+1), i+1, game.getWorkingPack(i).size());
          cardNum += game.getWorkingPack(i).size();
        }
        Assert.assertEquals("Počet karet je ve všech prac. bal. dokopy 28", 28, cardNum);
        for (int i = 0; i < 4; i++) {
          Assert.assertTrue("Cílové balíčky jsou prázdné", game.getTargetPack(i).isEmpty());
        }
        Assert.assertNotNull("Stock není null", game.getStock());
        Assert.assertEquals("Pocet karet v stock je 24", 24, game.getStock().size());
        Assert.assertEquals("Pocet karet ve waste je 0", 0, game.getWaste().size());
        Assert.assertTrue("Zdrojový balíček deck je prázdný", game.getDeck().isEmpty());
        Assert.assertEquals("Pocet rozehratých her je 1", 1, KlondikeGame.getGameCnt());
        
        game.quitGame();
        
        Assert.assertEquals("Pocet rozehratých her je 0", 0, KlondikeGame.getGameCnt());
        
        game.newGame();
        fileName = game.saveGame();
        //System.out.println("Filename: " + fileName);
        
        Assert.assertEquals("Pocet karet v prac. balíku 0 je 1", 1, game.getWorkingPack(0).size());
        Assert.assertEquals("Pocet karet v stock je 24", 24, game.getStock().size());
        game.getWorkingPack(0).pop();
        game.getStock().pop();
        Assert.assertEquals("Pocet karet v prac. balíku 1 je 0", 0, game.getWorkingPack(0).size());
        Assert.assertEquals("Pocet karet v stock je 23", 23, game.getStock().size());
        
        game.loadGame(fileName);
        
        Assert.assertEquals("Pocet karet v prac. balíku 1 je 1", 1, game.getWorkingPack(0).size());
        Assert.assertEquals("Pocet karet v stock je 24", 24, game.getStock().size());
        
        for (Card card : game.getStock().getCards()) {
          System.out.println(card);
        }
    }
}
