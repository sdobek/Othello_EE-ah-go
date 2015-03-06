package com.sdobek.othello;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdobek.othello.models.Color;
import com.sdobek.othello.models.GameTile;

public class GameActivity extends Activity {

	public static boolean gameOver;

	List<GameTile> tiles;
	TileArrayAdapter tilesAdapter;
	GridView board;
	RelativeLayout boardImage;
	Color turn;

	TextView infoBox;
	TextView blackScore;
	TextView whiteScore;
	int b_score = 2;
	int w_score = 2;
	int delta = 0;

	private float scale = 1f;
	private ScaleGestureDetector SGD;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		//TODO: Implement with a device to test
		//SGD = new ScaleGestureDetector(this, new ScaleListener());
		createGame();
	}

	public void createGame() {
		board = (GridView) findViewById(R.id.gv_board);
		boardImage = (RelativeLayout) findViewById(R.id.rl_boardEdges);
		tiles = new ArrayList<GameTile>();
		tilesAdapter = new TileArrayAdapter(this, tiles);
		board.setAdapter(tilesAdapter);
		blackScore = (TextView) findViewById(R.id.tv_blackScore);
		blackScore.setText("2");
		b_score = 2;
		whiteScore = (TextView) findViewById(R.id.tv_whiteScore);
		whiteScore.setText("2");
		w_score = 2;
		infoBox = (TextView) findViewById(R.id.tv_infoBox);
		infoBox.setText(R.string.black);
		turn = Color.BLACK;

		for (int i = 0; i < 64; i++) {
            int row = i / TileArrayAdapter.ROW_COL_SIZE;
            int col = i % TileArrayAdapter.ROW_COL_SIZE;
            GameTile g;
            if (i == 27 || i == 36){
                g = new GameTile(row, col, Color.BLACK);
            } else if (i == 28 || i == 35){
                g = new GameTile(row, col, Color.WHITE);
            } else {
                g = new GameTile(row, col);
            }
			tiles.add(g);
		}

		tilesAdapter.setValidMoves();
		board.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				GameTile gt = tiles.get(position);
				if (gt.getCanMove(turn)) {
					gt.setColor(turn);
					delta = 0;
					tilesAdapter.clearFlipAnimations(); // reset tile animations 
														// to false
					flip(gt, position);
					// update
					if (turn == Color.BLACK) {
						b_score += 1 + delta;
						w_score -= delta;
					} else {
						w_score += 1 + delta;
						b_score -= delta;
					}
					blackScore.setText(Integer.toString(b_score));
					whiteScore.setText(Integer.toString(w_score));

					tilesAdapter.notifyDataSetChanged();
					tilesAdapter.setValidMoves();
					nextTurn();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	public void nextTurn() {
		boolean validBlack = tilesAdapter.getValidBlack();
		boolean validWhite = tilesAdapter.getValidWhite();
		if (!validBlack && !validWhite) { // no more available moves
			if (b_score > w_score) {
				infoBox.setText(R.string.gameover_black);
			} else if (b_score < w_score) {
				infoBox.setText(R.string.gameover_white);
			} else {
				infoBox.setText(R.string.gameover_draw);
			}
		} else {
			turn = turn.getFlip();
			if (turn == Color.BLACK && validBlack) {
				infoBox.setText(R.string.black);
			} else if (turn == Color.WHITE && validWhite) {
				infoBox.setText(R.string.white);
			} else if (!validBlack) {
				infoBox.setText(R.string.no_black);
				turn = Color.WHITE;
			} else if (!validWhite) {
				infoBox.setText(R.string.no_white);
				turn = Color.BLACK;
			}
		}
	}

	public void flip(GameTile gt, int position) {
		GameTile next = null;
		int offset = 0;
		boolean adjacent[] = null;
		switch (gt.getColor()) {
		case BLACK:
			adjacent = gt.getAdjacenciesBlack();
			break;
		case WHITE:
			adjacent = gt.getAdjacenciesWhite();
			break;
		default:
			break;
		}
		// top
		if (adjacent[0]) {
            flipDirVector(gt, -1, 0);
		}
		// top right
		if (adjacent[1]) {
            flipDirVector(gt, -1, 1);
		}
		// right
		if (adjacent[2]) {
            flipDirVector(gt, 0, 1);
		}
		// bottom right
		if (adjacent[3]) {
            flipDirVector(gt, 1, 1);
		}
		// bottom
		if (adjacent[4]) {
            flipDirVector(gt, 1, 0);
		}
		// bottom left
		if (adjacent[5]) {
            flipDirVector(gt, 1, -1);
		}
		// left
		if (adjacent[6]) {
            flipDirVector(gt, 0, -1);
		}
		// top left
		if (adjacent[7]) {
            flipDirVector(gt, -1, -1);
		}
	}

    private void flipDirVector(GameTile previous, int dRow, int dCol){
        GameTile current = tilesAdapter.getNextGameTile(previous, dRow, dCol);
        if (current.getColor().getFlip() == turn){
            current.setAnimateFlip(true);
            current.setColor(turn);
            delta++;
            flipDirVector(current, dRow, dCol);
        }
    }

	public void onNewGame(View v) {
		createGame();
	}

//	@Override
//	public boolean onTouchEvent(MotionEvent ev) {
//		SGD.onTouchEvent(ev);
//		return true;
//	}

//	private class ScaleListener extends
//			ScaleGestureDetector.SimpleOnScaleGestureListener {
//		@Override
//		public boolean onScale(ScaleGestureDetector detector) {
//			scale *= detector.getScaleFactor();
//			scale = Math.max(0.1f, Math.min(scale, 5.0f));
//			boardImage.setScaleX(scale);
//			boardImage.setScaleY(scale);
//			return true;
//			
//		}
//	}

}
