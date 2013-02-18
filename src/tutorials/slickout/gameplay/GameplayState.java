package tutorials.slickout.gameplay;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
 
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
 
import tutorials.slickout.GameInfo;
import tutorials.slickout.dda.sensor.ISensor;
import tutorials.slickout.dda.sensor.SensorFactory;
import tutorials.slickout.gameplay.collision.CollisionManager;
import tutorials.slickout.gameplay.level.Ball;
import tutorials.slickout.gameplay.level.Brick;
import tutorials.slickout.gameplay.level.ILevel;
import tutorials.slickout.gameplay.level.LevelImpl;
import tutorials.slickout.gameplay.level.Paddle;
import tutorials.slickout.gameplay.level.Paddle.PAD_STATE;
import tutorials.slickout.gameplay.level.PowerUp;
import tutorials.slickout.gameplay.level.collision.BrickBallCollisionHandler;
import tutorials.slickout.gameplay.level.collision.BumperAndPadBallCollisionHandler;
import tutorials.slickout.gameplay.level.collision.PadAndPowerUpCollisionHandler;
import tutorials.slickout.playerinfo.PlayerInfo;
 
public class GameplayState extends BasicGameState {
 
	private ILevel level;
	private String levelFile;
	private PlayerInfo playerInfo;
 
	private int counter = 0;
	private String message = null;
 
	private static enum LEVEL_STATES { BALL_LAUNCH, NORMAL_GAME, LIFE_LOST, LEVEL_OVER, GAME_OVER };
 
	private LEVEL_STATES currentState;
 
	private CollisionManager collisionManager;
	
	private ISensor powerUpSensor;
 
	@Override
	public int getID() {
		return 1;
	}
 
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
	}
 
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
 
		// load level
		if(levelFile == null){
			throw new SlickException("No level to load"); 
		}
 
		try {
			level = LevelImpl.loadLevel( new FileInputStream(new File(levelFile)) );
		} catch (FileNotFoundException e) {
			throw new SlickException("Could not load file");
		}
 
		collisionManager = new CollisionManager();
 
		collisionManager.addCollidable(level.getLeftBumper());
		collisionManager.addCollidable(level.getRightBumper());
		collisionManager.addCollidable(level.getTopBumper());
 
		collisionManager.addCollidable(level.getPaddle());
 
		for(Brick brick : level.getBricks()){
			collisionManager.addCollidable(brick);
		}
		
		PadAndPowerUpCollisionHandler padPowerUpHandler = new PadAndPowerUpCollisionHandler(level, collisionManager);
		
		collisionManager.addHandler(new BumperAndPadBallCollisionHandler());
		collisionManager.addHandler(new BrickBallCollisionHandler(level, collisionManager));
		collisionManager.addHandler(padPowerUpHandler);
 
		gc.getInput().addMouseListener(level.getPaddle());
 
		currentState = LEVEL_STATES.BALL_LAUNCH;
 
		playerInfo = GameInfo.getCurrentGameInfo().getPlayerInfo();
		
		SensorFactory factory = new SensorFactory();
		powerUpSensor = factory.createSensor("PowerUp", padPowerUpHandler);
	}
 
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gr)
			throws SlickException {
 
		// Background
		level.getBackground().render(gr);
 
		// Bumpers
		level.getLeftBumper().render(gr);
 
		level.getRightBumper().render(gr);
 
		level.getTopBumper().render(gr);
 
		// Bricks
		for ( Brick brick : level.getBricks() ){
			brick.render(gr);
		}
 
		// Paddle
		level.getPaddle().render(gr);
 
		// Ball
		for ( Ball ball : level.getBalls()){
			ball.render(gr);
		}
		
		//powerups
		for ( PowerUp powerUp : level.getPowerUps()){
			powerUp.render(gr);
		}
 
		gr.drawString("Lives: " + playerInfo.getLives(), 700, 10);
		gr.drawString("Score: " + playerInfo.getScore(), 500, 10);
 
		if(message != null){
			gr.drawString(message, 300, 300);
		}
		
		//draws sensor values at bottom of screen
		gr.drawString("Power Ups Collected: "+ powerUpSensor.getValue(), 50, 700);
	}
 
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
 
		switch(currentState){
		case BALL_LAUNCH:{
			if( level.getBalls().size() == 0 ){
				Ball ball = level.addNewBall();
				ball.setSpeed(0.0f);
				ball.setDirection(new Vector2f(0,0));
				// TODO: Catalog this
				message = "Get Ready!";
			}
 
			Ball ball = level.getBalls().get(0);
 
			if(level.getPaddle().getState() == Paddle.PAD_STATE.STICKY){
 
				Vector2f position = level.getPaddle().getPosition().copy();
 
				position.x += level.getPaddle().getCollisionShape().getWidth()/2 - 10;
				position.y -= 20;
				
 
				ball.setPosition( position );
			}else{
				// set the normal state to the paddle
				level.getPaddle().setState(Paddle.PAD_STATE.NORMAL);
				// add movement and direction to the ball
				ball.setSpeed(0.5f);
 
				collisionManager.addCollidable(ball);
 
				Vector2f direction = new Vector2f(0,1);
				Random r = new Random();
 
				direction.add( r.nextInt(45) * (r.nextBoolean()? -1 : 1) );
				ball.setDirection(direction);
				// change the game state
				currentState = LEVEL_STATES.NORMAL_GAME;
 
				// TODO: Catalog this
				message = null;
			}
			break;}
		case NORMAL_GAME:{
			// update all balls
			List<Ball> removals = null;
 
			for(Ball ball : level.getBalls()){
				ball.update(gc, sbg, delta);
 
				if(ball.getPosition().y > gc.getHeight()){
					if(removals == null){
						removals = new ArrayList<Ball>();
					}
					removals.add(ball);
				}
			}
 
			if(removals != null){
				for(Ball ball : removals){
					level.getBalls().remove(ball);
 
					collisionManager.removeCollidable(ball);
				}
			}
			
			//update all powerups
			List<PowerUp> puRemovals = null;
 
			for(PowerUp powerUp : level.getPowerUps()){
				powerUp.update(gc, sbg, delta);
 
				if(powerUp.getPosition().y > gc.getHeight()){
					if(puRemovals == null){
						puRemovals = new ArrayList<PowerUp>();
					}
					puRemovals.add(powerUp);
				}
			}
			
			if(puRemovals != null){
				for(PowerUp pu : puRemovals){
					level.getPowerUps().remove(pu);
 
					collisionManager.removeCollidable(pu);
				}
			}
 
			// perform collisions
			collisionManager.processCollisions();
 
			// check for bricks left
			if(level.getBricks().size() == 0){
				currentState = LEVEL_STATES.LEVEL_OVER;
				counter = 3000;
			}
 
			// check if ball drops from screen
			if(level.getBalls().size() == 0){
				currentState = LEVEL_STATES.LIFE_LOST;
			}
 
			break;}
		case LIFE_LOST:
			playerInfo.decrementLives();
 
			if(playerInfo.getLives() == 0){
				currentState = LEVEL_STATES.GAME_OVER;
				counter = 3000;
			}else{
				currentState = LEVEL_STATES.BALL_LAUNCH;
				level.getPaddle().setState(PAD_STATE.STICKY);
			}
			break;
		case LEVEL_OVER:
			if(counter == 3000){
				gc.getInput().removeMouseListener(level.getPaddle());
				message = "CONGRATULATIONS!\nLevel completed!";
			}
			counter -= delta;
 
			if(counter < 0){
				// jump to level selection screen
				sbg.enterState(2);
			}
			break;
		case GAME_OVER:
			if(counter == 3000){
				gc.getInput().removeMouseListener(level.getPaddle());
				message = "GAME OVER\nSCORE :" + GameInfo.getCurrentGameInfo().getPlayerInfo().getScore();
			}
			counter -= delta;
 
			if(counter < 0){
				sbg.enterState(0);
			}
			break;
 
		}
	}
 
	public void setLevelFile(String file){
		levelFile = file;
	}
 
}