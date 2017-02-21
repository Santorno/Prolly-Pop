package org.bboed.students.ahmedmoustafa18;

public abstract class Logic {
	public static int generateRandomNumber(int min, int max) {
		int num = (int) ((Math.random() * (max - min + 1)) + min);
		return num;
	}
	
	public static float generateRandomFloat(float min, float max) {
		float num = (float) ((Math.random() * (max - min)) + min);
		return num;
	}
}
