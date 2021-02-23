package com.pb.app.bookmarketingE.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.pb.app.bookmarketingE.data.favorite.FavoriteEntity;

public class DatabaseSQL {

    private static DatabaseSQL instance;

    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private ContentValues contentValues;
    private boolean isDBSuccessful = false;

    private DatabaseSQL(){}

    public boolean writeUserDB(Context context) {
        dbHelper = new DBHelper(context);
        try {
            database = dbHelper.getWritableDatabase();
        }
        catch (SQLiteException ex){
            database = dbHelper.getReadableDatabase();
        }
        contentValues = new ContentValues();
        isDBSuccessful = false;
        FavoriteEntity.getInstance().clearFavorite();
        Cursor cursor = database.query(DBHelper.TABLE_TYPES, null, null, null,
                null, null, null);
        if (cursor.moveToFirst()) {
            isDBSuccessful = true;
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int tokenIndex = cursor.getColumnIndex(DBHelper.KEY_TOKEN);
            FavoriteEntity.getInstance().addFavorite(cursor.getString(tokenIndex), cursor.getString(idIndex));
            while (cursor.moveToNext()){
                int id = cursor.getColumnIndex(DBHelper.KEY_ID);
                int token = cursor.getColumnIndex(DBHelper.KEY_TOKEN);
                FavoriteEntity.getInstance().addFavorite(cursor.getString(token), cursor.getString(id));
            };
        }

        cursor.close();
        return isDBSuccessful;
    }

    public void readUserDb(Context context, String token, String id){
        dbHelper = new DBHelper(context);
        try {
            database = dbHelper.getReadableDatabase();
        }
        catch (SQLiteException ex){
            database = dbHelper.getWritableDatabase();
        }
        contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_TOKEN, token);
        database.insert(DBHelper.TABLE_TYPES, null, contentValues);
        writeUserDB(context);
    }

    public void deleteUserDb(Context context, String id){
        dbHelper = new DBHelper(context);
        try {
            database = dbHelper.getWritableDatabase();
        }
        catch (SQLiteException ex){
            database = dbHelper.getReadableDatabase();
        }
        database.delete(DBHelper.TABLE_TYPES, DBHelper.KEY_ID + "=" + id, null);
        writeUserDB(context);
    }

    public boolean isDBSuccessful(){
        return isDBSuccessful;
    }

    public static DatabaseSQL getInstance(){
        if(instance == null) instance = new DatabaseSQL();
        return instance;
    }
}
