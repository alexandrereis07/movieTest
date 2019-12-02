package com.example.alexandremovietest.database.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.alexandremovietest.database.DbGateway;
import com.example.alexandremovietest.database.model.Display;

//OBS: DAO feita somente para cumprir o checklist do teste
public class DisplayDAO {

    private final String TABLE = "display";
    private DbGateway gw;

    public DisplayDAO(Context ctx){
        gw = DbGateway.getInstance(ctx);
    }

    public Display getTypeDisplay(){
        Display display = new Display();
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM "+TABLE, null);
        if(!cursor.moveToNext()){
            ContentValues cv = new ContentValues();
            cv.put("display_type", 1);
            gw.getDatabase().insert(TABLE, null, cv);
            display.setId(1);
            display.setDisplayType(1);
        }else{
            display.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            display.setDisplayType(cursor.getInt(cursor.getColumnIndex("display_type")));
        }
        cursor.close();
        return display;
    }


    public void update(Display display){
        ContentValues cv = new ContentValues();
        cv.put("display_type" , display.getDisplayType());
        gw.getDatabase().update(TABLE, cv,"_id="+display.getId() , null);
    }

}
