package com.example;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;


public class MainActivity extends AppCompatActivity {
    private FrameLayout container;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FloatingActionButton fab;
    private Button buttonSell, buttonCalculate, buttonPriceList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UpdateTask updateTask = new UpdateTask();
        updateTask.execute();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fabCall);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+7-812-389-2392"));
                startActivity(intent);
            }
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean isFirstStart = preferences.getBoolean("FIRST_START", true);
                if (isFirstStart){
                    Intent intent = new Intent(MainActivity.this, IntroApp.class);
                    startActivity(intent);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("FIRST_START", false);
                    editor.apply();
                }
            }
        });
        thread.start();

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.colorPrimaryTMDark));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawerlayout, R.string.open_drawerlayout);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigationView);
        NavigationView.OnNavigationItemSelectedListener listner = new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.nav_menu_item_main:
                        if (drawerLayout.isDrawerOpen(Gravity.START))
                            drawerLayout.closeDrawer(Gravity.START);
                        break;
                    case R.id.nav_menu_pricelist:
                        intent = new Intent(MainActivity.this, PricelistActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_menu_item_sell_metall:
                        intent = new Intent(MainActivity.this, SellMetallActivity.class);
                        startActivity(intent);
                        break;
//                    case R.id.nav_menu_item_stock:
//                        intent = new Intent(MainActivity.this, StockActivity.class);
//                        startActivity(intent);
//                        break;
                    case R.id.nav_menu_item_info:
                        intent = new Intent(MainActivity.this, InfoActivity.class);
                        startActivity(intent);
                        break;

                }
                return true;
            }
        };
        navigationView.setNavigationItemSelectedListener(listner);

        container = findViewById(R.id.containerValutes);
        getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.containerValutes, new USD_EUR_Fragment(), null)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .commit();

        buttonSell = findViewById(R.id.buttonSell);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonPriceList = findViewById(R.id.buttonPricelist);
        View.OnClickListener buttonListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()){
                    case R.id.buttonSell :
                        intent = new Intent(MainActivity.this, SellMetallActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.buttonPricelist :
                        intent = new Intent(MainActivity.this, PricelistActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.buttonCalculate :
                        intent = new Intent(MainActivity.this, PricelistActivity.class);
                        intent.putExtra("needCalculate", true);
                        startActivity(intent);
                        break;
                }
            }
        };
        buttonSell.setOnClickListener(buttonListner);
        buttonPriceList.setOnClickListener(buttonListner);
        buttonCalculate.setOnClickListener(buttonListner);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(navigationView.getMenu());
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.START))
            drawerLayout.closeDrawer(Gravity.START);
        else super.onBackPressed();
    }

    private void updateDbFromFirebase(Context context){
        FirebaseConnector firebaseConnector = new FirebaseConnector(context);
        firebaseConnector.parseAndPush(firebaseConnector.getJSONData());
    }

    public static boolean isOnline(Context context){
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        return false;
    }

    private class UpdateTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            if(isOnline(getBaseContext())){
                updateDbFromFirebase(getBaseContext());
            }
            return null;
        }
    }


}
