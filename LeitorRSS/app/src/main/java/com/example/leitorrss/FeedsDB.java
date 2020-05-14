package com.example.leitorrss;

import android.provider.BaseColumns;

/*
* ESTA CLASSE SIMPLIFICA O NOME DOS CAMPOS E DAS TABELAS PARA QUE
* SEJA POSSIVEL REFERENCIALOS DE FORMA MAIS SIMPLES*/

public class FeedsDB {

    //nome da base de dados
    public static final String DB_NAME = "jogo.db";
    //versao da base
    public static final int DB_VERSION = 1;
    //esta CLASSE NÃO DEVE SER INSTANCIADA
    private FeedsDB(){ }
    //definição da tabela posts
    public static final class Posts implements BaseColumns{
        //essa classe não deve ser instanciada
        private Posts(){}
        //ordem por defeito
        public static final String DEFAULT_SORT_ORDER = "_ID DESC";

        public static final String NOME_TABELA = "feeds";
        public static final String _ID = "_id";
        public static final String TITLE = "title";
        public static final String LINK = "link";
        public static final String COMMENTS = "comments";
        public static final String PUB_DATE = "pubDate";
        public static final String CREATOR = "creator";
        public static final String DESCRIPTION = "description";
        public static final String FRASE = "frase";
        public static final String _COUNT = "8";
    }
}
