package survive.utils;

import survive.elements.Game;

public class RandomPosGenerator {
	
	public static float randomPosX() {
		return (float)(Math.random()*Game.sizeX);
	}
	
	public static float randomPosY() {
		return (float)(Math.random()*Game.sizeY);
	}

}
