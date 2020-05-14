package com.example.enciclopedia;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class BaseDados extends AppCompatActivity {
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_dados);

        Button botao = (Button) findViewById(R.id.btnGuardaFormandos);

        db = new DataBaseHelper(this);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionar(db);
                mostrarNomes(db);
            }
        });
    }

    public void adicionar(DataBaseHelper db){
        EditText nome = (EditText) findViewById(R.id.nomeFormando);
        EditText contato = (EditText) findViewById(R.id.contatoFormando);
        db.inserir(nome.getText().toString(), contato.getText().toString());
    }

    public void mostrarNomes(DataBaseHelper db){
        ListView lista = (ListView) findViewById(R.id.listaFormandos);
        List<String> nomes = db.getTodos();

        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nomes);
        lista.setAdapter(a);
    }
}
