package com.example.leitorrss;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.example.leitorrss.FeedsDB.Posts;

/*
* ESSA CLASSE TARTA-SE DO FORNECEDOR DE CONTEUDO RESPONSAVEL PELA INTRODUÇAO
* E RETIRADA DE DADOS DA BASE DE DADOS*/

public class FeedsProvider extends ContentProvider {
    public static final Uri CONTENT_URI = Uri.parse("content://pplware.sapo.pt/feed/");
    public static final int POST = 1;
    public static final int POST_ID = 2;
    public static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("pplware.sapo.pt/feed/", "post", POST);
        uriMatcher.addURI("pplware.sapo.pt/feed/", "post/#", POST_ID);
    }
    private SQLiteDatabase feedsDB;



    @Override
    public boolean onCreate() {
        Context context = getContext();
        FeedsSQLHelper dbHelper = new FeedsSQLHelper(context);
        feedsDB = dbHelper.getWritableDatabase();
        return (feedsDB == null) ? false : true;
    }
    //notifica os objetos apos operações realizadas para que possam ser atualizados
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
        sqlBuilder.setTables(Posts.NOME_TABELA);
        if(uriMatcher.match(uri) == POST_ID){
            sqlBuilder.appendWhere(Posts._ID + " = "
            + uri.getPathSegments().get(1));
        }
        if(sortOrder == null || sortOrder == ""){
            sortOrder = Posts.DEFAULT_SORT_ORDER;
        }
        Cursor c = sqlBuilder.query(feedsDB, projection, selection, selectionArgs,
                null, null, sortOrder);
        //registo das alteraçoes para que o observer entre
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        //define types para um conjunto de noticias e outro para a noticias simples

        switch (uriMatcher.match(uri)){
            //para conjunto de posts
            case POST:
                return "vnd.android.cursor.dir/vnd.pplware.sapo.pt.post";
            case POST_ID:
                return  "vnd.android.cursor.item/vnd.pplware.sapo.pt.post";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);

        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long rowId = feedsDB.replace(Posts.NOME_TABELA, "", values);
        //se ocorreu tudo bem
        if(rowId>0){
            Uri baseUri = Uri.parse("content://pplware.sapo.pt/feed/");
            Uri _uri = ContentUris.withAppendedId(baseUri, rowId);
            getContext().getContentResolver().notifyChange(_uri, null);
            getContext().getContentResolver().notifyChange(baseUri, null);
            return _uri;

        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String where, @Nullable String[] whereargs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case POST:
                count = feedsDB.delete(Posts.NOME_TABELA, where, whereargs);
                break;
            case POST_ID:
                String id = uri.getPathSegments().get(1);
                count = feedsDB.delete(Posts.NOME_TABELA,
                        Posts._ID
                        + " = "
                        + id
                        + (!TextUtils.isEmpty(where) ? "AND (" + where
                                + ')' : ""), whereargs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);

        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case POST:
                count = feedsDB.update(Posts.NOME_TABELA, values, selection, selectionArgs);
                break;
            case POST_ID:
                count = feedsDB.update(Posts.NOME_TABELA, values,
                        Posts._ID
                + " = "
                + uri.getPathSegments().get(1)
                + (!TextUtils.isEmpty(selection) ? " AND ("
                                + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknow URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
