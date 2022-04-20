package com.example.newforyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Change_Password extends Users {
    TextView titleActinBar, textChangePass;
    EditText editTextOldPass, editTextNewPass, editTextConfirmNewPass;
    Button btnVerfyoldPass, btnResetewPass;
    ProgressBar progressBarChangePass;
    ImageView homeChangePass;
    FirebaseAuth authProfile;
    String userPwCur;


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
        setContentView(R.layout.change_password);

        titleActinBar = (TextView) findViewById(R.id.titleActinBar);
        titleActinBar.setText(" تغير كلمة المرور");
        textChangePass = (TextView) findViewById(R.id.textChangePass);
        editTextOldPass = (EditText) findViewById(R.id.editTextOldPass);
        editTextNewPass = (EditText) findViewById(R.id.editTextNewPass);
        editTextConfirmNewPass = (EditText) findViewById(R.id.editTextConfirmNewPass);
        btnVerfyoldPass = (Button) findViewById(R.id.btnVerfyoldPass);
        btnResetewPass = (Button) findViewById(R.id.btnResetewPass);
        progressBarChangePass = (ProgressBar) findViewById(R.id.progressBarChangePass);
        homeChangePass = (ImageView) findViewById(R.id.homeChangePass);

        // Display EditText For new Password , Confirm new Password and make change password button unclickable till user is authntecation
        editTextNewPass.setEnabled(false);
        editTextConfirmNewPass.setEnabled(false);
        btnResetewPass.setEnabled(false);
        btnResetewPass.setBackgroundResource(R.drawable.border_btn_disenable);

//        btnResetewPass.setBackgroundResource(R.color.gray);
        homeChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Main_category.class);
                startActivity(i);

            }
        });

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        if (firebaseUser.equals("")) {
            Toast.makeText(getApplicationContext(), "حدث خطأ ما ، معلومات المستخدم غير موجودة ", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), DeafMute_Edit_Profile.class);
            startActivity(i);
            finish();
        } else {
            reAuthenticateUser(firebaseUser);

        }


    }

    //ReAuthenticate User before changing password
    public void reAuthenticateUser(FirebaseUser firebaseUser) {
        btnVerfyoldPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPwCur = editTextOldPass.getText().toString();
                if (TextUtils.isEmpty(userPwCur)) {
                    Toast.makeText(Change_Password.this, "كلمة المرور مطلوبة", Toast.LENGTH_SHORT).show();
                    editTextOldPass.setError(" من فضلك أدخل كلمة المرور الحالية للتحقق");
                    editTextOldPass.requestFocus();
                } else {
                    progressBarChangePass.setVisibility(View.VISIBLE);

                  //ReAuthenticate User now
                    AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(),userPwCur);

                    firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressBarChangePass.setVisibility(View.GONE);

                                //Display editText For Current Password, Enable EditText for new password and Confirm new Password
                                editTextOldPass.setEnabled(false);
                                editTextNewPass.setEnabled(true);
                                editTextConfirmNewPass.setEnabled(true);

                                // Enable change pwd button , Display Authenticate button
                                btnVerfyoldPass.setEnabled(false);
                                btnResetewPass.setEnabled(true);

                                //Set TextView to show user is authenticate/verified
                                textChangePass.setText("لقد تمت عملية التحقق والمصادقة. " + "يمكنك تغيير كلمة المرور الآن");
                                Toast.makeText(Change_Password.this, " لقد تم التحقق من كلمةالمرور ", Toast.LENGTH_SHORT).show();

                                //Update color of change password button
                                btnResetewPass.setBackgroundTintList(ContextCompat.getColorStateList(Change_Password.this, R.color.purple));
                                btnResetewPass.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        changePassword(firebaseUser);

                                    }
                                });
                            }else {
                                try{
                                    throw task.getException();
                                }catch (Exception e){
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }
                            progressBarChangePass.setVisibility(View.GONE);
                        }

                    });

                }


            }
        });

    }

    public void changePassword(FirebaseUser firebaseUser) {
        String userPwdNew = editTextNewPass.getText().toString();
        String userPwdConfirmNew = editTextConfirmNewPass.getText().toString();
        if (TextUtils.isEmpty(userPwdNew)) {
            Toast.makeText(Change_Password.this, "كلمة المرور الجديدة مطلوبة", Toast.LENGTH_SHORT).show();
            editTextNewPass.setError("من فضلك أدخل كلمة المرور الجديدة");
            editTextNewPass.requestFocus();
        } else if (TextUtils.isEmpty(userPwdConfirmNew)) {
            Toast.makeText(Change_Password.this, "من فضلك قم بتأكيد كلمة المرور", Toast.LENGTH_SHORT).show();
            editTextConfirmNewPass.setError("من فضلك أعد إدخال كلمة المرور الجديدة");
            editTextConfirmNewPass.requestFocus();
        } else if (!userPwdNew.matches(userPwdConfirmNew)) {
            Toast.makeText(Change_Password.this, "كلمة المرور غير متطابقة", Toast.LENGTH_SHORT).show();
            editTextConfirmNewPass.setError("من فضلك أعد إدخال نفس كلمة المرور الجديدة");
            editTextConfirmNewPass.requestFocus();
        }else if (userPwCur.matches(userPwdNew)) {
            Toast.makeText(Change_Password.this, "كلمة المرور الجديدة لا يمكن أن تكون مطابقة لكلمة المرور القديمة", Toast.LENGTH_SHORT).show();
            editTextNewPass.setError("من فضلك أدخل كلمة مرور جديدة");
            editTextNewPass.requestFocus();
        }else {
//            String uid,userNameDeafMute , AgeDeafMute , bloodType,EmailDeafMute,passWordDeafMute;
//            ReadWriteUserDetails
////                    writeUserDetails = new ReadWriteUserDetails(uid, 0, userNameDeafMute, AgeDeafMute, bloodType, EmailDeafMute, passWordDeafMute);
//            FirebaseDatabase.getInstance().getReference("Registered User")
//                    .child(uid).child("password").setValue("new password");
            progressBarChangePass.setVisibility(View.VISIBLE);
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseUser.updatePassword(userPwdNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
//                        FirebaseDatabase.getInstance().getReference("Registered User")
//                                .child(uid).child("newPassword").setValue(userPwdNew);
//                        Users user = new Users();
//                        user.
//                                checkUserType ();
                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        firebaseDatabase.getReference().child("Registered User").child(uid).child("userTypeInt").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int userTypeInt = snapshot.getValue(Integer.class);
                                if (userTypeInt ==0) {
                                    FirebaseDatabase.getInstance().getReference("Registered User")
                                            .child(uid).child("passWordD").setValue(userPwdNew);
                                    Intent i = new Intent(getApplicationContext(), DeafMute_Edit_Profile.class);
                                    startActivity(i);
                                    finish(); // Close Login_PageActivity
                                } else if (userTypeInt ==1) {
                                    FirebaseDatabase.getInstance().getReference("Registered User")
                                            .child(uid).child("passWordL").setValue(userPwdNew);
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

                    }else {
                        try{
                            throw task.getException();
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                    progressBarChangePass.setVisibility(View.GONE);

                }
            });
        }
    }
}
