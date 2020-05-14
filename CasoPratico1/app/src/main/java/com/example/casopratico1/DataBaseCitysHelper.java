package com.example.casopratico1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBaseCitysHelper extends SQLiteOpenHelper {

    /* DEFINICAO CAMPOS */
    private static final int VERSAO_BD = 1;
    private static final String NOME_BD = "cidades";
    private static final String T_CIDADES = "cidades";
    private static final String C_ID = "id";
    private static final String C_NOME = "nome";


    /* CONSTRUTOR */
    public DataBaseCitysHelper(Context context){
        super(context, NOME_BD, null, VERSAO_BD);
    }

    /* CRIAÇÃO TABELA */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String c1 = C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        String c2 = C_NOME + " TEXT NOT NULL) ";
        String criar = "CREATE TABLE " + T_CIDADES + "(" + c1 + c2;
        sqLiteDatabase.execSQL(criar);
    }

    /* ATUALIZAÇÃO TABELA*/
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    /* INSERIR CIDADES */
    public void inserir(String nome){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_NOME, nome);
        db.insert(T_CIDADES, null, values);
        db.close();
    }

    /* LISTAR CIDADES */
    public List<String> getTodos(){
        List<String> lista = new ArrayList<>();
        String sql = "SELECT " + "ALL * FROM " + T_CIDADES + " ORDER BY " + C_NOME + " ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        //Verifica se a lista possui registros
        if(cursor.moveToFirst()){
            do{
                String item = cursor.getString(1);
                lista.add(item);
            }while (cursor.moveToNext());
        }
        db.close();
        return lista;
    }
}
