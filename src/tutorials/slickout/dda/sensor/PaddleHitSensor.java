package tutorials.slickout.dda.sensor;

import tutorials.slickout.gameplay.level.collision.BumperAndPadBallCollisionHandler;

public class PaddleHitSensor extends AbstractSensor {
	
	BumperAndPadBallCollisionHandler padBallHandler;
	
	public PaddleHitSensor(String name,BumperAndPadBallCollisionHandler padBallHandler){
		super(name);
		this.padBallHandler = padBallHandler;
		value = 0;
	}
	
	@Override
	public void refreshValue() {
		if(padBallHandler.getTimesHitPaddle() > (Integer) value){
			value = padBallHandler.getTimesHitPaddle() ;
			setChanged();
			notifyObservers(value);
		}
	}

}
