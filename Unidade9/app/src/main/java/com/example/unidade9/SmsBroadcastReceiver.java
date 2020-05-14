package com.example.unidade9;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try{
            Object[] pdus = (Object[]) intent.getExtras().get("pdus");
            int numPds = pdus.length;
            SmsMessage[] mensagens = new SmsMessage[numPds];

            for(int x=0; x<numPds;x++){
                mensagens[x] = SmsMessage.createFromPdu((byte[]) pdus[x]);
                String mensagem_de = mensagens[x].getOriginatingAddress();
                String corpo_mensagem = mensagens[x].getDisplayMessageBody();

                Toast.makeText(context, "Acabou de receber uma mensagem de: "+ mensagem_de + "\n\n"
                    + corpo_mensagem, Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
