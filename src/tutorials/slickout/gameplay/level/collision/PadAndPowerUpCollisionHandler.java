package tutorials.slickout.gameplay.level.collision;
 
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import tutorials.slickout.gameplay.collision.ICollisionHandler;
import tutorials.slickout.gameplay.level.Ball;
import tutorials.slickout.gameplay.level.ICollidableObject;
import tutorials.slickout.gameplay.level.ILevel;
import tutorials.slickout.gameplay.level.Paddle;
import tutorials.slickout.gameplay.level.PowerUp;

public class PadAndPowerUpCollisionHandler implements ICollisionHandler {
	//private ILevel levelData;

	public PadAndPowerUpCollisionHandler() {
		//levelData = level;
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
		ICollidableObject object = null;
		
		// Cast the correct objects		

		if (collidable1 instanceof PowerUp) {
			pu = (PowerUp) collidable1;
			object = collidable2;
		} else {
			pu = (PowerUp) collidable2;
			object = collidable1;
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
		} while (pu.isCollidingWith(object));
		
		// obtain the shapes of the objects
		Shape puShape = pu.getCollisionShape();
		Shape objectShape = object.getCollisionShape();

		/* obtain a fresh direction of the ball
		direction = pu.getDirection().copy();

		// define the new direction
		if (ballShape.getMinY() > objectShape.getMaxY() || ballShape.getMaxY() < objectShape.getMinY()) {
			direction.set(direction.x, -direction.y);
		} else {
			direction.set(-direction.x, direction.y);
		}*/

		if (object instanceof Paddle) {
			System.out.println("BANG");
			//levelData.playBop();
		} else {
			//levelData.playBump();
		}
	}

}
