package survive.utils;

import java.util.TimerTask;

public class GameUpdaterTask extends TimerTask{

	@Override
	public void run() {
		InactiveUnitsKicker.kickInactiveUnits();
		UnitPositionCalculator.updateUnitPositions();
		CellTeamCalculator.updateCellTeams();		
	}
	
}
