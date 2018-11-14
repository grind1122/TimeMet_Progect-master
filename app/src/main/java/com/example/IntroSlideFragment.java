package com.example;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class IntroSlideFragment extends Fragment {
    private static final String ARG_LAYOUT_RES_ID = "layoutResId";
    private int layoutResId;

    public static IntroSlideFragment newInstance(int layoutId){
        IntroSlideFragment introSlideFragment = new IntroSlideFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_LAYOUT_RES_ID, layoutId);
        introSlideFragment.setArguments(bundle);
        return introSlideFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID)){
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layoutResId, container,false);
    }
}
