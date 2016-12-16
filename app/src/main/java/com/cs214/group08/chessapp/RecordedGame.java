package com.cs214.group08.chessapp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Renard on 12/15/2016.
 */

public class RecordedGame implements Serializable {
    private String name;
    private String date;
    private ArrayList<MoveData> data;

    public RecordedGame(String name, ArrayList<MoveData> data){
        this.name = name;
        this.data = data;
        date = new SimpleDateFormat("yyyy/MM/dd (kk:mm:ss)").format(new Date());
    }

    public String getName(){
        return name;
    }
    public String getDate(){
        return date;
    }
    public ArrayList<MoveData> getData(){
        return data;
    }

    @Override
    public String toString(){
        return getName() + " - " + getDate();
    }
}
