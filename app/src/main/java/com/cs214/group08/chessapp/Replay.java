package com.cs214.group08.chessapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Replay extends AppCompatActivity {

    RecordedGame game;
    int listIndex; //index of game
    int turnIndex; //index of turn

    private TextView nameTextView;
    private TextView dateTextView;
    private TextView turnTextView;
    private TextView consoleTextView;

    private Button prevButton;
    private Button nextButton;

    ImageView[][] cellImageViewList; // 2d array of graphical board

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replay);

        Intent intent = getIntent();
        RecordedGamesList recordedGamesList = (RecordedGamesList)intent.getSerializableExtra("key_replay");
        listIndex = (int)intent.getSerializableExtra("key_index");
        game = recordedGamesList.loadGame(listIndex);

        nextButton = (Button)findViewById(R.id.nextButton);
        nameTextView = (TextView)findViewById(R.id.nameTextView);
        dateTextView = (TextView)findViewById(R.id.dateTextView);
        nameTextView.setText(game.getName());
        dateTextView.setText(game.getDate());

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

        turnIndex = 0;
        newChessGame(); //new board
    }

    public void nextButtonHandler(View v){
        if (turnIndex == game.getData().size()){
            Toast.makeText(getApplicationContext(), "End of File", Toast.LENGTH_SHORT).show(); //make toast
            return;
        }
        String command = game.getData().get(turnIndex).getInstruction();
        playerTurn(command);
        setConsoleTextView(command);
        turnIndex++;
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

    /************************************
     * Event Listeners / Button Handlers
     ************************************/
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
        refreshBoardImage();
        setTurnTextView();
        setConsoleTextView("Starting replay...");
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
                refreshBoardImage();
                checkGameState('w');
            } else{ //move fail
                setConsoleTextView("Illegal move, try again");
                //wait for new command
            }

            // BLACK DO TURN
        } else{
            if (drawProposal == 'w') { //if white proposed draw
                Toast.makeText(getApplicationContext(), "Opponent motions a DRAW?", Toast.LENGTH_LONG).show(); //make toast
            } else if (drawProposal == 'b'){ // was set from player's last turn, reset to 0
                drawProposal = '0';
            }

            if(black.doTurn(instruction)){ //move success
                white.unempassent();
                whiteTurn = true; //make white's turn next time
                setTurnTextView();
                setConsoleTextView("");
                refreshBoardImage();
                checkGameState('b');
            } else{ //move fail
                setConsoleTextView("Illegal move, try again");
                //wait for new command
            }
        }
    }
}
