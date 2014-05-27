package com.sdobek.othello.models;


public class GameTile {

	private boolean open;
	private boolean canMoveBlack;
	private boolean canMoveWhite;
	private Color color;

	private boolean adjacentBlack[];
	private boolean adjacentWhite[];
	
	private boolean animateFlip;

	public GameTile() {
		open = true;
		color = Color.COLORLESS;
		canMoveBlack = false;
		canMoveWhite = false;
		adjacentBlack = new boolean[8];
		adjacentWhite = new boolean[8];
		animateFlip = false;
	}

	public GameTile(Color color) {
		this.open = false;
		this.color = color;
		canMoveBlack = false;
		canMoveWhite = false;
		adjacentBlack = new boolean[8];
		adjacentWhite = new boolean[8];
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean getOpen() {
		return open;
	}

	public void setColor(Color c) {
		this.color = c;
	}

	public Color getColor() {
		return color;
	}

	public void setCanMove(Color c) {
		switch (c) {
		case BLACK:
			canMoveBlack = true;
			break;
		case WHITE:
			canMoveWhite = true;
			break;
		default:
			break;
		}
	}

	public void setCannotMove(Color c) {
		switch (c) {
		case BLACK:
			canMoveBlack = false;
			break;
		case WHITE:
			canMoveWhite = false;
			break;
		default:
			break;
		}
	}

	public boolean getCanMove(Color c) {
		if (c == Color.BLACK) {
			return canMoveBlack;
		} else if (c == Color.WHITE) {
			return canMoveWhite;
		}
		return false;
	}

	public void setAdjacenciesBlack(boolean adjN, boolean adjS, boolean adjE, boolean adjW, 
										boolean adjNE, boolean adjNW, boolean adjSE, boolean adjSW) {
		adjacentBlack[0] = adjN;
		adjacentBlack[1] = adjNE;
		adjacentBlack[2] = adjE;
		adjacentBlack[3] = adjSE;
		adjacentBlack[4] = adjS;
		adjacentBlack[5] = adjSW;
		adjacentBlack[6] = adjW;
		adjacentBlack[7] = adjNW;
	}
	
	public void setAdjacenciesWhite(boolean adjN, boolean adjS, boolean adjE, boolean adjW, 
										boolean adjNE, boolean adjNW, boolean adjSE, boolean adjSW) {
		adjacentWhite[0] = adjN;
		adjacentWhite[1] = adjNE;
		adjacentWhite[2] = adjE;
		adjacentWhite[3] = adjSE;
		adjacentWhite[4] = adjS;
		adjacentWhite[5] = adjSW;
		adjacentWhite[6] = adjW;
		adjacentWhite[7] = adjNW;
	}

	public boolean[] getAdjacenciesBlack(){
		return adjacentBlack;
	}
	
	public boolean[] getAdjacenciesWhite(){
		return adjacentWhite;
	}
	
	public void setAnimateFlip(boolean flip){
		animateFlip = flip;
	}
	
	public boolean getAnimateFlip(){
		return animateFlip;
	}

}
