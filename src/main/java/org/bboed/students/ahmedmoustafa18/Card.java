package org.bboed.students.ahmedmoustafa18;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;

import java.util.Random;

public class Card {
    private int cardType;

    private Image idleImage;
    private Image hoverImage;
    private Image renderImage;

    private Sound continueSound;
    private Sound cardSound;

    private Rectangle hitbox;

    private Vector2f position;

    private boolean render = false;

    private Shape darkOverlay;

    private GrabBag gbOverlay = new GrabBag();

    private boolean panningOut = false;
    private boolean panComplete = false;
    public static boolean cardDisplayed = false;

    private static final int PAN_OUT_SPEED = 2000; //pixels per second
    private static final int BUTTON_FROM_LEFT = 45; //the number of pixels between the top of the image and the top of the continue button
    private static final int BUTTON_FROM_TOP = 312; //the number of pixels between the left of the image and the left of the continue button
    private static final int BUTTON_WIDTH = 189; //width of continue button
    private static final int BUTTON_HEIGHT = 42; //height of continue button

    public Card(int cardType) throws SlickException {
        this.cardType = cardType;

        this.continueSound = new Sound("resources/sounds/game/pop.ogg");

        this.idleImage = new Image("resources/images/game/cards/" + getImageReferenceFromType(cardType));
        this.hoverImage = new Image("resources/images/game/cards-hover/" + getImageReferenceFromType(cardType));
        this.renderImage = idleImage;

        this.darkOverlay = new Rectangle(0, 0, (float) Engine.WINDOW_SIZE.getWidth(), (float) Engine.WINDOW_SIZE.getHeight());

        this.position = new Vector2f((float) (Engine.WINDOW_SIZE.getWidth() / 2) - (idleImage.getWidth() / 2),
                (float) (Engine.WINDOW_SIZE.getHeight() / 2) - (idleImage.getHeight() / 2));
        this.hitbox = new Rectangle(position.getX() + BUTTON_FROM_LEFT, position.getY() + BUTTON_FROM_TOP, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        if(render) {
            g.setColor(new Color(0, 0,0,150));

            g.fill(darkOverlay);
            renderImage.draw(position.getX(), position.getY());

            if(panComplete) {
                gbOverlay.render(gc, sbg, g);
            }
        }
    }

    public void update(GameContainer gc, StateBasedGame sbg, Game game, int delta) throws SlickException {
        if(render) {
            boolean mouseInHitbox = hitbox.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY());

            if(mouseInHitbox && renderImage != hoverImage) {
                renderImage = hoverImage;
            } else if(!mouseInHitbox && renderImage != idleImage) {
                renderImage = idleImage;
            }

            if(mouseInHitbox && gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                this.continueSound.play(1f, 2f);
                this.panningOut = true;
            }

            if(panningOut && !panComplete) {
                this.position.x -= PAN_OUT_SPEED * (delta / 1000.0f);
                this.hitbox.setX(this.hitbox.getX() - PAN_OUT_SPEED * (delta / 1000.0f));

                if(this.position.getX() <= -this.renderImage.getWidth()) {
                    panComplete = true;
                }
            }

            if(panComplete) {
                gbOverlay.update(gc, sbg, game, delta);
            }
        }
    }

    private String getImageReferenceFromType(int cardType) throws SlickException {
        switch(cardType) {
            case Bag.PLUS_CANDY:
                this.cardSound = new Sound("resources/sounds/game/gain.ogg");
                return "gain1.png";
            case  Bag.MINUS_CANDY:
                this.cardSound = new Sound("resources/sounds/game/lose.ogg");
                return "lose1.png";
            case Bag.MINUS_2_CANDY:
                this.cardSound = new Sound("resources/sounds/game/lose.ogg");
                return "lose2.png";
            case Bag.MINUS_3_CANDY:
                this.cardSound = new Sound("resources/sounds/game/lose.ogg");
                return "lose3.png";
        }

        throw new IllegalArgumentException();
    }

    public void makeVisible() {
        this.cardSound.play(1f, 2f);
        cardDisplayed = true;
        this.render = true;
    }

    private class GrabBag {

        private Image renderImage;

        private Vector2f position = new Vector2f();

        private Random randNum = new Random();

        private BagContent bagContent;

        private boolean panningIn = true;
        private boolean panningOut = false;
        private boolean panningOutComplete = false;
        private boolean bagWon;

        private Button yesBtn = new Button(Button.YES_BUTTON_ID);
        private Button noBtn = new Button(Button.NO_BUTTON_ID);

        private static final int PAN_SPEED = 2000;

        public GrabBag() throws SlickException {
            this.renderImage = new Image("resources/images/game/grab-bag.png");

            this.position.x = (float) Engine.WINDOW_SIZE.getWidth();
            this.position.y = (float) Engine.WINDOW_SIZE.getHeight() / 2 - renderImage.getHeight() / 2;

            if(randNum.nextInt(21) == 0) {
                bagWon = true;
            } else {
                bagWon = false;
            }

            bagContent = new BagContent();
        }

        public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
            if(!render) {
                return;
            }

            renderImage.draw(position.getX(), position.getY());
            yesBtn.render(gc, sbg, g);
            noBtn.render(gc, sbg, g);

            if(panningOutComplete) {
                bagContent.render(gc, sbg, g);
            }
        }

        public void update(GameContainer gc, StateBasedGame sbg, Game game, int delta) throws SlickException {
            if(!render) {
                return;
            }

            if(panningIn) {
                position.x -= PAN_SPEED * (delta / 1000.0f);

                if(position.getX() <= Engine.WINDOW_SIZE.getWidth() / 2 - renderImage.getWidth() / 2) {
                    panningIn = false;
                    position.x = (float) Engine.WINDOW_SIZE.getWidth() / 2 - renderImage.getWidth() / 2;

                }
            }

            if(panningOut) {
                position.x -= PAN_SPEED * (delta / 1000.0f);

                if(position.getX() <= -renderImage.getWidth()) {
                    panningOut = false;
                    panningOutComplete = true;
                }
            }

            if(panningOutComplete) {
                bagContent.update(gc, sbg, game, delta);
            }

            yesBtn.update(gc, sbg, game, delta);
            noBtn.update(gc, sbg, game, delta);
        }

        private class Button {

            private Image idleImage;
            private Image hoverImage;
            private Image renderImage;

            private Vector2f buttonPosition;

            private Shape buttonHitbox;

            private int buttonID;

            private static final int YES_BUTTON_ID = 1;
            private static final int NO_BUTTON_ID = 2;
            private static final int YES_BUTTON_DISTANCE_FROM_LEFT = 137;
            private static final int YES_BUTTON_DISTANCE_FROM_TOP = 238;
            private static final int NO_BUTTON_DISTANCE_FROM_LEFT = 218;
            private static final int NO_BUTTON_DISTANCE_FROM_TOP = 238;

            public Button(int buttonID) throws SlickException {
                this.idleImage = new Image("resources/images/game/buttons/" + getFileName(buttonID));
                this.hoverImage = new Image("resources/images/game/buttons-hover/" + getFileName(buttonID));
                this.renderImage = idleImage;

                this.buttonPosition = new Vector2f();

                this.buttonHitbox = new Rectangle(buttonPosition.getX(), buttonPosition.getY(), idleImage.getWidth(), idleImage.getHeight());

                this.buttonID = buttonID;
            }

            public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
                if(!render) {
                    return;
                }

                renderImage.draw(buttonPosition.getX(), buttonPosition.getY());
            }

            public void update(GameContainer gc, StateBasedGame sbg, Game game, int delta) throws SlickException {
                if(!render) {
                    return;
                }

                switch(buttonID) {
                    case YES_BUTTON_ID:
                        buttonPosition.x = position.getX() + YES_BUTTON_DISTANCE_FROM_LEFT;
                        buttonPosition.y = position.getY() + YES_BUTTON_DISTANCE_FROM_TOP;

                        buttonHitbox.setX(buttonPosition.getX());
                        buttonHitbox.setY(buttonPosition.getY());
                        break;
                    case NO_BUTTON_ID:
                        buttonPosition.x = position.getX() + NO_BUTTON_DISTANCE_FROM_LEFT;
                        buttonPosition.y = position.getY() + NO_BUTTON_DISTANCE_FROM_TOP;

                        buttonHitbox.setX(buttonPosition.getX());
                        buttonHitbox.setY(buttonPosition.getY());
                        break;
                }

                boolean mouseInHitbox = buttonHitbox.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY());

                if(mouseInHitbox && renderImage != hoverImage) {

                    renderImage = hoverImage;
                }  else if(!mouseInHitbox && renderImage != idleImage) {
                    renderImage = idleImage;
                }

                if(mouseInHitbox && gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                        if(buttonID == YES_BUTTON_ID) {
                            panningOut = true;
                            continueSound.play(1f, 2f);
                        } else if(buttonID == NO_BUTTON_ID) {
                            makeInvisible();
                            game.removeBag();
                            game.switchPlayer();
                            continueSound.play(1f, 2f);
                        }
                }
            }

            private String getFileName(int buttonID) {
                switch(buttonID) {
                    case YES_BUTTON_ID:
                        return "yes-btn.png";
                    case NO_BUTTON_ID:
                        return "no-btn.png";
                }

                throw new IllegalArgumentException();
            }
        }

        private class BagContent {
            private Image bagContent;
            private Image bagContentHover;
            private Image bagContentRender;

            private Vector2f bagContentPosition = new Vector2f();

            private Shape bagContentContinueHitbox;

            private Sound appearanceSound;

            private boolean bagContentPanned = false;

            private static final int CONTINUE_BUTTON_FROM_LEFT = 45;
            private static final int CONTINUE_BUTTON_FROM_TOP = 312;
            private static final int CONTINUE_BUTTON_WIDTH = 189;
            private static final int CONTINUE_BUTTON_HEIGHT = 42;

            public BagContent() throws SlickException {
                if(bagWon) {
                    bagContent = new Image("resources/images/game/cards/win.png");
                    bagContentHover = new Image("resources/images/game/cards-hover/win.png");
                    appearanceSound = new Sound("resources/sounds/game/win.ogg");
                } else {
                    bagContent = new Image("resources/images/game/cards/blank.png");
                    bagContentHover = new Image("resources/images/game/cards-hover/blank.png");
                    appearanceSound = new Sound("resources/sounds/game/lose.ogg");
                }

                bagContentRender = bagContent;

                bagContentPosition = new Vector2f((float) Engine.WINDOW_SIZE.getWidth(), (float) Engine.WINDOW_SIZE.getHeight() / 2 - bagContentRender.getHeight() / 2);

                bagContentContinueHitbox = new Rectangle(bagContentPosition.getX() + CONTINUE_BUTTON_FROM_LEFT, bagContentPosition.getY() + CONTINUE_BUTTON_FROM_TOP, CONTINUE_BUTTON_WIDTH, CONTINUE_BUTTON_HEIGHT);
            }

            public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
                if(!render) {
                    return;
                }

                bagContentRender.draw(bagContentPosition.getX(), bagContentPosition.getY());
            }

            public void update(GameContainer gc, StateBasedGame sbg, Game game, int delta) throws SlickException {
                if(panningOutComplete) {

                    if(!bagContentPanned) {
                        bagContentPosition.x -= PAN_SPEED * (delta / 1000.0f);

                        if(bagContentPosition.getX() <= Engine.WINDOW_SIZE.getWidth() / 2 - bagContentRender.getWidth() / 2) {
                            bagContentPosition.x = (float) Engine.WINDOW_SIZE.getWidth() / 2 - bagContentRender.getWidth() / 2;
                            bagContentContinueHitbox.setX(bagContentPosition.getX() + CONTINUE_BUTTON_FROM_LEFT);
                            bagContentPanned = true;
                            appearanceSound.play(1f, 2f);
                        }
                    } else {

                        boolean mouseOnContinue = bagContentContinueHitbox.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY());

                        if(mouseOnContinue && bagContentRender != bagContentHover) {
                            bagContentRender = bagContentHover;
                        } else if(!mouseOnContinue && bagContentRender != bagContent){
                            bagContentRender = bagContent;
                        }

                        if(mouseOnContinue && gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                            continueSound.play(1f, 2f);
                            makeInvisible();
                            game.removeBag();

                            if(bagWon) {
                                GameOverState.winner = Game.currentPlayer.getId();
                                Splash.music.stop();
                                sbg.enterState(States.GAME_OVER_STATE, null, new HorizontalSplitTransition());
                                GameOverState.gameBoard.resetBoard(gc, sbg);
                            } else {
                                game.switchPlayer();
                            }
                        }
                    }
                }
            }
        }
    }

    public void makeInvisible() {
        cardDisplayed = false;
        this.render = false;
    }
}
