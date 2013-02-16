package tutorials.slickout;
 
/*
 * TODO
 * HAVE VARIABLE POWERUP DURATIONS
 * HAVE POWERUPS FALLING ALL THE TIME, NO JUST IN NORMAL GAMEPLAYSTATE
 * HAVE EDGE OF ENLARGED PADDLE COLLIDE NOT GO OVER SCREEN
 * 
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
         app.setDisplayMode(800, 600, false);
         //app.setSmoothDeltas(true);
 
         app.start();
    }
 
 
}