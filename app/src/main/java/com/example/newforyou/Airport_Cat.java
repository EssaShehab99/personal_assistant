package com.example.newforyou;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Airport_Cat extends Hospital_Cat {
//    public static RecyclerView dataList;
//    public static List<String> titles;
//    public static List<Integer> images;
//    Adapter_Inside_Cat adapter;
//    EditText setTextToConvert;
//    TextToSpeech textToSpeech;
//    ImageButton btnSpeak;
//    public static Button favWordDB;
//    final int TTS_APP_VERSION = 210349294;


//    String s = "";
//
//    public Hospital_Cat(EditText setTextToConvert, void addFavWord) {
//        super();
//    }


//    public Hospital_Cat(Context applicationContext, ImageButton btnSpeak, Button favWordDB) {
//        //this.adapter = adapter;
////        this.setTextToConvert = setTextToConvert;
////        this.textToSpeech = textToSpeech;
//        this.btnSpeak = btnSpeak;
//        this.favWordDB = favWordDB;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycleview_edittext);
        //Log.d(TAG,"onCreate: started.");
        dataList = (RecyclerView) findViewById(R.id.dataList);
        setTextToConvert = (EditText) findViewById(R.id.setTextToConvert);
        btnSpeak = (ImageButton) findViewById(R.id.convertToVoice);
        favWordDB = (Button) findViewById(R.id.favorite_word);


        setImgInfo();
        setImgAdapter();

        // هذي الاون كليك الي لما يضغط على القلب الي جنب النص عشان يضيف جملة كاملة للمفضلة

        favWordDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFavWord();
            }

//            public void addFavWord() {
//
//                favWordDB.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
//
//                Toast.makeText(favWordDB.getContext(), "add", Toast.LENGTH_SHORT).show();
//
//            }
        });

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

//    public void convertTextToVoice() {
//    }

//

    public void setImgInfo() {

        titles = new ArrayList<>();
        images = new ArrayList<>();

        titles.add("أين");
        titles.add("متى");
        titles.add("طائرة");
        titles.add("تذكرة");
        titles.add("مقعدي");
        titles.add("بوابة");
        titles.add("بطاقة صعود");
        titles.add("موعد الإنطلاق");
        titles.add("موعد الهبوط");
        titles.add("صالة الإنتظار");


        images.add(R.drawable.where);
        images.add(R.drawable.whene);
        images.add(R.drawable.airplane);
        images.add(R.drawable.plane_ticket);
        images.add(R.drawable.seat);
        images.add(R.drawable.gate);
        images.add(R.drawable.boarding_pass);
        images.add(R.drawable.arrival);
        images.add(R.drawable.deoarture_time);
        images.add(R.drawable.lounge);

    }


    // method for calling Adapter "Adapter_Inside_Cat"

    public void setImgAdapter() {
        adapter = new Adapter_Inside_Cat(this, titles, images, setTextToConvert);

        LinearLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);

    }


//    // Start Menu Bar
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)  {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        //Add SearchView
//        MenuItem item = menu.findItem(R.id.search_bar);
//        SearchView searchView =(SearchView)item.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                adapter.getFilter().filter(s);
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }
//
//
//// Action Method Once you click on an item in the menu bar icon
//
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int item_id;
//        item_id = item.getItemId();
//
//        // get id for items
//        if (item_id == R.id.home) {
//            Intent i = new Intent(getApplicationContext(), Main_category.class);
//            startActivity(i);
//
//        }
//
//
//        if (item_id == R.id.login) {
//        }
//
//
//        if (item_id == R.id.learn_sign) {
//
//        }
//        if (item_id == R.id.favorite) {
//            Intent i = new Intent(getApplicationContext(), Main_category.class);
//            startActivity(i);
//
//        }
//
//        if (item_id == R.id.logoPrimary) {
//            Intent i = new Intent(getApplicationContext(), Main_category.class);
//            startActivity(i);
//
//        }
//
//        return true;
//    }

    // End Menu Bar

    // هذي الميثود الي لما يضغط على القلب الي جنب النص عشان يضيف جملة كاملة للمفضلة
    // الميثود حطيتها برا عشان يقدر يوصل لها من باقي الكلاسات الي وارثة منها وعرفت البوتن فوق ستاتيك عشان يشوفها

    public void addFavWord() {
        String editTextToConvert = setTextToConvert.getText().toString();


        if (editTextToConvert.isEmpty()) {
            Toast.makeText(favWordDB.getContext(), "Please Enter Text", Toast.LENGTH_SHORT).show();
        } else {

            favWordDB.setBackgroundResource(R.drawable.ic_baseline_favorite_24);

            Toast.makeText(favWordDB.getContext(), "Add To Favorite", Toast.LENGTH_SHORT).show();
        }

    }


    // End Menu Bar

}