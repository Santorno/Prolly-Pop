package org.bboed.students.ahmedmoustafa18;

import org.newdawn.slick.*;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;

import static org.lwjgl.opengl.Display.getWidth;

public class Engine extends StateBasedGame {

    private AppGameContainer appgc;

    public static final Dimension WINDOW_SIZE = new Dimension(900, 600);
    private static final String WINDOW_TITLE = "Prolly Pop";

    public Engine() {
        super(WINDOW_TITLE);
    }

    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.start();
    }

    public void start() {
        try {
            appgc = new AppGameContainer(this, (int) WINDOW_SIZE.getWidth(), (int) WINDOW_SIZE.getHeight(), false);
            appgc.setAlwaysRender(true);
            appgc.start();
        } catch(SlickException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        Image cursor = new Image("resources/images/cursor/point.png");
        cursor.setFilter(Image.FILTER_NEAREST);
        cursor = cursor.getScaledCopy(4);

        gc.setMouseCursor(cursor, 0, 0);
        this.addState(new Splash());
    }
}
