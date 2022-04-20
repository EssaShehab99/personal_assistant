package com.example.newforyou;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Restraint_Cat extends Hospital_Cat {

 //RecyclerView dataList;
  //List<String> titles;
 // List<Integer> images;
//   Adapter_Inside_Cat adapter;
  //EditText editText ;

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


//@Override
    public void setImgInfo() {

        titles = new ArrayList<>();
        images = new ArrayList<>();


        titles.add("أنا");
        titles.add("أريد");
        titles.add("قائمة الطعام");
        titles.add("برجر دجاج");
        titles.add("برجر لحم");
        titles.add("سَلطةٌ");
        titles.add("سوشي");
        titles.add("بيتزا");
        titles.add("كَبسة");
        titles.add("باستا ");
        titles.add("مشروب غازي");
        titles.add("عصير");


        images.add(R.drawable.me);
        images.add(R.drawable.iwants);
        images.add(R.drawable.menu1);
        images.add(R.drawable.burger);
        images.add(R.drawable.burger_m);
        images.add(R.drawable.salad);
        images.add(R.drawable.sushi);
        images.add(R.drawable.pizza);
        images.add(R.drawable.kabsa);
        images.add(R.drawable.pasta);
        images.add(R.drawable.softdrinks);
        images.add(R.drawable.juice);

    }

//@Override
//    public void setImgAdapter() {
//       adapter = new Adapter_Inside_Cat(this, titles, images, editText);
//        LinearLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
//        dataList.setLayoutManager(gridLayoutManager);
//        dataList.setAdapter(adapter);
//
//    }


}
