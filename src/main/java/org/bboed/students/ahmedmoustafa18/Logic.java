package org.bboed.students.ahmedmoustafa18;

public abstract class Logic {
	public static int generateRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min + 1)) + min);
	}
	
	public static float generateRandomFloat(float min, float max) {
		return (float) ((Math.random() * (max - min)) + min);
	}
}
