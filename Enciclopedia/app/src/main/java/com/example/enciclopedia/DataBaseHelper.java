package com.example.enciclopedia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    /*
    POR REGRA O NOME DAS CONSTANTES É EM LETRA MAIUSCULA PARA DIFERENCIAR DAS VARIAVEIS
     */
    private static final int VERSAO_BD = 1;
    private static final String NOME_BD = "agenda";
    private static final String T_FORMANDOS = "formandos";
    private static final String C_ID = "id";
    private static final String C_NOME = "nome";
    private static final String C_CONTATO = "contato";

    /*
    CONSTRUTOR
     */
    public DataBaseHelper(Context context){
        super(context, NOME_BD, null, VERSAO_BD);
    }
    /*
    CRIACAO DA TABELA
     */

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String c1 = C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        String c2 = C_NOME + " TEXT NOT NULL, ";
        String c3 = C_CONTATO + " TEXT NOT NULL) ";

        String criar = "CREATE TABLE " + T_FORMANDOS + "(" + c1  + c2  + c3;
        sqLiteDatabase.execSQL(criar);

    }
    /*
    CASO A APLICAÇAO PRECISE ATUALIZAR A TABELA COM NOVOS CAMPOS POR EXEMPLO
    ALTERA-SE A CONSTANTE VERSAO_DB
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    /*
    ACOES TABELA
     */
    public void inserir(String nome, String contato){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_NOME, nome);
        values.put(C_CONTATO, contato);
        db.insert(T_FORMANDOS, null, values);
        db.close();
    }

    public List<String> getTodos(){
        List<String> lista = new ArrayList<String>();
        String sql = "SELECT " +  "ALL * FROM " + T_FORMANDOS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        //checa se a lista tem registros
        if(cursor.moveToFirst()){
            do{
                String item = cursor.getString(1);
                lista.add(item);
            }while(cursor.moveToNext());
        }
        db.close();
        return lista;
    }
}
