package tutorials.slickout.dda.sensor;

import tutorials.slickout.gameplay.level.collision.BumperAndPadBallCollisionHandler;

public class PaddleHitSensor extends AbstractSensor {
	
	BumperAndPadBallCollisionHandler padBallHandler;
	
	public PaddleHitSensor(BumperAndPadBallCollisionHandler padBallHandler){
		super();
		this.padBallHandler = padBallHandler;
	}
	
	@Override
	public void refreshValue() {
		value = padBallHandler.getTimesHitPaddle();
		
	}

}
