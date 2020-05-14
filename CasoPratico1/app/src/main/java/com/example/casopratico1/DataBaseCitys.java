package com.example.casopratico1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class DataBaseCitys extends AppCompatActivity {
    DataBaseCitysHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base_citys);

        Button guardaCidade = (Button) findViewById(R.id.button);

        db = new DataBaseCitysHelper(this);
        mostrarCidades(db);
        guardaCidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionar(db);
                mostrarCidades(db);
            }
        });
    }

    public void adicionar(DataBaseCitysHelper db){
        EditText nome = (EditText) findViewById(R.id.editText);
        db.inserir(nome.getText().toString());
        nome.setText("");
    }

    public void mostrarCidades(DataBaseCitysHelper db){
        ListView lista = (ListView) findViewById(R.id.lista);
        List<String> cidades = db.getTodos();

        ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cidades);
        lista.setAdapter(a);

    }


}
