package com.example.pr_idi.mydatabaseexample;

/**
 * MySQLiteHelper
 * Created by pr_idi on 10/11/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_FILMS = "films";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_YEAR_RELEASE = "year_release";
    public static final String COLUMN_DIRECTOR = "director";
    public static final String COLUMN_PROTAGONIST = "protagonist";
    public static final String COLUMN_CRITICS_RATE = "critics_rate";


    private static final String DATABASE_NAME = "films.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " + TABLE_FILMS + "( "
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_COUNTRY + " text not null, "
            + COLUMN_YEAR_RELEASE + " integer not null, "
            + COLUMN_DIRECTOR + " text not null, "
            + COLUMN_PROTAGONIST + " text not null, "
            + COLUMN_CRITICS_RATE + " integer"
            + ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_TITLE, "Blade Runner");
            values.put(MySQLiteHelper.COLUMN_DIRECTOR, "Ridley Scott");
            values.put(MySQLiteHelper.COLUMN_COUNTRY, "USA");
            values.put(MySQLiteHelper.COLUMN_YEAR_RELEASE, 1982);
            values.put(MySQLiteHelper.COLUMN_PROTAGONIST, "Harrison Ford");
            values.put(MySQLiteHelper.COLUMN_CRITICS_RATE, 0);

            database.insert(MySQLiteHelper.TABLE_FILMS, null, values);

            values.put(MySQLiteHelper.COLUMN_TITLE, "The Rocky Horror Picture Show");
            values.put(MySQLiteHelper.COLUMN_DIRECTOR, "Jim Sharman");
            values.put(MySQLiteHelper.COLUMN_COUNTRY, "USA");
            values.put(MySQLiteHelper.COLUMN_YEAR_RELEASE, 1975);
            values.put(MySQLiteHelper.COLUMN_PROTAGONIST, "Tim Curry");
            values.put(MySQLiteHelper.COLUMN_CRITICS_RATE, 0);

            database.insert(MySQLiteHelper.TABLE_FILMS, null, values);

            values.put(MySQLiteHelper.COLUMN_TITLE, "The Godfather");
            values.put(MySQLiteHelper.COLUMN_DIRECTOR, "Francis Ford Coppola");
            values.put(MySQLiteHelper.COLUMN_COUNTRY, "USA");
            values.put(MySQLiteHelper.COLUMN_YEAR_RELEASE, 1972);
            values.put(MySQLiteHelper.COLUMN_PROTAGONIST, "Marlon Brando");
            values.put(MySQLiteHelper.COLUMN_CRITICS_RATE, 0);

            database.insert(MySQLiteHelper.TABLE_FILMS, null, values);

            values.put(MySQLiteHelper.COLUMN_TITLE, "Toy Story");
            values.put(MySQLiteHelper.COLUMN_DIRECTOR, "John Lasseter");
            values.put(MySQLiteHelper.COLUMN_COUNTRY, "USA");
            values.put(MySQLiteHelper.COLUMN_YEAR_RELEASE, 1995);
            values.put(MySQLiteHelper.COLUMN_PROTAGONIST, "Tom Hanks");
            values.put(MySQLiteHelper.COLUMN_CRITICS_RATE, 0);

            database.insert(MySQLiteHelper.TABLE_FILMS, null, values);

            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILMS);
        this.onCreate(db);
    }

}