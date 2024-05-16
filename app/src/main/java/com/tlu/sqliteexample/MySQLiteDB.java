package com.tlu.sqliteexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;

public class MySQLiteDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "books.db";
    public static final String TABLE_BOOK = "Books";
    public  static final String BOOK_ID = "ID";
    public static final String BOOK_TITLE = "TITLE";
    public static final String BOOK_AUTHOR = "AUTHOR";
    public static final String BOOK_TAG = "TAG";


    private static final String CREATE_TABLE_BOOK = "CREATE TABLE " + TABLE_BOOK + " (" + BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BOOK_TITLE + " TEXT, " + BOOK_AUTHOR + " TEXT, " + BOOK_TAG + " TEXT)";

    public MySQLiteDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(CREATE_TABLE_BOOK);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}