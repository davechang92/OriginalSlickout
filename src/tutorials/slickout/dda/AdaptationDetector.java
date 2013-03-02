package tutorials.slickout.dda;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import tutorials.slickout.dda.observer.AbstractObserver;
import tutorials.slickout.dda.observer.LifeAndPaddleObserver;
import tutorials.slickout.dda.sensor.AbstractSensor;
import tutorials.slickout.gameplay.level.ILevel;

public class AdaptationDetector {
	
	int padHit=0;
	int bricksHit=0;
	int lastBricksTotal = 0;
	int padHitTotal = 0;
	List<AbstractObserver> observers = new ArrayList<AbstractObserver>();
	Driver driver;
	
	public AdaptationDetector(ILevel level){
		driver = new Driver(level);
	}
	
	public List<AbstractObserver> getObservers(){
		return observers;
	}
	
	public void start(){
		driver.start();
	}
	


	public void detectAdaptations() {

		//get values from observers
		for(AbstractObserver observer: observers){
			System.out.println(observer.getClass().getSimpleName());

			
			if(observer.getClass().getSimpleName().equals("LifeAndPaddleObserver")){
				
			}else if(observer.getClass().getSimpleName().equals("PaddleAndBricksObserver")){
				System.out.println("A");
				if(observer.getAdaptations()==1){
					driver.setConstPowerUp(true);
					System.out.println("B");

				}

			}
		}
	}
	
}
