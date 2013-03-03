package tutorials.slickout.dda.sensor;

import tutorials.slickout.gameplay.level.ILevel;
import tutorials.slickout.gameplay.level.collision.BrickBallCollisionHandler;

public class PowerUpProductionSensor extends AbstractSensor {

	BrickBallCollisionHandler brickBallHandler;
	private int puType = 0;
	private ILevel level;
	
	public PowerUpProductionSensor(String name,BrickBallCollisionHandler brickBallHandler, ILevel level){
		super(name);
		this.brickBallHandler = brickBallHandler;
		value = 0;
		this.level = level;
	}
	
	public int getPUType(){
		return puType;
	}
	
	@Override
	public void refreshValue() {
		if(brickBallHandler.getPowerUpsProduced() > (Integer) value){
			value = brickBallHandler.getPowerUpsProduced();	
			puType = level.getLastPowerUpProducedType();
			setChanged();
			notifyObservers(value);
		}
	}

}
