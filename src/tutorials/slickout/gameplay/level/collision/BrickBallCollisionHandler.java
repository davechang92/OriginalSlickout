package tutorials.slickout.gameplay.level.collision;
 
import java.util.Random;
 
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
 
import tutorials.slickout.GameInfo;
import tutorials.slickout.gameplay.collision.CollisionManager;
import tutorials.slickout.gameplay.collision.ICollisionHandler;
import tutorials.slickout.gameplay.level.Ball;
import tutorials.slickout.gameplay.level.Brick;
import tutorials.slickout.gameplay.level.CollidableAnimationObject;
import tutorials.slickout.gameplay.level.CollidableImageObject;
import tutorials.slickout.gameplay.level.ICollidableObject;
import tutorials.slickout.gameplay.level.ILevel;
import tutorials.slickout.gameplay.level.PowerUp;
import tutorials.slickout.playerinfo.PlayerInfo;
 
public class BrickBallCollisionHandler implements ICollisionHandler {
 
	private Random r ;
 
	private ILevel levelData;
	private CollisionManager manager;
	
	//num of pus produced
	private int totalPowerUps = 0;
	//num of bricks hit
	private int bricksHit = 0;
 
	public BrickBallCollisionHandler(ILevel levelData, CollisionManager manager){
		r = new Random();
 
		this.levelData = levelData;
		this.manager = manager;
	}
 
	@Override
	public int getCollider1Type() {
		return 3;
	}
 
	@Override
	public int getCollider2Type() {
		return 2;
	}
 
	@Override
	public void performCollision(ICollidableObject collidable1,
			ICollidableObject collidable2) {
 
		bricksHit++;
		
		// check to see if collision is still applicable
		// sometimes the collision may be resolved by other handlers somehow
		if(!collidable1.isCollidingWith(collidable2)){
			return;
		}
 
		Ball ball = null;
		Brick brick = null;
 
		// Cast the correct objects		
		if(collidable1 instanceof Ball){
			ball = (Ball) collidable1;
			brick = (Brick) collidable2;
		}else{
			ball = (Ball) collidable2;
			brick = (Brick) collidable1;
		}
 
		// obtain a copy of the direction
		Vector2f direction = ball.getDirection().copy(); 
		// reverse it
		direction.set(direction.x*-1, direction.y*-1);
 
		// backtrack the position of the ball until it no longer collides with
		// the brick
		do {
			Vector2f pos = ball.getPosition();
			ball.setPosition(new Vector2f( pos.x + direction.x, pos.y - direction.y) );
		}while(ball.isCollidingWith(brick));
 
		// obtain the shapes of the objects
		Shape ballShape 	= ball.getCollisionShape();
		Shape objectShape   = brick.getCollisionShape();
 
		// obtain a fresh direction of the ball
		direction = ball.getDirection().copy(); 
 
		// define the new direction
		if(ballShape.getMinY() > objectShape.getMaxY() || ballShape.getMaxY() < objectShape.getMinY()){
			direction.set(direction.x, -direction.y);
		}else direction.set(-direction.x, direction.y);
 
		// add -10¼ to 10¼ degrees random to each bump in the bricks
		direction.add( r.nextInt(10) * (r.nextBoolean()? -1 : 1) );
 
		// set the new ball direction
		ball.setDirection(direction);
 
		// since the brick was hit, decrease the number of hits to be destroyed
		brick.decreaseHit();
 
		GameInfo.getCurrentGameInfo().getPlayerInfo().addScore(100);
 
		// if the brick has reached the last hit
		if(brick.getHitsLeft() == 0){
			// remove the brick from the level list
			levelData.getBricks().remove(brick);
			// remove it from the collision manager
			manager.removeCollidable(brick);
 
			GameInfo.getCurrentGameInfo().getPlayerInfo().addScore(250);
		}
		
		//according to probability of power up being created, a power-up may be created
		if(Math.random() <= levelData.getPowerUpP()){
			PowerUp pu = levelData.addPowerUp(brick.getPosition());
			
			manager.addCollidable(pu);
			
			totalPowerUps++;
		}
 
	}
	
	public int getPowerUpsProduced(){
		return totalPowerUps;
	}
	
	public int getBricksHit(){
		return bricksHit;
	}
}