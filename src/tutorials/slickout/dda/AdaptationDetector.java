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
	ILevel level;
	Driver driver;
	
	public AdaptationDetector(ILevel level){
		this.level = level;
		driver = new Driver(level);
	}
	
	public List<AbstractObserver> getObservers(){
		return observers;
	}
	
	public void start(){
		Timer timer = new Timer();
		timer.schedule(new AnalysisTask(), 0, 5000);
		driver.start();
	}
	
	private class AnalysisTask extends TimerTask{

		@Override
		public void run() {
			//get values from sensors
			for(AbstractObserver observer: observers){
				if(observer.getClass().getSimpleName().equals("LifeAndPaddleObserver")){
					if(observer.getAdaptations()==1){
						driver.setConstPowerUp(true);
					}
				}else if(observer.getClass().getSimpleName().equals("PaddleAndBricksObserver")){
					//padHit = (Integer) sensor.getValue() - padHitTotal;
					//padHitTotal = (Integer) sensor.getValue();
					//System.out.println("pad: "+padHit);

				}
			}
			
		}
		
	}
	
}
