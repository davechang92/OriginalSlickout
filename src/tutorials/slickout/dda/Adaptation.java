package tutorials.slickout.dda;

public class Adaptation {

	private final String name;
	
	public Adaptation(String code) throws Exception{
		if(code.equals("PowerUpRain") || code.equals("IncreaseRedPowerUps")){
			name = code;
		}else{
			throw new Exception("adaptation code not recognized");
		}
	}
	
	public String getCode(){
		return name;
	}
	
}
