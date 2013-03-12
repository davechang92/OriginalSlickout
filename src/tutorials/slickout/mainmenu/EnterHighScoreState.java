package tutorials.slickout.mainmenu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import tutorials.slickout.GameInfo;
import tutorials.slickout.gameplay.GameplayState;

public class EnterHighScoreState extends BasicGameState {

	private Image background;
	private int topScore;
 
	@Override
	public int getID() {
		return 4;
	}
 
	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		container.setMouseGrabbed(false);
 
		if ( GameInfo.getCurrentGameInfo() != null){
			topScore = ( topScore > GameInfo.getCurrentGameInfo().getPlayerInfo().getScore() ) ? topScore : GameInfo.getCurrentGameInfo().getPlayerInfo().getScore(); 
		}
	}
 
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		background = new Image("data/enterHighScoreScreen.png");
	}
 
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		background.draw();
 
		g.drawString("TOPSCORE : " + topScore, 10, 10) ;
	}
 
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		Input input = container.getInput();
		
		if(input.isKeyDown(Input.KEY_ENTER))
        {
            game.enterState(5);
        }
		
	}
	
}
