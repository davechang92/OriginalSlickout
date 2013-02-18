package tutorials.slickout.dda.sensor;
import java.util.Timer;
import java.util.TimerTask;

import tutorials.slickout.gameplay.level.collision.PadAndPowerUpCollisionHandler;


public class PowerUpCollectionSensor implements ISensor {
	
	Timer timer;
	PadAndPowerUpCollisionHandler padPUHandler;
	int totalPowerUpsCollected;
	
	public PowerUpCollectionSensor(PadAndPowerUpCollisionHandler padPUHandler){
		timer = new Timer();
		timer.schedule(new Task(), 1000, 5000);
		this.padPUHandler = padPUHandler;
	}
	
	public void refreshValue(){
		totalPowerUpsCollected = padPUHandler.getTotalPowerUpsCollected();
	}
	
	public class Task extends TimerTask{

		@Override
		public void run() {
			refreshValue();
		}
		
	}

	@Override
	public Object getValue() {
		return totalPowerUpsCollected;
	}
	
}
