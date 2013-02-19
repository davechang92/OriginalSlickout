package tutorials.slickout.dda.sensor;

import tutorials.slickout.gameplay.level.collision.BrickBallCollisionHandler;
import tutorials.slickout.gameplay.level.collision.PadAndPowerUpCollisionHandler;

public class SensorFactory {
	
	
	public AbstractSensor createSensor(String name, Object objectToBeMonitored){
		if(name.equals("PowerUpCollection")){
			PowerUpCollectionSensor puCollectionSensor = new PowerUpCollectionSensor((PadAndPowerUpCollisionHandler) objectToBeMonitored);
			return puCollectionSensor;
		}else if(name.equals("PowerUpProduction")){
			PowerUpProductionSensor puProductionSensor = new PowerUpProductionSensor((BrickBallCollisionHandler) objectToBeMonitored);
			return puProductionSensor;
		}
		return null;
	}
	
}