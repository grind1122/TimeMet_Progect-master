package com.example;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PricelistAdapter extends RecyclerView.Adapter<PricelistAdapter.ViewHolder> {
    private List<List> data;
    private List<String> name;
    private List<String> cost;
    private List<String> description;
    private List<Integer> imageResources;
    private FragmentManager fm;

    public PricelistAdapter(List<String> name, List<String> cost,List<String>description, List<Integer> imageResources, FragmentManager fm) {
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.imageResources = imageResources;
        this.fm = fm;
        data = new ArrayList<>();
        this.data.add(name);
        this.data.add(cost);
        this.data.add(description);
        this.data.add(imageResources);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        public ViewHolder(CardView view) {
            super(view);
            cardView = view;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder((CardView)LayoutInflater.from(parent.getContext()).inflate(R.layout.metall_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.imageView);
        imageView.setImageDrawable(cardView.getResources().getDrawable(imageResources.get(position)));
        final TextView name = cardView.findViewById(R.id.nameTextView);
        name.setText(this.name.get(position));
        TextView description = cardView.findViewById(R.id.descriptionTextView);
        description.setText(this.cost.get(position));
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoMetalDialogFragment dialogFragment = new InfoMetalDialogFragment();
                dialogFragment.setData(data);
                dialogFragment.setPosition(position);
                dialogFragment.show(fm, "dialogInfo");
                }
            }
        );
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

}
