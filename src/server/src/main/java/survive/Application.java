package survive;

import java.util.Timer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import survive.elements.Game;
import survive.utils.CellTeamCalculator;
import survive.utils.Debugger;
import survive.utils.GameGenerator;
import survive.utils.GameUpdaterTask;

@SpringBootApplication
public class Application {
	

    public static void main(String[] args) {
    	Debugger.enable();
        GameGenerator.generateGame("map.png");
        CellTeamCalculator.updateCellTeams();
        Timer timer = new Timer();
        timer.schedule(new GameUpdaterTask(), 5000l, (long)(1000/Game.UPDATES_PER_SECOND));
    	SpringApplication.run(Application.class, args);        
    }
    
}
