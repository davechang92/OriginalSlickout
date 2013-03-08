package tutorials.slickout;

/*
 * TODO
 * -HAVE VARIABLE POWERUP DURATIONS
 * -HAVE POWERUPS FALLING ALL THE TIME, NO JUST IN NORMAL GAMEPLAYSTATE
 * -HAVE EDGE OF ENLARGED PADDLE COLLIDE NOT GO OVER SCREEN
 * -NEED TO MAKE POWER-UPS SPECIFIC TO SPECIFIC BALLS - MAY NEED TO CHANGE POWER-UP CLASS FOR THIS (SEE BOOKMARK); IN THE 
 * MEANTIME I'M GOING TO JUST HAVE TIMER REVERT ALL BALLS TO STANDARD SPEED.
 * -ENSURE THAT BALL DOESN'T REBOUND AT ANNOYING ANGLES
 * -RARE GLITCH THAT LETS BALL FLY OUT OF SCREEN? FUDGED HANDLING BY ADDING IN CHECKS FOR THIS IN GAMEPLAYSTATE, BUT NOT FIXED
 * -HAVE POWER UP NAMES AS ENUMERATION TYPE
 */

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
 
import tutorials.slickout.gameplay.GameplayState;
import tutorials.slickout.levelselector.LevelSelector;
import tutorials.slickout.mainmenu.MainMenuGameState;
import tutorials.slickout.playerinfo.PlayerInfo;
 
 
public class SlickOutGame extends StateBasedGame {
	public SlickOutGame() {
		super("Slick Tutorials : SlickOut");
	}
 
	public void initStatesList(GameContainer gc) throws SlickException {
 
		addState(new MainMenuGameState());
 
		GameplayState state = new GameplayState();
 
		addState(new LevelSelector());
 
		addState(state);
	}
 
	public static void main(String[] args) throws SlickException
    {
         AppGameContainer app = new AppGameContainer(new SlickOutGame());
 
         // Application properties
         //app.setDisplayMode(1440, 900, true); //fullscreen
         app.setDisplayMode(800, 800, false);
         app.setMouseGrabbed(true);
         //app.setSmoothDeltas(true);
 
         app.start();
    }
 
 
}