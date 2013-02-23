package tutorials.slickout.dda.sensor;

import tutorials.slickout.gameplay.level.collision.BrickBallCollisionHandler;


public class BricksHitSensor extends AbstractSensor {
	
	BrickBallCollisionHandler brickBallHandler;
	
	public BricksHitSensor( String name, BrickBallCollisionHandler brickBallHandler, long period){
		super(name, period);
		this.brickBallHandler = brickBallHandler;
	}
	
	@Override
	public void refreshValue(){
		value = brickBallHandler.getBricksHit();
	}
	
}

