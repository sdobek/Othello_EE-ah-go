package com.sdobek.othello;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.sdobek.othello.models.Color;
import com.sdobek.othello.models.GameTile;

public class TileArrayAdapter extends ArrayAdapter<GameTile> {
    public static int ROW_COL_SIZE = 8;

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

		final ImageView iv_piece = (ImageView) convertView
				.findViewById(R.id.iv_piece);
		switch (gt.getColor()) {
		case BLACK:
			if (gt.getAnimateFlip()) {
				Animation animUp = AnimationUtils.loadAnimation(getContext(), R.animator.tile_flip_in);
				iv_piece.startAnimation(animUp);
				animUp.setAnimationListener(new AnimationListener() {
				    @Override
				    public void onAnimationStart(Animation animation) {
				        // Fires when animation starts
				    }

				    @Override
				    public void onAnimationEnd(Animation animation) {
				    	iv_piece.setImageResource(R.drawable.ic_black_piece);
				    	Animation animDown = AnimationUtils.
				    							loadAnimation(getContext(), R.animator.tile_flip_out);
						iv_piece.startAnimation(animDown);
				    }

				    @Override
				    public void onAnimationRepeat(Animation animation) {
				       // ...			
				    }
				});
			} else { // initial place
				iv_piece.setVisibility(View.VISIBLE);
				iv_piece.setImageResource(R.drawable.ic_black_piece);
			}
			break;
		case WHITE:
			if (gt.getAnimateFlip()) {
				Animation animUp = AnimationUtils.loadAnimation(getContext(), R.animator.tile_flip_in);
				iv_piece.startAnimation(animUp);
				animUp.setAnimationListener(new AnimationListener() {
				    @Override
				    public void onAnimationStart(Animation animation) {
				        // Fires when animation starts
				    }

				    @Override
				    public void onAnimationEnd(Animation animation) {
				    	iv_piece.setImageResource(R.drawable.ic_white_piece);
				    	Animation animDown = AnimationUtils.
				    							loadAnimation(getContext(), R.animator.tile_flip_out);
						iv_piece.startAnimation(animDown);
				    }

				    @Override
				    public void onAnimationRepeat(Animation animation) {
				       // ...			
				    }
				});
			} else { // initial place
				iv_piece.setVisibility(View.VISIBLE);
				iv_piece.setImageResource(R.drawable.ic_white_piece);
			}
			break;
		default:
			iv_piece.setVisibility(View.INVISIBLE);
			break;
		}

		return convertView;
	}

	public void setValidMoves() {
		
		whiteMoves = checkValidMoves(Color.WHITE, Color.BLACK);
		blackMoves = checkValidMoves(Color.BLACK, Color.WHITE);
	}

	public boolean getValidBlack() {
		return blackMoves;
	}

	public boolean getValidWhite() {
		return whiteMoves;
	}

	public boolean checkValidMoves(Color player, Color diff) {
		boolean playerValidMoves = false;
		for (int pos = 0; pos < 64; pos++) {
			// Check if tile is empty - and therefore a move can be made
			GameTile gt = this.getItem(pos);
			if (gt.getColor() != Color.COLORLESS) {
				gt.setCannotMove(player);
				continue;
			}
			// All adjacencies checked as valid moves
			boolean adjN = false, adjS = false, adjE = false, adjW = false, adjNE = false, adjNW = false, adjSE = false, adjSW = false;

			GameTile current = this.getItem(pos);
			// top
			if (current.getRow() >= 2 && getNextGameTile(current, -1, 0).getColor() == diff) {
				int row = current.getRow() - 2;
                int col = current.getCol();
				adjN = canMoveInDirection(row, col, -1, 0, player);
			}
			// top left
			if (current.getRow() >= 2 && current.getCol() >= 2 && getNextGameTile(current, -1, -1).getColor() == diff){
                int row = current.getRow() - 2;
                int col = current.getCol() - 2;
                adjNW = canMoveInDirection(row, col, -1, -1, player);
            }
			// top right
			if (current.getRow() >= 2 && current.getCol() <= 5 && getNextGameTile(current, -1, 1).getColor() == diff){
                int row = current.getRow() - 2;
                int col = current.getCol() + 2;
                adjNE = canMoveInDirection(row, col, -1, 1, player);
			}
			// bottom
			if (current.getRow() <= 5 && getNextGameTile(current, 1, 0).getColor() == diff){
                int row = current.getRow() + 2;
                int col = current.getCol();
                adjS = canMoveInDirection(row, col, 1, 0, player);
			}
            // bottom left
            if (current.getRow() <= 5 && current.getCol() >= 2 && getNextGameTile(current, 1, -1).getColor() == diff){
                int row = current.getRow() + 2;
                int col = current.getCol() - 2;
                adjSW = canMoveInDirection(row, col, 1, -1, player);
            }
            // bottom right
            if (current.getRow() <= 5 && current.getCol() <= 5 && getNextGameTile(current, 1, 1).getColor() == diff){
                int row = current.getRow() + 2;
                int col = current.getCol() + 2;
                adjSE = canMoveInDirection(row, col, 1, 1, player);
            }
            // left
            if (current.getCol() >= 2 && getNextGameTile(current, 0, -1).getColor() == diff){
                int row = current.getRow();
                int col = current.getCol() - 2;
                adjW = canMoveInDirection(row, col, 0, -1, player);
            }
            // right
            if (current.getRow() <= 5 && current.getCol() <= 5 && getNextGameTile(current, 0, 1).getColor() == diff){
                int row = current.getRow();
                int col = current.getCol() + 2;
                adjE = canMoveInDirection(row, col, 0, 1, player);
            }

			if (adjN || adjS || adjE || adjW || adjNE || adjNW || adjSE
					|| adjSW) {
                playerValidMoves = true;
				gt.setCanMove(player);
			} else {
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

    public boolean canMoveInDirection(int row, int col, int dRow, int dCol, Color player){
        if (row >= 0 && row < ROW_COL_SIZE
                && col >= 0 && col < ROW_COL_SIZE){
            Color c = getGameTile(row, col).getColor();
            if (c == player) {
                return true;
            } else if (c == Color.COLORLESS) {
                return false;
            }
            return canMoveInDirection(row+dRow, col+dCol, dRow, dCol, player);
        }
        return false;
    }


    public GameTile getNextGameTile(GameTile current, int dRow, int dCol){
        return this.getItem(((current.getRow() + dRow) * ROW_COL_SIZE) + (current.getCol() + dCol));
    }

    public GameTile getGameTile(int row, int col){
        return this.getItem((row * ROW_COL_SIZE) + col);
    }

    public void clearFlipAnimations(){
		for (int tile = 0; tile < 64; tile++){
			this.getItem(tile).setAnimateFlip(false);
		}
	}

}
