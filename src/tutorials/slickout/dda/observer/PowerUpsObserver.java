package tutorials.slickout.dda.observer;

import java.util.Observable;

import tutorials.slickout.dda.sensor.PowerUpCollectionSensor;
import tutorials.slickout.dda.sensor.PowerUpProductionSensor;

public class PowerUpsObserver extends AbstractObserver {

	private int puType = 0;
	private int counter = 0;
	
	//if, out of the last 5 red pus produced, 3 were collected, increase probability of red pus
	@Override
	public void update(Observable sensor, Object value) {
		
		if(sensor.getClass().getSimpleName().equals("PowerUpProductionSensor")){
			puType = ((PowerUpProductionSensor) sensor).getPUType();
			if(puType % 2 == 0)	{
				
				System.out.println("red produced: "+puType);
			}
		}else if(sensor.getClass().getSimpleName().equals("PowerUpCollectionSensor")){
			puType = ((PowerUpCollectionSensor) sensor).getPUType();
			if(puType % 2 == 0)	{
				
				System.out.println("red collected: "+puType);
			}
		}

		
	}

}
