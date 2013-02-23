package tutorials.slickout.dda.sensor;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import tutorials.slickout.dda.Observer;

public abstract class AbstractSensor {	

	Timer timer;
	Object value;
	String name;
	List<Observer> observers;
	
	public AbstractSensor(String name, long period){
		this.name = name;
		timer = new Timer();
		timer.schedule(new Task(), 500, period);
	}
	
	public abstract void refreshValue();
	
	public Object getValue(){
		return value;
	}
	
	public String getName(){
		return name;
	}
	
	public List<Observer> getObservers(){
		return observers;
	}
	
		public class Task extends TimerTask{
	
			@Override
			public void run() {
				refreshValue();
			}
			
		}
	
}
