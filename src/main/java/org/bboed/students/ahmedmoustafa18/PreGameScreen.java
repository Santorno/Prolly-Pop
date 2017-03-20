package org.bboed.students.ahmedmoustafa18;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.OutlineEffect;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;
import java.awt.Font;


public class PreGameScreen extends BasicGameState {

    private Image background;

    private Sound buttonClick;

    private Button backButton;
    private Button playButton;

    private TextField p1NameText;
    private TextField p2NameText;

    private static final int BUTTON_MARGIN = 220; //pixels
    private static final String P1_USERNAME = "Player 1 Name:";
    private static final String P2_USERNAME = "Player 2 Name:";
    private static final Color TEXT_BOX_COLOR = new Color(255, 255, 255, 160);
    public static final TrueTypeFont PRIMARY_FONT = new TrueTypeFont(new Font("Verdana", Font.PLAIN, 20), true);

    private java.awt.Font UIFont1;
    private org.newdawn.slick.UnicodeFont uniFont;

    private static final String[] BACK_BUTTON_IMAGE_PATHS = { "resources/images/pregame/back-button.png", "resources/images/pregame/back-button-hover.png" };
    private static final String[] PLAY_BUTTON_IMAGE_PATHS = { "resources/images/pregame/play-button.png", "resources/images/pregame/play-button-hover.png" };

    @SuppressWarnings("unused")
    private boolean exitingScene = false;

    @Override
    public int getID() {
        return States.PRE_GAME_STATE;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        background = new Image("resources/images/splash/background.png");

        Image backButtonImage = new Image(BACK_BUTTON_IMAGE_PATHS[0]);
        Shape backButtonHitbox = new Circle((float) ((Engine.WINDOW_SIZE.getWidth() / 2) - (backButtonImage.getWidth() / 2) - (BUTTON_MARGIN / 2.0) + Button.GUI_BUTTON_CENTER_X_RELATIVE), (float) (Engine.WINDOW_SIZE.getHeight() - 250) + Button.GUI_BUTTON_CENTER_Y_RELATIVE, Button.GUI_BUTTON_HITBOX_RADIUS);

        Image backButtonHover = new Image(BACK_BUTTON_IMAGE_PATHS[1]);

        try{
            UIFont1 = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
                    org.newdawn.slick.util.ResourceLoader.getResourceAsStream("resources/fonts/Doctor Soos Light 1.1.ttf"));
            UIFont1 = UIFont1.deriveFont(java.awt.Font.PLAIN, 32); //You can change "PLAIN" to "BOLD" or "ITALIC"... and 16.f is the size of your font

            uniFont = new org.newdawn.slick.UnicodeFont(UIFont1);
            uniFont.addAsciiGlyphs();
            uniFont.getEffects().add(new ColorEffect(java.awt.Color.black)); //You can change your color here, but you can also change it in the render{ ... }
//            uniFont.getEffects().add(new OutlineEffect(1, java.awt.Color.black));
            uniFont.addAsciiGlyphs();
            uniFont.loadGlyphs();
        }catch(Exception e){
            e.printStackTrace();
        }

        buttonClick = new Sound("resources/sounds/splash/pop.ogg");

        backButton = new Button(backButtonImage, backButtonHover, backButtonHitbox, (float) ((Engine.WINDOW_SIZE.getWidth() / 2) - (backButtonImage.getWidth() / 2) - (BUTTON_MARGIN / 2.0)), Engine.WINDOW_SIZE.getHeight() - 250, new Command() {
            @Override
            public void executeCommand() {
                backButton.forceUp();
                exitingScene = true;
                buttonClick.play(1, 2f);
                sbg.enterState(States.SPLASH_STATE, null, new HorizontalSplitTransition());
            }
        });

        Image playButtonImage = new Image(PLAY_BUTTON_IMAGE_PATHS[0]);
        Shape playButtonHitbox = new Circle((float) ((Engine.WINDOW_SIZE.getWidth() / 2) - (playButtonImage.getWidth() / 2) + (BUTTON_MARGIN / 2.0) + Button.GUI_BUTTON_CENTER_X_RELATIVE), (float) (Engine.WINDOW_SIZE.getHeight() - 250) + Button.GUI_BUTTON_CENTER_Y_RELATIVE, Button.GUI_BUTTON_HITBOX_RADIUS);

        System.out.println(playButtonHitbox.getX() + " " + playButtonHitbox.getY());

        Image playButtonHover = new Image(PLAY_BUTTON_IMAGE_PATHS[1]);

        playButton = new Button(playButtonImage, playButtonHover, playButtonHitbox, (float) ((Engine.WINDOW_SIZE.getWidth() / 2) - (playButtonImage.getWidth() / 2) + (BUTTON_MARGIN / 2.0)), Engine.WINDOW_SIZE.getHeight() - 250, new Command() {
            @Override
            public void executeCommand() {
                Game.player1 = new Player(p1NameText.getText(), 1);
                Game.player2 = new Player(p2NameText.getText(), 2);

                playButton.forceUp();
                exitingScene = true;
                buttonClick.play(1, 2f);
                sbg.enterState(States.GAME_STATE, null, new HorizontalSplitTransition());
            }
        });


        p1NameText = new TextField(gc, PRIMARY_FONT, (int) Engine.WINDOW_SIZE.getWidth() / 2 - 50,(int) Engine.WINDOW_SIZE.getHeight() / 3, 200, 30);
        p1NameText.setBackgroundColor(new Color(TEXT_BOX_COLOR));
        p1NameText.setTextColor(Color.black);
        p1NameText.setBorderColor(Color.gray);
        p1NameText.setMaxLength(12);

        p2NameText = new TextField(gc, PRIMARY_FONT, (int) Engine.WINDOW_SIZE.getWidth() / 2 - 50,(int) Engine.WINDOW_SIZE.getHeight() / 3 + 50, 200, 30);
        p2NameText.setBackgroundColor(new Color(TEXT_BOX_COLOR));
        p2NameText.setTextColor(Color.black);
        p2NameText.setBorderColor(Color.gray);
        p2NameText.setMaxLength(12);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setFont(uniFont);
        g.setColor(TEXT_BOX_COLOR);
        background.draw(0, 0);
        backButton.render(g);
        playButton.render(g);
        p1NameText.render(gc, g);
        p2NameText.render(gc, g);

        g.setColor(new Color(0, 0, 0, 255));

        g.drawString(P1_USERNAME, p1NameText.getX() - uniFont.getWidth(P1_USERNAME) - 25, (int) (p1NameText.getY() + (p1NameText.getHeight() / 2.0) - (uniFont.getLineHeight() / 2.0)));
        g.drawString(P2_USERNAME, p2NameText.getX() - uniFont.getWidth(P2_USERNAME) - 25, (int) (p2NameText.getY() + (p2NameText.getHeight() / 2.0) - (uniFont.getLineHeight() / 2.0)));
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        backButton.update(gc, sbg, delta);
        playButton.update(gc, sbg, delta);
    }
}