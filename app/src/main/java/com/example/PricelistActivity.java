package com.example;


import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;


public class PricelistActivity extends AppCompatActivity implements CalculatorFragment.FabVisibleListener{
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricelist);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fab = findViewById(R.id.fabCall);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FragmentManager manager = getSupportFragmentManager();
        PricelistPagerAdapter adapter = new PricelistPagerAdapter(manager);
        tabLayout = findViewById(R.id.tabLayoutPricelist);
        viewPager = findViewById(R.id.viewPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        if(getIntent().getBooleanExtra("needCalculate", false))
        viewPager.setCurrentItem(1);
    }

    @Override
    public void setFabVisible(boolean visible) {
        if(visible)
            fab.setVisibility(View.VISIBLE);
        else
            fab.setVisibility(View.INVISIBLE);
    }

    public class PricelistPagerAdapter extends FragmentPagerAdapter {


        public PricelistPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0 :
                    return new PricelistFragment();
                case 1 :
                    return new CalculatorFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 : return "Категории";
                case 1 : return "Калькулятор";
            }
            return null;
        }

    }
}
