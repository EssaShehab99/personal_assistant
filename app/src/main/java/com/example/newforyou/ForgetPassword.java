package com.example.newforyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class ForgetPassword extends AppCompatActivity {

    AutoCompleteTextView emailResetPass;
    Button btnResetPass;
    ProgressBar progressBarResset;
    TextView titleActinBar;
    ImageView homeResetPass;
    public final static String TAG = "ForgetPassword";
    FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Custome ActionBar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title_layout);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setContentView(R.layout.sign_up_deaf_mute_page);
        Toast.makeText(getApplicationContext(), "نسيان  كلمة المرور", Toast.LENGTH_SHORT).show();

        setContentView(R.layout.forget_password);
        emailResetPass =(AutoCompleteTextView) findViewById(R.id.emailResetPass);
        progressBarResset = (ProgressBar) findViewById(R.id.progressBarResset);
        btnResetPass = (Button) findViewById(R.id.btnResetPass);
        titleActinBar = (TextView) findViewById(R.id.titleActinBar);
        titleActinBar.setText(" نسيان كلمة المرور");


        homeResetPass = (ImageView) findViewById(R.id.homeResetPass);
        homeResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Main_category.class);
                startActivity(intent);
            }
        });

        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailResetPass.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(getApplicationContext(), "من فضلك أدخل البريد الإلكتروني للحساب الخاص بك", Toast.LENGTH_SHORT).show();
                    emailResetPass.setError("الإيميل مطلوب");
                    emailResetPass.requestFocus();
                }else if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "من فضلك أدخل إيميل صالح", Toast.LENGTH_SHORT).show();
                    emailResetPass.setError("الإيميل غير صالح");
                    emailResetPass.requestFocus();
                }else {
                    progressBarResset.setVisibility(view.VISIBLE);
                    resetPassword(email);
                }

            }

            public void resetPassword(String email) {
                authProfile = FirebaseAuth.getInstance();
                authProfile.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "يرجى التحقق من صندوق الوارد الخاص بك للحصول على رابط إعادة تعيين كلمة المرور", Toast.LENGTH_SHORT).show();
//                            checkUserType();

//                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//                            FirebaseDatabase.getInstance().getReference("Registered User")
//                                    .child(uid).child("newPassword").setValue(userPwdNew);
//
//                            Intent i = new Intent(getApplicationContext(), Main_category.class);
//
//                            //clear stack to prevent user coming back to ForgetPassword Activity
//                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(i);
//                            finish();//Close UserProfileActivity
                        }else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e){
                                emailResetPass.setError("المستخدم غير موجود أو لم يعدالإيميل صالحًا. الرجاء التسجيل مرة أخرى.");
                            }catch (Exception e){
                                Log.e(TAG , e.getMessage());
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }

                        progressBarResset.setVisibility(View.GONE);

                    }
                });

            }
        });


    }

//    public void checkUserType () {
//        String uid = authProfile.getInstance().getCurrentUser().getUid();
//
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        firebaseDatabase.getReference().child("Registered User").child(uid).child("userTypeInt").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                int userTypeInt = snapshot.getValue(Integer.class);
//                if (userTypeInt ==0) {
//                    Intent i = new Intent(getApplicationContext(), Main_category.class);
//                    startActivity(i);
//                    finish(); // Close Login_PageActivity
//                } else if (userTypeInt ==1) {
//                    Intent intent = new Intent(getApplicationContext(), Learrn_Sign_Language.class);
//                    startActivity(intent);
//                    finish(); // Close Login_PageActivity
//                }
////
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//        });
//
//    }

}