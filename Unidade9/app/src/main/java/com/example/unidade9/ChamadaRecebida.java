package com.example.unidade9;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

public class ChamadaRecebida extends BroadcastReceiver {
    private String numeroChamada;
    private String numeroIndesejado = "1234";


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle b=intent.getExtras();
        numeroChamada=b.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Class c = Class.forName(tm.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            com.android.internal.telephony.ITelephony telephonyService = (ITelephony) m.invoke(tm);
            Log.i( numeroIndesejado, "entrou no onreceive");
            if(numeroIndesejado.equals(numeroChamada)){
                telephonyService.endCall();
                Log.i( numeroIndesejado, "entrou no indesejado");



            }

        }catch(Exception e){

        }
    }
}
