package tutorials.slickout.dda.sensor;

import tutorials.slickout.playerinfo.PlayerInfo;


public class LivesLostSensor extends AbstractSensor {
	
	PlayerInfo playerInfo;
	
	public LivesLostSensor(String name, PlayerInfo playerInfo, long period){
		super(name, period);
		this.playerInfo = playerInfo;
	}
	
	@Override
	public void refreshValue(){
		value = playerInfo.getLivesLost();
	}
	
}

