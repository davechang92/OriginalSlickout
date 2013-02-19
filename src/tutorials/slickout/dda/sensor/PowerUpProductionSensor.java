package tutorials.slickout.dda.sensor;

import tutorials.slickout.gameplay.level.collision.BrickBallCollisionHandler;

public class PowerUpProductionSensor extends AbstractSensor {

	BrickBallCollisionHandler brickBallHandler;
	
	public PowerUpProductionSensor(BrickBallCollisionHandler brickBallHandler){
		super();
		this.brickBallHandler = brickBallHandler;
	}
	
	@Override
	public void refreshValue() {
		value = brickBallHandler.getPowerUpsProduced();	
	}

}
