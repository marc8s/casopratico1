package com.example.casopratico1;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    private GoogleMap mMap;
    Geocoder geocoder;
    DataBaseCitysHelper db;
    Spinner spinner;
    String cidade;
    LatLng coordenadas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        geocoder = new Geocoder(this);
        db = new DataBaseCitysHelper(this);
        carregarCidades(db);
        spinner.setOnItemSelectedListener(this);

        Button botao = (Button) findViewById(R.id.button4);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                novoLocal();
            }
        });


    }
    public void novoLocal(){
        List<Address> addr = null;
        try {
            addr = geocoder.getFromLocationName(cidade, 3);
        }catch (IOException e){
            e.printStackTrace();
        }
        Address ad = addr.get(0);
        coordenadas = new LatLng(ad.getLatitude(), ad.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadas, 14));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public void carregarCidades(DataBaseCitysHelper db){
        List<String> cidades = db.getTodos();
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cidades);
        spinner.setAdapter(a);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        cidade = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
