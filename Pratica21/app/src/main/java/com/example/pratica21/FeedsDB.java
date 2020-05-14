package com.example.pratica21;

import android.provider.BaseColumns;

public class FeedsDB {
    //nome bd
    public static final String DB_NAME = "noticiario.db";
    public static final int DB_VERSION = 1;

    private FeedsDB(){

    }
    //definição da tabela
    public static final class Posts implements BaseColumns{
        private Posts(){

        }
        public static final String DEFAULT_SORT_ORDER = "_ID DESC";
        public static final String NOME_TABELA = "feeds";
        public static final String _ID = "_id";
        public static final String TITLE = "title";
        public static final String LINK = "link";
        public static final String COMMENTS = "comments";
        public static final String PUB_DATE = "pub_date";
        public static final String CREATOR = "creator";
        public static final String DESCRIPTION = "description";
        public static final String FRASE = "frase";
        public static final String _COUNT = "8";


    }
}
