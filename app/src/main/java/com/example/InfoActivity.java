package com.example;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;



public class InfoActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private View call;
    private View mail;
    private View web;
    private View geolocation;
    private ViewPager viewPagerLogo;
    private ImageView vkLogo;
    private ImageView okLogo;
    private ImageView fbLogo;
    private ImageView arrowRight;
    private ImageView arrowLeft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_info);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab = findViewById(R.id.fabCall);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+7-812-389-2392"));
                startActivity(intent);
            }
        });

        call = findViewById(R.id.callLayout);
        mail = findViewById(R.id.mailLayout);
        web = findViewById(R.id.webLayout);
        geolocation = findViewById(R.id.geoLayout);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+7-812-389-2392"));
                startActivity(intent);
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:grind1122@mail.ru"));
                startActivity(intent);
            }
        });
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://timemet.ru"));
                startActivity(intent);
            }
        });
        geolocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=Тайммет+-+прием+металлолома"));
                startActivity(intent);
            }
        });

        viewPagerLogo = findViewById(R.id.viewPagerLogo);
        ViewPagerLogoAdapter adapter = new ViewPagerLogoAdapter(getSupportFragmentManager());
        viewPagerLogo.setAdapter(adapter);

        vkLogo = findViewById(R.id.vk_logo);
        okLogo = findViewById(R.id.ok_logo);
        fbLogo = findViewById(R.id.fb_logo);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()){
                    case R.id.ok_logo :
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ok.ru/group/52356620025984"));
                        startActivity(intent);
                        break;
                    case R.id.vk_logo :
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/timemet"));
                        startActivity(intent);
                        break;
                    case R.id.fb_logo :
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/timemet/"));
                        startActivity(intent);
                        break;
                }
            }
        };
        vkLogo.setOnClickListener(listener);
        okLogo.setOnClickListener(listener);
        fbLogo.setOnClickListener(listener);

        arrowLeft = findViewById(R.id.arrowLeft);
        arrowRight = findViewById(R.id.arrowRight);
        arrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idCurrent = viewPagerLogo.getCurrentItem();
                if (idCurrent < 7){
                    viewPagerLogo.setCurrentItem(idCurrent + 1, true);
                }
            }
        });
        arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idCurrent = viewPagerLogo.getCurrentItem();
                if (idCurrent > 0){
                    viewPagerLogo.setCurrentItem(idCurrent - 1, true);
                }
            }
        });
        viewPagerLogo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    arrowLeft.setVisibility(View.INVISIBLE);
                }
                if(position > 0 && position < 7){
                    arrowRight.setVisibility(View.VISIBLE);
                    arrowLeft.setVisibility(View.VISIBLE);
                }
                if (position == 7){
                    arrowRight.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




    }

    private class ViewPagerLogoAdapter extends FragmentPagerAdapter{

        public ViewPagerLogoAdapter(FragmentManager fm) {
            super(fm);
            }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0 :
                    return new Logo1PartnersFragment();
                case 1 :
                    return new Logo2PartnersFragment();
                case 2 :
                    return new Logo3PartnersFragment();
                case 3 :
                    return new Logo4PartnersFragment();
                case 4 :
                    return new Logo5PartnersFragment();
                case 5 :
                    return new Logo6PartnersFragment();
                case 6 :
                    return new Logo7PartnersFragment();
                case 7 :
                    return new Logo8PartnersFragment();

                default: return null;
            }
        }

        @Override
        public int getCount() {
            return 8;
        }

    }

    public static class Logo1PartnersFragment extends Fragment {

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_logo_1, container,false);
            ImageView imageViewLogo = view.findViewById(R.id.logoPartner);
            imageViewLogo.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.metrostroi));
            imageViewLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://metrostroy-spb.ru"));
                    startActivity(intent);
                }
            });
            return view;
        }
    }

    public static class Logo2PartnersFragment extends Fragment {

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_logo_1, container,false);
            ImageView imageViewLogo = view.findViewById(R.id.logoPartner);
            imageViewLogo.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.prf));
            imageViewLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.pfrf.ru"));
                    startActivity(intent);
                }
            });
            return view;
        }
    }

    public static class Logo3PartnersFragment extends Fragment {

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_logo_1, container,false);
            ImageView imageViewLogo = view.findViewById(R.id.logoPartner);
            imageViewLogo.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.ilim));
            imageViewLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ilimgroup.ru"));
                    startActivity(intent);
                }
            });
            return view;
        }
    }
    public static class Logo4PartnersFragment extends Fragment {

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_logo_1, container,false);
            ImageView imageViewLogo = view.findViewById(R.id.logoPartner);
            imageViewLogo.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.mtk));
            imageViewLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.murmantara.ru"));
                    startActivity(intent);
                }
            });
            return view;
        }
    }

    public static class Logo5PartnersFragment extends Fragment {

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_logo_1, container,false);
            ImageView imageViewLogo = view.findViewById(R.id.logoPartner);
            imageViewLogo.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.specstal));
            imageViewLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://omz-specialsteel.com"));
                    startActivity(intent);
                }
            });
            return view;
        }
    }

    public static class Logo6PartnersFragment extends Fragment {

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_logo_1, container,false);
            ImageView imageViewLogo = view.findViewById(R.id.logoPartner);
            imageViewLogo.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.vtz));
            imageViewLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tmk-group.ru"));
                    startActivity(intent);
                }
            });
            return view;
        }
    }

    public static class Logo7PartnersFragment extends Fragment {

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_logo_1, container,false);
            ImageView imageViewLogo = view.findViewById(R.id.logoPartner);
            imageViewLogo.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.aomz));
            imageViewLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.aomz.ru"));
                    startActivity(intent);
                }
            });
            return view;
        }
    }
    public static class Logo8PartnersFragment extends Fragment {

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_logo_1, container,false);
            ImageView imageViewLogo = view.findViewById(R.id.logoPartner);
            imageViewLogo.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.severstal));
            imageViewLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.severstal.com"));
                    startActivity(intent);
                }
            });
            return view;
        }
    }

}
