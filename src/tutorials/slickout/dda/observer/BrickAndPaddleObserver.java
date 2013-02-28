package tutorials.slickout.dda.observer;

import java.util.Observable;

public class BrickAndPaddleObserver extends AbstractObserver {
	
	int bricksHit;
	int padHit;
	
	public int getBricksHit(){
		return bricksHit;
	}
	
	public int getPadHit(){
		return padHit;
	}

	@Override
	public void update(Observable sensor, Object value) {
		//if(sensor. instanceof PaddleHitSensor)
		if(sensor.getClass().getSimpleName().equals("BricksHitSensor")){
			bricksHit = (Integer) value;
			//System.out.println("hit brick "+bricksHit);
		}else if(sensor.getClass().getSimpleName().equals("PaddleHitSensor")){
			padHit = (Integer) value;
			//System.out.println("hit pad "+padHit);

		}
		
		
		

	}

}
