package tutorials.slickout.dda.sensor;

import tutorials.slickout.gameplay.level.collision.PadAndPowerUpCollisionHandler;


public class PowerUpCollectionSensor extends AbstractSensor {
	
	PadAndPowerUpCollisionHandler padPUHandler;
	
	public PowerUpCollectionSensor(String name,PadAndPowerUpCollisionHandler padPUHandler, long period){
		super(name, period);
		this.padPUHandler = padPUHandler;
	}
	
	@Override
	public void refreshValue(){
		value = padPUHandler.getTotalPowerUpsCollected();
	}
	
}
