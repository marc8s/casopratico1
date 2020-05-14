package com.example.unidade9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class ChamadaActivity extends AppCompatActivity {

    //private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamada);
        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        tManager.listen(new TelefoneListener(), PhoneStateListener.LISTEN_CALL_STATE);
    }


    class TelefoneListener extends PhoneStateListener{
        @Override
        public void onCallStateChanged(int state, String number) {
            switch (state){
                case TelephonyManager.CALL_STATE_RINGING:
                    Toast.makeText(ChamadaActivity.this, "Telefone a tocar ..." + number,
                            Toast.LENGTH_LONG).show();
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Toast.makeText(ChamadaActivity.this, "Telefone fora do gancho..." + number,
                            Toast.LENGTH_LONG).show();
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    Toast.makeText(ChamadaActivity.this, "Em espera..." + number,
                            Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

}
