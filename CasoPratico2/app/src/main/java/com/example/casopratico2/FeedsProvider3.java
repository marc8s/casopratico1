package com.example.casopratico2;

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
import android.util.Log;

public class FeedsProvider3 extends ContentProvider {
    public static final Uri CONTENT_URI = Uri.parse("content://tsf.pt");
    public static final int POST = 1;
    public static final int POST_ID = 2;
    public static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("tsf.pt", "post", POST);
        uriMatcher.addURI("tsf.pt", "post/#", POST_ID);
    }
    private SQLiteDatabase feedsDB;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        FeedsSQLHelper3 dbHelper = new FeedsSQLHelper3(context);
        feedsDB = dbHelper.getWritableDatabase();
        return (feedsDB == null) ? false : true;
    }


    @Override
    public String getType( Uri uri) {
        switch (uriMatcher.match(uri)){
            case POST:
                return "vnd.android.cursor.dir/vnd.tsf.post";
            case POST_ID:
                return "vnd.android.cursor.item/vnd.tsf.post";
            default:
                throw new IllegalArgumentException("Unsupported URI: " +uri);
        }
    }



    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = feedsDB.replace(FeedsDB.Posts.NOME_TABELA_3, "", values);
        if(rowID>0){
            Uri baseUri = Uri.parse("content://tsf.pt/post");
            Uri _uri = ContentUris.withAppendedId(baseUri, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            getContext().getContentResolver().notifyChange(baseUri, null);
            return _uri;
        }throw new SQLException("Failed to insert row into "+ uri);

    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case POST:
                count = feedsDB.delete(FeedsDB.Posts.NOME_TABELA_3, where, whereArgs);
                break;
            case POST_ID:
                String id = uri.getPathSegments().get(1);
                count = feedsDB.delete(FeedsDB.Posts.NOME_TABELA_3, FeedsDB.Posts._ID
                        + " = "
                        + id
                        + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update( Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case POST:
                count = feedsDB.update(FeedsDB.Posts.NOME_TABELA_3, values, selection, selectionArgs);
                break;
            case POST_ID:
                count = feedsDB.update(FeedsDB.Posts.NOME_TABELA_3, values, FeedsDB.Posts._ID
                        + " = "
                        +uri.getPathSegments().get(1)
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
        sqlBuilder.setTables(FeedsDB.Posts.NOME_TABELA_3);
        if(uriMatcher.match(uri) == POST_ID){
            sqlBuilder.appendWhere(FeedsDB.Posts._ID + " = "
                    + uri.getPathSegments().get(1));
        }
        if (sortOrder == null || sortOrder == ""){
            sortOrder = FeedsDB.Posts.DEFAULT_SORT_ORDER;
        }
        Cursor c = sqlBuilder.query(feedsDB, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }



}
