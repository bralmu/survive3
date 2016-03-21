package survive.responses;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import survive.elements.Game;
import survive.utils.Debugger;

@RestController
public class ResponseController {
    
    @CrossOrigin(origins = Game.SERVER_DOMAIN)
    @RequestMapping("/game")
    public GameResponse game() {
        return new GameResponse();
    }
    
    @CrossOrigin(origins = Game.SERVER_DOMAIN)
    @RequestMapping("/move")
    public MoveResponse move(@RequestParam(value="uid") int uid, @RequestParam(value="targetx") float targetX, @RequestParam(value="targety") float targetY) {
    	Debugger.log("Moving "+uid+" to ("+targetX+", "+targetY+")");
    	return new MoveResponse(uid, targetX, targetY);
    }
    
    @CrossOrigin(origins = Game.SERVER_DOMAIN)
    @RequestMapping("/join")
    public JoinResponse join(@RequestParam(value="teamname") String teamName) {
    	return new JoinResponse(teamName);
    }
    
}
