package tutorials.slickout.gameplay.level;
 
import java.util.List;
 
import org.newdawn.slick.SlickException;
 
public interface ILevel {
 
	public ImageObject getBackground();
 
	public List<Ball> getBalls();
 
	public Paddle getPaddle();
 
	public CollidableImageObject getLeftBumper();
 
	public CollidableImageObject getRightBumper();
 
	public CollidableImageObject getTopBumper();
 
	public List<Brick> getBricks();
 
	public Ball addNewBall();
	
	public double getPowerUpP();
	
	public void addPowerUp(PowerUp p);
	
	/*
	public void addBall(Ball newBall);
 
	public void removeBall(Ball ball);
 
	public int getBallCount();
 
	public int
	*/ 
}