package tutorials.slickout.dda.observer;

import java.util.Observable;
import java.util.Observer;

public class LifeObserver implements Observer {

	Object observedData;
	String unitOfMeasure;
	
	public void getData(){
		
	}

	@Override
	public void update(Observable o, Object lifeSensor) {
		System.out.println("ob");
		
	}

}
