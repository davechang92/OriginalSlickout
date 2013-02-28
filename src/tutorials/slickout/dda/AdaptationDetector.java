package tutorials.slickout.dda;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import tutorials.slickout.dda.sensor.AbstractSensor;
import tutorials.slickout.gameplay.level.ILevel;

public class AdaptationDetector {
	
	int padHit=0;
	int bricksHit=0;
	int lastBricksTotal = 0;
	int padHitTotal = 0;
	List<AbstractSensor> sensors = new ArrayList<AbstractSensor>();
	ILevel level;
	Driver driver;
	
	public AdaptationDetector(ILevel level){
		this.level = level;
		driver = new Driver(level);
	}
	
	public List<AbstractSensor> getSensors(){
		return sensors;
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
			for(AbstractSensor sensor: sensors){
				if(sensor.getClass().getSimpleName().equals("BricksHitSensor")){
					bricksHit = (Integer) sensor.getValue() - lastBricksTotal;
					lastBricksTotal = (Integer) sensor.getValue();
					System.out.println("bricks: " +bricksHit);
				}else if(sensor.getClass().getSimpleName().equals("PaddleHitSensor")){
					padHit = (Integer) sensor.getValue() - padHitTotal;
					padHitTotal = (Integer) sensor.getValue();
					System.out.println("pad: "+padHit);

				}
			}
			
			//analyse
			if(true){
				level.setPowerUpP(0.1);
				if(level.getPowerUpP()>1)
					driver.setConstPowerUp(true);
			}
		}
		
	}
	
}
