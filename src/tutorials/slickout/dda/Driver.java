package tutorials.slickout.dda;

import java.util.Timer;
import java.util.TimerTask;

import org.newdawn.slick.geom.Vector2f;

import tutorials.slickout.gameplay.level.ILevel;

public class Driver {
	
	ILevel level;
	boolean isRaining = false;

	public Driver(ILevel level){
		this.level = level;
	}
	
	public void setConstPowerUp(boolean val){
		isRaining = val;
	}
	
	public void start(){

	}
	

	public void run() {
		if(isRaining){
			level.addPowerUp(new Vector2f((float) (Math.random()*level.getWidth()),0));
		}
	}
		
	
	
}
