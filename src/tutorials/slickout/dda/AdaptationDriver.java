package tutorials.slickout.dda;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.newdawn.slick.geom.Vector2f;

import tutorials.slickout.dda.observer.AbstractObserver;
import tutorials.slickout.dda.observer.LifeAndPaddleObserver;
import tutorials.slickout.dda.sensor.AbstractSensor;
import tutorials.slickout.gameplay.collision.CollisionManager;
import tutorials.slickout.gameplay.level.ILevel;
import tutorials.slickout.gameplay.level.PowerUp;

public class AdaptationDriver {
	
	private CollisionManager collisionManager;
	private List<AbstractObserver> observers = new ArrayList<AbstractObserver>();
	private ILevel level;
	//used to time pu rain events
	private int rainTimer = 0;
	//used to determine if puRain has been turned on
	boolean isRaining = false;
	//used to record total time played + timestamp adaptations
	private int timePlayed = 0;
	private String logfilepath;
	
	public AdaptationDriver(ILevel level, CollisionManager collisionManager, String logfilepath) {
		this.level = level;
		this.collisionManager = collisionManager;
		this.logfilepath = logfilepath;
	}





	public List<AbstractObserver> getObservers(){
		return observers;
	}
	
	
	


	public void processAdaptations(int delta) {
		
		timePlayed++;

		rainTimer += delta;
		
		for(AbstractObserver observer: observers){	
			
			//used to remove adaptations once they've been implemented (power-up rain isn't removed)
			List<Adaptation> removals = new ArrayList<Adaptation>();
			
			for(Adaptation adaptation: observer.getAdaptations()){
				if(adaptation.getCode().equals("PowerUpRain") && rainTimer >= 2000){
					isRaining = true;
					removals.add(adaptation);
				}else if(adaptation.getCode().equals("IncreaseRedPowerUps")){
					level.setExtraRedP(0.1);
					removals.add(adaptation);
				}else if(adaptation.getCode().equals("IncreaseYellowPowerUps")){
					level.setExtraYellowP(0.1);
					removals.add(adaptation);
				}else if(adaptation.getCode().equals("DecreaseRedPowerUps")){
					level.setExtraRedP(-0.1);
					removals.add(adaptation);
				}else if(adaptation.getCode().equals("DecreaseYellowPowerUps")){
					level.setExtraYellowP(-0.1);
					removals.add(adaptation);
				}else if(adaptation.getCode().equals("SlowDownPowerUps")){
					level.changePowerUpSpeed(-0.04f);
					removals.add(adaptation);
				}
			}
			
			//rain pus if necessary
			if(isRaining && rainTimer >= 2000){
				
				if(Math.random() < level.getExtraRedP()){
					int type;
					if(Math.random() <0.5){
						type = 2;
					}else{
						type = 4;
					}
					PowerUp pu = level.addPowerUp(new Vector2f((float) (Math.random()*(level.getWidth()-100)) + 50,40), type);
					
					collisionManager.addCollidable(pu);
					
					
				}else if(Math.random() < level.getExtraYellowP()){
					int type;
					if(Math.random() <0.5){
						type = 1;
					}else{
						type = 3;
					}
					PowerUp pu = level.addPowerUp(new Vector2f((float) (Math.random()*(level.getWidth()-100)) + 50,40), type);
					
					collisionManager.addCollidable(pu);

					
				}else{
				
					PowerUp pu = level.addPowerUp(new Vector2f((float) (Math.random()*(level.getWidth()-100)) + 50,40), (int) (Math.random()*4)+1);
					collisionManager.addCollidable(pu);

				}
				
				rainTimer = 0;

			}
			
			//make sure adapatations aren't re-implemented; also used to log which adaptations were added, and when
			for(Adaptation adaptation: removals){
				//logging
				BufferedWriter writer = null;
				try
				{
					writer = new BufferedWriter( new FileWriter( logfilepath, true));
					writer.write(adaptation.getCode() + " added at: "+ timePlayed/1000 + "\n");

				}
				catch ( IOException e)
				{
				}
				finally
				{
					try
					{
						if ( writer != null)
							writer.close( );
					}
					catch ( IOException e)
					{
					}
			     }
				//remove adaptation
				observer.getAdaptations().remove(adaptation);
			}
			
			
		}
		
		
	}
	
}
