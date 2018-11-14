package com.example;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SellMetallActivity extends AppCompatActivity {
    private final int ADD_IMAGE_REQUEST_CODE = 1;
    private final int PERMISSION_REQUEST_CODE = 1;
    private final int SETTING_REQUEST_CODE = 2;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private StringBuilder sbForImageFolder = new StringBuilder();
    private String imageFolder = null;
    private EditText name, number, mail, weight;
    private ImageView buttonAddPhoto;
    private TextView textViewCatecory;
    private TextView textViewConventions;
    private TextView textViewPolicy;
    private Button buttonSend, buttonChoose;
    private RecyclerView recyclerViewPhoto;
    private final PhotoRecyclerAdapter photoRecyclerAdapter = new PhotoRecyclerAdapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_metall);

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

        name = findViewById(R.id.editTextName);
        number = findViewById(R.id.editTextNumber);
        mail = findViewById(R.id.editTextEmail);
        weight = findViewById(R.id.editTextTonnCount);
        textViewCatecory = findViewById(R.id.textViewCategory);
        buttonChoose = findViewById(R.id.buttonChoose);
        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseCategoryForSaleDialogFragment fragment = new ChooseCategoryForSaleDialogFragment();
                fragment.show(getSupportFragmentManager(), "chooseDialog");
            }
        });

        buttonAddPhoto = findViewById(R.id.imageViewAddPhoto);
        buttonAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(SellMetallActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, ADD_IMAGE_REQUEST_CODE);
                } else {
                    final String s = "Необходимо разрешение для доступа к фотографиям.";
                    Snackbar.make(v,s,5000).setAction("Разрешить", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestPermission();
                        }
                    }).show();

                }
            }
        });
        buttonSend = findViewById(R.id.buttonMail);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1, s2;
                if(!name.getText().toString().equals("")){
                    s1 = name.getText().toString();
                }else{
                    Toast.makeText(SellMetallActivity.this, "Введите имя", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (number.getText().length() == 11 || number.getText().length() == 12){
                    s2 = number.getText().toString();
                }else{
                    Toast.makeText(SellMetallActivity.this, "Некорректно указан номер телефона", Toast.LENGTH_SHORT).show();
                    return;
                }

                String s3 = mail.getText().toString();
                String s4 = ChooseCategoryForSaleDialogFragment.getMetallCategory();
                String s5 = weight.getText().toString();
                String messageBody = getMessageBody(s1,s2,s3,s4,s5);

                SendingAsync sendingAsync = new SendingAsync();
                String fileNamesAfterDelete = photoRecyclerAdapter.getFilesAfterDelete();
                sendingAsync.execute(messageBody, imageFolder, fileNamesAfterDelete);
                buttonSend.setEnabled(false);
                sbForImageFolder.delete(0,sbForImageFolder.length());
                Snackbar.make(v, "Заявка была успешно отпавлена", Snackbar.LENGTH_SHORT).show();
            }
        });
        textViewConventions = findViewById(R.id.textViewConventions);
        textViewConventions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellMetallActivity.this, NotificationService.class);
                startService(intent);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contaierSellMetall, new ConventionsFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();
            }
        });

        textViewPolicy = findViewById(R.id.textViewPolicy);
        textViewPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contaierSellMetall, new PolicyFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();
            }
        });

        recyclerViewPhoto = findViewById(R.id.recyclerViewPhoto);
        recyclerViewPhoto.setAdapter(photoRecyclerAdapter);
        recyclerViewPhoto.setLayoutManager(new LinearLayoutManager(this));



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ADD_IMAGE_REQUEST_CODE:
                if(resultCode == RESULT_OK){
                    final Uri uri = data.getData();
                    if (uri != null)
                    sbForImageFolder.append(getRealPathFromURI(this,uri) + " ");
                    Log.i("Uri", getRealPathFromURI(this,uri));
                    String[] s = getRealPathFromURI(this, uri).split("/");
                    photoRecyclerAdapter.setFolder(s[s.length  - 1]);
                    photoRecyclerAdapter.notifyDataSetChanged();
                    recyclerViewPhoto.smoothScrollToPosition(photoRecyclerAdapter.getItemCount() - 1);
                    imageFolder = sbForImageFolder.toString();
                    break;
                }
            case SETTING_REQUEST_CODE :
                if(ContextCompat.checkSelfPermission(SellMetallActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, ADD_IMAGE_REQUEST_CODE);
                }

        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public String getMessageBody(String s1, String s2, String s3, String s4, String s5){
        String body = String.format("Имя клиента: %1s\n" +
                        "Номер телефона: %2s\n" +
                        "Адрес эл. почты: %3s\n" +
                        "Виды металла: %4s\n" +
                        "Количество металла: %5s т\n",
                            s1,s2,s3,s4,s5);
        return body;
    }


    private class PhotoRecyclerAdapter extends RecyclerView.Adapter<PhotoRecyclerAdapter.ViewHolder>{
        private List<String> folderList = new ArrayList<>();
        public PhotoRecyclerAdapter() {
            }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_view_for_sell, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

            TextView textViewPhoto = holder.container.findViewById(R.id.textViewPhoto);
            if(folderList.get(position).charAt(0) != '(' && folderList.get(position).charAt(2) != ')') {
                folderList.set(position,"(" + String.valueOf(position + 1) + ") " + folderList.get(position));
            }
            textViewPhoto.setText(folderList.get(position));
            ImageView imageViewClearPhoto = holder.container.findViewById(R.id.imageViewClearPhoto);
            imageViewClearPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentPosition = position;
                    folderList.remove(currentPosition);
                    photoRecyclerAdapter.notifyItemRemoved(currentPosition);
                    photoRecyclerAdapter.notifyItemRangeChanged(0,photoRecyclerAdapter.getItemCount());
                }
            });
        }

        @Override
        public int getItemCount() {
            return folderList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            FrameLayout container;
            public ViewHolder(View itemView) {
                super(itemView);
                this.container = (FrameLayout) itemView;
            }
        }

        private void setFolder(String folder){
            this.folderList.add(folder);
        }

        private String getFilesAfterDelete(){
            if (!folderList.isEmpty()){
            for (String x :folderList)
                folderList.set(folderList.indexOf(x), x.substring(4));
            return folderList.toString().substring(1, folderList.toString().length() - 1);
            } else  return null;
        }
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }



    public static class ChooseCategoryForSaleDialogFragment extends DialogFragment{
        private StringBuilder sb = new StringBuilder();
        private static String categories;
        private List<CheckBox> checkBoxList = new ArrayList<>();
        private Button buttonOK;
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_metalls_category_for_sell, container, false);
            CheckBox checkBoxA3 = view.findViewById(R.id.cb_a_3);
            checkBoxList.add(checkBoxA3);
            CheckBox checkBoxA3gd = view.findViewById(R.id.cb_a_3gd);
            checkBoxList.add(checkBoxA3gd);
            CheckBox checkBoxA32 = view.findViewById(R.id.cb_a_32);
            checkBoxList.add(checkBoxA32);
            CheckBox checkBoxA5 = view.findViewById(R.id.cb_a_5);
            checkBoxList.add(checkBoxA5);
            CheckBox checkBoxA5m = view.findViewById(R.id.cb_a_5m);
            checkBoxList.add(checkBoxA5m);
            CheckBox checkBoxA12 = view.findViewById(R.id.cb_a_12);
            checkBoxList.add(checkBoxA12);
            CheckBox checkBoxA121 = view.findViewById(R.id.cb_a_121);
            checkBoxList.add(checkBoxA121);
            CheckBox checkBoxA16 = view.findViewById(R.id.cb_a_16);
            checkBoxList.add(checkBoxA16);
            CheckBox checkBoxA17 = view.findViewById(R.id.cb_a_17);
            checkBoxList.add(checkBoxA17);
            CheckBox checkBoxA19 = view.findViewById(R.id.cb_a_19);
            checkBoxList.add(checkBoxA19);
            CheckBox checkBoxA20 = view.findViewById(R.id.cb_a_20);
            checkBoxList.add(checkBoxA20);
            buttonOK = view.findViewById(R.id.buttonCategoryForSellOK);
            buttonOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SellMetallActivity sellMetallActivity = (SellMetallActivity) getActivity();
                    String s;
                    for (CheckBox cb : checkBoxList){
                        if(cb.isChecked()){
                            String[] sArray = cb.getText().toString().split(" ");
                            sb.append(sArray[sArray.length - 1] + " ");
                        }
                    }
                    if(sb.toString().equals("")){
                        dismiss();
                        return;
                    }
                    if (sb.toString().split(" ").length <= 3){
                        sb.deleteCharAt(sb.length() - 1);
                        s = sb.toString().replace(" ", ", ");
                        if(getActivity() != null){
                            sellMetallActivity.textViewCatecory.setText(s);
                        }
                    } else {
                        String[] sArray = sb.toString().split(" ");
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < 3; i++){
                            stringBuilder.append(sArray[i] + ", ");
                        }
                        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                        stringBuilder.append("...");
                        if(getActivity() != null){
                            sellMetallActivity.textViewCatecory.setText(stringBuilder.toString());
                        }
                    }
                    categories = sb.toString();
                    sb = new StringBuilder();
                    ChooseCategoryForSaleDialogFragment.this.dismiss();
                }
            });
            return view;
        }

        public static String getMetallCategory(){
            if(categories != null)
                return categories;
            else
                return "";
        }
    }
}
