package org.bboed.students.ahmedmoustafa18;

import org.newdawn.slick.*;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;
import java.awt.*;

public class Engine extends StateBasedGame {

    public static final Dimension WINDOW_SIZE = new Dimension(900, 600);

    private static final int CURSOR_SCALE = 4;
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
            AppGameContainer appgc = new AppGameContainer(this, (int) WINDOW_SIZE.getWidth(), (int) WINDOW_SIZE.getHeight(), false);
            appgc.setAlwaysRender(true);

            String[] icons = new String[3];
            icons[0] = "resources/images/icon16.png";
            icons[1] = "resources/images/icon32.png";
            icons[2] = "resources/images/icon64.png";

            appgc.setIcons(icons);

            appgc.start();
        } catch(SlickException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        Image cursor = new Image("resources/images/cursor/point.png");
        cursor.setFilter(Image.FILTER_NEAREST);
        cursor = cursor.getScaledCopy(CURSOR_SCALE);

        gc.setMouseCursor(cursor, 0, 0);

        this.addState(new Splash());
        this.addState(new PreGameScreen());
        this.addState(new Game());
        this.addState(new GameOverState());
    }
}
