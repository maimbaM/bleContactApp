package com.maimba.west.bleContactApp.DB;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {ScannedPacket.class, ExposurePacket.class , Location.class, ServiceData.class},version = 2)
public abstract class ContractTracingDB extends RoomDatabase {

    private static ContractTracingDB instance;

    public abstract ScannedDao scannedDao();


    public static synchronized ContractTracingDB getInstance(Context context){

        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ContractTracingDB.class,"ContactTracingDB")
                    .fallbackToDestructiveMigration()
                    .build();


        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };


}
