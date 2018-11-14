package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class InfoMetalDialogFragment extends DialogFragment {
    private List<List> data;
    private int position;
    Button sellButton;

    public void setData(List<List> data) {
        this.data = data;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public InfoMetalDialogFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pricelist_info, container, false);
        ImageView imageViewInfo = view.findViewById(R.id.imageViewInfo);
        imageViewInfo.setImageDrawable(ContextCompat.getDrawable(view.getContext(), (int)(data.get(3).get(position))));
        TextView textViewName = view.findViewById(R.id.textViewName);
        textViewName.setText((String)(data.get(0).get(position)));
        TextView textViewDesc = view.findViewById(R.id.textViewDesc);
        textViewDesc.setText((String)(data.get(2).get(position)));
        sellButton = view.findViewById(R.id.buttonSellMetall);
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SellMetallActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
