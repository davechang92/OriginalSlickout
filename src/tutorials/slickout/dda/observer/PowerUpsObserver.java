package tutorials.slickout.dda.observer;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import tutorials.slickout.dda.Adaptation;
import tutorials.slickout.dda.sensor.PowerUpCollectionSensor;
import tutorials.slickout.dda.sensor.PowerUpProductionSensor;

public class PowerUpsObserver extends AbstractObserver {

	private int puType = 0;
	private int redProducedCounter = 0;
	private int redCollectedCounter = 0;
	private int yellowProducedCounter = 0;
	private int yellowCollectedCounter = 0;
	//maps of power ups produced and collected, by type
	private Map<Integer, Integer> produced;
	private Map<Integer, Integer> collected;
	
	public PowerUpsObserver(int numOfPuTypes){
		super();
		produced = new HashMap<Integer, Integer>();
		collected = new HashMap<Integer, Integer>();
		for(int i = 1; i <= numOfPuTypes; i++){
			produced.put(i, 0);
			collected.put(i, 0);
		}
	}

	
	//if, out of the last 5 red pus produced
	//		3 or more were collected, increase probability of red pus
	//		1 or less were collected, decrease probability
	//same applies for yellows
	@Override
	public synchronized void update(Observable sensor, Object value) {
		
		if(sensor.getClass().getSimpleName().equals("PowerUpProductionSensor")){
			puType = ((PowerUpProductionSensor) sensor).getPUType();
			if(puType % 2 == 0)	{
				redProducedCounter++;
			}else{
				yellowProducedCounter++;
			}
			produced.put(puType, produced.get(puType)+ 1);
		}else if(sensor.getClass().getSimpleName().equals("PowerUpCollectionSensor")){
			puType = ((PowerUpCollectionSensor) sensor).getPUType();
			if(puType % 2 == 0)	{
				redCollectedCounter++;
			}else{
				yellowCollectedCounter++;
			}
			collected.put(puType, collected.get(puType)+ 1);
		}
		
		if(redProducedCounter > 5){
			if(redCollectedCounter >= 3){
				try {
					adaptations.add(new Adaptation("IncreaseRedPowerUps"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			redProducedCounter = 0;
			redCollectedCounter = 0;
		}
		
		if(yellowProducedCounter > 5){
			if(yellowCollectedCounter >= 3){
				try {
					adaptations.add(new Adaptation("IncreaseYellowPowerUps"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			yellowProducedCounter = 0;
			yellowCollectedCounter = 0;
		}

		
	}
	
	public Map<Integer,Integer> getProduced(){
		return produced;
	}
	
	public Map<Integer,Integer> getcollected(){
		return collected;
	}

}
