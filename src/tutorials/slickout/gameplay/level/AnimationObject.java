package tutorials.slickout.gameplay.level;
 
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
 
public class AnimationObject implements ILevelObject {
 
	protected String name;
	protected Animation animation;
	protected Vector2f position;
 
	public AnimationObject(String name, Animation animation, Vector2f position){
		this.name = name;
		this.position = position;
		this.animation = animation;
	}
 
	@Override
	public String getName() {
		return name;
	}
 
	@Override
	public Vector2f getPosition() {
		return position;
	}
 
	@Override
	public void setPosition(Vector2f position) {
		this.position = position;
	}
	
	public void setAnimation(String ssPath, int frameWidth, int frameHeight, int duration) throws SlickException{
		SpriteSheet ss = new SpriteSheet(new Image(ssPath), frameWidth, frameHeight);
		 
		Animation animation = new Animation(ss, duration);
		this.animation = animation;
	}
 
 
	@Override
	public void render(Graphics graphics) {
		animation.draw(position.x, position.y);
	}
 
 
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		animation.update(delta);
	}
 
}