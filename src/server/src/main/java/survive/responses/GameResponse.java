package survive.responses;

import java.util.List;
import java.util.Map;

import survive.elements.Cell;
import survive.elements.Game;
import survive.elements.Team;
import survive.elements.Unit;

/**
 * 
 * Answer to /game request. Gives game, cells, teams and units info.
 *
 */
public class GameResponse {
    
    public List<Cell> getCells() {
    	return Game.cells;
    }
    
    public Map<Integer, Team> getTeams() {
    	return Game.teams;
    }
    
    public Map<Integer, Unit> getUnits() {
    	return Game.units;
    }
    
    public float getGameWidth() {
    	return Game.sizeX;
    }
    
    public float getGameHeight() {
    	return Game.sizeY;
    }
    
    public float getCellSize() {
    	return Game.CELL_SIZE;
    }
}
