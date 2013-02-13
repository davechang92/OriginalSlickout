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
		
		switch (pu.getPowerType()){
			case 1: System.out.println("1");
					paddle.setAnimation("data/padanimation150.png", 150, 20, 1000);
					paddle.setCollisionShape(new Rectangle(0, 0, 150, 20));
					Timer resetPaddleTimer = new Timer();
					resetPaddleTimer.schedule(new timerTask(paddle), pu.getDuration());
					break;
			case 2: System.out.println("2");
					break;
		}
		
	}
	
	//class used to reset after powerup expires
	class timerTask extends TimerTask{
		
		Paddle pad;
		public timerTask(Paddle pad){
			this.pad = pad;
		}
		
		@Override
		public void run() {
			try {
				pad.setAnimation("data/padanimation.png", 100, 20, 1000);
				pad.setCollisionShape(new Rectangle(0,0,100,20));
			} catch (SlickException e) {
				System.out.println("resetting pad animaton failed");
				e.printStackTrace();
			}
		}
		
	}

}
