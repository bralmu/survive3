package survive.responses;

import java.util.Map;

import survive.elements.Game;
import survive.elements.Team;
import survive.elements.Unit;

/**
 * Answer to /gameupdate request. Gives cells' team, teams and units.
 */
public class GameUpdateResponse {
	
	private float[] cellsTeamId;
	
	public GameUpdateResponse() {
		cellsTeamId = new float[Game.cells.size()];
		for(int i=0; i<Game.cells.size(); i++) {
			cellsTeamId[i] = Game.cells.get(i).getTeamId();
		}
	}
	
	public Map<Integer, Unit> getUnits() {
    	return Game.units;
    }
	
	public Map<Integer, Team> getTeams() {		
    	return Game.teams;
    }
	
	public float[] getCellsTeamId() {
		return cellsTeamId;
	}
}
