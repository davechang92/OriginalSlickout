package tutorials.slickout.dda.sensor;

import tutorials.slickout.gameplay.level.collision.BrickBallCollisionHandler;


public class BricksHitSensor extends AbstractSensor {
	
	BrickBallCollisionHandler brickBallHandler;
	
	public BricksHitSensor( BrickBallCollisionHandler brickBallHandler){
		super();
		this.brickBallHandler = brickBallHandler;
	}
	
	@Override
	public void refreshValue(){
		value = brickBallHandler.getBricksHit();
	}
	
}

