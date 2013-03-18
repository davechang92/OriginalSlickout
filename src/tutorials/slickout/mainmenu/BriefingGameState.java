package tutorials.slickout.mainmenu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import tutorials.slickout.GameInfo;
import tutorials.slickout.gameplay.GameplayState;

public class BriefingGameState extends BasicGameState implements MouseListener {

	private Image background;
	private Image selector;
	private int selection; 
	private int optionSelected;
	private int topScore;
	//stores which of the instructions screens we're on
	private int screen =1;
 
	@Override
	public int getID() {
		return 3;
	}
 
	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		container.setMouseGrabbed(false);
		screen=1;
		background = new Image("data/instructionsPage.png");
		selection = -1;
		optionSelected = selection;

	}
 
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		background = new Image("data/instructionsPage.png");
		selector = new Image("data/selector.png");
	}
 
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		background.draw();
		if(selection == 1){
			selector.draw(242,520);
		}else if(selection == 2 || selection == 3){
			selector.draw(575, 520);
		}
 
	}
 
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		//go to main menu
		if(optionSelected==1){
			game.enterState(0);
		}
		//go to next instructions screen
		if(optionSelected == 2 && screen == 1){
			background = new Image("data/instructionsPage2.png");
			screen = 2;
			optionSelected = -1;
		}else if(optionSelected == 3 && screen == 2){	//go to game
			GameInfo.createNewGameInfo();

			String levelfile = "data/level1.lvl";
			// obtain the game state
			GameplayState gameplay = (GameplayState) game.getState(1);

			gameplay.setLevelFile(levelfile);

			game.enterState(1);
		}
	}
 
	public void mouseMoved(int oldx, int oldy, int newX, int newY){
		selection = -1;
		if(newX > 30 && newX < 242){
			if ( newY > 540 && newY < 580){
				selection = 1;
			}
		}
			
		if(newX > 640 && newX < 750){
			if ( newY > 540 && newY < 580){
				if(screen==1){
					selection = 2;
				}else{
					selection = 3;
				}
			}
		}
	}
 
	public void mouseClicked(int button, int x, int y, int clickCount){
		optionSelected = selection;
	}

}
