package com.example.enciclopedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.arminterno:
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ArmazenamentoInterno.class);
                startActivity(intent);
                return true;
            case R.id.basedados:
                Intent intent1 = new Intent();
                intent1.setClass(getApplicationContext(), BaseDados.class);
                startActivity(intent1);
                return true;
            case R.id.contatoAgenda:
                Intent intent2 = new Intent();
                intent2.setClass(getApplicationContext(), ContatoAgenda.class);
                startActivity(intent2);
                return true;
            case R.id.contatosProvider:
                Intent intent3 = new Intent();
                intent3.setClass(getApplicationContext(), ContatoProvider.class);
                startActivity(intent3);
                return true;
            case R.id.preferenciasCor:
                Intent intent4 = new Intent();
                intent4.setClass(getApplicationContext(), PreferenciasCor.class);
                startActivity(intent4);
                return true;
            case R.id.toast:
                String aboutTxt2 = "Eu sou uma toast";
                Toast.makeText(MainActivity.this, aboutTxt2, Toast.LENGTH_LONG).show();
                return true;
            case R.id.menusair:
                finish();
                return true;
            default:
                return false;

        }
    }

}
