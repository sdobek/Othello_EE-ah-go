package com.sdobek.othello.models;

public enum Color {
	BLACK{
		public Color getFlip(){
			return Color.WHITE;
		}
	}, 
	WHITE{
		public Color getFlip(){
			return Color.BLACK;
		}
	}, 
	COLORLESS{
		public Color getFlip(){
			return Color.COLORLESS;
		}
	};
	public abstract Color getFlip();
}
