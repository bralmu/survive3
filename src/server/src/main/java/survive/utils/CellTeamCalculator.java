package survive.utils;

import survive.elements.Cell;
import survive.elements.Game;

public class CellTeamCalculator {
	
	/**
	 * Updates the team of each cell and the total score of each team.
	 */
	public static void updateCellTeams() {
		int[] teamNewScores = new int[Game.teams.size()];
		
		for(Cell c: Game.cells) {
			float shortestDistance = Float.MAX_VALUE;
	        int closestUnitId = -1;
	        for (Integer uid: Game.units.keySet()) {
	            float distance = (float)Math.sqrt(Math.pow(Game.units.get(uid).getPosX()-c.getPosX(),2)+Math.pow(Game.units.get(uid).getPosY()-c.getPosY(),2));
	            if(distance < shortestDistance) {
	                shortestDistance = distance;
	                closestUnitId = uid;
	            }
	        }
	        if(closestUnitId > -1) {
	        	teamNewScores[Game.units.get(closestUnitId).getTeamId()]++;
	        	c.setTeamId(Game.units.get(closestUnitId).getTeamId());
	        }else {
	        	teamNewScores[0]++;
	        	c.setTeamId(0);
	        }
		}
		for(Integer tid: Game.teams.keySet()) {
			Game.teams.get(tid).setScore(teamNewScores[tid]);
		}
	}
}
