package tutorials.slickout.gameplay.level;
 
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
 
public interface ICollidableObject {
 
	public void setPosition(Vector2f position);
 
	public Vector2f getPosition();
 
	public Shape getNormalCollisionShape();
 
	public Shape getCollisionShape();
 
	public int getCollisionType();
 
	public boolean isCollidingWith(ICollidableObject collidable);
}