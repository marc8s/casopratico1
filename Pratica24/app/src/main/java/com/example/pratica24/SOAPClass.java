package com.example.pratica24;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SOAPClass {
    static final String TAG = "Resposta";
    static String SOAP_ACTION = "https://www.w3schools.com/xml/CelsiusToFahrenheit";
    static String METHOD_NAME = "CelsiusToFahrenheit";
    static String NAMESPACE = "https://www.w3schools.com/xml/";
    static String URL = "https://www.w3schools.com/xml/tempconvert.asmx";

    public static String calculoRemoto(String celsius){
        SoapPrimitive resultado = null;
        try{
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("Celsius", celsius);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, envelope);

            resultado = (SoapPrimitive) envelope.getResponse();
            Log.i(TAG, resultado+ " fahrenheit");
        }catch (Exception e){
            Log.i(TAG, "ERRO" + e.getMessage());
        }
        return resultado.toString();
    }
}
