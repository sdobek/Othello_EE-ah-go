package com.sdobek.othello;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.othello.R;
import com.sdobek.othello.models.Color;
import com.sdobek.othello.models.GameTile;

public class TileArrayAdapter extends ArrayAdapter<GameTile> {

	private static LayoutInflater inflator = null;
	private boolean blackMoves;
	private boolean whiteMoves;

	public TileArrayAdapter(Context context, List<GameTile> tiles) {
		super(context, R.layout.game_tile, tiles);
		inflator = LayoutInflater.from(getContext());
		blackMoves = true;
		whiteMoves = true;
	}

	public View getView(int pos, View convertView, ViewGroup parent) {
		GameTile gt = this.getItem(pos);
		if (convertView == null) {
			convertView = inflator.inflate(R.layout.game_tile, parent, false);
		}

		ImageView iv_piece = (ImageView) convertView
				.findViewById(R.id.iv_piece);
		switch (gt.getColor()) {
		case BLACK:
			iv_piece.setVisibility(View.VISIBLE);
			iv_piece.setImageResource(R.drawable.ic_black_piece);
			break;
		case WHITE:
			iv_piece.setVisibility(View.VISIBLE);
			iv_piece.setImageResource(R.drawable.ic_white_piece);
			break;
		default:
			iv_piece.setVisibility(View.INVISIBLE);
			break;
		}

		return convertView;
	}

	public void setValidMoves() {
		blackMoves = checkValidMoves(Color.BLACK, Color.WHITE);
		whiteMoves = checkValidMoves(Color.WHITE, Color.BLACK);
	}
	
	public boolean getValidBlack(){
		return blackMoves;
	}
	
	public boolean getValidWhite(){
		return whiteMoves;
	}

	public boolean checkValidMoves(Color player, Color diff) {
		boolean playerValidMoves = false;
		for (int pos = 0; pos < 64; pos++) {
			if (pos == 50){
				pos = 50;
			}
			// Check if tile is empty - and therefore a move can be made
			GameTile gt = this.getItem(pos);
			if (gt.getColor() != Color.COLORLESS) {
				continue;
			}
			// All adjacencies checked as valid moves
			boolean adjN = false, adjS = false, adjE = false, adjW = false, adjNE = false, adjNW = false, adjSE = false, adjSW = false;
		
			// top
			if (pos > 7 && this.getItem(pos - 8).getColor() == diff) {
				int above = pos - 16; // next one up
				while (above >= 0) {
					if (this.getItem(above).getColor() == player) {
						adjN = true;
						playerValidMoves = true;
						break;
					}
					above -= 8;
				}
			}
			// top left
			if (pos > 8 && pos % 8 != 0 && this.getItem(pos - 9).getColor() == diff) {
				int aboveLeft = pos - 18; // next one up
				while (aboveLeft >= 0) {
					if (this.getItem(aboveLeft).getColor() == player) {
						adjNW = true;
						playerValidMoves = true;
						break;
					}
					else if (aboveLeft % 8 == 0){
						break;
					}
					aboveLeft -= 9;
				}
			}
			// top right
			if (pos > 7 && (pos + 1) % 8 != 0 && this.getItem(pos - 7).getColor() == diff) {
				int aboveRight = pos - 14; // next one up
				while (aboveRight >= 0) {
					if (this.getItem(aboveRight).getColor() == player) {
						adjNE = true;
						playerValidMoves = true;
						break;
					}
					else if ((aboveRight+1) % 8 == 0){
						break;
					}
					aboveRight -= 7;
				}
			}
			// bottom
			if (pos < 56 && this.getItem(pos + 8).getColor() == diff) {
				int below = pos + 16; // next one down
				while (below <= 63) {
					if (this.getItem(below).getColor() == player) {
						adjS = true;
						playerValidMoves = true;
						break;
					}
					below += 8;
				}
			}
			// bottom left
			if (pos < 56 && pos % 8 != 0 && this.getItem(pos + 7).getColor() == diff) {
				int belowLeft = pos + 14; // next one down
				while (belowLeft <= 63) {
					if (this.getItem(belowLeft).getColor() == player) {
						adjSW = true;
						playerValidMoves = true;
						break;
					}
					else if (belowLeft % 8 == 0){
						break;
					}
					belowLeft += 7;
				}
			}
			// bottom right
			if (pos < 55 && (pos + 1) % 8 != 0 && this.getItem(pos + 9).getColor() == diff) {
				int belowRight = pos + 18; // next one down
				while (belowRight <= 63) {
					if (this.getItem(belowRight).getColor() == player) {
						adjSE = true;
						playerValidMoves = true;
						break;
					}
					else if ((belowRight+1) % 8 == 0){
						break;
					}
					belowRight += 9;
				}
			}
			// left
			if (pos % 8 != 0 && this.getItem(pos - 1).getColor() == diff) {
				int left = pos - 2; // next one up
				int leftEndpoint = 8 * (pos / 8);
				while (left >= leftEndpoint) {
					if (this.getItem(left).getColor() == player) {
						adjW = true;
						playerValidMoves = true;
						break;
					}
					left--;
				}
			}
			// right
			if ((pos + 1) % 8 != 0 && this.getItem(pos + 1).getColor() == diff) {
				int right = pos + 2; // next one up
				int rightEndpoint = 8 * (pos / 8 + 1);
				while (right < rightEndpoint) {
					if (this.getItem(right).getColor() == player) {
						adjE = true;
						playerValidMoves = true;
						break;
					}
					right++;
				}
			}

			if (adjN || adjS || adjE || adjW || adjNE || adjNW || adjSE
					|| adjSW) {
				gt.setCanMove(player);
			}
			else {
				gt.setCannotMove(player);
			}

			switch (player) {
			case BLACK:
				gt.setAdjacenciesBlack(adjN, adjS, adjE, adjW, adjNE, adjNW,
						adjSE, adjSW);
				break;
			case WHITE:
				gt.setAdjacenciesWhite(adjN, adjS, adjE, adjW, adjNE, adjNW,
						adjSE, adjSW);
				break;
			default:
				break;
			}
		}
		return playerValidMoves;
	}

}
