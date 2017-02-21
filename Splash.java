package org.bboed.students.ahmedmoustafa18;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;

public class Splash extends BasicGameState {

    private Image background;
    private Sound music;

    private SplashLogo logo;
    private FlyingItem peppermint;
    private FlyingItem lollipop;
    private Cloud cloud;
    private Cloud cloud2;
    private Cloud cloud3;
    private Cloud cloud4;
    private Button start;

    private boolean exitingScene = false;

    private static final float BUTTON_PAN_LOCATION = (float) (Engine.WINDOW_SIZE.getHeight() - 250);
    private static final String[] START_BUTTON_IMAGE_PATHS = { "resources/images/splash/start-button.png", "resources/images/splash/start-button-hover.png" };

    @Override
    public int getID() {
        return States.SPLASH_STATE;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        background = new Image("resources/images/splash/background.png");
        logo = new SplashLogo();
        peppermint = new FlyingItem("resources/images/splash/peppermint.png", null, gc, 800, 360, 0.5f);
        lollipop = new FlyingItem("resources/images/splash/lollipop.png", null, gc, 800, 360, 1);
        cloud = new Cloud("resources/images/splash/cloud.png", 10, 50f, 50, 1);
        cloud2 = new Cloud("resources/images/splash/cloud2.png", Engine.WINDOW_SIZE.getWidth() - 300, 100, 50, 1f);
        cloud3 = new Cloud("resources/images/splash/cloud3.png", Engine.WINDOW_SIZE.getWidth() - 600, Engine.WINDOW_SIZE.getHeight() - 500, 50, 1f);
        cloud4 = new Cloud("resources/images/splash/cloud.png", 300, 300, 50, 1f);

        start = new Button(START_BUTTON_IMAGE_PATHS, Button.CENTER, Engine.WINDOW_SIZE.getHeight() - 250, new Command() {
            @Override
            public void executeCommand() {
                music.stop();
                exitingScene = true;
                sbg.enterState(States.PRE_GAME_STATE, null, new HorizontalSplitTransition());
            }
        });

        music = new Sound("resources/sounds/splash/background.ogg");
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        background.draw(0, 0);
        peppermint.render();
        lollipop.render();
        cloud.render();
        cloud2.render();
        cloud3.render();
        cloud4.render();
        logo.render(gc, sbg, g);
        start.render(g);

        if(!music.playing() && !exitingScene) {
            music.loop();
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        logo.update(gc, sbg, delta);
        peppermint.update(gc, sbg, delta);
        lollipop.update(gc, sbg, delta);
        cloud.update(gc, sbg, delta);
        cloud2.update(gc, sbg, delta);
        cloud3.update(gc, sbg, delta);
        cloud4.update(gc, sbg, delta);
        start.update(gc, sbg, delta);
    }
}
