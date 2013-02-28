package tutorials.slickout.dda.observer;

import java.util.Observable;
import java.util.Observer;

public class LifeAndPaddleObserver extends AbstractObserver {
	
	int livesLost;
	int padHit;

	@Override
	public void update(Observable sensor, Object value) {
		//if(sensor. instanceof PaddleHitSensor)
		if(sensor.getClass().getSimpleName().equals("PaddleHitSensor")){
			padHit = (Integer) value;
		}else if(sensor.getClass().getSimpleName().equals("LivesLostSensor")){
			livesLost = (Integer) value;
		}
		
		
		

	}

}
