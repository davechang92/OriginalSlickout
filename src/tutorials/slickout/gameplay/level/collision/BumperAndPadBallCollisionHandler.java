package tutorials.slickout.gameplay.level.collision;
 
import java.util.Random;
 
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
 
import tutorials.slickout.gameplay.collision.ICollisionHandler;
import tutorials.slickout.gameplay.level.Ball;
import tutorials.slickout.gameplay.level.ICollidableObject;
 
public class BumperAndPadBallCollisionHandler implements ICollisionHandler {
 
	private Random r ;
 
	public BumperAndPadBallCollisionHandler(){
		r = new Random();
	}
 
	@Override
	public int getCollider1Type() {
		return 1;
	}
 
	@Override
	public int getCollider2Type() {
		return 2;
	}
 
	@Override
	public void performCollision(ICollidableObject collidable1,
			ICollidableObject collidable2) {
 
		// check to see if collision is still applicable
		// sometimes the collision may be resolved by other handlers somehow
		if(!collidable1.isCollidingWith(collidable2)){
			return;
		}
 
		Ball ball = null;
		ICollidableObject object = null;
 
		// Cast the correct objects		
		if(collidable1 instanceof Ball){
			ball = (Ball) collidable1;
			object = collidable2;
		}else{
			ball = (Ball) collidable2;
			object = collidable1;
		}
 
		// obtain a copy of the direction
		Vector2f direction = ball.getDirection().copy(); 
		// reverse it
		direction.set(direction.x*-1, direction.y*-1);
 
		// backtrack the position of the ball until it no longer collides with
		// the paddle/brick
		do {
			Vector2f pos = ball.getPosition();
			ball.setPosition(new Vector2f( pos.x + direction.x, pos.y - direction.y) );
		}while(ball.isCollidingWith(object));
 
		// obtain the shapes of the objects
		Shape ballShape 	= ball.getCollisionShape();
		Shape objectShape   = object.getCollisionShape();
 
		// obtain a fresh direction of the ball
		direction = ball.getDirection().copy(); 
 
		// define the new direction
		if(ballShape.getMinY() > objectShape.getMaxY() || ballShape.getMaxY() < objectShape.getMinY()){
			direction.set(direction.x, -direction.y);
		}else direction.set(-direction.x, direction.y);
 
		// set the new ball direction
		ball.setDirection(direction);
	}
 
}