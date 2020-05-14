package com.example.casopratico2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedsSQLHelper4 extends SQLiteOpenHelper {

    //construtor
    public FeedsSQLHelper4(Context context){
        super(context, FeedsDB.DB_NAME_4, null, FeedsDB.DB_VERSION_4);
    }

    //CRIAÇÃO DAS TABELAS NA BASE DE DADOS
    @Override
    public void onCreate(SQLiteDatabase db) {
        if (db.isReadOnly()){
            db = getWritableDatabase();
        }
        db.execSQL("CREATE TABLE " + FeedsDB.Posts.NOME_TABELA_4 + "("
                + FeedsDB.Posts._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FeedsDB.Posts.TITLE + " TEXT," + FeedsDB.Posts.LINK
                + " TEXT UNIQUE," + FeedsDB.Posts.PUB_DATE + " INTEGER"+ ")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

