package com.example;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PolicyFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_policy_and_conv, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        TextView textView = getView().findViewById(R.id.textViewConventionsFull);
        textView.setText(Html.fromHtml(getResources().getString(R.string.policy_full)));
    }
}
