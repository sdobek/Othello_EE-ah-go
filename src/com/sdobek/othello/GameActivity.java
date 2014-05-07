package com.sdobek.othello;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.example.othello.R;
import com.sdobek.othello.models.Color;
import com.sdobek.othello.models.GameTile;

public class GameActivity extends Activity {
	
	public static boolean gameOver;
	
	List<GameTile> tiles;
	TileArrayAdapter tilesAdapter;
	GridView board;
	Color turn;
	
	TextView infoBox;
	TextView blackScore;
	TextView whiteScore;
	int b_score = 2;
	int w_score = 2;
	int delta = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        board = (GridView) findViewById(R.id.gv_board);
        tiles = new ArrayList<GameTile>();
        tilesAdapter = new TileArrayAdapter(this, tiles);
        board.setAdapter(tilesAdapter);
        blackScore = (TextView) findViewById(R.id.tv_blackScore);
        blackScore.setText("2");
        whiteScore = (TextView) findViewById(R.id.tv_whiteScore);
        whiteScore.setText("2");
        infoBox = (TextView) findViewById(R.id.tv_infoBox);
        infoBox.setText("Black's turn");
        turn = Color.BLACK;
        
        for (int i = 0; i < 64; i++){
        	GameTile g = new GameTile();
        	tiles.add(g);
        }
        tiles.get(27).setColor(Color.BLACK);
        tiles.get(28).setColor(Color.WHITE);
        tiles.get(35).setColor(Color.WHITE);
        tiles.get(36).setColor(Color.BLACK);
        //tiles.addAll(tiles);
        tilesAdapter.setValidMoves();
        board.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				GameTile gt = tiles.get(position);
				if (gt.getCanMove(turn)){
					gt.setColor(turn);
					delta = 0;
					flip(gt, position);
					//update
					if (turn == Color.BLACK){
						b_score += 1 + delta;
						w_score -= delta;
					}
					else {
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
    
    public void nextTurn(){
    	boolean validBlack = tilesAdapter.getValidBlack();
    	boolean validWhite = tilesAdapter.getValidWhite();
    	if (!validBlack && !validWhite){ //no more available moves
    		if (b_score > w_score){
    			infoBox.setText("Game Over.  Black Wins!");
    		}
    		else if (b_score < w_score){
    			infoBox.setText("Game Over.  White Wins!");
    		}
    		else {
    			infoBox.setText("Game Over.  Draw!");
    		}
    	}
    	turn = turn.getFlip();
    	if (turn == Color.BLACK && validBlack){
    		infoBox.setText("Black's turn");
    	}
    	else if (turn == Color.WHITE && validWhite){
    		infoBox.setText("White's turn");
    	}
    	else if (!validBlack){
    		infoBox.setText("Black has no moves.  Still white's turn");
    	}
    	else if (!validWhite){
    		infoBox.setText("White has no moves.  Still black's turn");
    	}
    }
    
    public void flip(GameTile gt, int position){
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
    	//top
    	if (adjacent[0]){
    		offset = -8;
    		next = tilesAdapter.getItem(position+offset);
    		while (next.getColor().getFlip() == turn){
    			next.setColor(turn);
    			delta++;
    			offset -= 8;
        		next = tilesAdapter.getItem(position+offset); 
    		}
    	}
    	//top right
    	if (adjacent[1]){
    		offset = -7;
    		next = tilesAdapter.getItem(position+offset);
    		while (next.getColor().getFlip() == turn){
    			next.setColor(turn);
    			delta++;
    			offset -= 7;
        		next = tilesAdapter.getItem(position+offset); 
    		}
    	}
    	//right
    	if (adjacent[2]){
    		offset = 1;
    		next = tilesAdapter.getItem(position+offset);
    		while (next.getColor().getFlip() == turn){
    			next.setColor(turn);
    			delta++;
    			offset += 1;
        		next = tilesAdapter.getItem(position+offset); 
    		}
    	}
    	//bottom right
    	if (adjacent[3]){
    		offset = 9;
    		next = tilesAdapter.getItem(position+offset);
    		while (next.getColor().getFlip() == turn){
    			next.setColor(turn);
    			delta++;
    			offset += 9;
        		next = tilesAdapter.getItem(position+offset); 
    		}
    	}
    	//bottom
    	if (adjacent[4]){
    		offset = 8;
    		next = tilesAdapter.getItem(position+offset);
    		while (next.getColor().getFlip() == turn){
    			next.setColor(turn);
    			delta++;
    			offset += 8;
        		next = tilesAdapter.getItem(position+offset); 
    		}
    	}
    	//bottom left
    	if (adjacent[5]){
    		offset = 7;
    		next = tilesAdapter.getItem(position+offset);
    		while (next.getColor().getFlip() == turn){
    			next.setColor(turn);
    			delta++;
    			offset += 7;
        		next = tilesAdapter.getItem(position+offset); 
    		}
    	}
    	//left
    	if (adjacent[6]){
    		offset = -1;
    		next = tilesAdapter.getItem(position+offset);
    		while (next.getColor().getFlip() == turn){
    			next.setColor(turn);
    			delta++;
    			offset -= 1;
        		next = tilesAdapter.getItem(position+offset); 
    		}
    	}
    	//top left
    	if (adjacent[7]){
    		offset = -9;
    		next = tilesAdapter.getItem(position+offset);
    		while (next.getColor().getFlip() == turn){
    			next.setColor(turn);
    			delta++;
    			offset -= 9;
        		next = tilesAdapter.getItem(position+offset); 
    		}
    	}
    	
    	
    }
    
    
}
