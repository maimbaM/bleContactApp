package com.maimba.west.bleContactApp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Blepackets.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table ScannedPackets(id INTEGER primary key AUTOINCREMENT,pktData TEXT, timeSeen datetime default current_timestamp)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists Blepackets");

    }

    public Boolean insertpktdata( String pktData){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("pktData", pktData);
        long result=DB.insert("ScannedPackets",null,contentValues);
        if (result==-1){
            return false;
        }else {
            return true;
        }


    }
    public Boolean deletepktdata(){
        SQLiteDatabase DB = this.getWritableDatabase();

       long result = DB.delete("ScannedPackets","timeSeen = ?", new String[]{"(timeSeen<= datetime('now', '-14 days'))"} );
        if (result==-1){
            return false;
        }else {
            return true;
        }

    }
}
