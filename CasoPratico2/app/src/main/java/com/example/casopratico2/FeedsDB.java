package com.example.casopratico2;

import android.provider.BaseColumns;

public class FeedsDB {

    public static final String DB_NAME = "noticia.db";
    public static final String DB_NAME_3 = "tsffeed.db";
    public static final String DB_NAME_4 = "correio.db";
    public static final int DB_VERSION = 1;
    public static final int DB_VERSION_3 = 1;
    public static final int DB_VERSION_4 = 1;

    private FeedsDB(){

    }
    //definição da tabela
    public static final class Posts implements BaseColumns{
        private Posts(){

        }
        public static final String DEFAULT_SORT_ORDER = "_ID DESC";
        public static final String NOME_TABELA_2 = "publico";
        public static final String NOME_TABELA_3 = "tst";
        public static final String NOME_TABELA_4 = "correio";
        public static final String _ID = "_id";
        public static final String TITLE = "title";
        public static final String LINK = "link";
        public static final String PUB_DATE = "pub_date";

        public static final String _COUNT = "8";


    }
}
