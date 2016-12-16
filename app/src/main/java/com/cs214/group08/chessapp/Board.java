/*
 * Board class chess is played on
 * 
 * @author Renard Tumbokon, Nikhil Menon
 */

package com.cs214.group08.chessapp;

import java.util.ArrayList;

public class Board {
	/*	ex. [row(i) = num][col(j) = letter] (row outer in nested for loop)
	 *   [*0*][*0*] | b[0][*1*] |  [0][*2*] | b[0][*3*] |  [0][*4*] | b[0][*5*] |  [0][*6*] | b[0][*7*]
	 *  b[*1*][ 0 ] |  [1][ 1 ] | b[1][ 2 ] |  [1][ 3 ] | b[1][ 4 ] |  [1][ 5 ] | b[1][ 6 ] |  [1][ 7 ]
	 *   [*2*][ 0 ] | b[2][ 1 ] |  [2][ 2 ] | b[2][ 3 ] |  [2][ 4 ] | b[2][ 5 ] |  [2][ 6 ] | b[2][ 7 ]
	 *  b[*3*][ 0 ] |  [3][ 1 ] | b[3][ 2 ] |  [3][ 3 ] | b[3][ 4 ] |  [3][ 5 ] | b[3][ 6 ] |  [3][ 7 ]
	 *   [*4*][ 0 ] | b[4][ 1 ] |  [4][ 2 ] | b[4][ 3 ] |  [4][ 4 ] | b[4][ 5 ] |  [4][ 6 ] | b[4][ 7 ]
	 *  b[*5*][ 0 ] |  [5][ 1 ] | b[5][ 2 ] |  [5][ 3 ] | b[5][ 4 ] |  [5][ 5 ] | b[5][ 6 ] |  [5][ 7 ]
	 *   [*6*][ 0 ] | b[6][ 1 ] |  [6][ 2 ] | b[6][ 3 ] |  [6][ 4 ] | b[6][ 5 ] |  [6][ 6 ] | b[6][ 7 ]
	 *  b[*7*][ A ] |  [7][ B ] | b[7][ C ] |  [7][ D ] | b[7][ E ] |  [7][ F ] | b[7][ G ] |  [7][ H ]
	 */
	private Cell[][] grid = new Cell[8][8];
	public ArrayList<MoveData> gameLog;
	
	public Board(){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				grid[i][j] = new Cell(i, j);
			}
		}
		gameLog = new ArrayList<MoveData>();
	}
	
	public void setCell(Piece piece, char rowChar, char colChar){
		int[] indexArr = convertRankFile(rowChar, colChar); // ('1','a')
		if (piece == null){
			grid[indexArr[0]][indexArr[1]].free(); 
		} else{
			grid[indexArr[0]][indexArr[1]].occupy(piece); 
		}
	}
	public void setCell(Piece piece, int rowInt, int colInt){
		if (piece == null){
			grid[rowInt][colInt].free(); 
		} else{
			grid[rowInt][colInt].occupy(piece); 
		}
	}
	
	public Cell getCell(int rowInt, int colInt){
		return grid[rowInt][colInt];
	}
	public Cell getCell(char rowChar, char colChar){// ('num','letter')
		int[] indexArr = convertRankFile(rowChar, colChar); // ('1','a')
		return grid[indexArr[1]][indexArr[0]];
	}
	
	//Convert 'a,8' -> [0,0]
	public static int[] convertRankFile(char rowChar, char colChar){ //(1,a)
		int rowInt = ((int) rowChar)-48;  //number	
		rowInt = 8-rowInt;
		int colInt = "abcdefgh".indexOf(colChar);		//char
		int[] toReturn = {rowInt, colInt};
		return toReturn; 
	}
	
	//Convert [0,0] -> 'a,8'
	public static char[] convertRowCol(int row, int col){
		char[] alpha = {'a','b','c','d','e','f','g','h'};
		char rowChar = alpha[col];	 //number
		String colStr = (8-row) + "";
		char colChar = colStr.charAt(0); 	//char
		char[] toReturn = {rowChar, colChar};
		return toReturn; 
	}
	
	static boolean checkBoardBounds(int row, int col){
		if (row > 7 || row < 0 || col > 7 || col < 0){
			return false;
		}
		return true;
	}
	
	//Find if space is being attacked (check all angles of attack)
	public boolean isSpaceAttacked(char enemyColor, int row, int col){
		//Pawn, Bishop, Rook, Queen, King moveset
		for(int i= 1; i<=8; i++){ //up rows
			if (!checkBoardBounds(row-i, col)){
				break;
			}
			Piece pieceInCell = getCell(row-i, col).getPiece();
			if (pieceInCell != null) {
				if (pieceInCell.getColor() == enemyColor && (pieceInCell instanceof Rook || pieceInCell instanceof Queen || (i == 1 && pieceInCell instanceof King))){
					return true; //enemy can capture on this spot
				}
				break;
			}
		}
		for(int i= 1; i<=8; i++){ //up+right
			if (!checkBoardBounds(row-i, col+i)){
				break;
			}
			Piece pieceInCell = getCell(row-i, col+i).getPiece();
			if (pieceInCell != null) {
				if (pieceInCell.getColor() == enemyColor && (pieceInCell instanceof Bishop || pieceInCell instanceof Queen || 
					(i == 1 && (pieceInCell instanceof King || (enemyColor == 'b' && pieceInCell instanceof Pawn))))){
						return true; //enemy can capture on this spot
				}
				break;
			}
		}
		for(int i= 1; i<=8; i++){ //right cols
			if (!checkBoardBounds(row, col+i)){
				break;
			}
			Piece pieceInCell = getCell(row, col+i).getPiece();
			if (pieceInCell != null) {
				if (pieceInCell.getColor() == enemyColor && (pieceInCell instanceof Rook || pieceInCell instanceof Queen || (i == 1 && pieceInCell instanceof King))){
					return true; //enemy can capture on this spot
				}
				break;
			}
		}	
		for(int i= 1; i<=8; i++){ //down+right
			if (!checkBoardBounds(row+i, col+i)){
				break;
			}
			Piece pieceInCell = getCell(row+i, col+i).getPiece();
			if (pieceInCell != null) {
				if (pieceInCell.getColor() == enemyColor && (pieceInCell instanceof Bishop || pieceInCell instanceof Queen || 
					(i == 1 && (pieceInCell instanceof King || (enemyColor == 'w' && pieceInCell instanceof Pawn))))){
						return true; //enemy can capture on this spot
				}
				break;
			}
		}
		for(int i= 1; i<=8; i++){ //down rows
			if (!checkBoardBounds(row+i, col)){
				break;
			}
			Piece pieceInCell = getCell(row+i, col).getPiece();
			if (pieceInCell != null) {
				if (pieceInCell.getColor() == enemyColor && (pieceInCell instanceof Rook || pieceInCell instanceof Queen || (i == 1 && pieceInCell instanceof King))){
					return true; //enemy can capture on this spot
				}
				break;
			}
		}	
		for(int i= 1; i<=8; i++){ //down+left
			if (!checkBoardBounds(row+i, col-i)){
				break;
			}
			Piece pieceInCell = getCell(row+i, col-i).getPiece();
			if (pieceInCell != null) {
				if (pieceInCell.getColor() == enemyColor && (pieceInCell instanceof Bishop || pieceInCell instanceof Queen || 
					(i == 1 && (pieceInCell instanceof King || (enemyColor == 'w' && pieceInCell instanceof Pawn))))){
						return true; //enemy can capture on this spot
				}
				break;
			}
		}
		for(int i= 1; i<=8; i++){ //left cols
			if (!checkBoardBounds(row, col-i)){
				break;
			}
			Piece pieceInCell = getCell(row, col-i).getPiece();
			if (pieceInCell != null) {
				if (pieceInCell.getColor() == enemyColor && (pieceInCell instanceof Rook || pieceInCell instanceof Queen || (i == 1 && pieceInCell instanceof King))){
					return true; //enemy can capture on this spot
				}
				break;
			}
		}	
		for(int i= 1; i<=8; i++){ //up+left
			if (!checkBoardBounds(row-i, col-i)){
				break;
			}
			Piece pieceInCell = getCell(row-i, col-i).getPiece();
			if (pieceInCell != null) {
				if (pieceInCell.getColor() == enemyColor && (pieceInCell instanceof Bishop || pieceInCell instanceof Queen || 
					(i == 1 && (pieceInCell instanceof King || (enemyColor == 'b' && pieceInCell instanceof Pawn))))){
						return true; //enemy can capture on this spot
				}
				break;
			}
		}
		
		//Knight moveset
		if (checkBoardBounds(row+2, col+1)){ 
			Piece pieceInCell = getCell(row+2, col+1).getPiece();
			if (pieceInCell != null && pieceInCell.getColor() == enemyColor && pieceInCell instanceof Knight){
				return true;
			}
		}
		if (checkBoardBounds(row-2, col-1)){ 
			Piece pieceInCell = getCell(row-2, col-1).getPiece();
			if (pieceInCell != null && pieceInCell.getColor() == enemyColor && pieceInCell instanceof Knight){
				return true;
			}
		}
		if (checkBoardBounds(row+2, col-1)){ 
			Piece pieceInCell = getCell(row+2, col-1).getPiece();
			if (pieceInCell != null && pieceInCell.getColor() == enemyColor && pieceInCell instanceof Knight){
				return true;
			}
		}
		if (checkBoardBounds(row-2, col+1)){ 
			Piece pieceInCell = getCell(row-2, col+1).getPiece();
			if (pieceInCell != null && pieceInCell.getColor() == enemyColor && pieceInCell instanceof Knight){
				return true;
			}
		}
		if (checkBoardBounds(row+1, col+2)){ 
			Piece pieceInCell = getCell(row+1, col+2).getPiece();
			if (pieceInCell != null && pieceInCell.getColor() == enemyColor && pieceInCell instanceof Knight){
				return true;
			}
		}
		if (checkBoardBounds(row-1, col-2)){ 
			Piece pieceInCell = getCell(row-1, col-2).getPiece();
			if (pieceInCell != null && pieceInCell.getColor() == enemyColor && pieceInCell instanceof Knight){
				return true;
			}
		}
		if (checkBoardBounds(row+1, col-2)){ 
			Piece pieceInCell = getCell(row+1, col-2).getPiece();
			if (pieceInCell != null && pieceInCell.getColor() == enemyColor && pieceInCell instanceof Knight){
				return true;
			}
		}
		if (checkBoardBounds(row-1, col+2)){ 
			Piece pieceInCell = getCell(row-1, col+2).getPiece();
			if (pieceInCell != null && pieceInCell.getColor() == enemyColor && pieceInCell instanceof Knight){
				return true;
			}
		}
		return false;
	}
	
	//does move to see status of board (isCheck) and reverts back to old board state
	public boolean peekMoveIfIsCheck(King king, int fromRow, int fromCol, int toRow, int toCol){ //must be valid move
		boolean isCheck;
		Piece pieceFrom = getCell(fromRow, fromCol).getPiece(); //save
		Piece pieceTo = getCell(toRow, toCol).getPiece();
		
		setCell(pieceFrom, toRow, toCol); //set
		setCell(null, fromRow, fromCol);
		
		if (pieceFrom instanceof King){ //if king moved
			pieceFrom.postMoveUpdate(toRow, toCol, "peekMoveIfIsCheck");
		}
		
		isCheck = king.isCheck(); //check to see if move makes king in check
		
		setCell(pieceFrom, fromRow, fromCol); //revert
		setCell(pieceTo, toRow, toCol);
		
		if (pieceFrom instanceof King){ //if king moved
			pieceFrom.postMoveUpdate(fromRow, fromCol, "peekMoveIfIsCheck");
		}

		return isCheck; //illegal to move there
	}
	
	//print entire board
	public void print(){
		System.out.println("");//new line
		//print row by row
		for(int i = 0; i < 8; i++){ 
			for(int j = 0; j < 8; j++){
				System.out.print(grid[i][j].toString() + " ");
			}
			System.out.print(8-i + "\n"); //print numbers for rows + new line
		}
		System.out.println(" a  b  c  d  e  f  g  h");  //print letters for cols
		System.out.println("");//new line
	}

	public void record(MoveData data){
		gameLog.add(data);
	}
}
