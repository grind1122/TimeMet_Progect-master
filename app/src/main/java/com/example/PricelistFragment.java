package com.example;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PricelistFragment extends Fragment {
    RecyclerView recyclerView;


    public PricelistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pricelist, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        List<String> names = new ArrayList<>();
        List<String> description = new ArrayList<>();
        List<String> cost = new ArrayList<>();
        List<Integer> imageResources = new ArrayList<>();
        MySQLiteHelper helper = new MySQLiteHelper(getView().getContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("PRICE_LIST", new String[]{"NAME", "DESCRIPTION", "PRICE", "IMAGE_RESOURCES_ID"},null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                names.add(cursor.getString(0));
                description.add(cursor.getString(1));
                cost.add(String.format("От %d руб/тонна...", cursor.getInt(2)));
                imageResources.add(cursor.getInt(3));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

//        List<Metalls> metalls = Metalls.getMetallsForCardView();
//        List<String> names = new ArrayList<>();
//        for (Metalls x : metalls)
//            names.add(x.getName());
//        List<String> cost = new ArrayList<>();
//        for (Metalls x : metalls)
//            cost.add(x.getCost());
//        List<String> description = new ArrayList<>();
//        for(Metalls x : metalls)
//            description.add(x.getDescription());
//        List<Integer> imageResources = new ArrayList<>();
//        for (Metalls x : metalls)
//            imageResources.add(x.getImageResources());

        if(getView() != null)
        recyclerView = (RecyclerView) getView();
        PricelistAdapter pricelistAdapter = new PricelistAdapter(names, cost, description, imageResources, getFragmentManager());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(pricelistAdapter);


    }
}
