package com.example.newforyou;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Learrn_Sign_Language extends AppCompatActivity {

    private static final int TYPE_CAROUSEL = 1;
    RecyclerView dataList;
    List<String> titles;
    List<Integer> images;
    Adapter_Learrn_Sign_Language adapter;
//    public ScaleGestureDetector scaleGestureDetector;
//    public float mScaleFactor = 1.0f;



    //EditText editText ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Enabled previous page icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.recyclerview_item);
        dataList = (RecyclerView) findViewById(R.id.dataList);
//        dataList.getRecycledViewPool().setMaxRecycledViews(TYPE_CAROUSEL, 0);

//        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

//        setTextToConvert = (EditText)findViewById(R.id.setTextToConvert);
//        btnSpeak = (ImageButton) findViewById(R.id.convertToVoice);
//        favWordDB = (Button) findViewById(R.id.favorite_word);
        //  Main_category A = new Main_category();


//        Adapter_Inside_Cat A = new Adapter_Inside_Cat();
        setImgInfo();
        // extend method setImgAdapter from parent "Hospital_Cat"
        setImgAdapter();

    }



    //@Override
    public void setImgInfo() {

        titles = new ArrayList<>();
        images = new ArrayList<>();

//
        titles.add("الحروف العربية");
        titles.add("الحروف الإنجليزية");
        titles.add("الأرقام ");
        titles.add("كلمات");
        titles.add("كلمات");
        titles.add("قناة يوتيوب لتعلم لغة الإشارة");
        titles.add("تعلم بعض المصلطحات بلغة الإشارة !");
        titles.add("موقع لتعلم لغة الإشارة");
        titles.add("كتاب قاموس تعلم لغة الإشارة");




//
//
//
//
        images.add(R.drawable.character);
        images.add(R.drawable.character_eng);
        images.add(R.drawable.number);
        images.add(R.drawable.words);
        images.add(R.drawable.wd);
        images.add(R.drawable.ytube);
        images.add(R.drawable.terms);
        images.add(R.drawable.cource);
        images.add(R.drawable.dictionary_learner);







    }

//    @Override
//    public boolean onTouchEvent(MotionEvent motionEvent) {
//        scaleGestureDetector.onTouchEvent(motionEvent);
//        return true;
//    }
//    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
//        @Override
//        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
//            mScaleFactor *= scaleGestureDetector.getScaleFactor();
//            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
//            dataList.setScaleX(mScaleFactor);
//            dataList.setScaleY(mScaleFactor);
//            return true;
//        }
//    }


    public void setImgAdapter() {
        adapter = new Adapter_Learrn_Sign_Language(this, titles, images);
        LinearLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);

    }


}
