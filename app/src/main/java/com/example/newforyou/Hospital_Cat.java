package com.example.newforyou;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.*;

public class Hospital_Cat extends AppCompatActivity {

    public static RecyclerView dataList;
    public static List<String> titles;
    public static List<Integer> images;
    Adapter_Inside_Cat adapter;
    EditText setTextToConvert;
    TextToSpeech textToSpeech;
    public static ImageButton btnSpeak;
    public static Button favWordDB;
    final int TTS_APP_VERSION = 210349294;
    FirebaseAuth authProfileLogin;
    static FirebaseAuth mAuth;

    int count = 2;

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
        authProfileLogin = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();

        setImgInfo();
        setImgAdapter();


         // For one time Alert Dialog "guideline" on Startup App

//        SharedPreferences prefs = getSharedPreferences (  "prefs",MODE_PRIVATE)
//        boolean firstStart = prefs.getBoolean (  "firstStart", true);
//        if (firstStart) {
//                showStartDialog() ;
//
//    }
//
//        SharedPreferences prefs = getSharedPreferences (  "prefs",MODE_PRIVATE);
//        SharedPreferences.Editor editor =  prefs.edit();
//        editor.putBoolean ("firstStart", false);
//        editor.apply();

        // هذي الاون كليك الي لما يضغط على القلب الي جنب النص عشان يضيف جملة كاملة للمفضلة

        favWordDB.setOnClickListener(view -> addFavWord());


        //convertTextToVoice();
        textToSpeech = new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if (i == TextToSpeech.SUCCESS) {
                            Locale loc = new Locale("ar");
                            /*  under API 20 */
                            textToSpeech.setLanguage(loc);
                            /* over API 21 */
                            String voiceName = loc.toLanguageTag();
                            Voice voice = new Voice(voiceName, loc, Voice.QUALITY_HIGH, Voice.LATENCY_HIGH, false, null);
                            textToSpeech.setVoice(voice);

                        }

                    }
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

        // Convert Voice To Text
        btnSpeak.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                count--;

//                getSpeechInput(view);
                // btnSpeak.setImageResource(R.drawable.ic_baseline_mic_none_24);
//                if (count == 1) {
                    btnSpeak.setImageResource(R.drawable.ic_baseline_mic_none_24);
                    getSpeechInput(view);

//
//                }else if(btnSpeak.setImageResource(R.drawable.micrphone))  {
//
//
//                }

                return true;
            }

        });

    }


    public void setImgInfo() {

        titles = new ArrayList<>();
        images = new ArrayList<>();

        titles.add("أستفسر");
        titles.add("أنا");
        titles.add("أين");
        titles.add("قسم");
        titles.add("دكتور العيون");
        titles.add("دكتور الباطنية");
        titles.add("دكتور العظام");
        titles.add("دكتور نفسي");
        titles.add("صيدلية");
        titles.add("الطوارئ");

        images.add(R.drawable.ask);
        images.add(R.drawable.me);
        images.add(R.drawable.where);
        images.add(R.drawable.secton);
        images.add(R.drawable.eye);
        images.add(R.drawable.stomch);
        images.add(R.drawable.bone);
        images.add(R.drawable.psychologist);
        images.add(R.drawable.pharmcy);
        images.add(R.drawable.emergency1);


        // editText.setText((CharSequence) titles);


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
        if (authProfileLogin.getCurrentUser() != null) {

                if (editTextToConvert.isEmpty()) {
                    Toast.makeText(favWordDB.getContext(), "Please Enter Text", Toast.LENGTH_SHORT).show();
                } else {

                    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Registered User").child(Objects.requireNonNull(mAuth.getUid())).child("Favoritos");

                    ref.child(UUID.randomUUID().toString()).setValue(editTextToConvert);
                    favWordDB.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                    Toast.makeText(favWordDB.getContext(), "تم إضافة العنصر الى المفضلة", Toast.LENGTH_SHORT).show();
                }

        }else {
            Toast.makeText(favWordDB.getContext(), "يجب عليك إنشاء حساب حتى تسطيع إضافة العنصر الى المفضلة ", Toast.LENGTH_SHORT).show();

        }




    }


    // End Menu Bar

    public void checkAppToConvertTextToVoice() {
        String name = "com.google.android.tts";
        if (available(name) < TTS_APP_VERSION) {
            Toast.makeText(getApplicationContext(), " لن تعمل جميع ممبزات هذاالتطبيق ، يرجى    تحديث تطبيق Speech Services  ", Toast.LENGTH_SHORT).show();
            //here will go to google play
            openGooglePlay(name);
        } else {
            // Get Edit text value
            String s = setTextToConvert.getText().toString();
            // Text Convert To Speech
            int speech = textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    // check with the package name
    // if app is available or not
    public int available(String name) {
        int virsion = 0;

        try {
            // check if available
            virsion = getPackageManager().getPackageInfo(name, 0).versionCode;

            System.out.println("virsion = " + virsion);

        } catch (PackageManager.NameNotFoundException e) {
            // if not available set
            // available as false
            return virsion;
        }
        return virsion;
    }


    public void openGooglePlay(String appPackageName) {

        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }


    }
    //Start Code Convert Voice To Text
    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "جهازك لا يدعم إدخال الكلام", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    setTextToConvert.setText(result.get(0));
                }
                break;
        }
    }//End Code Convert Voice To Text

}