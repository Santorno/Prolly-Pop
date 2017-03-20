package org.bboed.students.ahmedmoustafa18;


import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;

public class GameOverState extends BasicGameState {
    private Image background;
    private Image winnerCloud;

    private Sound backgroundMusic;
    private Sound buttonClick;

    private Cloud cloud;
    private Cloud cloud2;
    private Cloud cloud3;
    private Cloud cloud4;
    private Button back;
    private Button exit;

    private String winnerName;
    private String winnerDescription;

    private boolean rendered = false;

    private static final int BUTTON_MARGIN = 220; //pixels
    private static final int NAME_FROM_LEFT = 106;
    private static final int NAME_FROM_TOP = 50;
    private static final int DESCRIPTION_fROM_TOP = 100;

    public static Game gameBoard;

    public static int winner;
    public static final int TIE = -1;

    @Override
    public int getID() {
        return States.GAME_OVER_STATE;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        background = new Image("resources/images/splash/background.png");
        winnerCloud = new Image("resources/images/gameover/winner.png");

        buttonClick = new Sound("resources/sounds/game/pop.ogg");
        backgroundMusic = new Sound("resources/sounds/gameover/winner.ogg");

        cloud = new Cloud("resources/images/splash/cloud.png", 10, 50f, 50, 1);
        cloud2 = new Cloud("resources/images/splash/cloud2.png", Engine.WINDOW_SIZE.getWidth() - 300, 100, 50, 1f);
        cloud3 = new Cloud("resources/images/splash/cloud3.png", Engine.WINDOW_SIZE.getWidth() - 600, Engine.WINDOW_SIZE.getHeight() - 500, 50, 1f);
        cloud4 = new Cloud("resources/images/splash/cloud.png", 300, 300, 50, 1f);

        Image backButtonImage = new Image("resources/images/pregame/back-button.png");
        Image backButtonHover = new Image("resources/images/pregame/back-button-hover.png");

        Shape backButtonHitbox = new Circle((float) ((Engine.WINDOW_SIZE.getWidth() / 2) - (backButtonImage.getWidth() / 2) - (BUTTON_MARGIN / 2.0) + Button.GUI_BUTTON_CENTER_X_RELATIVE), (float) (Engine.WINDOW_SIZE.getHeight() - 250) + Button.GUI_BUTTON_CENTER_Y_RELATIVE, Button.GUI_BUTTON_HITBOX_RADIUS);

        back = new Button(backButtonImage, backButtonHover, backButtonHitbox, (float) ((Engine.WINDOW_SIZE.getWidth() / 2) - (backButtonImage.getWidth() / 2) - (BUTTON_MARGIN / 2.0)), Engine.WINDOW_SIZE.getHeight() - 250, new Command() {
            @Override
            public void executeCommand() {
                back.forceUp();
                buttonClick.play(1, 2f);
                backgroundMusic.stop();
                Splash.music.loop(1f, 0.3f);

                sbg.enterState(States.SPLASH_STATE, null, new HorizontalSplitTransition());
            }
        });

        Image exitButtonImage = new Image("resources/images/gameover/exit-button.png");
        Shape exitButtonHitbox = new Circle((float) ((Engine.WINDOW_SIZE.getWidth() / 2) - (exitButtonImage.getWidth() / 2) + (BUTTON_MARGIN / 2.0) + Button.GUI_BUTTON_CENTER_X_RELATIVE), (float) (Engine.WINDOW_SIZE.getHeight() - 250) + Button.GUI_BUTTON_CENTER_Y_RELATIVE, Button.GUI_BUTTON_HITBOX_RADIUS);

        Image exitButtonHover = new Image("resources/images/gameover/exit-button-hover.png");

        exit = new Button(exitButtonImage, exitButtonHover, exitButtonHitbox, (float) ((Engine.WINDOW_SIZE.getWidth() / 2) - (exitButtonImage.getWidth() / 2) + (BUTTON_MARGIN / 2.0)), Engine.WINDOW_SIZE.getHeight() - 250, new Command() {
            @Override
            public void executeCommand() {
                System.exit(0);
            }
        });
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        if(!rendered) {
            backgroundMusic.loop();

            if(winner == TIE) {
                winnerName = "It's a tie!";
                winnerDescription = "Both players won " + Game.player1.getCandyCount() + " candy";
            } else {
                winnerName = (winner == 1 ? Game.player1.getName() : Game.player2.getName()) + " won!";
                winnerDescription = "Player won " + (Game.player1.getCandyCount() + Game.player2.getCandyCount() + Game.getCandyInLossPile()) + " candy";
            }

            rendered = true;
        }

        background.draw(0, 0);
        cloud.render();
        cloud2.render();
        cloud3.render();
        cloud4.render();
        back.render(g);
        exit.render(g);
        winnerCloud.draw((float) Engine.WINDOW_SIZE.getWidth() / 2 - winnerCloud.getWidth() / 2, 20);

        g.setColor(Color.black);
        g.setFont(Game.uniFont);
        g.drawString(winnerName, (float) Engine.WINDOW_SIZE.getWidth() / 2 - Game.uniFont.getWidth(winnerName) / 2 - 5, 20 + NAME_FROM_TOP);

        g.setFont(Game.uniFont3);
        g.drawString(winnerDescription, (float) Engine.WINDOW_SIZE.getWidth() / 2 - Game.uniFont3.getWidth(winnerDescription) / 2, 20 + DESCRIPTION_fROM_TOP);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        cloud.update(gc, sbg, delta);
        cloud2.update(gc, sbg, delta);
        cloud3.update(gc, sbg, delta);
        cloud4.update(gc, sbg, delta);
        back.update(gc, sbg, delta);
        exit.update(gc, sbg, delta);
    }
}
