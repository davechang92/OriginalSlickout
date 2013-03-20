package tutorials.slickout.gameplay;
 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
 
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
 
import tutorials.slickout.GameInfo;
import tutorials.slickout.dda.AdaptationDriver;
import tutorials.slickout.dda.observer.LifeAndPaddleObserver;
import tutorials.slickout.dda.observer.PaddleAndBricksObserver;
import tutorials.slickout.dda.observer.PowerUpsObserver;
import tutorials.slickout.dda.sensor.AbstractSensor;
import tutorials.slickout.dda.sensor.SensorFactory;
import tutorials.slickout.gameplay.collision.CollisionManager;
import tutorials.slickout.gameplay.level.AnimationObject;
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
 
	//used to display a  message for 3 seconds when level complete or game over
	private int counter = 0;
	private String message = null;
 
	private static enum LEVEL_STATES { BALL_LAUNCH, NORMAL_GAME, LIFE_LOST, LEVEL_OVER, GAME_OVER };
 
	private LEVEL_STATES currentState;
 
	private CollisionManager collisionManager;
	
	private AbstractSensor powerUpCollectionSensor;
	private AbstractSensor powerUpProductionSensor;
	private AbstractSensor livesLostSensor;
	private AbstractSensor bricksHitSensor;
	private AbstractSensor paddleHitSensor;
	
	private AdaptationDriver adaptationDriver;
	
	private PaddleAndBricksObserver pbo;
	private PowerUpsObserver puo;
	private LifeAndPaddleObserver lpo;
	
	private int timePlayed = 0;
	
	private String logfilepath = "output/logfile.csv";
	private String adaptationLogfilepath = "output/adtaptationLog.txt";
	
	Logger logger = new Logger();
	
	//boolean used to decide whether dda will be in this game or not
	boolean dda;
	
	public GameplayState(boolean dda){
		super();
		this.dda = dda;
	}
 
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
 
		gc.setMouseGrabbed(true);
		
		timePlayed=0;
		
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
		
		BumperAndPadBallCollisionHandler bumperPadBallHandler = new BumperAndPadBallCollisionHandler();
		PadAndPowerUpCollisionHandler padPowerUpHandler = new PadAndPowerUpCollisionHandler(level, collisionManager);
		BrickBallCollisionHandler brickBallHandler = new BrickBallCollisionHandler(level, collisionManager);
		
		collisionManager.addHandler(bumperPadBallHandler);
		collisionManager.addHandler(brickBallHandler);
		collisionManager.addHandler(padPowerUpHandler);
 
		gc.getInput().addMouseListener(level.getPaddle());
 
		currentState = LEVEL_STATES.BALL_LAUNCH;
 
		playerInfo = GameInfo.getCurrentGameInfo().getPlayerInfo();
		
		//initial log
		logger.openingLog();
		
		
		//dda stuff
			
			pbo = new PaddleAndBricksObserver();
			puo = new PowerUpsObserver(level.getNumOfPowerTypes());
			lpo = new LifeAndPaddleObserver();
			
			SensorFactory factory = new SensorFactory(level);
			
			powerUpCollectionSensor = factory.createSensor("PowerUpCollection", padPowerUpHandler);
			powerUpCollectionSensor.addObserver(puo);
			
			powerUpProductionSensor = factory.createSensor("PowerUpProduction", level);
			powerUpProductionSensor.addObserver(puo);

			livesLostSensor = factory.createSensor("LivesLost", playerInfo); 
			livesLostSensor.addObserver(lpo);
			
			bricksHitSensor = factory.createSensor("BricksHit", brickBallHandler);
			bricksHitSensor.addObserver(pbo);
			
			paddleHitSensor = factory.createSensor("PaddleHit", bumperPadBallHandler);
			paddleHitSensor.addObserver(pbo);
			paddleHitSensor.addObserver(lpo);
			
			if(dda){
				adaptationDriver = new AdaptationDriver(level, collisionManager, adaptationLogfilepath);

				adaptationDriver.getObservers().add(pbo);
				adaptationDriver.getObservers().add(puo);
				adaptationDriver.getObservers().add(lpo);
			}
			
	}
 
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gr)
			throws SlickException {
 
 
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
		
		//pu collection explosions
		for( AnimationObject explosion : level.getExplosions()){
			explosion.render(gr);
		}
 
		//gr.drawString("Lives: " + playerInfo.getLives(), 700, 10);
		gr.drawString("Score: " + playerInfo.getScore(), 500, 10);
 
		if(message != null){
			//gr.drawString(message, 300, 300);
		}
		
		//draws sensor values at bottom of screen
		gr.drawString("Power Ups Collected: "+ powerUpCollectionSensor.getValue() + " / " + powerUpProductionSensor.getValue(), 25,670);
		gr.drawString("Lives Lost: "+ livesLostSensor.getValue(), 25, 700);
		gr.drawString("Bricks hit: "+ bricksHitSensor.getValue(), 25, 730);
		gr.drawString("Paddle hit: "+ paddleHitSensor.getValue(), 300, 670);
		gr.drawString("PowerUp P: "+ level.getPowerUpP(), 300, 700);
		gr.drawString("ExtraRed P: "+ level.getExtraRedP(), 300, 730);
		gr.drawString("ExtraYellow P: "+ level.getExtraYellowP(), 300, 760);
		gr.drawString("Enlarge paddle collected: " + puo.getcollected().get(1)+ " / " +puo.getProduced().get(1), 500,670 );
		gr.drawString("Speed up collected: " + puo.getcollected().get(2) + " / " + puo.getProduced().get(2), 500,700 );
		gr.drawString("Slow down collected: " + puo.getcollected().get(3)+ " / " +puo.getProduced().get(3), 500,730 );
		gr.drawString("Shrink paddle collected: " + puo.getcollected().get(4)+ " / " +puo.getProduced().get(4), 500,760 );

		//long currentTime = System.currentTimeMillis();
		//gr.drawString("Time since start: " + (Math.round((currentTime - startTime)/100)), 450, 780);

	}
 
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		timePlayed+=delta;
		
		//allows game quit with escape key
		Input input = gc.getInput();
		if(input.isKeyDown(Input.KEY_ESCAPE))
        {
			logger.closingLog(false);
			sbg.enterState(0);
        }
 
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
							 
				if(ball.getPosition().y > level.getHeight() || ball.getPosition().y < 0 || ball.getPosition().x < 0 || ball.getPosition().x > level.getWidth()){
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
 
				if(powerUp.getPosition().y > level.getHeight()){
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
			
			//check to see if any explosions have finished their animations; if so, remove them from the level
			List<AnimationObject> expRemovals = null;
			
			for(AnimationObject explosion: level.getExplosions()){
				if(explosion.getAnimation().isStopped()){
					if(expRemovals == null){
						expRemovals = new ArrayList<AnimationObject>();
					}
					expRemovals.add(explosion);
				}
			}
			
			if(expRemovals!=null){
				for(AnimationObject explosion: expRemovals){
					level.getExplosions().remove(explosion);
				}
			}
			
			//implement any adaptations needed
			if(dda){
				adaptationDriver.processAdaptations(delta);
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
			playerInfo.lifeLost();
			playerInfo.decreaseScore(1000);
 
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
				
				logger.closingLog(true);
				
				// jump to enter high score screen
				sbg.enterState(4);
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
 
	public class Logger{
		
		//log of player data that happens when game opens
		public void openingLog(){
			BufferedWriter writer = null;
			try
			{
				writer = new BufferedWriter( new FileWriter( logfilepath, true));
				writer.write("\n" + playerInfo.getName() + ",");
				writer.write(new Date().toString() + ",");
				writer.write(dda + ",");
				
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
			
			BufferedWriter writer2 = null;
			try
			{
				writer2 = new BufferedWriter( new FileWriter( adaptationLogfilepath, true));
				writer2.write("\n" + playerInfo.getName() + "\n");
				writer2.write(new Date().toString() + "\n");
				
			}
			catch ( IOException e)
			{
			}
			finally
			{
				try
				{
					if ( writer2 != null)
						writer2.close( );
				}
				catch ( IOException e)
				{
				}
		     }
		}
		
		//log of player data that happens when game opens
		public void closingLog(boolean complete){
			
			BufferedWriter writer = null;
			try
			{
				writer = new BufferedWriter( new FileWriter( logfilepath, true));
				writer.write(complete + ",");
				writer.write(timePlayed/1000 + ",");
				writer.write(powerUpCollectionSensor.getValue() + ",");
				writer.write((puo.getProduced().get(1) +puo.getProduced().get(2) +puo.getProduced().get(3) +puo.getProduced().get(4)) + ",");
				writer.write(livesLostSensor.getValue()+ ",");
				writer.write(bricksHitSensor.getValue()+ ",");
				writer.write(paddleHitSensor.getValue()+ ",");
				writer.write(level.getPowerUpP()+ ",");
				writer.write( level.getExtraRedP()+ ",");
				writer.write(level.getExtraYellowP()+ ",");
				writer.write(puo.getcollected().get(1)+ "," );
				writer.write(puo.getProduced().get(1)+ "," );
				writer.write(puo.getcollected().get(2) +  "," );
				writer.write(puo.getProduced().get(2)+ "," );
				writer.write( puo.getcollected().get(3)+  ",");
				writer.write(puo.getProduced().get(3)+ ",");
				writer.write( puo.getcollected().get(4)+  "," );
				writer.write(puo.getProduced().get(4) + "" );

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
		}
		
	}
	
}