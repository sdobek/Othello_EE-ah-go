package com.sdobek.othello;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.GridView;

import com.example.othello.R;
import com.sdobek.othello.models.Color;
import com.sdobek.othello.models.GameTile;

public class GameActivity extends Activity {
	
	List<GameTile> tiles;
	TileArrayAdapter tilesAdapter;
	GridView board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        board = (GridView) findViewById(R.id.gv_board);
        tiles = new ArrayList<GameTile>();
        tilesAdapter = new TileArrayAdapter(this, tiles);
        
        for (int i = 0; i < 64; i++){
        	GameTile g = new GameTile();
        	tiles.add(g);
        }
        tiles.get(27).setColor(Color.BLACK);
        tiles.get(28).setColor(Color.WHITE);
        tiles.get(35).setColor(Color.WHITE);
        tiles.get(36).setColor(Color.BLACK);
        //tiles.addAll(tiles);
        board.setAdapter(tilesAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }
    
}
