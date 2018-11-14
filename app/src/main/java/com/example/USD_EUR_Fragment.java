package com.example;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;


/**
 * A simple {@link Fragment} subclass.
 */
public class USD_EUR_Fragment extends Fragment {
    TextView textViewEUR;
    TextView textViewUSD;
    ImageView imageViewEUR;
    ImageView imageViewUSD;

    public USD_EUR_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_usd__eur, container, false);
        textViewEUR = view.findViewById(R.id.textViewEUR);
        textViewUSD = view.findViewById(R.id.textViewUSD);
        imageViewEUR = view.findViewById(R.id.imageViewEUR);
        imageViewUSD = view.findViewById(R.id.imageViewUSD);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ParseAsyncTask parseAsyncTask = new ParseAsyncTask();
        parseAsyncTask.execute();
    }

    private class ParseAsyncTask extends AsyncTask<Void, Void, String>{
        HttpsURLConnection urlConnection = null;
        String jsonResult = "";
        BufferedReader reader = null;
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://www.cbr-xml-daily.ru/daily_json.js");
                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null){
                    sb.append(line + "\n");
                }
                jsonResult = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonResult;
        }

        @Override
        protected void onPostExecute(String s) {
            double usd = 0;
            double eur = 0;
            String date;
            super.onPostExecute(s);
            try {
                JSONObject jsonRoot = new JSONObject(s);
                date = jsonRoot.getString("Date");
                JSONObject valutesJson = jsonRoot.getJSONObject("Valute");
                JSONObject usdJson = valutesJson.getJSONObject("USD");
                usd = usdJson.getDouble("Value");
                JSONObject eurJson = valutesJson.getJSONObject("EUR");
                eur = eurJson.getDouble("Value");
                if (eurJson.getDouble("Previous") > eur)
                    imageViewEUR.setImageDrawable(ContextCompat.getDrawable(getView().getContext(), R.drawable.money_down_24dp));
                else imageViewEUR.setImageDrawable(ContextCompat.getDrawable(getView().getContext(), R.drawable.money_up_24dp));
                if (usdJson.getDouble("Previous") > usd)
                    imageViewEUR.setImageDrawable(ContextCompat.getDrawable(getView().getContext(), R.drawable.money_down_24dp));
                else imageViewEUR.setImageDrawable(ContextCompat.getDrawable(getView().getContext(), R.drawable.money_up_24dp));
            } catch (Exception e) {
                e.printStackTrace();
            }
            textViewEUR.setText(String.format(Locale.ENGLISH, "EUR - %.2f", eur));
            textViewUSD.setText(String.format(Locale.ENGLISH, "USD - %.2f", usd));
        }
    }

}
