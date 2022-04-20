package com.example.newforyou;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

public class Users extends AppCompatActivity {

    public FirebaseAuth authProfile;
    public FirebaseUser firebaseUser;
    DatabaseReference firebaseDatabase;
    public StorageReference storageReference;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("PicturesProfile");


    }

    //To Verification Email
    public void showAlertDialog() {
//        Custom  Dialog For Email Verified
        final Dialog loginPage = new Dialog(Users.this);
        loginPage.setContentView(R.layout.dialog_login_page);
        loginPage.show();
        //open Email App
        Button positiveButton = (Button) loginPage.findViewById(R.id.positiveButton);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            ///open Email Apps if User clicks action ok
            @Override
            public void onClick(View view) {
                //so action main is considered an entry point for the application and usually it combines with category launcher in an intent filter to indicate
                // an activity tha t should appear in the home screen launcher or anything else that consider itself tobe a launcher
                Intent intent = new Intent(Intent.ACTION_MAIN);
                ///intent for open Email Apps to user email verify
                //in this case if you have many email apps on your phone and you run this app on your phone then the android is going to display the list of those email apps to choose
                // from but since in our emulator hav only one email app so it won't give any option and instead it will directly open the email any ways
                intent.addCategory(Intent.CATEGORY_APP_EMAIL); // عشان تفتح تطبيقات الايميل الي عندي ليتحقق اليوزر من ايميله

                //so that the email app is opend in new window and not within our app
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // عشان يفتح تطبيق الايميل بصفحه ثانيه مو بصفحة التطبيق
                startActivity(intent);
            }
        });
    }


 //DeleteUserProfile
    public void showDialogDeleteUserProfile() {
//        Custom  Dialog For Email Verified
        final Dialog DialogDeleteUserProfile = new Dialog(Users.this);
        DialogDeleteUserProfile.setContentView(R.layout.dialog_delert_user);
        DialogDeleteUserProfile.show();
        //open Email App
        Button Yes = (Button) DialogDeleteUserProfile.findViewById(R.id.yesDelete);
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                progressBarEditL.setVisibility(View.VISIBLE);
                firebaseUser.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
//                                    progressBarEditL.setVisibility(View.GONE);
                                    Toast.makeText(Users.this, "تم حذف حسابك بنجاح", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), Main_category.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);

                                    firebaseDatabase = FirebaseDatabase.getInstance().getReference();
                                    firebaseDatabase.child("Registered User").child(firebaseUser.getUid()).removeValue();
//                                    .child(storageReference.getPath())
//                                    Log.d(TAG, "User account deleted.");
//                                    deleteImageProfile(view);
//                                    String urI = "ggs://newforyou-882fa.appspot.com/PicturesProfile";
//                                    StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(urI);
//                                    reference.delete();
                                } else {
                                    Toast.makeText(Users.this, "حدث خطأ ما ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
        Button No = (Button) DialogDeleteUserProfile.findViewById(R.id.noDelete);
        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogDeleteUserProfile.dismiss();

            }
        });

    }
    public void deleteImageProfile(View view){
//        String urI = "ggs://newforyou-882fa.appspot.com/PicturesProfile";
//        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(urI);

        storageReference.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "حذف الصورة", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), " الصورة رفضت تنحذف ليه؟ اندري", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void checkUserType () {
        String uid = authProfile.getInstance().getCurrentUser().getUid();

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
                    startActivity(intent);
                    finish(); // Close Login_PageActivity
                }
//
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }


}
