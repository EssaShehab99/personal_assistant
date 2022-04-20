package com.example.newforyou;


import android.app.ApplicationExitInfo;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.google.firebase.database.core.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main_category extends AppCompatActivity {

//    ImageView fullImage;
    RecyclerView dataList;
    List<String> catName;
    List<Integer> catImage;
    Adapter_Cat adapter;
    Button favDB;
    FirebaseAuth authProfileLogin;
    String uid;
    String textEmail, textpasswprd;
    String userNameDeafMute, AgeDeafMute, bloodType, EmailDeafMute, passWordDeafMute, userNameLearner, EmailLearner, passWordLearner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.logo);
        //3String s = new String (String.valueOf(R.drawable.logo));
        setContentView(R.layout.recyclerview_item);


        dataList = (RecyclerView) findViewById(R.id.dataList);
        //dataList.toString();
        favDB = (Button) findViewById(R.id.favBtn);
        authProfileLogin = FirebaseAuth.getInstance();
//        fullImage =(ImageView) findViewById(R.id.fullImage);

        setCatInfo();
        setAdapter();


//        favDB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), Hospital_Cat.class);
//                startActivity(i);
//
//            }
//        });
    }


    public void setCatInfo() {
//match array with recyclerview to display data in UI
        catName = new ArrayList<>();
        catImage = new ArrayList<>();


        catName.add("مستشفى");
        catName.add("مطعم");
        catName.add("مقهى");
        catName.add("مطار");
        catName.add("    سوبر ماركت");


        catImage.add(R.drawable.hospital);
        catImage.add(R.drawable.restaurant);
        catImage.add(R.drawable.coffee);
        catImage.add(R.drawable.airplane);
        catImage.add(R.drawable.supermarket);


    }

    public void setAdapter() {

        // create obj from Adapter_Cat class , return titles and images
        //call adapter
        adapter = new Adapter_Cat(this, catName, catImage);
        /*(adapter) this adapter is that control in display items in RecycleView */
        dataList.setAdapter(adapter);
        LinearLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);

    }


    //عشان اذا ضغط على زر ال جوع من الهوم بالغلط
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        final Dialog mainCat = new Dialog(Main_category.this);
        mainCat.setContentView(R.layout.dialog_main_cat);
        mainCat.show();

        Button Stay = (Button) mainCat.findViewById(R.id.stay);
        Stay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainCat.dismiss();// to stay in the application

            }
        });
    }

    //    Button ext = (Button) findViewById(R.id.ext);
    public void ext(View v) {
        finishAffinity();// to exit from application
    }

    public void rat(View view) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.android.chrome"));
        startActivity(intent);
    }


// End


    // Action Method Once you click on an item in the menu bar icon
    // Start Menu Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (authProfileLogin.getCurrentUser() != null) {
            getMenuInflater().inflate(R.menu.menu_inside, menu);

        } else {
            getMenuInflater().inflate(R.menu.menu, menu);
        }

        //Add SearchView
        MenuItem item = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


//    When the user selects an item from the options menu, the system calls your activity' onOptionsItemSelected()method.

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id;
        item_id = item.getItemId();

        // get id for items
        if (item_id == R.id.home) {
            Intent i = new Intent(getApplicationContext(), Main_category.class);
            startActivity(i);

        }


        if (item_id == R.id.login) {
            Intent i = new Intent(getApplicationContext(), Who_Are_You_Page.class);
            startActivity(i);
        }


        if (item_id == R.id.learn_sign) {
            Intent i = new Intent(getApplicationContext(), Learrn_Sign_Language.class);
            startActivity(i);

        }
        if (item_id == R.id.favorite) {
            Intent i = new Intent(getApplicationContext(), FavoriteActivity.class);
            startActivity(i);

        }

        if (item_id == R.id.logoPrimary) {
            Intent i = new Intent(getApplicationContext(), Main_category.class);
            startActivity(i);

        }

        if (item_id == R.id.shareApp) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT,"شارك التطبيق مع اصدقائك :) \n \n https://play.google.com/store/apps/details?id=" +getPackageName()+"\n\n فريق تطوير تطبيق لأجلكم يتمنى لكم تجربة ممتعة\uD83D\uDE0D");
            startActivity(Intent.createChooser(sendIntent,"ShareVia"));








//            ApplicationInfo api = getApplicationContext().getApplicationInfo();
//            String apkPath = api.sourceDir;
//            Intent intent = new Intent(Intent.ACTION_SEND);
//            intent.setType("application/vnd.android.package-archive");
//            intent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(new File(apkPath)));
//            startActivity(Intent.createChooser(intent,"ShareVia"));


//
//            Uri imageUri;
//            Intent intent;
//
//            imageUri = Uri.parse("https://play.google.com/store" +getPackageName());
//
//            intent = new Intent(Intent.ACTION_SEND);
//            //text
//            intent.putExtra(Intent.EXTRA_TEXT, "Hello");
//            //image
//            intent.putExtra(Intent.EXTRA_STREAM, imageUri);
//            //type of things
//            intent.setType("*/*");
//            //sending
//            startActivity(intent);


//            ImageView fullImage;
//
//            fullImage =(ImageView) findViewById(R.id.fullImage);
//            BitmapDrawable drawable= (BitmapDrawable) fullImage.getDrawable();
//            Bitmap bitmap = drawable.getBitmap();
//            String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver () , bitmap,"tite",null);
//            Uri uri = Uri.parse (bitmapPath) ;
//
//            Intent intent = new Intent (Intent.ACTION_SEND);
//            intent.setType("image/png");
//            intent.putExtra (Intent.EXTRA_STREAM, uri);
//            intent.putExtra(Intent .EXTRA_TEXT, "Playstore Link: https://play.google.com/store"+getPackageName());
//            startActivity (Intent.createChooser(intent, "share"));

        }

        if (item_id == R.id.editD) {

//            editProfileUserTypeInt();
            //                        Users user = new Users();
//                        user.
            editProfileUserTypeInt ();
        }

        if (item_id == R.id.logout) {
            authProfileLogin.signOut();
            Toast.makeText(getApplicationContext(), "لقد تم تسجيل الخروج", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), Main_category.class);
            //clear stack to prevent user coming back to UserProfileActivity onpressing back button after logging out
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish(); // Close UserProfileActivity

        }


        return true;


        // End Menu Bar




    }
    public void editProfileUserTypeInt(){

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("Registered User").child(uid).child("userTypeInt").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int userTypeInt = snapshot.getValue(Integer.class);
                    if (userTypeInt ==0) {
                        Intent i = new Intent(getApplicationContext(), DeafMute_Edit_Profile.class);
                        startActivity(i);
                        finish(); // Close Login_PageActivity
                    } else if (userTypeInt ==1) {
                        Intent intent = new Intent(getApplicationContext(), Learner_Edit_Profile.class);
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish(); // Close Login_PageActivity
                    }
//                }
//                else {
//                    Toast.makeText(Main_category.this, "تستطيع تسجيل الدخول الان", Toast.LENGTH_SHORT).show();
//
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        }
}
