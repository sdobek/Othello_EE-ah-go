package com.sdobek.othello.models;

public class GameTile {
	
	private boolean open;
	private Color color;
	
	public GameTile(){
		open = true;
		color = Color.COLORLESS;
	}
	
	public GameTile(Color color){
		this.open = false;
		this.color = color;
	}
	
	public void setOpen(boolean open){
		this.open = open;
	}
	
	public boolean getOpen(){
		return open;
	}
	
	public void setColor(Color c){
		this.color = c;
	}
	
	public Color getColor(){
		return color;
	}
}
