package survive.utils;

import java.util.Map;

import survive.elements.Game;
import survive.elements.Unit;

public class UnitPositionCalculator {
	
	public static void updateUnitPositions() {
		for (Map.Entry<Integer, Unit> e: Game.units.entrySet()) {
	        Unit u = e.getValue();
	        float deltaX = u.getTargetX() - u.getPosX();
	        float deltaY = u.getTargetY() - u.getPosY();
	        float deltaDistance = (float) Math.sqrt(Math.pow(deltaX,2) + Math.pow(deltaY,2));
	        if(deltaDistance >= 10) {
	            u.setPosX(u.getPosX() + deltaX / deltaDistance * u.getSpeed());
	            u.setPosY(u.getPosY() + deltaY / deltaDistance * u.getSpeed());
	            Debugger.log("Moving "+e.getKey()+" to ("+u.getPosX()+", "+u.getPosY()+")");
	        }else if (deltaDistance > 1) {
	        	u.setPosX(u.getPosX() + deltaX / deltaDistance);
	        	u.setPosY(u.getPosY() + deltaY / deltaDistance);
	        }
	    }
	}
}
