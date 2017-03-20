package org.bboed.students.ahmedmoustafa18;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;

public class Splash extends BasicGameState {

    private Image background;
    public static Sound music;
    private Sound buttonClick;

    private Cloud cloud;
    private Cloud cloud2;
    private Cloud cloud3;
    private Cloud cloud4;
    private Button start;
    private SplashLogo logo;
    private FlyingItem peppermint;
    private FlyingItem lollipop;

    private boolean exitingScene = false;

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

        Image startButtonImage = new Image(START_BUTTON_IMAGE_PATHS[0]);
        Shape startButtonHitbox = new Circle(((float) (Engine.WINDOW_SIZE.getWidth() / 2) - (startButtonImage.getWidth() / 2)) + Button.GUI_BUTTON_CENTER_X_RELATIVE, (float) (Engine.WINDOW_SIZE.getHeight() - 250) + Button.GUI_BUTTON_CENTER_Y_RELATIVE, Button.GUI_BUTTON_HITBOX_RADIUS);

        Image startButtonHover = new Image(START_BUTTON_IMAGE_PATHS[1]);

        music = new Sound("resources/sounds/splash/background.ogg");
        buttonClick = new Sound("resources/sounds/splash/pop.ogg");

        start = new Button(startButtonImage, startButtonHover, startButtonHitbox, Button.CENTER, Engine.WINDOW_SIZE.getHeight() - 250, new Command() {
            @Override
            public void executeCommand() {
                start.forceUp();
                buttonClick.play(1, 2f);
                exitingScene = true;
                sbg.enterState(States.PRE_GAME_STATE, null, new HorizontalSplitTransition());
            }
        });
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
            music.loop(1, 0.3f);
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