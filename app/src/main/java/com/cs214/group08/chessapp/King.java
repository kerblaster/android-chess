/*
 * King class
 * 
 * @author Renard Tumbokon, Nikhil Menon
 */

package com.cs214.group08.chessapp;

public class King extends Piece{ 
	public boolean neverMoved; //for castling
	
	public King(int row, int col, char color, Board board){
		super(row, col, color, board);
		neverMoved = true;
	}
	
	public boolean isCheck(){
		char enemyColor;
		if (getColor() == 'w'){
			enemyColor = 'b';
		} else{
			enemyColor = 'w';
		}
		return board.isSpaceAttacked(enemyColor, getRow(), getCol()); //see if enemyColor is attacking space King is on
	}
	public boolean isCheckAt(int row, int col){ //custom checker for possibleMoves
		char enemyColor;
		if (getColor() == 'w'){
			enemyColor = 'b';
		} else{
			enemyColor = 'w';
		}
		return board.isSpaceAttacked(enemyColor, row, col); //see if enemyColor is attacking space King is on
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
			if (this.neverMoved && !isCheck()){ //castling
				//Castle king side
				if (board.getCell(7,7).getPiece() != null && board.getCell(7,7).getPiece().getColor() == 'w' && board.getCell(7,7).getPiece() instanceof Rook){
					Rook rookToCastle = (Rook)board.getCell(7,7).getPiece();
					if (rookToCastle.neverMoved){ //check if rook moved
						if (!isCheckAt(7,6) && board.getCell(7, 6).getPiece() == null && board.getCell(7,5).getPiece() == null){
							possibleMoves.add(board.getCell(7,6));
						}
					}
				}
				//Castle queen side
				if (board.getCell(7,0).getPiece() != null && board.getCell(7,0).getPiece().getColor() == 'w' && board.getCell(7,0).getPiece() instanceof Rook){
					Rook rookToCastle = (Rook)board.getCell(7,0).getPiece();
					if ((rookToCastle).neverMoved){ //check if rook moved
						if (!isCheckAt(7,2) && board.getCell(7, 1).getPiece() == null && board.getCell(7,2).getPiece() == null && board.getCell(7,3).getPiece() == null){
							possibleMoves.add(board.getCell(7,2));
						}
					}
				}
			}
			if (checkBoardBounds(getRow()-1, getCol())){ //up
				if(board.getCell(getRow()-1, getCol()).getPiece() == null || (board.getCell(getRow()-1, getCol()).getPiece().getColor() == 'b')){
						possibleMoves.add(board.getCell(getRow()-1, getCol()));
				}
			}			
			if (checkBoardBounds(getRow()-1, getCol()+1)){ //up+right
				if(board.getCell(getRow()-1, getCol()+1).getPiece() == null || (board.getCell(getRow()-1, getCol()+1).getPiece().getColor() == 'b')){
						possibleMoves.add(board.getCell(getRow()-1, getCol()+1));
				}
			}			
			if (checkBoardBounds(getRow(), getCol()+1)){ //right
				if(board.getCell(getRow(), getCol()+1).getPiece() == null || (board.getCell(getRow(), getCol()+1).getPiece().getColor() == 'b')){
						possibleMoves.add(board.getCell(getRow(), getCol()+1));
				}
			}
			if (checkBoardBounds(getRow()+1, getCol()+1)){ //down+right
				if(board.getCell(getRow()+1, getCol()+1).getPiece() == null || (board.getCell(getRow()+1, getCol()+1).getPiece().getColor() == 'b')){
						possibleMoves.add(board.getCell(getRow()+1, getCol()+1));
				}
			}					
			if (checkBoardBounds(getRow()+1, getCol())){ //down
				if(board.getCell(getRow()+1, getCol()).getPiece() == null || (board.getCell(getRow()+1, getCol()).getPiece().getColor() == 'b')){
						possibleMoves.add(board.getCell(getRow()+1, getCol()));
				}
			}
			if (checkBoardBounds(getRow()+1, getCol()-1)){ //down+left
				if(board.getCell(getRow()+1, getCol()-1).getPiece() == null || (board.getCell(getRow()+1, getCol()-1).getPiece().getColor() == 'b')){
						possibleMoves.add(board.getCell(getRow()+1, getCol()-1));
				}
			}		
			if (checkBoardBounds(getRow(), getCol()-1)){ //left
				if(board.getCell(getRow(), getCol()-1).getPiece() == null || (board.getCell(getRow(), getCol()-1).getPiece().getColor() == 'b')){
						possibleMoves.add(board.getCell(getRow(), getCol()-1));
				}	
			}	
			if (checkBoardBounds(getRow()-1, getCol()-1)){ //up+left
				if(board.getCell(getRow()-1, getCol()-1).getPiece() == null || (board.getCell(getRow()-1, getCol()-1).getPiece().getColor() == 'b')){
						possibleMoves.add(board.getCell(getRow()-1, getCol()-1));
				}
			}					
		}
		else { //if black
			if (this.neverMoved && !isCheck()){ //castling
				//Castle king side
				if (board.getCell(0,7).getPiece() != null && board.getCell(0,7).getPiece().getColor() == 'b' && board.getCell(0,7).getPiece() instanceof Rook){
					Rook rookToCastle = (Rook)board.getCell(0,7).getPiece();
					if ((rookToCastle).neverMoved){ //check if rook moved
						if (!isCheckAt(0,6) && board.getCell(0, 6).getPiece() == null && board.getCell(0,5).getPiece() == null){
							possibleMoves.add(board.getCell(0,6));
						}
					}
				}
				//Castle queen side
				if (board.getCell(0,0).getPiece() != null && board.getCell(0,0).getPiece().getColor() == 'b' && board.getCell(0,0).getPiece() instanceof Rook){
					Rook rookToCastle = (Rook)board.getCell(0,0).getPiece();
					if ((rookToCastle).neverMoved){ //check if rook moved
						if (!isCheckAt(0,2) && board.getCell(0, 1).getPiece() == null && board.getCell(0,2).getPiece() == null && board.getCell(0,3).getPiece() == null){
							possibleMoves.add(board.getCell(0,2));
						}
					}
				}
			}
			if (checkBoardBounds(getRow()-1, getCol())){ //up
				if(board.getCell(getRow()-1, getCol()).getPiece() == null || (board.getCell(getRow()-1, getCol()).getPiece().getColor() == 'w')){
						possibleMoves.add(board.getCell(getRow()-1, getCol()));
				}
			}			
			if (checkBoardBounds(getRow()-1, getCol()+1)){ //up+right
				if(board.getCell(getRow()-1, getCol()+1).getPiece() == null || (board.getCell(getRow()-1, getCol()+1).getPiece().getColor() == 'w')){
						possibleMoves.add(board.getCell(getRow()-1, getCol()+1));
				}
			}			
			if (checkBoardBounds(getRow(), getCol()+1)){ //right
				if(board.getCell(getRow(), getCol()+1).getPiece() == null || (board.getCell(getRow(), getCol()+1).getPiece().getColor() == 'w')){
						possibleMoves.add(board.getCell(getRow(), getCol()+1));
				}
			}
			if (checkBoardBounds(getRow()+1, getCol()+1)){ //down+right
				if(board.getCell(getRow()+1, getCol()+1).getPiece() == null || (board.getCell(getRow()+1, getCol()+1).getPiece().getColor() == 'w')){
						possibleMoves.add(board.getCell(getRow()+1, getCol()+1));
				}
			}					
			if (checkBoardBounds(getRow()+1, getCol())){ //down
				if(board.getCell(getRow()+1, getCol()).getPiece() == null || (board.getCell(getRow()+1, getCol()).getPiece().getColor() == 'w')){
						possibleMoves.add(board.getCell(getRow()+1, getCol()));
				}
			}
			if (checkBoardBounds(getRow()+1, getCol()-1)){ //down+left
				if(board.getCell(getRow()+1, getCol()-1).getPiece() == null || (board.getCell(getRow()+1, getCol()-1).getPiece().getColor() == 'w')){
						possibleMoves.add(board.getCell(getRow()+1, getCol()-1));
				}
			}		
			if (checkBoardBounds(getRow(), getCol()-1)){ //left
				if(board.getCell(getRow(), getCol()-1).getPiece() == null || (board.getCell(getRow(), getCol()-1).getPiece().getColor() == 'w')){
						possibleMoves.add(board.getCell(getRow(), getCol()-1));
				}	
			}	
			if (checkBoardBounds(getRow()-1, getCol()-1)){ //up+left
				if(board.getCell(getRow()-1, getCol()-1).getPiece() == null || (board.getCell(getRow()-1, getCol()-1).getPiece().getColor() == 'w')){
						possibleMoves.add(board.getCell(getRow()-1, getCol()-1));
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
		boolean[] specialCases = new boolean[]{neverMoved, false}; //(neverMoved, castledThisTurn)
		if (this.neverMoved && instruction.equals("peekMoveIfIsCheck")){ //castle check (should only be called if actually castled)
			//move rook when castle
			if (getColor() == 'w'){
				if (toCol == 6){ //castle king side
					specialCases[1] = true;
					Rook rookToCastle = (Rook)board.getCell(7,7).getPiece();
					board.setCell(rookToCastle, 7, 5);
					board.getCell(7, 7).free();
					rookToCastle.postMoveUpdate(7, 5, "castle");
				} else if (toCol == 2){ //castle queen side
					specialCases[1] = true;
					Rook rookToCastle = (Rook)board.getCell(7,0).getPiece();
					board.setCell(rookToCastle, 7, 3);
					board.getCell(7, 0).free();
					rookToCastle.postMoveUpdate(7, 3, "castle");
				}				
			} else{ // color == 'b'
				if (toCol == 6){ //castle king side
					specialCases[1] = true;
					Rook rookToCastle = (Rook)board.getCell(0,7).getPiece();
					board.setCell(rookToCastle, 0, 5);
					board.getCell(0, 7).free();
					rookToCastle.postMoveUpdate(0, 5, "castle");
				} else if (toCol == 2){ //castle queen side
					specialCases[1] = true;
					Rook rookToCastle = (Rook)board.getCell(0,0).getPiece();
					board.setCell(rookToCastle, 0, 3);
					board.getCell(0, 0).free();
					rookToCastle.postMoveUpdate(0, 3, "castle");
				}						
			}
		}
		if (!instruction.equals("peekMoveIfIsCheck")){ // guard against peek moves (execute when true move)
			board.record(new MoveData(getRow(), getCol(), toRow, toCol, specialCases)); //save before setting new row/col
			neverMoved = false;
		}
		setRow(toRow);
		setCol(toCol);
	}
	
	@Override
	public String toString(){
		return super.toString() + "K"; //wk or bk
	}
}