package tutorials.slickout.dda.sensor;

import tutorials.slickout.playerinfo.PlayerInfo;


public class LivesLostSensor extends AbstractSensor {
	
	PlayerInfo playerInfo;
	
	public LivesLostSensor(String name, PlayerInfo playerInfo){
		super(name);
		this.playerInfo = playerInfo;
		value = 0;
	}
	
	@Override
	public void refreshValue(){
		if(playerInfo.getLivesLost() > (Integer) value){
			value = playerInfo.getLivesLost();
			setChanged();
			notifyObservers();
		}
		
	}
	
}

