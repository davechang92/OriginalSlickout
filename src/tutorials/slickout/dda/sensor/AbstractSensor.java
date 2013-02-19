package tutorials.slickout.dda.sensor;

import java.util.Timer;
import java.util.TimerTask;

public abstract class AbstractSensor {	

	Timer timer;
	Object value;
	
	public AbstractSensor(){
		timer = new Timer();
		timer.schedule(new Task(), 1000, 1000);
	}
	
	public abstract void refreshValue();
	
	public Object getValue(){
		return value;
	}
	
		public class Task extends TimerTask{
	
			@Override
			public void run() {
				refreshValue();
			}
			
		}
	
}
