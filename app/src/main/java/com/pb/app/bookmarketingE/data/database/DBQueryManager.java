package com.pb.app.bookmarketingE.data.database;

import android.database.sqlite.SQLiteDatabase;

public class DBQueryManager {

    private SQLiteDatabase database;

    DBQueryManager (SQLiteDatabase database){
        this.database = database;
    }
}
