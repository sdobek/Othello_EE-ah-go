package com.sdobek.othello.models;

public enum Color {
	BLACK{
		public Color getFlip(){
			return Color.WHITE;
		}
		public String toString(){
			return "BLACK";
		}
	}, 
	WHITE{
		public Color getFlip(){
			return Color.BLACK;
		}
		public String toString(){
			return "WHITE";
		}
	}, 
	COLORLESS{
		public Color getFlip(){
			return Color.COLORLESS;
		}
		public String toString(){
			return "BLANK";
		}
	};
	public abstract Color getFlip();
	public abstract String toString();
}
