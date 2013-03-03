package tutorials.slickout.dda.observer;

import java.util.Observable;

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

	//
	@Override
	public void update(Observable sensor, Object value) {
		
		if(sensor.getClass().getSimpleName().equals("BricksHitSensor")){
			totalBricksHit = (Integer) value;
			//System.out.println("hit brick "+bricksHit);
		}else if(sensor.getClass().getSimpleName().equals("PaddleHitSensor")){
			padHit = (Integer) value;
			System.out.println("observer adaptations "+adaptations);

			//System.out.println("hit pad "+padHit);
			//if, after 2 pad hits, no bricks were hit, then turn on pu rain
			if((padHit%2)==0){
				bricksDiff = totalBricksHit - lastTotalBricksHit;
				lastTotalBricksHit = totalBricksHit;
				if(bricksDiff==0)
					adaptations = 1;
				System.out.println("hit bricks "+bricksDiff);
			}
		}
		

	}

}
