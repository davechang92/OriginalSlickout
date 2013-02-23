package tutorials.slickout.dda;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import tutorials.slickout.dda.sensor.AbstractSensor;

public class AdaptationDetector {

	List<AbstractSensor> sensors = new ArrayList<AbstractSensor>();
	List<Threshold> thresholds;
	ThresholdAnalyser thresholdAnalyser = new ThresholdAnalyser();
	//used to calculate num of lives lost in last period
	int lastLivesLost = 0;
	
	public AdaptationDetector(){
		//Threshold lifeThreshold = new Threshold(lifeThreshold, null);
	}

	public void createTrigger(){
		
	}
	
	public List<AbstractSensor> getSensors(){
		return sensors;
	}
	
	public void start(){
		Timer timer = new Timer();
		timer.schedule(new AnalysisTask(), 0, 30000);
	}
	
	public class AnalysisTask extends TimerTask{

		@Override
		public void run() {
			for(AbstractSensor sensor: sensors){
				thresholdAnalyser.compare(sensor.getName(), sensor.getValue());
			}
			
		}
		
	}
	
}
