package survive.utils;

import java.util.ArrayList;
import java.util.List;

import survive.elements.Game;
import survive.elements.Unit;

public class InactiveUnitsKicker {
	
	public static void kickInactiveUnits() {
		List<Integer> inactiveUnitsIds = new ArrayList<Integer>();
		for(Unit u: Game.units.values()) {
			if(u.checkInactivity())
				inactiveUnitsIds.add(u.getId());
		}
		Game.units.keySet().removeAll(inactiveUnitsIds);
		if(inactiveUnitsIds.size() > 0) {
			Debugger.log("Kicking units with ids "+inactiveUnitsIds+".");
		}
	}

}
