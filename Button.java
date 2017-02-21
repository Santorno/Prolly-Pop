package org.bboed.students.ahmedmoustafa18;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tests.xml.Entity;

public class Button extends Entity{
    private Rectangle button;

    boolean buttonDown = false;
    boolean buttonReleased = false;

    private Image renderImage;
    private Image buttonUp;
    private Image buttonHover;

    private String[] imagePaths;

    private Vector2f position;

    private SplashLogo logo; //we pass SplashLogo to Button() so that the buttons start panning only after the logo has completed its pan

    public static final int CENTER = -700;

    private Command listener;

    public Button(String[] imagePath, double x, double y, Command command) throws SlickException {
        imagePaths = imagePath;
        try {
            this.buttonUp = new Image(imagePaths[0]);
            this.buttonUp.setFilter(Image.FILTER_NEAREST);

            this.buttonHover = new Image(imagePaths[1]);
            this.buttonHover.setFilter(Image.FILTER_NEAREST);

        } catch (SlickException e) {
            e.printStackTrace();
        }

        if(x == CENTER) {
            x = (Engine.WINDOW_SIZE.getWidth() / 2) - (buttonUp.getWidth() / 2);
        }

        this.position = new Vector2f((float) x, (float) y);
        this.button = new Rectangle(position.x, position.y, buttonUp.getWidth(), buttonUp.getHeight());
        this.renderImage = this.buttonUp;

        this.listener = command;

//        Cursor.clickableHitboxes.add(button);
    }

    public void update (GameContainer gc, StateBasedGame sbg, int delta) {
        buttonReleased = false;

        if (button.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
            if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                buttonDown = true;
            }
            else {
                if (buttonDown) {
                    buttonDown = false;
                    buttonReleased = true;
                }
                renderImage = buttonHover;
            }
        } else {
            buttonDown = false;
            renderImage = buttonUp;
        }

        if(buttonDown) {
            listener.executeCommand();
        }
    }

    public void render (Graphics gr) {
        renderImage.draw(position.x, position.y);
    }

    public boolean isClicked(){
        if (buttonReleased) {
            buttonReleased = false;
            return true;
        }
        return false;
    }
}