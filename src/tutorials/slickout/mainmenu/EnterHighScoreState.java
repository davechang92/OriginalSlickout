package tutorials.slickout.mainmenu;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import tutorials.slickout.GameInfo;
import tutorials.slickout.gameplay.GameplayState;

public class EnterHighScoreState extends BasicGameState implements KeyListener {

	private Image background;
	private int score;
	private String name;
	private StateBasedGame sbg;
 
	@Override
	public int getID() {
		return 4;
	}
 
	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		sbg = game;
		
		container.setMouseGrabbed(false);
		
		name = "";
		
		
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
		g.drawString(name.toUpperCase(),300 ,300);
	}
 
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
			
		
	}
	
	@Override
    public void keyPressed(int key, char c) {
		if(key == Input.KEY_BACK){
			name = name.substring(0, name.length()-1);
		}else if(key == Input.KEY_ENTER){
						
			if ( GameInfo.getCurrentGameInfo() != null){
				score = GameInfo.getCurrentGameInfo().getPlayerInfo().getScore(); 
			}
			BufferedWriter writer = null;
			try
			{
				writer = new BufferedWriter( new FileWriter( "output/highScores.txt", true));
				writer.write( name + "\n" + score + "\n");

			}
			catch ( IOException e)
			{
			}
			finally
			{
				try
				{
					if ( writer != null)
						writer.close( );
				}
				catch ( IOException e)
				{
				}
		     }
			
            sbg.enterState(5);
       
		}else{
			if(name.length() < 5)
				name = name + c;
		}
    }
	
}
