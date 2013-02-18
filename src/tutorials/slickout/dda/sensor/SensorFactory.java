package tutorials.slickout.dda.sensor;

import tutorials.slickout.gameplay.level.collision.PadAndPowerUpCollisionHandler;

public class SensorFactory {
	
	
	public ISensor createSensor(String name, Object objectToBeMonitored){
		if(name.equals("PowerUp")){
			PowerUpCollectionSensor puSensor = new PowerUpCollectionSensor((PadAndPowerUpCollisionHandler) objectToBeMonitored);
			return puSensor;
		}
		return null;
	}
	
}