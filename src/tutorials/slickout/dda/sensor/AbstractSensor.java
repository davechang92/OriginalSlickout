package tutorials.slickout.dda.sensor;

import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import tutorials.slickout.dda.observer.*;

public abstract class AbstractSensor extends Observable {	

	Timer timer;
	Object value;
	String name;
	Date date;
	long timestamp;
	
	public AbstractSensor(String name){
		this.name = name;
		timer = new Timer();
		timer.schedule(new Task(), 500, 500);
		
		date = new Date();
	}
	
	public abstract void refreshValue();
	
	public Object getValue(){
		return value;
	}
	
	public String getName(){
		return name;
	}
	
	public long getTimestamp(){
		return timestamp;
	}
	
		public class Task extends TimerTask{
	
			@Override
			public void run() {
				refreshValue();
			}
			
		}
	
}
