package com.example.newforyou;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SuperMarket_Cat extends Hospital_Cat {
//    EditText setTextToConvert;
//    TextToSpeech textToSpeech;
//    ImageButton btnSpeak;


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


//        Adapter_Inside_Cat A = new Adapter_Inside_Cat();
        setImgInfo();
        // extend method setImgAdapter from parent "Hospital_Cat"
        super.setImgAdapter();
        //super.convertTextToVoice();



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

    public void setImgInfo() {

        titles = new ArrayList<>();
        images = new ArrayList<>();

        titles.add("أنا");
        titles.add("أريد");
        titles.add("أين");
        titles.add("قسم");
        titles.add("المشروبات");
        titles.add("فواكه");
        titles.add("المخبوزات");
        titles.add("خضروات");
        titles.add("اللحوم");
        titles.add("المنظفات");

        images.add(R.drawable.me);
        images.add(R.drawable.iwants);
        images.add(R.drawable.where);
        images.add(R.drawable.secton);
        images.add(R.drawable.milk);
        images.add(R.drawable.fruit);
        images.add(R.drawable.bread);
        images.add(R.drawable.vegetables);
        images.add(R.drawable.proteins);
        images.add(R.drawable.cleaning);

    }

}
