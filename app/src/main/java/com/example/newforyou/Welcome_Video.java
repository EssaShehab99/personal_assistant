package com.example.newforyou;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class Welcome_Video extends AppCompatActivity {
    Button skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        skip = (Button)findViewById(R.id.skipVideo);

        VideoView videoView = findViewById(R.id.welcomeVideo);
        videoView.setVideoPath("android.resource://"+getPackageName()+"/"+ R.raw.welcome_video);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();


//        videoView.setMediaController(null);

//        videoView.setRight(12);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =  new Intent(getApplicationContext(), Main_category.class);
                startActivity(intent);

            }
        });
//        Intent intent =  new Intent(this,Main_category.class);
//        startActivity(intent);

        //        تنفيذ أمر معين عند فتح التطبيق لأول مرة
        // this code Show Welcome Video Activity Once when Application Running for the First Time
        // Check if application is opened for the first time
        // PREFERENCE The name of the file in which we will save the state of the application
//        SharedPreferences preferences = getSharedPreferences("PREFERENCE",MODE_PRIVATE);
//        String FirstTime = preferences.getString("FirstTimeInstall","");
//
//        if (FirstTime.equals("yes")){
//            // if application was opened for first time
//            Intent intent = new Intent(Welcome_Video.this,Main_category.class);
//            startActivity(intent);
//
//        }else{
//
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putString("FirstTimeInstall","yes");
//            editor.apply();
//        }

        // End code Show Welcome Video Activity Once when Application Running for the First Time
    }
}