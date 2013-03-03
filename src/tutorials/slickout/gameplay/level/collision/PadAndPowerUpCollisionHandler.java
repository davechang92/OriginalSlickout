package tutorials.slickout.gameplay.level.collision;
 
import java.util.Timer;
import java.util.TimerTask;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import tutorials.slickout.gameplay.collision.CollisionManager;
import tutorials.slickout.gameplay.collision.ICollisionHandler;
import tutorials.slickout.gameplay.level.Ball;
import tutorials.slickout.gameplay.level.ICollidableObject;
import tutorials.slickout.gameplay.level.ILevel;
import tutorials.slickout.gameplay.level.Paddle;
import tutorials.slickout.gameplay.level.PowerUp;

public class PadAndPowerUpCollisionHandler implements ICollisionHandler {

	
	private ILevel level;
	private CollisionManager manager;
	
	Timer enlargenPaddleTimer = new Timer();
	Timer shrinkPaddleTimer = new Timer();
	Timer increaseBallSpeedTimer = new Timer();
	Timer decreaseBallSpeedTimer = new Timer();
	//used for sensor to detect change
	int totalPowerUpsCollected = 0;
	int lastPUType = 0;
	
	public PadAndPowerUpCollisionHandler(ILevel level, CollisionManager manager) {
		this.level = level;
		this.manager = manager;
	}

	//paddle
	@Override
	public int getCollider1Type() {
		return 1;
	}

	//powerup
	@Override
	public int getCollider2Type() {
		return 4;
	}
	
	public int getTotalPowerUpsCollected(){
		return totalPowerUpsCollected;
	}

	// check to see if collision is still applicable
	// sometimes the collision may be resolved by other handlers somehow
	@Override
	public void performCollision(ICollidableObject collidable1, ICollidableObject collidable2) throws SlickException {
		if (!collidable1.isCollidingWith(collidable2)) {
			return;
		}
		
		PowerUp pu = null;
		Paddle paddle = null;
		
		// Cast the correct objects		

		if (collidable1 instanceof PowerUp) {
			pu = (PowerUp) collidable1;
			paddle = (Paddle) collidable2;
		} else {
			pu = (PowerUp) collidable2;
			paddle = (Paddle) collidable1;
		}
		
		lastPUType = pu.getPowerType();
		totalPowerUpsCollected++;


		// obtain a copy of the direction
		Vector2f direction = pu.getDirection().copy();
		// reverse it
		direction.set(direction.x * -1, direction.y * -1);
		
		// backtrack the position of the powerup until it no longer collides with
		// the paddle/brick
		do {
			Vector2f pos = pu.getPosition();
			pu.setPosition(new Vector2f(pos.x + direction.x, pos.y - direction.y));
		} while (pu.isCollidingWith(paddle));

		//remove powerup from level and collision manager
		level.getPowerUps().remove(pu);
		manager.removeCollidable(pu);
 
		//POWER UP TYPE SPECIFIC HANDLING HAPPENS HERE
		
		switch (pu.getPowerType()){
			case 1: //System.out.println("1");
					paddle.setAnimation("data/padanimation150.png", 150, 20, 1000);//enlarge paddle
					paddle.setCollisionShape(new Rectangle(0, 0, 150, 20));
					try {
						enlargenPaddleTimer.cancel();
						enlargenPaddleTimer.purge();
						enlargenPaddleTimer = new Timer();
					} catch (IllegalStateException e) {
						//do nothing when exception caught (just means that timer hasn't been scheduled yet)
					}
					enlargenPaddleTimer.schedule(new timerTask(1), pu.getDuration());
					
					break;
			case 2:// System.out.println("speed up collected");
					for(Ball ball: level.getBalls()){	//increase ball speed
						ball.increaseSpeed(0.25f);
						decreaseBallSpeedTimer.schedule(new timerTask(2), pu.getDuration());
					}
					break;
			case 3:// System.out.println("slow down collected");
					//decrease ball speed ( as long as decrease wouldn't result in stationary ball)
					boolean decreased = false;
					for(Ball ball: level.getBalls()){	
						if(ball.getSpeed() > 0.25f){
							ball.decreaseSpeed(0.25f);
							decreased = true;
						}
					}
					if(decreased)
						increaseBallSpeedTimer.schedule(new timerTask(3), pu.getDuration());
					break;
			case 4://decrease paddlesize
				paddle.setAnimation("data/padanimation50.png", 50, 20, 1000);//shrink paddle
				paddle.setCollisionShape(new Rectangle(0, 0, 50, 20));
				try {
					shrinkPaddleTimer.cancel();
					shrinkPaddleTimer.purge();
					shrinkPaddleTimer = new Timer();
				} catch (IllegalStateException e) {
					//do nothing when exception caught (just means that timer hasn't been scheduled yet)
				}
				shrinkPaddleTimer.schedule(new timerTask(1), pu.getDuration());
					
		}
		
	}
	
	//class used to reset after powerup expires
	class timerTask extends TimerTask{
		

		int type;
		
		public timerTask(int type){
			this.type = type;
		}
		
		@Override
		public void run() {
			try {
				switch(type){
					//resets paddle to normal size after enlargening or shrinking
					case 1:	Paddle pad = level.getPaddle();
							pad.setAnimation("data/padanimation.png", 100, 20, 1000);
							pad.setCollisionShape(new Rectangle(0,0,100,20));
							break;
					//decreases ball speed after a speed-up	
					case 2: for(Ball ball: level.getBalls()){
								//System.out.println("speed decreased");
								ball.setSpeed(0.5f);
							}
							break;
					//increases ball speed after a slow down
					case 3:for(Ball ball: level.getBalls()){
								//System.out.println("speed increased");
								ball.setSpeed(0.5f);
							}
							break;
				}
			} catch (SlickException e) {
				System.out.println("resetting after powerup of type " + type + " failed");
				e.printStackTrace();
			}
		}
		
	}

	public int getLastPUType(){
		return lastPUType;
	}
}
