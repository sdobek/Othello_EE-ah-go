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

import com.example.othello.R;
import com.sdobek.othello.models.Color;
import com.sdobek.othello.models.GameTile;

public class GameActivity extends Activity {
	
	List<GameTile> tiles;
	TileArrayAdapter tilesAdapter;
	GridView board;
	Color turn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        board = (GridView) findViewById(R.id.gv_board);
        tiles = new ArrayList<GameTile>();
        tilesAdapter = new TileArrayAdapter(this, tiles);
        board.setAdapter(tilesAdapter);
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
					flip(gt, position);
					//update
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
    	turn = turn.getFlip();
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
    			offset -= 8;
        		next = tilesAdapter.getItem(position+offset); 
    		}
    	}
    	//bottom
    	if (adjacent[4]){
    		offset = 8;
    		next = tilesAdapter.getItem(position+offset);
    		while (next.getColor().getFlip() == turn){
    			next.setColor(turn);
    			offset += 8;
        		next = tilesAdapter.getItem(position+offset); 
    		}
    	}
    	//left
    	if (adjacent[6]){
    		offset = -1;
    		next = tilesAdapter.getItem(position+offset);
    		while (next.getColor().getFlip() == turn){
    			next.setColor(turn);
    			offset -= 1;
        		next = tilesAdapter.getItem(position+offset); 
    		}
    	}
    	//right
    	if (adjacent[2]){
    		offset = 1;
    		next = tilesAdapter.getItem(position+offset);
    		while (next.getColor().getFlip() == turn){
    			next.setColor(turn);
    			offset += 1;
        		next = tilesAdapter.getItem(position+offset); 
    		}
    	}
    	
    }
    
    
}
