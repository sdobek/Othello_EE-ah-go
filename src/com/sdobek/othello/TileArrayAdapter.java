package com.sdobek.othello;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.othello.R;
import com.sdobek.othello.models.GameTile;

public class TileArrayAdapter extends ArrayAdapter<GameTile> {

	private static LayoutInflater inflator = null;
	
	public TileArrayAdapter(Context context, List<GameTile> tiles){
		super(context, R.layout.game_tile, tiles);
		inflator = LayoutInflater.from(getContext());
	}
	
	 public View getView(int position, View convertView, ViewGroup parent) {
		 GameTile gt = this.getItem(position); 
		 if (convertView == null){
			 convertView = inflator.inflate(R.layout.game_tile, parent, false);
		 }
		 
		 ImageView iv_piece = (ImageView) convertView.findViewById(R.id.iv_piece);
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
}
