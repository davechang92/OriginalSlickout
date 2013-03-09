package tutorials.slickout.mainmenu;
 
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
 
import tutorials.slickout.GameInfo;
import tutorials.slickout.playerinfo.PlayerInfo;
 
public class MainMenuGameState extends BasicGameState implements MouseListener{
 
	private Image background;
	private Image selector;
	private int selection; 
	private int optionSelected;
	private int topScore;
 
	@Override
	public int getID() {
		return 0;
	}
 
	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		container.setMouseGrabbed(false);
		
		selection = -1;
		optionSelected = selection;
 
		if ( GameInfo.getCurrentGameInfo() != null){
			topScore = ( topScore > GameInfo.getCurrentGameInfo().getPlayerInfo().getScore() ) ? topScore : GameInfo.getCurrentGameInfo().getPlayerInfo().getScore(); 
		}
	}
 
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		background = new Image("data/mainmenu.jpg");
		selector = new Image("data/selector.png");
	}
 
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		background.draw();
 
		if(selection == 1){
			selector.draw(158, 310);
			selector.draw(694, 310);
 
			GameInfo.createNewGameInfo();
		}else if(selection == 2){
			selector.draw(158, 474);
			selector.draw(694, 474);
		}
 
		g.drawString("TOPSCORE : " + topScore, 10, 10) ;
	}
 
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if(optionSelected == 1){
			// TODO log
			game.enterState(2);
		}else if(optionSelected == 2){
			System.exit(0);
		}
	}
 
	public void mouseMoved(int oldx, int oldy, int newX, int newY){
 
		if(newX > 228 && newX < 702){
			if ( newY > 308 && newY < 389){
				selection = 1;
			}else if ( newY > 475 && newY < 544){
				selection = 2;
			}else {
				selection = -1;
			}
		}
	}
 
	public void mouseClicked(int button, int x, int y, int clickCount){
		optionSelected = selection;
	}
}