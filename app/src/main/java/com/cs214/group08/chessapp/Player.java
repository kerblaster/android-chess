/*
 * For each player (white, black)
 * 
 * @author Renard Tumbokon, Nikhil Menon
 */

package com.cs214.group08.chessapp;

import java.util.ArrayList;

public class Player {

	Board board;
	char color;
	String name;
	char enemy; 
	ArrayList<Piece> pieces = new ArrayList<Piece>(); //for enpassent tracking
	King king;
	
	public Player(char color, Board board){
		this.color = color;
		this.board = board;
		int pawnRow;
		int backRow;
		if (color == 'w'){ //check if black or white
			name = "White";
			pawnRow = 6;
			backRow = 7;
		} else{
			name = "Black";
			pawnRow = 1;
			backRow = 0;
		}
		Pawn pawn1 = new Pawn(pawnRow, 0, color, board); pieces.add(pawn1); board.setCell(pawn1, pawnRow, 0); //row, col
		Pawn pawn2 = new Pawn(pawnRow, 1, color, board); pieces.add(pawn2); board.setCell(pawn2, pawnRow, 1);
		Pawn pawn3 = new Pawn(pawnRow, 2, color, board); pieces.add(pawn3); board.setCell(pawn3, pawnRow, 2);
		Pawn pawn4 = new Pawn(pawnRow, 3, color, board); pieces.add(pawn4); board.setCell(pawn4, pawnRow, 3);
		Pawn pawn5 = new Pawn(pawnRow, 4, color, board); pieces.add(pawn5); board.setCell(pawn5, pawnRow, 4);
		Pawn pawn6 = new Pawn(pawnRow, 5, color, board); pieces.add(pawn6); board.setCell(pawn6, pawnRow, 5);
		Pawn pawn7 = new Pawn(pawnRow, 6, color, board); pieces.add(pawn7); board.setCell(pawn7, pawnRow, 6);
		Pawn pawn8 = new Pawn(pawnRow, 7, color, board); pieces.add(pawn8); board.setCell(pawn8, pawnRow, 7);
		Knight knight1 = new Knight(backRow, 1, color, board); pieces.add(knight1); board.setCell(knight1, backRow, 1);
		Knight knight2 = new Knight(backRow, 6, color, board); pieces.add(knight2); board.setCell(knight2, backRow, 6);
		Bishop bishop1 = new Bishop(backRow, 2, color, board); pieces.add(bishop1); board.setCell(bishop1, backRow, 2);
		Bishop bishop2 = new Bishop(backRow, 5, color, board); pieces.add(bishop2); board.setCell(bishop2, backRow, 5);
		Rook rook1 = new Rook(backRow, 0, color, board); pieces.add(rook1); board.setCell(rook1, backRow, 0);
		Rook rook2 = new Rook(backRow, 7, color, board); pieces.add(rook2); board.setCell(rook2, backRow, 7);
		Queen queen = new Queen(backRow, 3, color, board); pieces.add(queen); board.setCell(queen, backRow, 3);
		king = new King(backRow, 4, color, board); pieces.add(king); board.setCell(king, backRow, 4);
		
	}
	
	public boolean doTurn(String input){
		System.out.println(name + "'s input = " + input);

		int fromRow;
		int fromCol;
		int toRow;
		int toCol;
        String otherInstr;

		if (!isValidInput(input)){
			System.out.println("Bad Code Error: '" + input + "'");
			return false;
		}

		//check if space to move is valid
		int[] fromArr = Board.convertRankFile(input.charAt(1), input.charAt(0)); //(1,a) (row, col)
		fromRow = fromArr[0];
		fromCol = fromArr[1];
		int[] toArr = Board.convertRankFile(input.charAt(4), input.charAt(3)); //(1,a) (row, col)
		toRow = toArr[0];
		toCol = toArr[1];

        if (input.length() > 5) otherInstr = input.substring(6);
        else otherInstr = "";

		return movePiece(fromRow, fromCol, toRow, toCol, otherInstr);
	}
	
	public boolean movePiece(int fromRow, int fromCol, int toRow, int toCol, String otherInstr){
		Piece fromPiece = board.getCell(fromRow, fromCol).getPiece();
		Piece toPiece = board.getCell(toRow, toCol).getPiece();
		if (fromPiece != null && fromPiece.getColor() == color){ // player color = piece color
			if (fromPiece.canMove(toRow, toCol, otherInstr)){ // ****Important check**** if movement is legal move
				//move the piece
				board.setCell(fromPiece, toRow, toCol);
				board.setCell(null, fromRow, fromCol);
				
				if (fromPiece instanceof King){ //if king moved
					fromPiece.postMoveUpdate(toRow, toCol, "peekMoveIfIsCheck");
				}
				//see if king in check when move happens
				if (king.isCheck()){
					//UNDO
					board.setCell(fromPiece, fromRow, fromCol);
					board.setCell(toPiece, toRow, toCol);
					if (fromPiece instanceof King){ //if king moved
						fromPiece.postMoveUpdate(fromRow, fromCol, "peekMoveIfIsCheck");
					}					
					return false; //function stops
				} else if (fromPiece instanceof King){
					fromPiece.postMoveUpdate(fromRow, fromCol, "peekMoveIfIsCheck"); //return past row,col for finalized recording
				}
				
				//finalize by updating info
				board.getCell(toRow, toCol).getPiece().postMoveUpdate(toRow, toCol, otherInstr); //****Important update****
				//make all enemy pawns un-enpassentable
				if (toPiece != null){ //if piece is going to kill
					toPiece.die();
				}
				
				//instructions handling 
				if (otherInstr == null){
					return true; //no instructions = break
				}
				if ((fromPiece instanceof Pawn) && (toRow == 0 || toRow == 7)){ //transform pawn
					char transformTo;
					if (otherInstr.length() <= 0){
						transformTo = 'Q';
					} else{
						transformTo = otherInstr.charAt(0);
					}
					MoveData lastData = board.gameLog.get(board.gameLog.size()-1);
					lastData.setTransformTo(transformTo);
					for (Piece p : pieces){ //remove pawn that transformed from existence
						if (p.getRow() == toRow && p.getCol() == toCol){
							pieces.remove(p);
							break;
						}
					}
					switch (transformTo){
						case 'R':
							Piece newRook = new Rook(toRow, toCol, color, board); pieces.add(newRook); board.setCell(newRook, toRow, toCol);
							break;
						case 'B':
							Piece newBishop = new Bishop(toRow, toCol, color, board); pieces.add(newBishop); board.setCell(newBishop, toRow, toCol);
							break;
						case 'N':
                            Piece newKnight = new Knight(toRow, toCol, color, board); pieces.add(newKnight); board.setCell(newKnight, toRow, toCol);
                        	break;
						case 'Q':
                        default:
                            Piece newQueen = new Queen(toRow, toCol, color, board); pieces.add(newQueen); board.setCell(newQueen, toRow, toCol);
                            break;
					}
				}
				return true;	//normal move
			}
		}
		return false; //invalid move
	}
	
	
	public boolean hasOptions(){ //return if piece has options without putting king in check
		for (Piece piece : pieces){
			if (piece.isAlive){
				piece.getPossibleMoves();
				for (Cell cell : piece.possibleMoves){ //check all possible moves
					int toRow = cell.row;
					int toCol = cell.col;
					if (!board.peekMoveIfIsCheck(king, piece.getRow(), piece.getCol(), toRow, toCol)){ //false = not in check
						return true; //there exist a move that wont put king in check (need to only find 1)
					}
				}
			}
		}
		return false; //cant find legal move after looking
	}

    public ArrayList<Piece> piecesWithOptionsList(){ //ret pieces with options without putting king in check
        ArrayList<Piece> temp = new ArrayList<Piece>();
        for (Piece piece : pieces){
            if (piece.isAlive){
                piece.getPossibleMoves();
                for (Cell cell : piece.possibleMoves){ //check all possible moves
                    int toRow = cell.row;
                    int toCol = cell.col;
                    if (!board.peekMoveIfIsCheck(king, piece.getRow(), piece.getCol(), toRow, toCol)){ //false = not in check
                        temp.add(piece);
                    }
                }
            }
        }
        return temp;
    }

    public ArrayList<Cell> pieceOptionsList(Piece piece){ //possible non-check options given piece
        if (!piece.isAlive){
            return null;
        }
        ArrayList<Cell> temp = new ArrayList<Cell>();
        piece.getPossibleMoves();
        for (Cell cell : piece.possibleMoves){ //check all possible moves
            int toRow = cell.row;
            int toCol = cell.col;
            if (!board.peekMoveIfIsCheck(king, piece.getRow(), piece.getCol(), toRow, toCol)){ //false = not in check
                temp.add(cell);
            }
        }
        return temp;
    }
	
	public void unempassent(){ //cant enpassent pawns after 1 move has passed
		for (Piece piece : pieces){
			if (piece instanceof Pawn){
				((Pawn) piece).canDieByEnpassent = false;
			}
		}
	}
	
	//input "a1" = output true, input "Z9" = output false
	private boolean isValidCoord(String coord){
		char charCol = coord.charAt(0); // letter
		char charRow = coord.charAt(1); // number
		
		if((charCol >= 'a' && charCol <= 'h') && (charRow >= '1' && charRow <= '8')){
			return true;
		}
		return false;
	}
	
	public boolean isValidInput(String input){
		if (input == null || input.length() < 4){return false;}
		if (input.equals("resign")){return true;}
		if (input.equals("draw")){return true;}
		if(!isValidCoord(input.substring(0, 2)) || !isValidCoord(input.substring(3, 5))){
			return false;
		}
		if (input.length() > 5){ //more than "a1 b2"
			String instruction = input.substring(6);
			if (instruction.equals("draw?")){ //for "a1 b2 draw?"
				return true;
			}
			if(instruction.equals("N") || instruction.equals("B") || instruction.equals("R") || instruction.equals("Q")){
				return true;
			}
			return false;
		}
		return true;
	}
}
