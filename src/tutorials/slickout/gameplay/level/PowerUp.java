package tutorials.slickout.gameplay.level;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class PowerUp extends CollidableImageObject {

	private int powerType;
	

	protected float speed;
	protected Vector2f direction;
 
	public PowerUp(String name, 
				Image image, 
				Vector2f position, 
				float speed, 
				Vector2f initialDirection,
				Shape collisionShape, 
				int collisionType,
				int powerType) {
		super(name, image, position, collisionShape, collisionType);
 
		this.speed = speed;
		this.direction = initialDirection.copy();
		this.powerType = powerType;
	}
 
	public void setDirection(Vector2f direction){
		this.direction = direction.copy();
	}
 
	public Vector2f getDirection(){
		return direction;
	}
 
	public void setSpeed(float speed){
		speed = speed;
	}
 
	public float getSpeed(){
		return speed;
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		position.x += direction.x * delta * speed;
		position.y -= direction.y * delta * speed;
	}
	
}
