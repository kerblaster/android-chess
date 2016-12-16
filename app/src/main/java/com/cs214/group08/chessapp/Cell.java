/*
 * Board has individual Cell classes
 * 
 * @author Renard Tumbokon, Nikhil Menon
 */

package com.cs214.group08.chessapp;

public class Cell {
	int row;
	int col;
	boolean isDark;	
	Piece piece;
	
	public Cell(int row, int col){
		this.row = row;
		this.col = col;
		piece = null;
		
		if (row % 2 == 0 && col % 2 == 1){
			// row even, col odd
			isDark = true;
		} else if (row % 2 == 1 && col % 2 == 0){
			// row odd, col even
			isDark = true;
		} else{
			isDark = false;
		}
	}
	
	public void occupy(Piece placedPiece){
		piece = placedPiece;
	}
	
	public void free(){
		piece = null;
	}
	
	public Piece getPiece(){
		return piece;
	}
	
	public void setPiece(Piece piece){
		this.piece = piece;
	}
	
	public String toString(){
		if (piece == null){ //empty cell
			if (isDark){
				return "##";
			} else{
				return "  ";
			}
		}
		if (!piece.isAlive){
			free(); //check if piece is still alive to print
		}
		return piece.toString(); //piece value
	}
}
