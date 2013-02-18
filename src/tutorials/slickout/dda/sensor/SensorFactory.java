package tutorials.slickout.dda.sensor;

public class SensorFactory {

	
	public void createSensor(String name, Object objectToBeMonitored){
		if(name.equals("PowerUpCollectionSensor")){
			PowerUpCollectionSensor puSensor = new PowerUpCollectionSensor();
		}
	}
	
}
