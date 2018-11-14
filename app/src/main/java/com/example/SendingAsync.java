package com.example;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.FileNotFoundException;

public class SendingAsync extends AsyncTask<String, Void, Boolean> {
    @Override
    protected Boolean doInBackground(String... strings) {
        if(strings[1] == null){
        try {
            GMailSender sender = new GMailSender("homealextest@gmail.com", "grind123!");
            sender.sendMail("Новый клиент!", strings[0], "homealextest@gmail.ru", "grind1122@mail.ru");
            Log.i("SendMsg", "Sended");
        } catch (Exception e) {
            Log.e("SendMsg", "Exception send msg");
            return false;
        }
        return true;
        } else {
            try {
                    GMailSender sender = new GMailSender("homealextest@gmail.com", "grind123!");
                    sender.sendMultiMail("Новый клиент!", strings[0], strings[1], strings[2], "homealextest@gmail.ru", "grind1122@mail.ru");
                    Log.i("SendMsg", "Sended");
                } catch (Exception e) {
                Log.e("SendMsg", "Exception send msg");
                return false;
            }
            return true;
        }
    }
}
