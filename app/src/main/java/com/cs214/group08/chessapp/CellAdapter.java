package com.cs214.group08.chessapp;

import android.graphics.Color;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Renard on 12/14/2016.
 */

public class CellAdapter extends BaseAdapter {

    private Context mContext;
    private List<Integer> thumbsList;// references to our images
    private Integer[] mThumbIds;

    //CONSTRUCTOR
    public CellAdapter(Context c, Board board) {
        mContext = c;
        //get image using data from board
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getCell(i,j).getPiece();
                if (piece == null){ //no piece
                    thumbsList.add(R.drawable.pixel);
                } else if(piece.getColor() == 'w'){ //white
                    if (piece instanceof Pawn) thumbsList.add(R.drawable.pawn_w);
                    else if (piece instanceof Bishop) thumbsList.add(R.drawable.bishop_w);
                    else if (piece instanceof Knight) thumbsList.add(R.drawable.knight_w);
                    else if (piece instanceof Rook) thumbsList.add(R.drawable.rook_w);
                    else if (piece instanceof Queen) thumbsList.add(R.drawable.queen_w);
                    else if (piece instanceof King) thumbsList.add(R.drawable.king_w);
                    else System.out.println("Bad Code Error: Cannot find instanceof white piece");
                } else{ //black
                    if (piece instanceof Pawn) thumbsList.add(R.drawable.pawn_b);
                    else if (piece instanceof Bishop) thumbsList.add(R.drawable.bishop_b);
                    else if (piece instanceof Knight) thumbsList.add(R.drawable.knight_b);
                    else if (piece instanceof Rook) thumbsList.add(R.drawable.rook_b);
                    else if (piece instanceof Queen) thumbsList.add(R.drawable.queen_b);
                    else if (piece instanceof King) thumbsList.add(R.drawable.king_b);
                    else System.out.println("Bad Code Error: Cannot find instanceof black piece");
                }
            }
        }
        mThumbIds = new Integer[thumbsList.size()];
        for(int i = 0; i < thumbsList.size(); i++) mThumbIds[i] = thumbsList.get(i);
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public Integer getImgID(int position){
        return mThumbIds[position];
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(45, 45)); //360/8=45 (gridView size)
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        } else {
            imageView = (ImageView) convertView;
        }

        if (position % 2 == 0){
            imageView.setBackgroundColor(Color.parseColor("#FFFFFF")); //white
        } else{
            imageView.setBackgroundColor(Color.parseColor("#000000")); //black
        }
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }
}
