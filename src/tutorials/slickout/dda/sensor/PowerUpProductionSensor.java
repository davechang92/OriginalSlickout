package tutorials.slickout.dda.sensor;

import tutorials.slickout.gameplay.level.ILevel;
import tutorials.slickout.gameplay.level.collision.BrickBallCollisionHandler;

public class PowerUpProductionSensor extends AbstractSensor {

	private int puType = 0;
	private ILevel level;
	
	public PowerUpProductionSensor(String name, ILevel level){
		super(name);
		value = 0;
		this.level = level;
	}
	
	public int getPUType(){
		return puType;
	}
	
	@Override
	public void refreshValue() {
		if(level.getPowerUpsProduced() > (Integer) value){
			value = level.getPowerUpsProduced();	
			puType = level.getLastPowerUpProducedType();
			setChanged();
			notifyObservers(value);
		}
	}

}
