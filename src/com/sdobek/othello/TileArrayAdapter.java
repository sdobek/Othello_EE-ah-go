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

			// top
			if (pos >= 16 && this.getItem(pos - 8).getColor() == diff) {
				int above = pos - 16; // next one up
				while (above >= 0) {
					Color c = this.getItem(above).getColor();
					if (c == player) {
						adjN = true;
						playerValidMoves = true;
						break;
					} else if (c == Color.COLORLESS) {
						break;
					}
					above -= 8;
				}
			}
			// top left
			if (pos >= 18 && pos % 8 >= 2
					&& this.getItem(pos - 9).getColor() == diff) {
				int aboveLeft = pos - 18; // next one up
				while (aboveLeft >= 0) {
					Color c = this.getItem(aboveLeft).getColor();
					if (c == player) {
						adjNW = true;
						playerValidMoves = true;
						break;
					} else if (aboveLeft % 8 == 0 || c == Color.COLORLESS) {
						break;
					}
					aboveLeft -= 9;
				}
			}
			// top right
			if (pos >= 16 && pos % 8 < 6
					&& this.getItem(pos - 7).getColor() == diff) {
				int aboveRight = pos - 14; // next one up
				while (aboveRight >= 0) {
					Color c = this.getItem(aboveRight).getColor();
					if (c == player) {
						adjNE = true;
						playerValidMoves = true;
						break;
					} else if ((aboveRight + 1) % 8 == 0
							|| c == Color.COLORLESS) {
						break;
					}
					aboveRight -= 7;
				}
			}
			// bottom
			if (pos <= 47 && this.getItem(pos + 8).getColor() == diff) {
				int below = pos + 16; // next one down
				while (below <= 63) {
					Color c = this.getItem(below).getColor();
					if (c == player) {
						adjS = true;
						playerValidMoves = true;
						break;
					} else if (c == Color.COLORLESS) {
						break;
					}
					below += 8;
				}
			}
			// bottom left
			if (pos <= 47 && pos % 8 >= 2
					&& this.getItem(pos + 7).getColor() == diff) {
				int belowLeft = pos + 14; // next one down
				while (belowLeft <= 63) {
					Color c = this.getItem(belowLeft).getColor();
					if (c == player) {
						adjSW = true;
						playerValidMoves = true;
						break;
					} else if (belowLeft % 8 == 0 || c == Color.COLORLESS) {
						break;
					}
					belowLeft += 7;
				}
			}
			// bottom right
			if (pos <= 45 && pos % 8 < 6
					&& this.getItem(pos + 9).getColor() == diff) {
				int belowRight = pos + 18; // next one down
				while (belowRight <= 63) {
					Color c = this.getItem(belowRight).getColor();
					if (c == player) {
						adjSE = true;
						playerValidMoves = true;
						break;
					} else if ((belowRight + 1) % 8 == 0
							|| c == Color.COLORLESS) {
						break;
					}
					belowRight += 9;
				}
			}
			// left
			if (pos % 8 >= 2 && this.getItem(pos - 1).getColor() == diff) {
				int left = pos - 2; // next one up
				int leftEndpoint = 8 * (pos / 8);
				while (left >= leftEndpoint) {
					Color c = this.getItem(left).getColor();
					if (c == player) {
						adjW = true;
						playerValidMoves = true;
						break;
					} else if (c == Color.COLORLESS) {
						break;
					}
					left--;
				}
			}
			// right
			if (pos % 8 < 6 && this.getItem(pos + 1).getColor() == diff) {
				int right = pos + 2; // next one up
				int rightEndpoint = 8 * (pos / 8 + 1);
				while (right < rightEndpoint) {
					Color c = this.getItem(right).getColor();
					if (c == player) {
						adjE = true;
						playerValidMoves = true;
						break;
					} else if (c == Color.COLORLESS) {
						break;
					}
					right++;
				}
			}

			if (adjN || adjS || adjE || adjW || adjNE || adjNW || adjSE
					|| adjSW) {
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
	
	public void clearFlipAnimations(){
		for (int tile = 0; tile < 64; tile++){
			this.getItem(tile).setAnimateFlip(false);
		}
	}

}
