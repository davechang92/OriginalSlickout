package tutorials.slickout.dda.observer;

import java.util.Observable;
import java.util.Observer;

public class LifeAndPaddleObserver implements Observer {
	
	int livesLost;
	int padHit;
	
	public void getData(){
		
	}

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
