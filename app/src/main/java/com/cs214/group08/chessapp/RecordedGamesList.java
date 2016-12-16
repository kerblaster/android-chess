package com.cs214.group08.chessapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.R.attr.data;

/**
 * Created by Renard on 12/15/2016.
 */

public class RecordedGamesList implements Serializable{
    private ArrayList<RecordedGame> recordedGamesList;

    public RecordedGamesList(){
        recordedGamesList = new ArrayList<RecordedGame>();
    }

    public void saveGame(RecordedGame game){
        recordedGamesList.add(game);
    }
    public RecordedGame loadGame(int index){
        return recordedGamesList.get(index);
    }
    public int getSize(){
        return recordedGamesList.size();
    }

    public ArrayList<RecordedGame> getGamesListByName(){
        Collections.sort(recordedGamesList, new nameComparator());
        return recordedGamesList;
    }

    public ArrayList<RecordedGame> getGamesListByDate(){
        Collections.sort(recordedGamesList, new dateComparator());
        return recordedGamesList;
    }
}

class nameComparator implements Comparator<RecordedGame>{
    @Override
    public int compare(RecordedGame game1, RecordedGame game2){
        return game1.getName().compareTo(game2.getName());
    }
}

class dateComparator implements Comparator<RecordedGame>{
    @Override
    public int compare(RecordedGame game1, RecordedGame game2){
        return game1.getDate().compareTo(game2.getDate());
    }
}