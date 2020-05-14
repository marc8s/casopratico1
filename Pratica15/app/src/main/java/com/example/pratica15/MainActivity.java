package com.example.pratica15;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private TextView proximidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        proximidade = (TextView) findViewById(R.id.proximidade);
        mSensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(
                Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        mSensorManager.unregisterListener(this,mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY));
        super.onStop();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.values[0]==0){
            //se ocorre alteração sensor pra perto
            setContentView(R.layout.layout_vermelho);
        }else{
            //sensor altera proximidade longe
            setContentView(R.layout.activity_main);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
