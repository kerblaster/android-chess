/*
 * Bishop class
 * 
 * @author Renard Tumbokon, Nikhil Menon
 */

package com.cs214.group08.chessapp;

public class Bishop extends Piece{ 
	public Bishop(int row, int col, char color, Board board){
		super(row, col, color, board);
	}
	
	/*
	 * Add possible moves to possibleMoves ArrayList
	 * include capture logic here
	 */
	public void getPossibleMoves(){
		possibleMoves.clear(); 
		if (!isAlive){
			return;
		}
		if (getColor() == 'w'){
			for(int i= 1; i<=8; i++){ 
				if (checkBoardBounds(getRow()+i, getCol()+i)) {
					if (board.getCell(getRow()+i, getCol()+i).getPiece() == null) {
						possibleMoves.add(board.getCell(getRow()+i, getCol()+i));
					}
					else if (board.getCell(getRow()+i, getCol()+i).getPiece() != null) {
						if (board.getCell(getRow()+i, getCol()+i).getPiece().getColor() == 'b'){
							possibleMoves.add(board.getCell(getRow()+i, getCol()+i));
						} else{
							break; //bump into own piece color
						}
					}
	
				}
			}
	
			for(int i= 1; i<=8; i++){ 
				if (checkBoardBounds(getRow()+i, getCol()-i)) {
					if (board.getCell(getRow()+i, getCol()-i).getPiece() == null) {
						possibleMoves.add(board.getCell(getRow()+i, getCol()-i));
					}
					else if (board.getCell(getRow()+i, getCol()-i).getPiece() != null) {
						if (board.getCell(getRow()+i, getCol()-i).getPiece().getColor() == 'b'){
							possibleMoves.add(board.getCell(getRow()+i, getCol()-i));
						} else{
							break; //bump into own piece color
						}
					}
				}
			}
	
			for(int i= 1; i<=8; i++){ 
				if (checkBoardBounds(getRow()-i, getCol()+i)) {
					if (board.getCell(getRow()-i, getCol()+i).getPiece() == null) {
						possibleMoves.add(board.getCell(getRow()-i, getCol()+i));
					}
					else if (board.getCell(getRow()-i, getCol()+i).getPiece() != null) {
						if (board.getCell(getRow()-i, getCol()+i).getPiece().getColor() == 'b'){
							possibleMoves.add(board.getCell(getRow()-i, getCol()+i));
						} else{
							break; //bump into own piece color
						}
					}
				}
			}
	
			for(int i= 1; i<=8; i++){ 
				if (checkBoardBounds(getRow()-i, getCol()-i)) {
					if (board.getCell(getRow()-i, getCol()-i).getPiece() == null) {
						possibleMoves.add(board.getCell(getRow()-i, getCol()-i));
					}
					else if (board.getCell(getRow()-i, getCol()-i).getPiece() != null) {
						if (board.getCell(getRow()-i, getCol()-i).getPiece().getColor() == 'b'){
							possibleMoves.add(board.getCell(getRow()-i, getCol()-i));
						} else{
							break; //bump into own piece color
						}
					}
				}
			}
	
		}else { //color of piece is black
		    for(int i= 1; i<=8; i++){ 
					if (checkBoardBounds(getRow()+i, getCol()+i)) {
						if (board.getCell(getRow()+i, getCol()+i).getPiece() == null) {
							possibleMoves.add(board.getCell(getRow()+i, getCol()+i));
						}
						else if (board.getCell(getRow()+i, getCol()+i).getPiece() != null) {
							if (board.getCell(getRow()+i, getCol()+i).getPiece().getColor() == 'w'){
								possibleMoves.add(board.getCell(getRow()+i, getCol()+i));
							} else{
								break; //bump into own piece color
							}
						}
					}
				}
		
				for(int i= 1; i<=8; i++){ 
					if (checkBoardBounds(getRow()+i, getCol()-i)) {
						if (board.getCell(getRow()+i, getCol()-i).getPiece() == null) {
							possibleMoves.add(board.getCell(getRow()+i, getCol()-i));
						}
						else if (board.getCell(getRow()+i, getCol()-i).getPiece() != null) {
							if (board.getCell(getRow()+i, getCol()-i).getPiece().getColor() == 'w'){
								possibleMoves.add(board.getCell(getRow()+i, getCol()-i));
							} else{
								break; //bump into own piece color
							}
						}
					}
				}
		
				for(int i= 1; i<=8; i++){ 
					if (checkBoardBounds(getRow()-i, getCol()+i)) {
						if (board.getCell(getRow()-i, getCol()+i).getPiece() == null) {
							possibleMoves.add(board.getCell(getRow()-i, getCol()+i));
						}
						else if (board.getCell(getRow()-i, getCol()+i).getPiece() != null) {
							if (board.getCell(getRow()-i, getCol()+i).getPiece().getColor() == 'w'){
								possibleMoves.add(board.getCell(getRow()-i, getCol()+i));
							} else{
								break; //bump into own piece color
							}
						}
					}
				}
		
				for(int i= 1; i<=8; i++){ 
					if (checkBoardBounds(getRow()-i, getCol()-i)) {
						if (board.getCell(getRow()-i, getCol()-i).getPiece() == null) {
							possibleMoves.add(board.getCell(getRow()-i, getCol()-i));
						}
						else if (board.getCell(getRow()-i, getCol()-i).getPiece() != null) {
							if (board.getCell(getRow()-i, getCol()-i).getPiece().getColor() == 'w'){
								possibleMoves.add(board.getCell(getRow()-i, getCol()-i));
							} else{
								break; //bump into own piece color
							}
						}
					}
				}
		}			
    }
	
	public boolean canMove(int toRow, int toCol, String instruction){
		getPossibleMoves();
		for (Cell cell : possibleMoves){
			if(cell == board.getCell(toRow, toCol)){ //found legal move
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Must update values after successful move
	 */
	public void postMoveUpdate(int toRow, int toCol, String instruction){
		board.record(new MoveData(getRow(), getCol(), toRow, toCol, null)); //save before setting new row/col
		setRow(toRow);
		setCol(toCol);
	}
	
	@Override
	public String toString(){
		return super.toString() + "B"; //wb or bb
	}
}