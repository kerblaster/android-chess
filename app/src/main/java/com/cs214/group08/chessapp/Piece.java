/*
 * Superclass for chess pieces
 * 
 * @author Renard Tumbokon, Nikhil Menon
 */

package com.cs214.group08.chessapp;

import java.util.ArrayList;

public abstract class Piece {
	private int row;
	private int col;
	private char color;
	boolean isAlive;
	public ArrayList<Cell> possibleMoves = new ArrayList<Cell>();
	Board board;
	
	public Piece(int row, int col, char color, Board board){
		this.row = row;
		this.col = col;
		this.color = color;
		this.board = board;
		isAlive = true;
	}

	public abstract boolean canMove(int toRow, int toCol, String instruction);
	public abstract void postMoveUpdate(int toRow, int toCol, String instruction);
	public abstract void getPossibleMoves();
	
	public void die(){
		isAlive = false; 
	}

	//setters for coordinates
	public void setRow(int x){row = x;}
	public void setCol(int y){col = y;}
	
	//getters for coordinates
	public int getRow(){return row;}
	public int getCol(){return col;}
	public char getColor(){return color;}
	
	boolean checkBoardBounds(int row, int col){
		return Board.checkBoardBounds(row, col);
	}
	
	public String toString(){
		if (color == 'w'){
			return "w";
		} else{
			return "b";
		}
	}
}
