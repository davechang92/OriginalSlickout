package tutorials.slickout.dda.sensor;

import tutorials.slickout.gameplay.level.collision.BumperAndPadBallCollisionHandler;

public class PaddleHitSensor extends AbstractSensor {
	
	BumperAndPadBallCollisionHandler padBallHandler;
	
	public PaddleHitSensor(String name,BumperAndPadBallCollisionHandler padBallHandler, long period){
		super(name, period);
		this.padBallHandler = padBallHandler;
	}
	
	@Override
	public void refreshValue() {
		value = padBallHandler.getTimesHitPaddle();
		
	}

}
