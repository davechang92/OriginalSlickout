package tutorials.slickout.dda.sensor;

import tutorials.slickout.gameplay.level.collision.PadAndPowerUpCollisionHandler;


public class PowerUpCollectionSensor extends AbstractSensor {
	
	PadAndPowerUpCollisionHandler padPUHandler;
	
	public PowerUpCollectionSensor(String name,PadAndPowerUpCollisionHandler padPUHandler){
		super(name);
		this.padPUHandler = padPUHandler;
	}
	
	@Override
	public void refreshValue(){
		value = padPUHandler.getTotalPowerUpsCollected();
	}
	
}
