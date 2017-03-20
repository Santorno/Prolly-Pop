package org.bboed.students.ahmedmoustafa18;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

@SuppressWarnings("UnusedParameters")
public class FlyingItem {
	private Sound sound;

    private Image renderImage;

    private boolean soundPlayed = false;
	
	private final Vector2f position = new Vector2f();
	private final Vector2f initialPosition = new Vector2f();

    private final boolean FLY_RIGHT;

    private final float SLOPE; //travel SLOPE
    private final float FLY_SPEED; //pixels/second
    private final float SPIN_SPEED; //degrees/second
    private final float APPEARANCE_FREQUENCY; //minutes/appearance

    private static final float MAXIMUM_APPEARANCE_FREQUENCY = 0.2f;
    private static final float MINIMUM_APPEARANCE_FREQUENCY = 0.25f;
	
	public FlyingItem(String imageReference, String soundReference, GameContainer gc, float flySpeed, float spinSpeed, float scale) {
		if(flySpeed > 0) {
			this.FLY_SPEED = flySpeed;
		} else {
			this.FLY_SPEED = 1000;
		}
		if(spinSpeed > 0) {
			this.SPIN_SPEED = spinSpeed;
		} else {
			this.SPIN_SPEED = 360;
		}
		this.APPEARANCE_FREQUENCY = Logic.generateRandomFloat(MINIMUM_APPEARANCE_FREQUENCY, MAXIMUM_APPEARANCE_FREQUENCY);
		//this.APPEARANCE_FREQUENCY = 0.1f;
        float MAXIMUM_SLOPE = ((float) gc.getHeight() / 2.0f) / (float) gc.getWidth() / 2.0f;
		this.SLOPE = Logic.generateRandomFloat(0, MAXIMUM_SLOPE) - MAXIMUM_SLOPE;
		this.initialPosition.y = position.y = Logic.generateRandomFloat((gc.getHeight() * 3)/4, gc.getHeight()/4);

		try {
			renderImage = new Image(imageReference);
			renderImage.setFilter(Image.FILTER_NEAREST);
			renderImage = renderImage.getScaledCopy(scale);
			
			if(soundReference != null) {
                String soundReference1 = soundReference;
				this.sound = new Sound(soundReference);
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		if(Logic.generateRandomNumber(0, 1) == 0) {
			this.FLY_RIGHT = false;
			this.position.x = ((flySpeed * 60) * APPEARANCE_FREQUENCY + renderImage.getWidth());

		} else {
			this.FLY_RIGHT = true;
			this.position.x = -((flySpeed * 60) * APPEARANCE_FREQUENCY - renderImage.getWidth());
			this.position.y = gc.getHeight()/2;
		}
	}
	
	public void render() {
		renderImage.draw(position.x, position.y);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		if(FLY_RIGHT) {
			if(position.x < -renderImage.getWidth()) {
				position.y = initialPosition.y;
				position.x += FLY_SPEED * (delta/1000.0);
			} else if(position.x > gc.getWidth()) {
				soundPlayed = false;
				this.initialPosition.y = position.y = Logic.generateRandomFloat((gc.getHeight() * 3)/4, gc.getHeight()/4);
				this.position.x = -((FLY_SPEED * 60) * APPEARANCE_FREQUENCY - renderImage.getWidth());
			} else {
				if(SLOPE < 0) {
					position.y -= (FLY_SPEED * SLOPE) * (delta/1000.0);
				} else {
					position.y += (FLY_SPEED * SLOPE) * (delta/1000.0);
				}
				if(sound != null) {
					if(position.x < gc.getWidth() - 200 && !soundPlayed) {
						soundPlayed = true;
						sound.play();
					}
				}
				position.x += FLY_SPEED * (delta/1000.0);
				renderImage.rotate((SPIN_SPEED * (delta/1000.0f)));
			}
		} else {
			if(position.x > gc.getWidth()) {
				position.y = initialPosition.y;
				position.x -= FLY_SPEED * (delta/1000.0);
			} else if(position.x < -renderImage.getWidth()) {
				soundPlayed = false;
				this.initialPosition.y = position.y = Logic.generateRandomFloat((gc.getHeight() * 3)/4, gc.getHeight()/4);
				this.position.x = ((FLY_SPEED * 60) * APPEARANCE_FREQUENCY + renderImage.getWidth());
			} else {
				if(SLOPE < 0) {
					position.y -= (FLY_SPEED * SLOPE) * (delta/1000.0);
				} else {
					position.y += (FLY_SPEED * SLOPE) * (delta/1000.0);
				}
				if(sound != null) {
					if(position.x > 200 && !soundPlayed) {
						soundPlayed = true;
						sound.play();
					}
				}
				position.x -= FLY_SPEED * (delta/1000.0);
				renderImage.rotate(-(SPIN_SPEED * (delta/1000.0f)));
			}
		}
	}
}
