package tutorials.slickout;

/*
 * TODO
 * -HAVE VARIABLE POWERUP DURATIONS
 * -HAVE POWERUPS FALLING ALL THE TIME, NO JUST IN NORMAL GAMEPLAYSTATE
 * -NEED TO MAKE POWER-UPS SPECIFIC TO SPECIFIC BALLS - MAY NEED TO CHANGE POWER-UP CLASS FOR THIS (SEE BOOKMARK); IN THE 
 * MEANTIME I'M GOING TO JUST HAVE TIMER REVERT ALL BALLS TO STANDARD SPEED.
 * -RARE GLITCH THAT LETS BALL FLY OUT OF SCREEN? FUDGED HANDLING BY ADDING IN CHECKS FOR THIS IN GAMEPLAYSTATE, BUT NOT FIXED
 * -HAVE POWER UP NAMES AS ENUMERATION TYPE
 * -BUG WHEN SCORE NAME HAS LENGTH -1
 * -PREVENT IMPOSSIBLE ANGLES FROM WALL/BRICK REBOUNDS
 */

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
 
import tutorials.slickout.gameplay.GameplayState;
import tutorials.slickout.levelselector.LevelSelector;
import tutorials.slickout.mainmenu.BriefingGameState;
import tutorials.slickout.mainmenu.DisplayHighScoreState;
import tutorials.slickout.mainmenu.EnterHighScoreState;
import tutorials.slickout.mainmenu.MainMenuGameState;
import tutorials.slickout.playerinfo.PlayerInfo;
 
 
public class SlickOutGame extends StateBasedGame {
	
	boolean dda;
	
	public SlickOutGame() {
		super("Breakout: Dynamo!");
	}
 
	public void initStatesList(GameContainer gc) throws SlickException {
 
		if(Math.random()<0.5)
			dda = true;
		else
			dda = false;
		
		addState(new MainMenuGameState());
 
		GameplayState state = new GameplayState(dda);
			
 
		//addState(new LevelSelector());
		
		addState(new BriefingGameState());
		
		addState(new EnterHighScoreState());
		
		addState(new DisplayHighScoreState());
 
		addState(state);
		
		
	}
 
	public static void main(String[] args) throws SlickException
    {
         AppGameContainer app = new AppGameContainer(new SlickOutGame());
 
         // Application properties
         //app.setDisplayMode(1440, 900, true); //fullscreen
         app.setDisplayMode(800, 600, false);
         //app.setMouseGrabbed(true);
         //app.setSmoothDeltas(true);
 
         app.start();
    }
 
 
}