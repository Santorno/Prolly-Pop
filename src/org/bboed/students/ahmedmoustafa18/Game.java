package org.bboed.students.ahmedmoustafa18;

import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;
import org.newdawn.slick.util.ResourceLoader;

public class Game
  extends BasicGameState
{
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
  
  private Bag[] bags = new Bag[20];
  private Font UIFont1;
  private Font UIFont2;
  private Font UIFont3;
  public static UnicodeFont uniFont;
  public static UnicodeFont uniFont2;
  public static UnicodeFont uniFont3;
  
  public Game() {}
  
  public int getID()
  {
    return 2;
  }
  
  public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
  {
    background = new Image("resources/images/splash/background.png");
    overlay = new Image("resources/images/game/overlay.png");
    overlay.setAlpha(0.4F);
    playerLabels = new Image("resources/images/game/player-labels.png");
    
    player1CandyLocation = new Vector2f(330.0F, 50.0F);
    player2CandyLocation = new Vector2f((int)(Engine.WINDOW_SIZE.getWidth() - 330.0D), 50.0F);
    try
    {
      UIFont1 = Font.createFont(0, 
        ResourceLoader.getResourceAsStream("resources/fonts/Doctor Soos Light 1.1.ttf"));
      UIFont1 = UIFont1.deriveFont(0, 32.0F);
      
      uniFont = new UnicodeFont(UIFont1);
      uniFont.addAsciiGlyphs();
      uniFont.getEffects().add(new ColorEffect(java.awt.Color.white));
      uniFont.addAsciiGlyphs();
      uniFont.loadGlyphs();
      
      UIFont2 = Font.createFont(0, 
        ResourceLoader.getResourceAsStream("resources/fonts/Doctor Soos Light 1.1.ttf"));
      UIFont2 = UIFont1.deriveFont(0, 50.0F);
      
      uniFont2 = new UnicodeFont(UIFont2);
      uniFont2.addAsciiGlyphs();
      uniFont2.getEffects().add(new ColorEffect(java.awt.Color.white));
      uniFont2.addAsciiGlyphs();
      uniFont2.loadGlyphs();
      
      UIFont3 = Font.createFont(0, 
        ResourceLoader.getResourceAsStream("resources/fonts/Doctor Soos Light 1.1.ttf"));
      UIFont3 = UIFont1.deriveFont(0, 24.0F);
      
      uniFont3 = new UnicodeFont(UIFont3);
      uniFont3.addAsciiGlyphs();
      uniFont3.getEffects().add(new ColorEffect(java.awt.Color.black));
      uniFont3.addAsciiGlyphs();
      uniFont3.loadGlyphs();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    for (int i = 1; i <= bags.length; i++) {
      bags[(i - 1)] = new Bag(i);
    }
    
    candyInLossPile = 0;
    bagsRemaining = 20;
    GameOverState.gameBoard = this;
  }
  
  public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
  {
    background.draw(0.0F, 0.0F);
    overlay.draw((float)(Engine.WINDOW_SIZE.getWidth() / 2.0D) - overlay.getWidth() / 2, (float)(185.0D + Engine.WINDOW_SIZE.getHeight()) / 2.0F - overlay.getHeight() / 2 - 20.0F);
    playerLabels.draw(0.0F, 0.0F);
    
    if (!namesRendered) {
      player1NameLocation = new Vector2f(10.0F, 55.0F);
      player2NameLocation = new Vector2f((int)(Engine.WINDOW_SIZE.getWidth() - 10.0D - uniFont.getWidth(player2.getName())), 55.0F);
      
      currentPlayer = player1;
      
      namesRendered = true;
    }
    
    g.setFont(uniFont);
    g.setColor(org.newdawn.slick.Color.white);
    g.drawString(player1.getName(), player1NameLocation.getX(), player1NameLocation.getY());
    g.drawString(player2.getName(), player2NameLocation.getX(), player2NameLocation.getY());
    
    g.setFont(uniFont2);
    g.drawString(player1.candyDisplayString(), player1CandyLocation.getX() - uniFont2.getWidth(player1.candyDisplayString()) / 2, player1CandyLocation.getY());
    g.drawString(player2.candyDisplayString(), player2CandyLocation.getX() - uniFont2.getWidth(player2.candyDisplayString()) / 2, player2CandyLocation.getY());
    
    for (Bag bag : bags) {
      if (!bag.getBagSelected()) {
        bag.render(gc, sbg, g);
      }
    }
    
    for (Bag bag : bags) {
      if (bag.getBagSelected()) {
        bag.getCard().render(gc, sbg, g);
      }
    }
  }
  
  public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
  {
    for (Bag bag : bags) {
      if (!bag.getBagSelected()) {
        bag.update(gc, sbg, currentPlayer, this, delta);
      } else {
        bag.getCard().update(gc, sbg, this, delta);
      }
    }
    
    if (bagsRemaining == 0) {
      if (player1.getCandyCount() == player2.getCandyCount()) {
        GameOverState.winner = -1;
      } else if (player1.getCandyCount() > player2.getCandyCount()) {
        GameOverState.winner = 1;
      } else {
        GameOverState.winner = 2;
      }
      
      Splash.music.stop();
      
      sbg.enterState(3, null, new HorizontalSplitTransition());
      GameOverState.gameBoard.resetBoard(gc, sbg);
    }
  }
  
  public void switchPlayer()
  {
    if (currentPlayer == player1) {
      currentPlayer = player2;
    } else {
      currentPlayer = player1;
    }
  }
  
  public void removeBag() {
    bagsRemaining -= 1;
  }
  
  public static int getCandyInLossPile() {
    return candyInLossPile;
  }
  
  public void resetBoard(GameContainer gc, StateBasedGame sbg) throws SlickException {
    init(gc, sbg);
  }
  
  public void addToLossPile(int candyToAdd) {
    candyInLossPile += candyToAdd;
  }
  
  public Player getCurrentPlayer() {
    return currentPlayer;
  }
}