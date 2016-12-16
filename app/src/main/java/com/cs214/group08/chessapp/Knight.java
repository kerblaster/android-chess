/*
 * Knight class
 * 
 * @author Renard Tumbokon, Nikhil Menon
 */

package com.cs214.group08.chessapp;

public class Knight extends Piece{ 
	public Knight(int row, int col, char color, Board board){
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
			if (checkBoardBounds(getRow()+2, getCol()+1)){ 
				if(board.getCell(getRow()+2, getCol()+1).getPiece() == null || (board.getCell(getRow()+2, getCol()+1).getPiece().getColor() == 'b')){
						possibleMoves.add(board.getCell(getRow()+2, getCol()+1));
					}
			}
			if (checkBoardBounds(getRow()-2, getCol()-1)){ 
				if(board.getCell(getRow()-2, getCol()-1).getPiece() == null || (board.getCell(getRow()-2, getCol()-1).getPiece().getColor() == 'b')){
						possibleMoves.add(board.getCell(getRow()-2, getCol()-1));
					}
			}
			if (checkBoardBounds(getRow()+2, getCol()-1)){ 
				if(board.getCell(getRow()+2, getCol()-1).getPiece() == null || (board.getCell(getRow()+2, getCol()-1).getPiece().getColor() == 'b')){
						possibleMoves.add(board.getCell(getRow()+2, getCol()-1));
					}
			}
			if (checkBoardBounds(getRow()-2, getCol()+1)){ 
				if(board.getCell(getRow()-2, getCol()+1).getPiece() == null || (board.getCell(getRow()-2, getCol()+1).getPiece().getColor() == 'b')){
						possibleMoves.add(board.getCell(getRow()-2, getCol()+1));
					}	
			}			
			
			if (checkBoardBounds(getRow()+1, getCol()+2)){ 
				if(board.getCell(getRow()+1, getCol()+2).getPiece() == null || (board.getCell(getRow()+1, getCol()+2).getPiece().getColor() == 'b')) {
						possibleMoves.add(board.getCell(getRow()+1, getCol()+2));
					}
			}
			if (checkBoardBounds(getRow()-1, getCol()-2)){ 
				if(board.getCell(getRow()-1, getCol()-2).getPiece() == null || (board.getCell(getRow()-1, getCol()-2).getPiece().getColor() == 'b')){
						possibleMoves.add(board.getCell(getRow()-1, getCol()-2));
					}
			}
			if (checkBoardBounds(getRow()+1, getCol()-2)){ 
				if(board.getCell(getRow()+1, getCol()-2).getPiece() == null || (board.getCell(getRow()+1, getCol()-2).getPiece().getColor() == 'b')){
						possibleMoves.add(board.getCell(getRow()+1, getCol()-2));
					}
			}
			if (checkBoardBounds(getRow()-1, getCol()+2)){ 
				if(board.getCell(getRow()-1, getCol()+2).getPiece() == null || (board.getCell(getRow()-1, getCol()+2).getPiece().getColor() == 'b')){
						possibleMoves.add(board.getCell(getRow()-1, getCol()+2));
					}	
			}
		}	

		else {
			if (checkBoardBounds(getRow()+2, getCol()+1)){ 
				if(board.getCell(getRow()+2, getCol()+1).getPiece() == null || (board.getCell(getRow()+2, getCol()+1).getPiece().getColor() == 'w')){
						possibleMoves.add(board.getCell(getRow()+2, getCol()+1));
				}
			}
			if (checkBoardBounds(getRow()-2, getCol()-1)){ 
				if(board.getCell(getRow()-2, getCol()-1).getPiece() == null || (board.getCell(getRow()-2, getCol()-1).getPiece().getColor() == 'w')){
						possibleMoves.add(board.getCell(getRow()-2, getCol()-1));
				}
			}
			if (checkBoardBounds(getRow()+2, getCol()-1)){ 
				if(board.getCell(getRow()+2, getCol()-1).getPiece() == null || (board.getCell(getRow()+2, getCol()-1).getPiece().getColor() == 'w')){
						possibleMoves.add(board.getCell(getRow()+2, getCol()-1));
				}
			}
			if (checkBoardBounds(getRow()-2, getCol()+1)){ 
				if(board.getCell(getRow()-2, getCol()+1).getPiece() == null || (board.getCell(getRow()-2, getCol()+1).getPiece().getColor() == 'w')){
						possibleMoves.add(board.getCell(getRow()-2, getCol()+1));
				}	
			}			
			
			if (checkBoardBounds(getRow()+1, getCol()+2)){ 
				if(board.getCell(getRow()+1, getCol()+2).getPiece() == null || (board.getCell(getRow()+1, getCol()+2).getPiece().getColor() == 'w')) {
						possibleMoves.add(board.getCell(getRow()+1, getCol()+2));
				}
			}
			if (checkBoardBounds(getRow()-1, getCol()-2)){ 
				if(board.getCell(getRow()-1, getCol()-2).getPiece() == null || (board.getCell(getRow()-1, getCol()-2).getPiece().getColor() == 'w')){
						possibleMoves.add(board.getCell(getRow()-1, getCol()-2));
				}
			}
			if (checkBoardBounds(getRow()+1, getCol()-2)){ 
				if(board.getCell(getRow()+1, getCol()-2).getPiece() == null || (board.getCell(getRow()+1, getCol()-2).getPiece().getColor() == 'w')){
						possibleMoves.add(board.getCell(getRow()+1, getCol()-2));
				}
			}
			if (checkBoardBounds(getRow()-1, getCol()+2)){ 
				if(board.getCell(getRow()-1, getCol()+2).getPiece() == null || (board.getCell(getRow()-1, getCol()+2).getPiece().getColor() == 'w')){
						possibleMoves.add(board.getCell(getRow()-1, getCol()+2));
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
		return super.toString() + "N"; //wn or bn
	}
}