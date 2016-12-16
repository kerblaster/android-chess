package com.cs214.group08.chessapp;

import java.io.Serializable;

/**
 * Created by Renard on 12/15/2016.
 */

public class MoveData implements Serializable{
    private int fromRow;
    private int fromCol;
    private int toRow;
    private int toCol;
    private boolean[] specialCases;
    private char transformTo; //for pawn transform use

    public MoveData(int fromRow, int fromCol, int toRow, int toCol, boolean[] specialCases){
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
        this.specialCases = specialCases;
        this.transformTo = 0;
    }

    public int getFromRow(){
        return fromRow;
    }
    public int getFromCol(){
        return fromCol;
    }
    public int getToRow(){
        return toRow;
    }
    public int getToCol(){
        return toCol;
    }

    public boolean[] getSpecialCases(){
        return specialCases;
    }

    public void setTransformTo(char symbol){
        transformTo = symbol;
    }
    public char getTransformTo() {
        return transformTo;
    }


    public String getInstruction(){
        String from = new String(Board.convertRowCol(fromRow, fromCol));
        String to = new String(Board.convertRowCol(toRow, toCol));
        if (transformTo != 0) {
            return from + " " + to + " " + transformTo;
        }
        return from + " " + to;
    }
}
