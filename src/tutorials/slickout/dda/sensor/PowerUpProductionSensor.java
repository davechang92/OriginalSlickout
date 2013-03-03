package tutorials.slickout.dda.sensor;

import tutorials.slickout.gameplay.level.collision.BrickBallCollisionHandler;

public class PowerUpProductionSensor extends AbstractSensor {

	BrickBallCollisionHandler brickBallHandler;
	
	public PowerUpProductionSensor(String name,BrickBallCollisionHandler brickBallHandler){
		super(name);
		this.brickBallHandler = brickBallHandler;
		value = 0;
	}
	
	@Override
	public void refreshValue() {
		if(brickBallHandler.getPowerUpsProduced() > (Integer) value){
			value = brickBallHandler.getPowerUpsProduced();	
			setChanged();
			notifyObservers(value);
		}
	}

}
