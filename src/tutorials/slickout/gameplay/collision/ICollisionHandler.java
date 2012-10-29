package tutorials.slickout.gameplay.collision;
 
import tutorials.slickout.gameplay.level.ICollidableObject;
 
public interface ICollisionHandler {
 
	public int getCollider1Type();
 
	public int getCollider2Type();
 
	public void performCollision(ICollidableObject collidable1, ICollidableObject collidable2);
}