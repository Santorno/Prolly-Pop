package org.bboed.students.ahmedmoustafa18;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class SplashLogo {

    private Image logo;
    private Vector2f position;

    private boolean logoPanned = false;
    private static final float LOGO_PAN_SPEED = 350f;

    public SplashLogo() throws SlickException {
        logo = new Image("resources/images/splash/logo.png");
        position = new Vector2f((float) ((Engine.WINDOW_SIZE.getWidth() / 2) - (logo.getWidth() / 2)), -logo.getHeight());
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        logo.draw(position.x, position.y);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        if(!logoPanned) {
            position.y += LOGO_PAN_SPEED * delta / 1000;

            if(position.y >= 0) {
                position.y = 0;
                logoPanned = true;
            }
        }
    }

    public boolean logoPanned() {
        return this.logoPanned;
    }
}
