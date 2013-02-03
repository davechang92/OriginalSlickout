package tutorials.slickout.gameplay.level.collision;

import org.newdawn.slick.SlickException;

import tutorials.slickout.gameplay.collision.CollisionManager;
import tutorials.slickout.gameplay.collision.ICollisionHandler;
import tutorials.slickout.gameplay.level.ICollidableObject;
import tutorials.slickout.gameplay.level.ILevel;

public class PadAndPowerUpCollisionHandler implements ICollisionHandler {

	
	private ILevel levelData;
	private CollisionManager manager;
	@Override
	public int getCollider1Type() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getCollider2Type() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void performCollision(ICollidableObject collidable1,
			ICollidableObject collidable2) throws SlickException {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
