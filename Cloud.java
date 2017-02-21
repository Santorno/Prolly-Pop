package org.bboed.students.ahmedmoustafa18;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Cloud {
	Image renderImage;
	
	String reference;

	double rightMostPoint;
	double leftMostPoint;
	
	double x;
	double y;
	double speed;
	double scale;
	
	boolean directionIsRight = false;
	
	public Cloud(String ref, double x, double y, double speed, double scale) {
		this.reference = ref;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.scale = scale;
		
		if(Logic.generateRandomNumber(0, 1) == 1) {
			directionIsRight = true;
		}
		
		try {
			renderImage = new Image(ref);
			renderImage = renderImage.getScaledCopy((float) scale);
			renderImage.setFilter(Image.FILTER_NEAREST);
			
			rightMostPoint = this.x + renderImage.getWidth();
			leftMostPoint = this.x;
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void render() {
		renderImage.draw((float) x, (float) y);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		rightMostPoint = this.x + renderImage.getWidth();
		leftMostPoint = this.x;

		if(directionIsRight) {
			if(leftMostPoint > gc.getWidth()) {
				x = 0 - renderImage.getWidth();
			} else {
				x += speed * (delta/1000.0);
			}
		} else {
			if(rightMostPoint < 0) {
				x = gc.getWidth();
			} else {
				x -= speed * (delta/1000.0);
			}
		}
	}
}
