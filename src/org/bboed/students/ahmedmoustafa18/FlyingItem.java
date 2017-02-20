package org.bboed.students.ahmedmoustafa18;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class FlyingItem {
	
	String imageReference;
	String soundReference;
	
	boolean flyDirectionRight;
	boolean soundPlayed = false;
	
	float flySpeed; //pixels/second
	float spinSpeed; //degrees/second
	float appearanceFrequency; //minutes/appearance
	float slope; //travel slope
	float rotation; //degrees
	
	final float MAXIMUM_APPEARANCE_FREQUENCY = 0.2f;
	final float MINIMUM_APPEARANCE_FREQUENCY = 0.25f;
	final float MAXIMUM_SLOPE;
	
	Image renderImage;
	
	Sound sound;
	
	Vector2f position = new Vector2f();
	Vector2f initialPosition = new Vector2f();
	
	public FlyingItem(String imageReference, String soundReference, GameContainer gc, float flySpeed, float spinSpeed, float scale) {
		this.imageReference = imageReference;
		if(flySpeed > 0) {
			this.flySpeed = flySpeed;
		} else {
			this.flySpeed = 1000;
		}
		if(spinSpeed > 0) {
			this.spinSpeed = spinSpeed;
		} else {
			this.spinSpeed = 360;
		}
		this.appearanceFrequency = Logic.generateRandomFloat(MINIMUM_APPEARANCE_FREQUENCY, MAXIMUM_APPEARANCE_FREQUENCY);
		//this.appearanceFrequency = 0.1f;
		MAXIMUM_SLOPE = ((float)gc.getHeight()/2.0f)/(float)gc.getWidth()/2.0f;
		this.slope = Logic.generateRandomFloat(0, MAXIMUM_SLOPE) - MAXIMUM_SLOPE;
		this.initialPosition.y = position.y = Logic.generateRandomFloat((gc.getHeight() * 3)/4, gc.getHeight()/4);

		try {
			renderImage = new Image(imageReference);
			renderImage.setFilter(Image.FILTER_NEAREST);
			renderImage = renderImage.getScaledCopy(scale);
			
			if(soundReference != null) {
				this.soundReference = soundReference;
				this.sound = new Sound(soundReference);
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		if(Logic.generateRandomNumber(0, 1) == 0) {
			this.flyDirectionRight = false;
			this.position.x = ((flySpeed * 60) * appearanceFrequency + renderImage.getWidth());

		} else {
			this.flyDirectionRight = true;
			this.position.x = -((flySpeed * 60) * appearanceFrequency - renderImage.getWidth());
			this.position.y = gc.getHeight()/2;
		}
	}
	
	public void render() {
		renderImage.draw(position.x, position.y);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		if(flyDirectionRight) {
			if(position.x < -renderImage.getWidth()) {
				position.y = initialPosition.y;
				position.x += flySpeed * (delta/1000.0);
			} else if(position.x > gc.getWidth()) {
				soundPlayed = false;
				this.initialPosition.y = position.y = Logic.generateRandomFloat((gc.getHeight() * 3)/4, gc.getHeight()/4);
				this.position.x = -((flySpeed * 60) * appearanceFrequency - renderImage.getWidth());
			} else {
				if(slope < 0) {
					position.y -= (flySpeed * slope) * (delta/1000.0);
				} else {
					position.y += (flySpeed * slope) * (delta/1000.0);
				}
				if(sound != null) {
					if(position.x < gc.getWidth() - 200 && !soundPlayed) {
						soundPlayed = true;
						sound.play();
					}
				}
				position.x += flySpeed * (delta/1000.0);
				renderImage.rotate((spinSpeed * (delta/1000.0f)));
			}
		} else {
			if(position.x > gc.getWidth()) {
				position.y = initialPosition.y;
				position.x -= flySpeed * (delta/1000.0);
			} else if(position.x < -renderImage.getWidth()) {
				soundPlayed = false;
				this.initialPosition.y = position.y = Logic.generateRandomFloat((gc.getHeight() * 3)/4, gc.getHeight()/4);
				this.position.x = ((flySpeed * 60) * appearanceFrequency + renderImage.getWidth());
			} else {
				if(slope < 0) {
					position.y -= (flySpeed * slope) * (delta/1000.0);
				} else {
					position.y += (flySpeed * slope) * (delta/1000.0);
				}
				if(sound != null) {
					if(position.x > 200 && !soundPlayed) {
						soundPlayed = true;
						sound.play();
					}
				}
				position.x -= flySpeed * (delta/1000.0);
				renderImage.rotate(-(spinSpeed * (delta/1000.0f)));
			}
		}
	}
}
