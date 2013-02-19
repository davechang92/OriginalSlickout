package tutorials.slickout.dda.sensor;

import tutorials.slickout.gameplay.level.collision.BrickBallCollisionHandler;
import tutorials.slickout.gameplay.level.collision.BumperAndPadBallCollisionHandler;
import tutorials.slickout.gameplay.level.collision.PadAndPowerUpCollisionHandler;
import tutorials.slickout.playerinfo.PlayerInfo;

public class SensorFactory {
	
	
	public AbstractSensor createSensor(String name, Object objectToBeMonitored){
		if(name.equals("PowerUpCollection")){
			PowerUpCollectionSensor puCollectionSensor = new PowerUpCollectionSensor((PadAndPowerUpCollisionHandler) objectToBeMonitored);
			return puCollectionSensor;
		}else if(name.equals("PowerUpProduction")){
			PowerUpProductionSensor puProductionSensor = new PowerUpProductionSensor((BrickBallCollisionHandler) objectToBeMonitored);
			return puProductionSensor;
		}else if(name.equals("LivesLost")){
			LivesLostSensor llSensor = new LivesLostSensor((PlayerInfo) objectToBeMonitored);
			return llSensor;
		}else if(name.equals("BricksHit")){
			BricksHitSensor bhSensor = new BricksHitSensor((BrickBallCollisionHandler) objectToBeMonitored);
			return bhSensor;
		}else if(name.equals("PaddleHit")){
			PaddleHitSensor phSensor = new PaddleHitSensor((BumperAndPadBallCollisionHandler) objectToBeMonitored);
			return phSensor;
		}
		return null;
	}
	
}