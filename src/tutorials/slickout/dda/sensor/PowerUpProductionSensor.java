package tutorials.slickout.dda.sensor;

import tutorials.slickout.gameplay.level.collision.BrickBallCollisionHandler;

public class PowerUpProductionSensor extends AbstractSensor {

	BrickBallCollisionHandler brickBallHandler;
	
	public PowerUpProductionSensor(String name,BrickBallCollisionHandler brickBallHandler, long period){
		super(name, period);
		this.brickBallHandler = brickBallHandler;
	}
	
	@Override
	public void refreshValue() {
		value = brickBallHandler.getPowerUpsProduced();	
	}

}
