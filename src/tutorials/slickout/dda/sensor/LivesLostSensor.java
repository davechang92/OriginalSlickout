package tutorials.slickout.dda.sensor;

import tutorials.slickout.playerinfo.PlayerInfo;


public class LivesLostSensor extends AbstractSensor {
	
	PlayerInfo playerInfo;
	
	public LivesLostSensor(PlayerInfo playerInfo){
		super();
		this.playerInfo = playerInfo;
	}
	
	@Override
	public void refreshValue(){
		value = playerInfo.getLivesLost();
	}
	
}

