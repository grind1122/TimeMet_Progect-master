package com.example;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class FirebaseConnector {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("timemet-cloud");
    private HttpsURLConnection connection;
    private Context context;

    public FirebaseConnector(Context context){
        this.context = context;
    }


    public String getJSONData(){
        try {
            URL url = new URL("https://timemet-cloud.firebaseio.com/metals.json");
            connection = (HttpsURLConnection) url.openConnection();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                Log.i("Connecting", "Connection error");
            }else {
                Log.i("Connecting", "Connection response is ok");
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0){
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            return new String(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Connecting", "Connecting failed");

            return null;
        } finally {
            connection.disconnect();
        }
    }

    public void parseAndPush(String s){
        MySQLiteHelper helper = new MySQLiteHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        if (s != null){
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length() - 1; i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String description = jsonObject.getString("description");
                    int price = jsonObject.getInt("price");
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("DESCRIPTION", description);
                    contentValues.put("PRICE", price);
                    db.update("PRICE_LIST",contentValues,"NAME = ?", new String[]{name});
                }
                db.close();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
