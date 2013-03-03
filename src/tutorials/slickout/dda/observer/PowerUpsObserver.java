package tutorials.slickout.dda.observer;

import java.util.Observable;

import tutorials.slickout.dda.sensor.PowerUpCollectionSensor;

public class PowerUpsObserver extends AbstractObserver {

	int puType = 0;
	
	@Override
	public void update(Observable sensor, Object value) {
		
		puType = ((PowerUpCollectionSensor) sensor).getPUType();
		if(puType % 2 == 0)	
			System.out.println("red: "+puType);
		
	}

}
