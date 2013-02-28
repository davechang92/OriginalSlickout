package tutorials.slickout.dda.sensor;

import tutorials.slickout.gameplay.level.collision.BrickBallCollisionHandler;


public class BricksHitSensor extends AbstractSensor {
	
	BrickBallCollisionHandler brickBallHandler;
	
	public BricksHitSensor( String name, BrickBallCollisionHandler brickBallHandler){
		super(name);
		this.brickBallHandler = brickBallHandler;
		value = 0;
	}
	
	@Override
	public void refreshValue(){
		if(brickBallHandler.getBricksHit() > (Integer) value){
			value = brickBallHandler.getBricksHit();
			setChanged();
			notifyObservers(value);
		}
	}
	
}

