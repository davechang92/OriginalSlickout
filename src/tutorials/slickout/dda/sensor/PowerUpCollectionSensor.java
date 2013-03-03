package tutorials.slickout.dda.sensor;

import tutorials.slickout.gameplay.level.collision.PadAndPowerUpCollisionHandler;


public class PowerUpCollectionSensor extends AbstractSensor {
	
	PadAndPowerUpCollisionHandler padPUHandler;
	private int puType = 0;
	
	public PowerUpCollectionSensor(String name,PadAndPowerUpCollisionHandler padPUHandler){
		super(name);
		this.padPUHandler = padPUHandler;
		value = 0;
	}
	
	public int getPUType(){
		return puType;
	}
	
	@Override
	public void refreshValue(){
		if(padPUHandler.getTotalPowerUpsCollected() > (Integer) value){
			value = padPUHandler.getTotalPowerUpsCollected();
			puType = padPUHandler.getLastPUType();
			setChanged();
			notifyObservers(value);
		}
	}
	
}
