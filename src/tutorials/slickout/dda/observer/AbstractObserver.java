package tutorials.slickout.dda.observer;

import java.util.Observer;

public abstract class AbstractObserver implements Observer {

	int adaptations = 0;
	
	public int getAdaptations(){
		return adaptations;
	}
	
}
