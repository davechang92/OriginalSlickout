package tutorials.slickout.gameplay.level;
 
import java.util.List;
 
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
 
public interface ILevel {
 
	public ImageObject getBackground();
 
	public List<Ball> getBalls();
 
	public Paddle getPaddle();
 
	public CollidableImageObject getLeftBumper();
 
	public CollidableImageObject getRightBumper();
 
	public CollidableImageObject getTopBumper();
 
	public List<Brick> getBricks();
 
	public Ball addNewBall();
	
	public PowerUp addPowerUp(Vector2f pos, int powerType);
	
	public List<PowerUp> getPowerUps();
	
	public List<AnimationObject> getExplosions();
	
	public double getPowerUpP();
	
	public double getExtraRedP();

	public double getExtraYellowP();

	public void setPowerUpP(double change);

	public void setExtraRedP(double change);

	public void setExtraYellowP(double change);

	public int getHeight();
	
	public int getWidth();
	
	public int getLastPowerUpProducedType();
	
	public float getPowerUpSpeed();
	
	public void changePowerUpSpeed(float change);
	
	public void addExplosion(AnimationObject exp);
	
	public int getNumOfPowerTypes();
			
	/*
	public void addBall(Ball newBall);
 
	public void removeBall(Ball ball);
 
	public int getBallCount();
 
	public int
	*/ 
}