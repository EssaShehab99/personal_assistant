package com.example.newforyou;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Who_Are_You_Page extends Hospital_Cat {
    public Button deafMute, signLanguageLearner;
    TextView loginText;
    ImageView homeWhoAreYou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple)));

        setContentView(R.layout.who_are_you_page);
        deafMute = (Button) findViewById(R.id.deafMute);
        signLanguageLearner = (Button) findViewById(R.id.signLanguageLearner);
        loginText = (TextView) findViewById(R.id.loginText);
        homeWhoAreYou = (ImageView) findViewById(R.id.homeWhoAreYou);
        homeWhoAreYou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Main_category.class);
                startActivity(intent);
            }
        });


        // select deafMutebutton from Who_Are_You Page
        deafMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                setContentView(R.layout.activity_sign_up_deaf_mute_page);
                Intent i = new Intent(getApplicationContext(), Sign_Up_Deaf_Mute.class);
                startActivity(i);

            }
        });


        // select signLanguageLearner from Who_Are_You Page

        signLanguageLearner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Sign_Up_Learner_Page.class);
                startActivity(intent);

            }
        });

        // select btnLoginLearner sign_up_learner Page

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Login_Page.class);
                startActivity(i);
            }
        });
    }
}