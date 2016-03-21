package survive.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import survive.elements.Cell;
import survive.elements.Game;
import survive.elements.Team;
import survive.elements.Unit;

public class GameGenerator {
	
	/**
	 * Generates cells from image, teams and some units for testing purposes.
	 * @param imagePath Route to map image.
	 * 					Each black image pixel will be a cell.
	 */
	public static void generateGame(String imagePath) {
		Game.cells = new ArrayList<Cell>();
		try
		{
			Debugger.log("Opening map image...");
		    BufferedImage picture = ImageIO.read(new File(imagePath));
		    Game.sizeX = picture.getWidth() * Game.CELL_SIZE;
		    Game.sizeY = picture.getHeight() * Game.CELL_SIZE;
		    Debugger.log("Image size: "+picture.getWidth()+" x "+picture.getHeight()+" pixels.");
		    for(int x=0; x<picture.getWidth(); x++) {
		    	for(int y=0; y<picture.getHeight(); y++) {
		    		if(picture.getRGB(x, y) == -15132645) {
		    			Game.cells.add(new Cell(x, y));
		    		}
		    	}
		    }
		    Debugger.log(Game.cells.size()+" cells found!");
		}
		catch (IOException e)
		{
		    String workingDir = System.getProperty("user.dir");
		    Debugger.log("Current working directory : " + workingDir);
		    e.printStackTrace();
		}
		
		Game.teams = new HashMap<Integer, Team>();
		Game.teams.put(0, new Team("free", 0));
		Game.teams.put(1, new Team("daesh", 1));
		Game.teams.put(2, new Team("israel", 2));
		Game.teams.put(3, new Team("lgbt", 3));
		Game.teams.put(4, new Team("usa", 4));
		Game.teams.put(5, new Team("china", 5));
		Game.teams.put(6, new Team("russia", 6));
		Game.teams.put(7, new Team("brazil", 7));
		Game.teams.put(8, new Team("india", 8));
		Game.teams.put(9, new Team("eu", 9));
		
		Game.units = new ConcurrentHashMap<Integer, Unit>();
		Game.units.put((int)Game.nextUID.getAndIncrement(), new Unit(RandomPosGenerator.randomPosX(), RandomPosGenerator.randomPosY(), 20, 2, 0));
		Game.units.put((int)Game.nextUID.getAndIncrement(), new Unit(RandomPosGenerator.randomPosX(), RandomPosGenerator.randomPosY(), 20, 3, 1));
		Game.units.put((int)Game.nextUID.getAndIncrement(), new Unit(RandomPosGenerator.randomPosX(), RandomPosGenerator.randomPosY(), 20, 4, 2));
		Game.units.put((int)Game.nextUID.getAndIncrement(), new Unit(RandomPosGenerator.randomPosX(), RandomPosGenerator.randomPosY(), 20, 1, 3));
	}

}
