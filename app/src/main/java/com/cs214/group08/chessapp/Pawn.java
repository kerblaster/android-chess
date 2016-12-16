/*
 * Pawn class
 * 
 * @author Renard Tumbokon, Nikhil Menon
 */

package com.cs214.group08.chessapp;

public class Pawn extends Piece{ 
	public boolean canDieByEnpassent;
	public boolean neverMoved; //special pawn-only variable, check if pawn never moved yet
	
	public Pawn(int row, int col, char color, Board board){
		super(row, col, color, board);
		canDieByEnpassent = false;
		neverMoved = true;
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
		if (getColor() == 'w'){ //moves differ per pawn color
			//can only move row downwards (7->0)
			if (neverMoved){ //special rule: can move twice
				for (int i = 1; i <= 2; i++){
					if (checkBoardBounds(getRow()-i, getCol()) && board.getCell(getRow()-i, getCol()).getPiece() == null){ //up i
						possibleMoves.add(board.getCell(getRow()-i, getCol()));
					} else{break;}
				}
			} else{
			//normal move
				if (checkBoardBounds(getRow()-1, getCol()) && board.getCell(getRow()-1, getCol()).getPiece() == null){ //up 1
					possibleMoves.add(board.getCell(getRow()-1, getCol()));
				}
			}
			//attack
			if (checkBoardBounds(getRow()-1, getCol()-1) && board.getCell(getRow()-1, getCol()-1).getPiece() != null && board.getCell(getRow()-1, getCol()-1).getPiece().getColor() == 'b'){ //up+left
				possibleMoves.add(board.getCell(getRow()-1, getCol()-1));
			//enpassent
			} else if (checkBoardBounds(getRow()-1, getCol()-1) && (board.getCell(getRow(), getCol()-1).getPiece() instanceof Pawn)){ //left enemy pawn
				Pawn pawnToEnpassent = (Pawn)board.getCell(getRow(), getCol()-1).getPiece();
				if (pawnToEnpassent.getColor() == 'b' && pawnToEnpassent.canDieByEnpassent){
					possibleMoves.add(board.getCell(getRow()-1, getCol()-1));  //up+left
				}
			}
			//attack
			if (checkBoardBounds(getRow()-1, getCol()+1) && board.getCell(getRow()-1, getCol()+1).getPiece() != null && board.getCell(getRow()-1, getCol()+1).getPiece().getColor() == 'b'){ //up+right
				possibleMoves.add(board.getCell(getRow()-1, getCol()+1));
			//enpassent
			} else if (checkBoardBounds(getRow()-1, getCol()+1) && (board.getCell(getRow(), getCol()+1).getPiece() instanceof Pawn)){ //right enemy pawn
				Pawn pawnToEnpassent = (Pawn)board.getCell(getRow(), getCol()+1).getPiece();
				if (pawnToEnpassent.getColor() == 'b' && pawnToEnpassent.canDieByEnpassent){
					possibleMoves.add(board.getCell(getRow()-1, getCol()+1)); //up+right
				}
			}
			
			
		} else{ //if (getColor() == 'b')
			//can only move row upwards (1->8)
			if (neverMoved){ //special rule: can move twice
				for (int i = 1; i <= 2; i++){
					if (checkBoardBounds(getRow()+i, getCol()) && board.getCell(getRow()+i, getCol()).getPiece() == null){ //down i
						possibleMoves.add(board.getCell(getRow()+i, getCol()));
					} else{break;}
				}
			} else{
			//normal move
				if (checkBoardBounds(getRow()+1, getCol()) && board.getCell(getRow()+1, getCol()).getPiece() == null){ //up 1
					possibleMoves.add(board.getCell(getRow()+1, getCol()));
				}
			}
			//normal attack
			if (checkBoardBounds(getRow()+1, getCol()-1) && board.getCell(getRow()+1, getCol()-1).getPiece() != null && board.getCell(getRow()+1, getCol()-1).getPiece().getColor() == 'w'){ //down+left
				possibleMoves.add(board.getCell(getRow()+1, getCol()-1));
			//enpassent
			} else if (checkBoardBounds(getRow()+1, getCol()-1) && (board.getCell(getRow(), getCol()-1).getPiece() instanceof Pawn)){ //left enemy pawn
				Pawn pawnToEnpassent = (Pawn)board.getCell(getRow(), getCol()-1).getPiece();
				if (pawnToEnpassent.getColor() == 'w' && pawnToEnpassent.canDieByEnpassent){
					possibleMoves.add(board.getCell(getRow()+1, getCol()-1)); //down+left
				}
			}
			//normal attack
			if (checkBoardBounds(getRow()+1, getCol()+1) && board.getCell(getRow()+1, getCol()+1).getPiece() != null && board.getCell(getRow()+1, getCol()+1).getPiece().getColor() == 'w'){ //down+right
				possibleMoves.add(board.getCell(getRow()+1, getCol()+1));
			//enpassent
			} else if (checkBoardBounds(getRow()+1, getCol()+1) && (board.getCell(getRow(), getCol()+1).getPiece() instanceof Pawn)){ //right enemy pawn
				Pawn pawnToEnpassent = (Pawn)board.getCell(getRow(), getCol()+1).getPiece();
				if (pawnToEnpassent.getColor() == 'w' && pawnToEnpassent.canDieByEnpassent){
					possibleMoves.add(board.getCell(getRow()+1, getCol()+1)); //down+right
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
		//record data
		boolean[] specialCases = new boolean[]{canDieByEnpassent, neverMoved, false, false}; //(canDieByEnpassent, neverMoved, transformedThisTurn, killUsingEnpassentThisTurn)

		//update current piece
		canDieByEnpassent = false; 
		if (toRow == 0 || toRow == 7){ //transforming this turn
			specialCases[2] = true;
		}

		 //check if can die by enpassent
		if((getColor() == 'w' && getRow()-2 == toRow) || (getColor() == 'b' && getRow()+2 == toRow)){
			canDieByEnpassent = true; 
		}
		
		//kill pawn below if by enpassent
		if (getColor() == 'w'){
			if (board.getCell(toRow+1, toCol).getPiece() instanceof Pawn){ //pawn below
				Pawn pawnToKill = (Pawn)board.getCell(toRow+1, toCol).getPiece();
				if (pawnToKill.canDieByEnpassent && pawnToKill.getColor() != getColor()){
					specialCases[3] = true;
					pawnToKill.die();
					board.getCell(toRow+1, toCol).free();
				}
			}
		} else{ // 'b'
			if (board.getCell(toRow-1, toCol).getPiece() instanceof Pawn){ //pawn above
				Pawn pawnToKill = (Pawn)board.getCell(toRow-1, toCol).getPiece();
				if (pawnToKill.canDieByEnpassent && pawnToKill.getColor() != getColor()){
					specialCases[3] = true;
					pawnToKill.die();
					board.getCell(toRow-1, toCol).free();
				}
			}		
		}
		neverMoved = false;

		board.record(new MoveData(getRow(), getCol(), toRow, toCol, specialCases)); //save before setting new row/col
		setRow(toRow);
		setCol(toCol);
		
		//note: transform check happens in Player.java
	}
	
	@Override
	public String toString(){
		return super.toString() + "p"; //wp or bp
	}
}
