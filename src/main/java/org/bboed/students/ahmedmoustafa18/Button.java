package org.bboed.students.ahmedmoustafa18;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tests.xml.Entity;

@SuppressWarnings("UnusedParameters")
public class Button extends Entity{
    private Image renderImage;

    private boolean buttonDown = false;
    private boolean buttonReleased = false;

    private final Shape HITBOX;

    private final Command COMMAND;

    private final Image BUTTON_UP;
    private final Image BUTTON_HOVER;

    private final Vector2f POSITION;

    //The following data is taken from the lollipop images.
    public static final int CENTER = -700;
    public static final int GUI_BUTTON_HITBOX_RADIUS = 61;
    public static final int GUI_BUTTON_CENTER_X_RELATIVE = 97;
    public static final int GUI_BUTTON_CENTER_Y_RELATIVE = 69;

    public Button(Image button, Image buttonHover, Shape hitbox, double x, double y, Command command) {
        this.BUTTON_UP = button;
        this.BUTTON_HOVER = buttonHover;

        this.HITBOX = hitbox;

        if(x == CENTER) {
            x = (Engine.WINDOW_SIZE.getWidth() / 2) - (BUTTON_UP.getWidth() / 2);
        }

        this.POSITION = new Vector2f((float) x, (float) y);
        this.renderImage = this.BUTTON_UP;
        this.COMMAND = command;
    }

    public void update (GameContainer gc, StateBasedGame sbg, int delta) {
        buttonReleased = false;

        if (HITBOX.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
            if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                buttonDown = true;
            }
            else {
                if (buttonDown) {
                    buttonDown = false;
                    buttonReleased = true;
                }
                renderImage = BUTTON_HOVER;
            }
        } else {
            buttonDown = false;
            renderImage = BUTTON_UP;
        }

        if(buttonDown) {
            COMMAND.executeCommand();
        }
    }

    public void render (Graphics g) {
        renderImage.draw(POSITION.x, POSITION.y);
    }


    @SuppressWarnings("unused")
    public boolean isClicked(){
        if (buttonReleased) {
            buttonReleased = false;
            return true;
        }
        return false;
    }

    @SuppressWarnings("unused")
    public boolean isButtonDown() {
        return buttonDown;
    }

    @SuppressWarnings("unused")
    public boolean isButtonReleased() {
        return buttonReleased;
    }

    public void forceUp() {
        renderImage = BUTTON_UP;
    }
}