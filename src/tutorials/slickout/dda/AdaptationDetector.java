package tutorials.slickout.dda;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import tutorials.slickout.dda.sensor.AbstractSensor;

public class AdaptationDetector {

	List<AbstractSensor> sensors;
	List<Threshold> thresholds;
	ThresholdAnalyser thresholdAnalyser = new ThresholdAnalyser();
	
	public AdaptationDetector(){
		Threshold lifeThreshold = new Threshold();
	}

	public void createTrigger(){
		
	}
	
	public List<AbstractSensor> getSensors(){
		return sensors;
	}
	
	public void start(){
		Timer timer = new Timer();
		timer.schedule(new AnalysisTask(), 0, 10000);
	}
	
	public class AnalysisTask extends TimerTask{

		@Override
		public void run() {
			for(AbstractSensor sensor: sensors){
				thresholdAnalyser.compare(sensor.getName(), sensor.getValue(), threshold);
			}
			
		}
		
	}
	
}
