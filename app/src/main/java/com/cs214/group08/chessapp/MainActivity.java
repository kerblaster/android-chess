package com.cs214.group08.chessapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView fromImageView;
    private ImageView toImageView;
    private TextView turnTextView;
    private TextView consoleTextView;
    private Button drawButton;
    private Button undoButton;

    RecordedGamesList gameList;

    ImageView[][] cellImageViewList; // 2d array of graphical board

    @Override
    public void onCreate(Bundle savedInstanceState) { //MAIN METHOD
        //default code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //start custom code here
        //declare xml variables
        cellImageViewList = new ImageView[][]{
                {(ImageView) findViewById(R.id.a8ImageView), (ImageView) findViewById(R.id.b8ImageView), (ImageView) findViewById(R.id.c8ImageView), (ImageView) findViewById(R.id.d8ImageView), (ImageView) findViewById(R.id.e8ImageView), (ImageView) findViewById(R.id.f8ImageView), (ImageView) findViewById(R.id.g8ImageView), (ImageView) findViewById(R.id.h8ImageView)},
                {(ImageView) findViewById(R.id.a7ImageView), (ImageView) findViewById(R.id.b7ImageView), (ImageView) findViewById(R.id.c7ImageView), (ImageView) findViewById(R.id.d7ImageView), (ImageView) findViewById(R.id.e7ImageView), (ImageView) findViewById(R.id.f7ImageView), (ImageView) findViewById(R.id.g7ImageView), (ImageView) findViewById(R.id.h7ImageView)},
                {(ImageView) findViewById(R.id.a6ImageView), (ImageView) findViewById(R.id.b6ImageView), (ImageView) findViewById(R.id.c6ImageView), (ImageView) findViewById(R.id.d6ImageView), (ImageView) findViewById(R.id.e6ImageView), (ImageView) findViewById(R.id.f6ImageView), (ImageView) findViewById(R.id.g6ImageView), (ImageView) findViewById(R.id.h6ImageView)},
                {(ImageView) findViewById(R.id.a5ImageView), (ImageView) findViewById(R.id.b5ImageView), (ImageView) findViewById(R.id.c5ImageView), (ImageView) findViewById(R.id.d5ImageView), (ImageView) findViewById(R.id.e5ImageView), (ImageView) findViewById(R.id.f5ImageView), (ImageView) findViewById(R.id.g5ImageView), (ImageView) findViewById(R.id.h5ImageView)},
                {(ImageView) findViewById(R.id.a4ImageView), (ImageView) findViewById(R.id.b4ImageView), (ImageView) findViewById(R.id.c4ImageView), (ImageView) findViewById(R.id.d4ImageView), (ImageView) findViewById(R.id.e4ImageView), (ImageView) findViewById(R.id.f4ImageView), (ImageView) findViewById(R.id.g4ImageView), (ImageView) findViewById(R.id.h4ImageView)},
                {(ImageView) findViewById(R.id.a3ImageView), (ImageView) findViewById(R.id.b3ImageView), (ImageView) findViewById(R.id.c3ImageView), (ImageView) findViewById(R.id.d3ImageView), (ImageView) findViewById(R.id.e3ImageView), (ImageView) findViewById(R.id.f3ImageView), (ImageView) findViewById(R.id.g3ImageView), (ImageView) findViewById(R.id.h3ImageView)},
                {(ImageView) findViewById(R.id.a2ImageView), (ImageView) findViewById(R.id.b2ImageView), (ImageView) findViewById(R.id.c2ImageView), (ImageView) findViewById(R.id.d2ImageView), (ImageView) findViewById(R.id.e2ImageView), (ImageView) findViewById(R.id.f2ImageView), (ImageView) findViewById(R.id.g2ImageView), (ImageView) findViewById(R.id.h2ImageView)},
                {(ImageView) findViewById(R.id.a1ImageView), (ImageView) findViewById(R.id.b1ImageView), (ImageView) findViewById(R.id.c1ImageView), (ImageView) findViewById(R.id.d1ImageView), (ImageView) findViewById(R.id.e1ImageView), (ImageView) findViewById(R.id.f1ImageView), (ImageView) findViewById(R.id.g1ImageView), (ImageView) findViewById(R.id.h1ImageView)}
        };

        turnTextView = (TextView)findViewById(R.id.turnTextView); // (big) text for turns
        consoleTextView = (TextView)findViewById(R.id.consoleTextView); // (small) text for console
        drawButton = (Button)findViewById(R.id.drawButton); // [draw] button
        undoButton = (Button)findViewById(R.id.undoButton); // [undo] button

        gameList = new RecordedGamesList();

       newChessGame(); //new board
    }

    //Call to refresh image of board and pieces
    public void refreshBoardImage(){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                Piece piece = board.getCell(i,j).getPiece();
                if (piece == null){ //no piece
                    cellImageViewList[i][j].setImageResource(0);
                } else if(piece.getColor() == 'w'){ //white
                    if (piece instanceof Pawn) cellImageViewList[i][j].setImageResource(R.drawable.pawn_w);
                    else if (piece instanceof Bishop) cellImageViewList[i][j].setImageResource(R.drawable.bishop_w);
                    else if (piece instanceof Knight) cellImageViewList[i][j].setImageResource(R.drawable.knight_w);
                    else if (piece instanceof Rook) cellImageViewList[i][j].setImageResource(R.drawable.rook_w);
                    else if (piece instanceof Queen) cellImageViewList[i][j].setImageResource(R.drawable.queen_w);
                    else if (piece instanceof King) cellImageViewList[i][j].setImageResource(R.drawable.king_w);
                    else System.out.println("Bad Code Error: Cannot find instanceof white piece");
                } else{ //black
                    if (piece instanceof Pawn){
                        cellImageViewList[i][j].setImageResource(R.drawable.pawn_b);
                    }
                    else if (piece instanceof Bishop) cellImageViewList[i][j].setImageResource(R.drawable.bishop_b);
                    else if (piece instanceof Knight) cellImageViewList[i][j].setImageResource(R.drawable.knight_b);
                    else if (piece instanceof Rook) cellImageViewList[i][j].setImageResource(R.drawable.rook_b);
                    else if (piece instanceof Queen) cellImageViewList[i][j].setImageResource(R.drawable.queen_b);
                    else if (piece instanceof King) cellImageViewList[i][j].setImageResource(R.drawable.king_b);
                    else System.out.println("Bad Code Error: Cannot find instanceof black piece");
                }
            }
        }
        //board.print(); //prints to console
    }

    //Send movement instruction coord pairs to send to computerized game
    public void sendCoords(String fromCoord, String toCoord){
        final String instruction = fromCoord + " " + toCoord;

        //pawn transform check
        int fromRow, fromCol, toRow; //, toCol;
        int[] fromArr = Board.convertRankFile(fromCoord.charAt(1), fromCoord.charAt(0)); //(1,a) (row, col)
        fromRow = fromArr[0];fromCol = fromArr[1];
        int[] toArr = Board.convertRankFile(toCoord.charAt(1), toCoord.charAt(0)); //(1,a) (row, col)
        toRow = toArr[0];//toCol = toArr[1];

        Piece piece = board.getCell(fromRow, fromCol).getPiece();
        if ((piece != null && piece instanceof Pawn) && (toRow == 0 || toRow == 7)) {
            CharSequence colors[] = new CharSequence[] {"Queen", "Rook", "Knight", "Bishop"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Transform Pawn:");
            builder.setItems(colors, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case 1:
                            playerTurn(instruction + " " + "R");
                            break;
                        case 2:
                            playerTurn(instruction + " " + "N");
                            break;
                        case 3:
                            playerTurn(instruction + " " + "B");
                            break;
                        case 0:
                        default:
                            playerTurn(instruction + " " + "Q");
                    }
                }
            });
            builder.show();
        } else {
            playerTurn(instruction);
        }
    }


    /************************************
     * Event Listeners / Button Handlers
     ************************************/
    //When any cell is clicked
    public void setFromAndTo(View v){
        if (!isPlaying){
            newChessGame();
            return;
        }
        if (fromImageView == null){ //add to fromImageView and highlight
            fromImageView = (ImageView)v;
            fromImageView.setAlpha(0.25f);
        } else { //add to toImageView and highlight, then do move logic
            toImageView = (ImageView)v;
            String fromCoord = (getResources().getResourceEntryName(fromImageView.getId())).substring(0,2);
            String toCoord = (getResources().getResourceEntryName(toImageView.getId())).substring(0,2);
            sendCoords(fromCoord, toCoord);
            //reset from and to
            fromImageView.setAlpha(1.0f);
            fromImageView = null;
            toImageView = null;
        }
    }

    public void undoButtonHandler(View v){
        if (!isPlaying || board.gameLog.size() < 1){
            newChessGame(); //reset game
            return;
        }
        MoveData moveToUndo = board.gameLog.get(board.gameLog.size()-1); //get last entry
        board.gameLog.remove(board.gameLog.size()-1);

        //get undo data

        int fromRow = moveToUndo.getFromRow();
        int fromCol = moveToUndo.getFromCol();
        int toRow = moveToUndo.getToRow();
        int toCol = moveToUndo.getToCol();
        Piece pieceToUndo = board.getCell(toRow, toCol).getPiece();
        if (pieceToUndo == null){ //shouldnt happen, but just in case
            return;
        }
        boolean[] specialCases = moveToUndo.getSpecialCases();
        System.out.println("Undo: " + fromRow + "," +fromCol + " <- " +toRow + ","+toCol);

        Player undoCaller; //gets to do turn again
        Player opponent;
        if (pieceToUndo.getColor() == 'w'){
            undoCaller = white;
            opponent = black;
        } else{ //getColor() == 'b'
            undoCaller = black;
            opponent = white;
        }

        //actually undo
        board.setCell(pieceToUndo, fromRow, fromCol); //move back piece
        board.getCell(toRow, toCol).free();
        pieceToUndo.setRow(fromRow);
        pieceToUndo.setCol(fromCol);
        pieceToUndo.getPossibleMoves();
        if (pieceToUndo instanceof Pawn){
            ((Pawn) pieceToUndo).canDieByEnpassent = specialCases[0];
            ((Pawn) pieceToUndo).neverMoved = specialCases[1];
            if (specialCases[2] == true){ //pawn transformed, remove tranformed piece
                undoCaller.pieces.add(pieceToUndo);
                for (Piece p : undoCaller.pieces){ //find transformed piece in opponent's list -> remove it
                    if ((p.getRow() == toRow && p.getCol() == toCol) && !(p instanceof Pawn)){
                        undoCaller.pieces.remove(p);
                        break;
                    }
                }
            }
        } else if (pieceToUndo instanceof Rook){
            ((Rook) pieceToUndo).neverMoved = specialCases[0];
        } else if (pieceToUndo instanceof King){
            ((King) pieceToUndo).neverMoved = specialCases[0];
            if (specialCases[1]){ //undo castle logic
                Rook rookCastled = null;
                if (pieceToUndo.getColor() == 'w'){
                    if (fromCol == 6){ //undo white castle king side
                        rookCastled = (Rook)board.getCell(7,5).getPiece();
                        board.setCell(rookCastled, 7, 7);
                        board.getCell(7,5).free();
                        rookCastled.postMoveUpdate(7, 7, "");
                        rookCastled.neverMoved = true;
                        rookCastled.getPossibleMoves();
                    } else{ //undo white castle queen side
                        rookCastled = (Rook)board.getCell(7,3).getPiece();
                        board.setCell(rookCastled, 7, 0);
                        board.getCell(7,3).free();
                        rookCastled.postMoveUpdate(7, 0, "");
                        rookCastled.neverMoved = true;
                        rookCastled.getPossibleMoves();
                    }
                } else{
                    if (fromCol == 6){ //undo black castle king side
                        rookCastled = (Rook)board.getCell(0,5).getPiece();
                        board.setCell(rookCastled, 0, 7);
                        board.getCell(0,5).free();
                        rookCastled.postMoveUpdate(0, 7, "");
                        rookCastled.neverMoved = true;
                        rookCastled.getPossibleMoves();
                    } else{ //undo black castle queen side
                        rookCastled = (Rook)board.getCell(0,3).getPiece();
                        board.setCell(rookCastled, 0, 0);
                        board.getCell(0,3).free();
                        rookCastled.postMoveUpdate(0, 0, "");
                        rookCastled.neverMoved = true;
                        rookCastled.getPossibleMoves();
                    }
                }
            }
        }
        //revive pieces eaten
        //find piece that was prev at (toRow,toCol)
        Piece pieceToRevive = null;
        if (pieceToUndo instanceof Pawn && specialCases[3] == true){ //undo enpassent killing
            for (Piece p : opponent.pieces){ //find dead piece in opponent's list
                if (p instanceof Pawn && (p.getRow() == fromRow && p.getCol() == toCol)){ //tricky logic (fromRow
                    pieceToRevive = p;
                    pieceToRevive.isAlive = true;
                    board.setCell(pieceToRevive, fromRow, toCol);
                    pieceToRevive.getPossibleMoves();
                    break;
                }
            }
        } else{
            for (Piece p : opponent.pieces){ //find dead piece in opponent's list
                if (p.getRow() == toRow && p.getCol() == toCol){
                    pieceToRevive = p;
                    pieceToRevive.isAlive = true;
                    board.setCell(pieceToRevive, toRow, toCol);
                    pieceToRevive.getPossibleMoves();
                    break;
                }
            }
        }

        //clean up
        undoButton.setEnabled(false); //force 1 undo max, re-enable after turn
        if (undoCaller.color == 'w') { //make it be undoCaller's turn
            whiteTurn = true;
            setTurnTextView();
        } else{
            whiteTurn = false;
            setTurnTextView();
        }
        drawProposal = '0'; //reset proposal
        checkCheckmate(undoCaller);
        if (isCheck){ // check?
            setConsoleTextView("Check");
            isCheck = false;
        }
        refreshBoardImage();
        //use piece location for easy guesses to "un-set" moved piece data (ex. hasMoved)
    }

    public void AIButtonHandler(View v){
        if (!isPlaying){
            newChessGame(); //reset game
            return;
        }

        Player playerAI;
        ArrayList<Piece> possiblePieces;
        if (whiteTurn){
            playerAI = white;
            possiblePieces = playerAI.piecesWithOptionsList();
        } else{ //blackTurn
            playerAI = black;
            possiblePieces = playerAI.piecesWithOptionsList();
        }
        int randPieceIndex = randInt(0, possiblePieces.size() - 1);
        Piece randomPiece = possiblePieces.get(randPieceIndex);
        int randMoveIndex = randInt(0, playerAI.pieceOptionsList(randomPiece).size() - 1);
        Cell randomCell = playerAI.pieceOptionsList(randomPiece).get(randMoveIndex);
        int randomRow = randomCell.row;
        int randomCol = randomCell.col;
        String randomFromCoords = new String(Board.convertRowCol(randomPiece.getRow(), randomPiece.getCol()));
        String randomToCoords = new String(Board.convertRowCol(randomRow, randomCol));

        //clean up
        if (fromImageView != null) fromImageView.setAlpha(1.0f);
        fromImageView = null;
        toImageView = null;

        sendCoords(randomFromCoords, randomToCoords);
    }

    public void drawButtonHandler(View v){
        if (!isPlaying){
            newChessGame(); //reset game
            return;
        }
        if ((whiteTurn && drawProposal == 'w') || ((!whiteTurn) && drawProposal == 'b')) { //other player pressed the button last turn
            setConsoleTextView("Mutually agreed upon");
            endGame('d');
            return;
        }
        drawButton.setEnabled(false);
        if (whiteTurn){
            drawProposal = 'b';
        } else{
            drawProposal = 'w';
        }

        Toast.makeText(getApplicationContext(), "Proposing a DRAW", Toast.LENGTH_SHORT).show(); //make toast
    }

    public void resignButtonHandler(View v){
        if (!isPlaying){
            newChessGame(); //reset game
            return;
        }
        setConsoleTextView("Opponent resignation");
        if (whiteTurn){
            endGame('b'); //black wins
        } else{
            endGame('w'); //white wins
        }
    }

    public void listButtonHandler(View v){
        Intent intent = new Intent(this, GameLister.class);
        intent.putExtra("key_gameList", gameList);
        startActivity(intent);
    }

    // Changes text depending on whos turn
    public void setTurnTextView(){ //based on boolean whiteTurn
        String turnStr;
        if (whiteTurn){
            turnStr = "White's Move";
        } else{
            turnStr = "Black's Move";
        }
        turnTextView.setText(turnStr);
        System.out.println(turnStr);
    }

    // Changes text depending on input
    public void setConsoleTextView(String text){
        consoleTextView.setText(text);
        System.out.println(text);
    }


    /*************************
     * chess game
     *************************/
    static Board board;
    static Player white;
    static Player black;
    static boolean isPlaying;
    static boolean whiteTurn;
    static char drawProposal;
    static boolean isCheck;

    //Initialize game state
    public void newChessGame(){
        board = new Board();
        white = new Player('w', board);
        black = new Player('b', board);
        isPlaying = true;
        whiteTurn = true;	// white starts
        drawProposal = '0';
        isCheck = false;
        drawButton.setEnabled(true);
        undoButton.setEnabled(true);
        refreshBoardImage();
        setTurnTextView();
        setConsoleTextView("NEW GAME");
    }

    //End game state
    public void endGame(char winner){ //input winner
        isPlaying = false;
        switch(winner){
            case 'w':
                turnTextView.setText("White wins!");
                break;
            case 'b':
                turnTextView.setText("Black wins!");
                break;
            case 'd':
            default: //'d'
                turnTextView.setText("Draw!");
        }
        recordGame();
    }

    public static boolean checkCheckmate(Player playerToCheckOnKing){
        if (playerToCheckOnKing.king.isCheck()){ //King threatened
            //Check player options
            boolean hasOptions = playerToCheckOnKing.hasOptions();
            if (hasOptions){
                isCheck = true; //print "check" for other player
                return false; //Player has options
            }
            return true;
        }
        return false;
    }

    public static boolean checkStalemate(Player playerToCheck){ //INCOMPLETE
        if (!playerToCheck.king.isCheck()){
            return !playerToCheck.hasOptions(); //has no options
        }
        return false;
    }

    //Check game state for opposite player to get ready for next part
    public void checkGameState(char color){
        if (color == 'w'){
            if (checkCheckmate(black)){ // checkmante?
                setConsoleTextView("Checkmate");
                endGame('w');
                isPlaying = false;
                return; //program quits
            }
            if (checkStalemate(black)){ // stalemate?
                setConsoleTextView("Stalemate");
                endGame('d');
                isPlaying = false;
                return;
            }
            if (isCheck){ // check?
                setConsoleTextView("Check");
                isCheck = false;
            }
        } else{
            if (checkCheckmate(white)){ // checkmante?
                setConsoleTextView("Checkmate");
                endGame('b');
                isPlaying = false;
                return; //program quits
            }
            if (checkStalemate(white)){ // stalemate?
                setConsoleTextView("Stalemate");
                endGame('d');
                isPlaying = false;
                return;
            }
            if (isCheck){ // check?
                setConsoleTextView("Check");
                isCheck = false;
            }
        }
    }

    public void recordGame(){
        final EditText gameName = new EditText(this);
        gameName.setHint("Enter save name");

        new AlertDialog.Builder(this)
                .setTitle("Save Game?")
                .setView(gameName)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String name = gameName.getText().toString();
                        recordGameHelper(name, board.gameLog);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
    }
    private void recordGameHelper(String name, ArrayList<MoveData> gameLog){
        RecordedGame game = new RecordedGame(name, gameLog);
        gameList.saveGame(game);

        /* Test
        for (RecordedGame g : gameList.getGamesList()){
            System.out.println(">> "+ g.getName() + " - " + g.getDate());
            for (MoveData d : g.getData()){
                System.out.println(d.getInstruction());
            }
        }*/
    }

    public void playerTurn(String instruction){ //instruction: "a1 b2 N"
        // WHITE DO TURN
        if (whiteTurn){
            if (drawProposal == 'b') { //if black proposed draw
                Toast.makeText(getApplicationContext(), "Opponent motions a DRAW?", Toast.LENGTH_SHORT).show(); //make toast
            } else if (drawProposal == 'w'){ // was set from player's last turn, reset to 0
                drawProposal = '0';
            }

            if(white.doTurn(instruction)){ //move success
                black.unempassent();
                whiteTurn = false; //make black's turn next time
                setTurnTextView();
                setConsoleTextView("");
                drawButton.setEnabled(true); //re-enable button if disabled
                undoButton.setEnabled(true);
                refreshBoardImage();
                checkGameState('w');
            } else{ //move fail
                setConsoleTextView("Illegal move, try again");
                //wait for new command
            }

        // BLACK DO TURN
        } else{
            if (drawProposal == 'w') { //if white proposed draw
                Toast.makeText(getApplicationContext(), "Opponent motions a DRAW?", Toast.LENGTH_SHORT).show(); //make toast
            } else if (drawProposal == 'b'){ // was set from player's last turn, reset to 0
                drawProposal = '0';
            }

            if(black.doTurn(instruction)){ //move success
                white.unempassent();
                whiteTurn = true; //make white's turn next time
                setTurnTextView();
                setConsoleTextView("");
                drawButton.setEnabled(true); //re-enable button if disabled
                undoButton.setEnabled(true);
                refreshBoardImage();
                checkGameState('b');
            } else{ //move fail
                setConsoleTextView("Illegal move, try again");
                //wait for new command
            }
        }
    }

    public static int randInt(int min, int max) { //inclusive
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}

//lol
