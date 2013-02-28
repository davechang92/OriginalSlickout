package tutorials.slickout.dda.sensor;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public abstract class AbstractSensor extends Observable {	

	Timer timer;
	Object value;
	String name;
	
	public AbstractSensor(String name){
		this.name = name;
		timer = new Timer();
		timer.schedule(new Task(), 500, 500);
		
	}
	
	public abstract void refreshValue();
	
	public Object getValue(){
		return value;
	}
	
	public String getName(){
		return name;
	}
	
		public class Task extends TimerTask{
	
			@Override
			public void run() {
				refreshValue();
			}
			
		}
	
}
