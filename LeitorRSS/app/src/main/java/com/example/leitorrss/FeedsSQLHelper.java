package com.example.leitorrss;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
* ESSA CLASSE SERA USADA PARA AJUDAR A CRIAR A BASE DE DADOS
* CASO SEJA NECESSÁRIO*/

public class FeedsSQLHelper extends SQLiteOpenHelper {

    //CONSTRUTOR
    public FeedsSQLHelper(Context context){
        super(context, FeedsDB.DB_NAME, null, FeedsDB.DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        if(db.isReadOnly()){
            db = getWritableDatabase();
        }
        db.execSQL("CREATE TABLE " + FeedsDB.Posts.NOME_TABELA + " ("
        + FeedsDB.Posts._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + FeedsDB.Posts.TITLE + " TEXT," + FeedsDB.Posts.LINK
        + " TEXT UNIQUE," + FeedsDB.Posts.COMMENTS + " TEXT,"
        + FeedsDB.Posts.PUB_DATE + "INTEGER" + FeedsDB.Posts.CREATOR
        + " TEXT," + FeedsDB.Posts.DESCRIPTION + " TEXT," + FeedsDB.Posts.FRASE + " TEXT"+ ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
