package tutorials.slickout.dda;

import java.util.List;

public class Threshold {
	Object value;
	Integer type; //-1 = <; 0 = ==; 1 = >
	
	public Threshold(Object value, Integer type){
		this.value = value;
		this.type = type;
	}
}
