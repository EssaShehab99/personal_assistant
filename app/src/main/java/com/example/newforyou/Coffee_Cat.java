package com.example.newforyou;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Coffee_Cat extends Hospital_Cat {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Enabled previous page icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.recycleview_edittext);
        dataList = (RecyclerView) findViewById(R.id.dataList);
        setTextToConvert = (EditText)findViewById(R.id.setTextToConvert);
        btnSpeak = (ImageButton) findViewById(R.id.convertToVoice);
        favWordDB = (Button) findViewById(R.id.favorite_word);
        //  Main_category A = new Main_category();
        favWordDB.setOnClickListener(view -> addFavWord(setTextToConvert.getText().toString()));


//        Adapter_Inside_Cat A = new Adapter_Inside_Cat();
        setImgInfo();
        // extend method setImgAdapter from parent "Hospital_Cat"
        super.setImgAdapter();
       // super.convertTextToVoice();

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String editTextToConvert = setTextToConvert.getText().toString();


                if (editTextToConvert.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "لايوجد نص لتحويله", Toast.LENGTH_SHORT).show();
                } else{

                    checkAppToConvertTextToVoice();

                }


            }


        });

        btnSpeak.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                getSpeechInput(view);

                return true;
            }

        });



    }


    //@Override
    public void setImgInfo() {

        titles = new ArrayList<>();
        images = new ArrayList<>();


        titles.add("أنا");
        titles.add("أريد");
        titles.add("الدفع");
        titles.add("كريب");
        titles.add("بان كيك");
        titles.add("تشيز كيك");
        titles.add("مشروب بارد");
        titles.add("مشروب ساخن");
        titles.add("شاي");
        titles.add("كروسان");




        images.add(R.drawable.me);
        images.add(R.drawable.iwants);
        images.add(R.drawable.pay);
        images.add(R.drawable.crepe);
        images.add(R.drawable.pancakes);
        images.add(R.drawable.cake);
        images.add(R.drawable.could_coffee);
        images.add(R.drawable.hot_cup);
        images.add(R.drawable.tea);
        images.add(R.drawable.croissant);


    }

}
