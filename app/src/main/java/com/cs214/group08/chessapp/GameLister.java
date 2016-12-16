package com.cs214.group08.chessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class GameLister extends AppCompatActivity {

    private Button sortByNameButton;
    private Button sortByDateButton;
    private ListView gamesListView;


    RecordedGamesList recordedGamesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lister);
        Intent intent = getIntent();
        recordedGamesList = (RecordedGamesList)intent.getSerializableExtra("key_gameList");

        gamesListView = (ListView) findViewById(R.id.gameListView);

        sortByNameButton = (Button)findViewById(R.id.sortByNameButton);
        sortByDateButton = (Button)findViewById(R.id.sortByDateButton);
        remakeListViewByName();
    }

    private void remakeListViewByName(){
        sortByNameButton.setEnabled(false);
        sortByDateButton.setEnabled(true);

        ArrayAdapter<RecordedGame> arrayAdapter = new ArrayAdapter<RecordedGame>(
                this,
                android.R.layout.simple_list_item_1,
                recordedGamesList.getGamesListByName()
        );
        gamesListView.setAdapter(arrayAdapter);
        setListViewListeners();
    }

    private void remakeListViewByDate(){
        sortByNameButton.setEnabled(true);
        sortByDateButton.setEnabled(false);

        ArrayAdapter<RecordedGame> arrayAdapter = new ArrayAdapter<RecordedGame>(
                this,
                android.R.layout.simple_list_item_1,
                recordedGamesList.getGamesListByDate()
        );
        gamesListView.setAdapter(arrayAdapter);
        setListViewListeners();
    }

    public void setListViewListeners(){
        gamesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GameLister.this, Replay.class);
                intent.putExtra("key_replay", recordedGamesList);
                intent.putExtra("key_index", position);
                startActivity(intent);
            }
        });
    }
    /****************
     *Buttons
     ***************/
    public void sortByNameButtonHandler(View v){
        remakeListViewByName();
    }

    public void sortByDateButtonHandler(View v){
        remakeListViewByDate();
    }
}
