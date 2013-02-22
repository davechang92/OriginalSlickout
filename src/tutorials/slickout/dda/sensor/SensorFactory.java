package tutorials.slickout.dda.sensor;

import java.util.ArrayList;
import java.util.List;

import tutorials.slickout.gameplay.level.collision.BrickBallCollisionHandler;
import tutorials.slickout.gameplay.level.collision.BumperAndPadBallCollisionHandler;
import tutorials.slickout.gameplay.level.collision.PadAndPowerUpCollisionHandler;
import tutorials.slickout.playerinfo.PlayerInfo;

public class SensorFactory {
	
	List<AbstractSensor> SensorRegistry = new ArrayList<AbstractSensor>();
	
	//creates appropriate sensor according to name and object to be monitored, first checking to see if a sensor of the desired type already exists, returning that if so
	public AbstractSensor createSensor(String name, Object objectToBeMonitored){
		
		if(name.equals("PowerUpCollection")){
			for(AbstractSensor s: SensorRegistry){
				if(s instanceof PowerUpCollectionSensor){
					return s;
				}
			}
			PowerUpCollectionSensor puCollectionSensor = new PowerUpCollectionSensor(name,(PadAndPowerUpCollisionHandler) objectToBeMonitored);
			SensorRegistry.add(puCollectionSensor);
			return puCollectionSensor;
			
		}else if(name.equals("PowerUpProduction")){
			for(AbstractSensor s: SensorRegistry){
				if(s instanceof PowerUpProductionSensor){
					return s;
				}
			}
			PowerUpProductionSensor puProductionSensor = new PowerUpProductionSensor(name,(BrickBallCollisionHandler) objectToBeMonitored);
			SensorRegistry.add(puProductionSensor);
			return puProductionSensor;
			
		}else if(name.equals("LivesLost")){
			for(AbstractSensor s: SensorRegistry){
				if(s instanceof LivesLostSensor){
					return s;
				}
			}
			LivesLostSensor llSensor = new LivesLostSensor(name,(PlayerInfo) objectToBeMonitored);
			SensorRegistry.add(llSensor);
			return llSensor;
			
		}else if(name.equals("BricksHit")){
			for(AbstractSensor s: SensorRegistry){
				if(s instanceof BricksHitSensor){
					return s;
				}
			}
			BricksHitSensor bhSensor = new BricksHitSensor(name,(BrickBallCollisionHandler) objectToBeMonitored);
			SensorRegistry.add(bhSensor);
			return bhSensor;
			
		}else if(name.equals("PaddleHit")){
			for(AbstractSensor s: SensorRegistry){
				if(s instanceof PaddleHitSensor){
					return s;
				}
			}
			PaddleHitSensor phSensor = new PaddleHitSensor(name,(BumperAndPadBallCollisionHandler) objectToBeMonitored);
			SensorRegistry.add(phSensor);
			return phSensor;
		}
		return null;
	}
	
}