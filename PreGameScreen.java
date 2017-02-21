package org.bboed.students.ahmedmoustafa18;

import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;
import java.awt.Font;


public class PreGameScreen extends BasicGameState {

    private Image background;
    private TextField test;

    @Override
    public int getID() {
        return States.PRE_GAME_STATE;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        background = new Image("resources/images/splash/background.png");
        test = new TextField(gc, new TrueTypeFont(new Font("Arial", Font.PLAIN, 24), true), 25, 25, 200, 50, null);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        background.draw(0, 0);
        test.render(gc, g);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

    }
}
