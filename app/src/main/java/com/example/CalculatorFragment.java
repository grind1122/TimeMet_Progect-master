package com.example;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalculatorFragment extends Fragment {
    private TextView textViewForMetall, textViewForCount, textViewForRegions, textViewForSubmerge, textViewResult;
//    private Spinner spinnerMetall;
    private Button buttonChooseCategory;
    private static TextView textViewMetallCategory;
    private Spinner spinnerRegions;
    private EditText editTextCount;
    private CheckBox checkBoxSubmerge;
    private Button buttonResult, buttonReset;
    private FabVisibleListener fabVisibleListener;


    public CalculatorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fabVisibleListener = (FabVisibleListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_calculator, container, false);

        textViewForMetall = view.findViewById(R.id.textViewForMetall);
        textViewForCount = view.findViewById(R.id.textViewForCount);
        textViewForRegions = view.findViewById(R.id.textViewForRegion);
        textViewForSubmerge = view.findViewById(R.id.textViewForSubmerge);
        textViewResult = view.findViewById(R.id.textViewResult);
//        spinnerMetall = view.findViewById(R.id.spinnerMetalls);
        textViewMetallCategory = view.findViewById(R.id.textViewMetallCategory);
        spinnerRegions = view.findViewById(R.id.spinnerRegions);
        editTextCount = view.findViewById(R.id.editTextCount);
        checkBoxSubmerge = view.findViewById(R.id.checkBoxSubmerge);
        buttonResult = view.findViewById(R.id.buttonResult);
        buttonReset = view.findViewById(R.id.buttonReset);
        buttonChooseCategory = view.findViewById(R.id.buttonChooseCategory);
        buttonChooseCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseCategoryDialogFragment chooseCategoryDialogFragment = new ChooseCategoryDialogFragment();
                chooseCategoryDialogFragment.show(getFragmentManager(), "categoryDialog");
            }
        });
        buttonResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result;
                int perTonn = 0;
                int sumTonns;
                int delivery = 0;
                int submerge = 0;
                MySQLiteHelper helper = new MySQLiteHelper(view.getContext());
                SQLiteDatabase db = helper.getReadableDatabase();
                Cursor cursor = db.query("PRICE_LIST",new String[]{"NAME","PRICE"},null,null,null,null,null);
                if(!editTextCount.getText().toString().equals("") && !textViewMetallCategory.getText().toString().equals("Вид металла")){
//
                    if(cursor.moveToFirst()){
                        do{
                            if(cursor.getString(0).contains(textViewMetallCategory.getText().toString())){
                                perTonn = cursor.getInt(1);
                                textViewForMetall.setText(String.format(getString(R.string.pattern_mettall_cost_per_ton), perTonn));
                            }
                            cursor.moveToNext();
                        }while (perTonn == 0);
                    }
                    cursor.close();
                    db.close();

                    double count = Double.parseDouble(editTextCount.getText().toString());
                    sumTonns = (int)(perTonn * count);
                    textViewForCount.setText(String.format(getString(R.string.pattern_mettall_cost), sumTonns));
                    switch ((int) spinnerRegions.getSelectedItemId()){
                        case  0 :
                            if(count > 5){
                                delivery = 10000;
                                textViewForRegions.setText(String.format(getString(R.string.pattern_region_cost), delivery));
                            } else {
                                delivery = 8000;
                                textViewForRegions.setText(String.format(getString(R.string.pattern_region_cost), delivery));
                            }
                            break;
                        case 1:
                            delivery = 13000;
                            textViewForRegions.setText(String.format(getString(R.string.pattern_region_cost), delivery));
                            break;
                    }
                    if (checkBoxSubmerge.isChecked()){
                        submerge = (int)(count * 500);
                        textViewForSubmerge.setText(String.format(getString(R.string.pattern_submerge_cost), submerge));
                    } else{
                        textViewForSubmerge.setText("");
                    }
                    result = (sumTonns - delivery - submerge);
                    textViewResult.setText(String.format(getString(R.string.pattern_result),result));
                }
                else {
                    Toast.makeText(view.getContext(), "Укажите вид металла и кол-во", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextCount.setText("");
                checkBoxSubmerge.setChecked(false);
                textViewForMetall.setText("");
                textViewForCount.setText("");
                textViewForRegions.setText("");
                textViewForSubmerge.setText("");
                textViewResult.setText("");

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fabVisibleListener.setFabVisible(false);
    }

    interface FabVisibleListener{
       void setFabVisible(boolean visible);
    }

    public static class ChooseCategoryDialogFragment extends DialogFragment{
        private RadioGroup radioGroup;
        private Button buttonOK;
        private RadioButton radioButton;
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_metalls_category, container, false);
            radioGroup = view.findViewById(R.id.radioGroup);
            radioGroup.check(0);
            buttonOK = view.findViewById(R.id.buttonCategoryOK);
            buttonOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    radioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                    if (radioButton != null){
                    String categoryName = radioButton.getText().toString();
                    String[] s = categoryName.split(" ");
                    String onlyCategory = s[s.length - 1];
                    textViewMetallCategory.setText(onlyCategory);
                    textViewMetallCategory.setTextColor(getResources().getColor(android.R.color.black));
                    }
                    ChooseCategoryDialogFragment.this.dismiss();
                }
            });
            return view;
        }
    }
}
