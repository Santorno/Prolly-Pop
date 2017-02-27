package org.bboed.students.ahmedmoustafa18;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Cloud {
	private Image renderImage;

	private double rightMostPoint;
	private double leftMostPoint;

	private double x;
	private final double y;

	boolean directionIsRight = false;

    private final double SPEED;

    public Cloud(String ref, double x, double y, double speed, double scale) {
		this.x = x;
		this.y = y;
		this.SPEED = speed;

		if(Logic.generateRandomNumber(0, 1) == 1) {
			directionIsRight = true;
		}

		try {
			renderImage = new Image(ref);
			renderImage = renderImage.getScaledCopy((float) scale);
			renderImage.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}

        rightMostPoint = this.x + renderImage.getWidth();
        leftMostPoint = this.x;
	}

	public void render() {
		renderImage.draw((float) x, (float) y);
	}

	public void update(GameContainer gc, @SuppressWarnings("UnusedParameters") StateBasedGame sbg, int delta) {
		rightMostPoint = this.x + renderImage.getWidth();
		leftMostPoint = this.x;

		if(directionIsRight) {
			if(leftMostPoint > gc.getWidth()) {
				x = 0 - renderImage.getWidth();
			} else {
				x += SPEED * (delta/1000.0);
			}
		} else {
			if(rightMostPoint < 0) {
				x = gc.getWidth();
			} else {
				x -= SPEED * (delta/1000.0);
			}
		}
	}
}