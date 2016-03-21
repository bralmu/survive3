package survive.responses;

import survive.elements.Game;
import survive.elements.Unit;
import survive.utils.Debugger;
import survive.utils.RandomPosGenerator;

/**
 * Answer to /join request. Creates a new unit at random position and sends its id.
 */
public class JoinResponse {
	
	private int uid;
	
	public JoinResponse(String teamName) {
		Debugger.log("Creating player with team "+teamName);
		int tid = -1;
		for(int k: Game.teams.keySet()) {
			if(Game.teams.get(k).getName().equals(teamName)){
				tid = k;
				break;
			}
		}
		if(tid == -1) {
			uid = -1;
			Debugger.log("No team id found for that name");
		}else {
			uid = (int)Game.nextUID.getAndIncrement();
			Debugger.log("Creating player with uid "+uid+" and tid "+tid);
			Game.units.put(uid, new Unit(RandomPosGenerator.randomPosX(), RandomPosGenerator.randomPosY(), 20, tid, uid));
		}		
	}
	
	public int getUID() {
		return this.uid;
	}

}
