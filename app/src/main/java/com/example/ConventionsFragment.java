package com.example;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConventionsFragment extends Fragment {


    public ConventionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_policy_and_conv, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        TextView textView = getView().findViewById(R.id.textViewConventionsFull);
        textView.setText(Html.fromHtml(getResources().getString(R.string.conventions_full)));
    }
}
