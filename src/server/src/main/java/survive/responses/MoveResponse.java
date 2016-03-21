package survive.responses;

import survive.elements.Game;
import survive.elements.Unit;

/**
 * Answer to /move request. Gets a unit id and its new target coordinates. 
 * Returns whether the movement request was valid or not.
 */
public class MoveResponse {
	private boolean valid;
	
	public MoveResponse(int uid, float targetX, float targetY) {
		this.valid = (Game.units.containsKey(uid) && 
						targetX > 0f &&
						targetX < Game.sizeX &&
						targetY > 0f &&
						targetY < Game.sizeY);
		if(valid) {
			Unit u = Game.units.get(uid);
			u.setTargetX(targetX);
			u.setTargetY(targetY);
			u.updateLastActivityTime();
		}
	}
	
	public boolean getValid() {
		return valid;
	}
}
