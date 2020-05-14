package com.example.pratica23;

import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import static android.content.ContentValues.TAG;

public class Dados extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        String txtJson = getJSON();
        String dados = "";
        if (txtJson!=null){
            try{
                JSONObject jsonObj = new JSONObject(txtJson);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");
                dados=main.getString("temp")+"/"+main.getString("humidity")+
                        "/"+sys.getString("sunrise")+"/"+sys.getString("sunset");
            }catch (Exception e){
                Log.e(TAG, "Json parsing error: "+ e.getMessage());
            }
        }
        return dados;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    public String getJSON(){
        String response = null, u=null;
        u="https://api.openweathermap.org/data/2.5/weather?q=Coimbra,pt&appid=330137e73c28f4b3b647ac4e2bcbf716";
        try {
            URL url = new URL(u);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        }catch (Exception e){
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    private String convertStreamToString(InputStream is){
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try{
            while((line = reader.readLine()) != null){
                sb.append(line).append('\n');
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}
