package tutorials.slickout.dda.observer;

import java.util.Observable;

import tutorials.slickout.dda.Adaptation;
import tutorials.slickout.dda.sensor.PowerUpCollectionSensor;
import tutorials.slickout.dda.sensor.PowerUpProductionSensor;

public class PowerUpsObserver extends AbstractObserver {

	private int puType = 0;
	private int producedCounter = 0;
	private int collectedCounter = 0;
	
	//if, out of the last 5 red pus produced
	//		3 or more were collected, increase probability of red pus
	//		1 or less were collected, decrease probability
	@Override
	public void update(Observable sensor, Object value) {
		
		if(sensor.getClass().getSimpleName().equals("PowerUpProductionSensor")){
			puType = ((PowerUpProductionSensor) sensor).getPUType();
			if(puType % 2 == 0)	{
				producedCounter++;
				System.out.println("produced: "+producedCounter);
			}
		}else if(sensor.getClass().getSimpleName().equals("PowerUpCollectionSensor")){
			puType = ((PowerUpCollectionSensor) sensor).getPUType();
			if(puType % 2 == 0)	{
				collectedCounter++;
				System.out.println("collected: "+collectedCounter);
			}
		}
		
		if(producedCounter > 5){
			if(collectedCounter >= 3){
				try {
					adaptations.add(new Adaptation("IncreaseRedPowerUps"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("IncreaseRedPowerUps Adaptation added");	
			}
			producedCounter = 0;
			collectedCounter = 0;
		}

		
	}

}
