package tutorials.slickout.playerinfo;
 
public class PlayerInfo {
 
	/*
	private static PlayerInfo _instance = null;
 
	public static PlayerInfo getCurrentPlayerInfo(){
		return _instance;
	}
 
	public static PlayerInfo createNewCurrentPlayerInfo(){
		_instance = new PlayerInfo();
 
		return getCurrentPlayerInfo();
	}*/
 
	private String name;
	private int lives;
	private int score;
	private int livesLost = 0;
 
 
	public PlayerInfo(){
		name ="AAA";
		lives = 999;
		score = 0;
	}
 
 
	public final String getName() {
		return name;
	}
 
 
	public final int getLives() {
		return lives;
	}
 
 
	public final int getScore() {
		return score;
	}
 
 
	public final void setName(String name) {
		this.name = name;
	}
 
 
	public final void incrementLives() {
		this.lives++;
	}
 
	public final void decrementLives() {
		this.lives--;
	}
 
	public final void addScore(int score) {
		this.score += score;
	}
 
	public final void decreaseScore(int score) {
		if(this.score - score >= 0){
			this.score -= score;
		}else{
			this.score = 0;
		}
	}
	
	public final void lifeLost(){
		livesLost++;
	}
	
	public final int getLivesLost(){
		return livesLost;
	}
}