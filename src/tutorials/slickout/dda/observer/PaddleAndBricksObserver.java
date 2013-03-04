package tutorials.slickout.dda.observer;

import java.util.Observable;

import tutorials.slickout.dda.Adaptation;

public class PaddleAndBricksObserver extends AbstractObserver {
	
	//used to store total num of brick and pad hits
	int totalBricksHit=0;
	int lastTotalBricksHit =0;
	int padHit=0;
	//the num of bricks hit every 3 paddle hits
	int bricksDiff = 0;
		
	public int getBricksHit(){
		return totalBricksHit;
	}
	
	public int getPadHit(){
		return padHit;
	}

	//turns on adaptations when pad hit > twice with no bricks hit. the resulting adaptation is power-up rain.
	@Override
	public void update(Observable sensor, Object value) {
		
		if(sensor.getClass().getSimpleName().equals("BricksHitSensor")){
			totalBricksHit = (Integer) value;
		}else if(sensor.getClass().getSimpleName().equals("PaddleHitSensor")){
			padHit = (Integer) value;

			//if, after 2 pad hits, no bricks were hit, then turn on pu rain
			if((padHit%2)==0){
				bricksDiff = totalBricksHit - lastTotalBricksHit;
				lastTotalBricksHit = totalBricksHit;
				if(bricksDiff==0)
					try {
						adaptations.add(new Adaptation("PowerUpRain"));
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
		

	}

}
