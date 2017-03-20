package org.bboed.students.ahmedmoustafa18;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;

import javax.swing.plaf.nimbus.State;

public class Game extends BasicGameState {

    public static Player player1;
    public static Player player2;
    public static Player currentPlayer;

    private static int candyInLossPile;
    private int bagsRemaining;

    public static final int BAG_COUNT = 20;
    public static final int BAG_MARGIN_X = 235;
    public static final int BAG_MARGIN_Y = 185;
    public static final int BETWEEN_BAG_MARGIN_X = 20;
    public static final int BETWEEN_BAG_MARGIN_Y = 10;

    private static final int OVERLAY_MARGIN = 20;

    private static Vector2f player1NameLocation;
    private static Vector2f player1CandyLocation;

    private static Vector2f player2NameLocation;
    private static Vector2f player2CandyLocation;

    private Image background;
    private Image overlay;
    private Image playerLabels;

    private boolean namesRendered = false;

    private Bag[] bags = new Bag[BAG_COUNT];

    private java.awt.Font UIFont1;
    private java.awt.Font UIFont2;
    private java.awt.Font UIFont3;

    public static org.newdawn.slick.UnicodeFont uniFont;
    public static org.newdawn.slick.UnicodeFont uniFont2;
    public static UnicodeFont uniFont3;

    @Override
    public int getID() {
        return States.GAME_STATE;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        background = new Image("resources/images/splash/background.png");
        overlay = new Image("resources/images/game/overlay.png");
        overlay.setAlpha(0.4f);
        playerLabels = new Image("resources/images/game/player-labels.png");

        player1CandyLocation = new Vector2f(330, 50);
        player2CandyLocation = new Vector2f((int) (Engine.WINDOW_SIZE.getWidth() - 330), 50);

        try{
            UIFont1 = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
                    org.newdawn.slick.util.ResourceLoader.getResourceAsStream("resources/fonts/Doctor Soos Light 1.1.ttf"));
            UIFont1 = UIFont1.deriveFont(java.awt.Font.PLAIN, 32); //You can change "PLAIN" to "BOLD" or "ITALIC"... and 16.f is the size of your font

            uniFont = new org.newdawn.slick.UnicodeFont(UIFont1);
            uniFont.addAsciiGlyphs();
            uniFont.getEffects().add(new ColorEffect(java.awt.Color.white)); //You can change your color here, but you can also change it in the render{ ... }
            uniFont.addAsciiGlyphs();
            uniFont.loadGlyphs();

            UIFont2 = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
                    org.newdawn.slick.util.ResourceLoader.getResourceAsStream("resources/fonts/Doctor Soos Light 1.1.ttf"));
            UIFont2 = UIFont1.deriveFont(java.awt.Font.PLAIN, 50); //You can change "PLAIN" to "BOLD" or "ITALIC"... and 16.f is the size of your font

            uniFont2 = new org.newdawn.slick.UnicodeFont(UIFont2);
            uniFont2.addAsciiGlyphs();
            uniFont2.getEffects().add(new ColorEffect(java.awt.Color.white)); //You can change your color here, but you can also change it in the render{ ... }
            uniFont2.addAsciiGlyphs();
            uniFont2.loadGlyphs();

            UIFont3 = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
                    org.newdawn.slick.util.ResourceLoader.getResourceAsStream("resources/fonts/Doctor Soos Light 1.1.ttf"));
            UIFont3 = UIFont1.deriveFont(java.awt.Font.PLAIN, 24); //You can change "PLAIN" to "BOLD" or "ITALIC"... and 16.f is the size of your font

            uniFont3 = new org.newdawn.slick.UnicodeFont(UIFont3);
            uniFont3.addAsciiGlyphs();
            uniFont3.getEffects().add(new ColorEffect(java.awt.Color.black)); //You can change your color here, but you can also change it in the render{ ... }
            uniFont3.addAsciiGlyphs();
            uniFont3.loadGlyphs();
        }catch(Exception e){
            e.printStackTrace();
        }

        for(int i = 1; i <= bags.length; i++) {
            bags[i - 1] = new Bag(i);
        }

        this.candyInLossPile = 0;
        this.bagsRemaining = 20;
        GameOverState.gameBoard = this;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        background.draw(0, 0);
        overlay.draw((float) (Engine.WINDOW_SIZE.getWidth() / 2) - (overlay.getWidth() / 2), (float) (BAG_MARGIN_Y + Engine.WINDOW_SIZE.getHeight()) / 2 - (overlay.getHeight() / 2) - OVERLAY_MARGIN);
        playerLabels.draw(0, 0);

        if(!namesRendered) {
            player1NameLocation = new Vector2f(10, 55);
            player2NameLocation = new Vector2f((int) (Engine.WINDOW_SIZE.getWidth() - 10 - uniFont.getWidth(player2.getName())), 55);

            currentPlayer = player1;

            namesRendered = true;
        }

        g.setFont(uniFont);
        g.setColor(Color.white);
        g.drawString(player1.getName(), player1NameLocation.getX(), player1NameLocation.getY());
        g.drawString(player2.getName(), player2NameLocation.getX(), player2NameLocation.getY());

        g.setFont(uniFont2);
        g.drawString(player1.candyDisplayString(), player1CandyLocation.getX() - uniFont2.getWidth(player1.candyDisplayString()) / 2, player1CandyLocation.getY());
        g.drawString(player2.candyDisplayString(), player2CandyLocation.getX() - uniFont2.getWidth(player2.candyDisplayString()) / 2, player2CandyLocation.getY());

        for(Bag bag : bags) {
            if(!bag.getBagSelected()) {
                bag.render(gc, sbg, g);
            }
        }

        for(Bag bag : bags) {
            if(bag.getBagSelected()) {
                bag.getCard().render(gc, sbg, g);
            }
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        for(Bag bag : bags) {
            if(!bag.getBagSelected()) {
                bag.update(gc, sbg, currentPlayer, this, delta);
            } else {
                bag.getCard().update(gc, sbg, this, delta);
            }
        }

        if(bagsRemaining == 0) {
            if(player1.getCandyCount() == player2.getCandyCount()) {
                GameOverState.winner = GameOverState.TIE;
            } else if(player1.getCandyCount() > player2.getCandyCount()) {
                GameOverState.winner = 1;
            } else {
                GameOverState.winner = 2;
            }

            Splash.music.stop();

            sbg.enterState(States.GAME_OVER_STATE, null, new HorizontalSplitTransition());
            GameOverState.gameBoard.resetBoard(gc, sbg);
        }

    }

    public void switchPlayer() {
        if(currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

    public void removeBag() {
        this.bagsRemaining--;
    }

    public static int getCandyInLossPile() {
        return candyInLossPile;
    }

    public void resetBoard(GameContainer gc, StateBasedGame sbg) throws SlickException {
        init(gc, sbg);
    }

    public void addToLossPile(int candyToAdd) {
        this.candyInLossPile += candyToAdd;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}