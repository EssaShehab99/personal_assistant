package com.example.newforyou;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Arrays;
import java.util.Objects;

public class Sign_Up_Deaf_Mute extends AppCompatActivity {

    public static final String TAG = "Sign_Up_Deaf_Mute";
    AutoCompleteTextView nameDeafMute, ageDeafMute, bloadType, emailDeafMute;
    EditText inputPasswordDeafMute, conferminputPasswordDeafMute;
    Button btnLoginDeafMute, btnregisterDeafMute;
    ProgressBar progressBarDeafMute;
    TextView titleActinBar;
    ImageView homeSignUpDeafMute;
    String[] specifyBlood = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};

//    public Sign_Up_Deaf_Mute(String uid, int i, String userNameDeafMute, String ageDeafMute, String bloodType, String emailDeafMute, String passWordDeafMute) {
//    }

//    String userType = "DeafMute";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Custome ActionBar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title_layout);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple)));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.sign_up_deaf_mute_page);
        Toast.makeText(getApplicationContext(), "يمكنك الان إنشاء حساب", Toast.LENGTH_SHORT).show();

        nameDeafMute = (AutoCompleteTextView) findViewById(R.id.nameDeafMute);
        ageDeafMute = (AutoCompleteTextView) findViewById(R.id.ageDeafMute);
        bloadType = (AutoCompleteTextView) findViewById(R.id.bloodType);
        emailDeafMute = (AutoCompleteTextView) findViewById(R.id.emailDeafMute);
        inputPasswordDeafMute = (EditText) findViewById(R.id.inputPasswordDeafMute);
        conferminputPasswordDeafMute = (EditText) findViewById(R.id.conferminputPasswordDeafMute);
        btnLoginDeafMute = (Button) findViewById(R.id.btnLoginDeafMute);
        btnregisterDeafMute = (Button) findViewById(R.id.btnregisterDeafMute);
        progressBarDeafMute = (ProgressBar) findViewById(R.id.progresBarDeafMute);
        titleActinBar = (TextView) findViewById(R.id.titleActinBar);
        titleActinBar.setText("إنشاء حساب");


        homeSignUpDeafMute = (ImageView) findViewById(R.id.homeSignUpDeafMute);
        homeSignUpDeafMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Main_category.class);
                startActivity(intent);
            }
        });

        btnLoginDeafMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_Up_Deaf_Mute.this, Login_Page.class);
                startActivity(intent);
            }
        });
        // button Register to get data from filed
        btnregisterDeafMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // obtion the enterd data
                String userNameDeafMute = nameDeafMute.getText().toString().trim();
                String AgeDeafMute = ageDeafMute.getText().toString().trim();
                String EmailDeafMute = emailDeafMute.getText().toString().trim();
                String bloodType = bloadType.getText().toString().trim().toUpperCase();
                String passWordDeafMute = inputPasswordDeafMute.getText().toString().trim();
                String textConfermPassWordDeafMute = conferminputPasswordDeafMute.getText().toString().trim();

                if (TextUtils.isEmpty(userNameDeafMute)) {
                    Toast.makeText(Sign_Up_Deaf_Mute.this, "من فضلك أدخل اسمك", Toast.LENGTH_SHORT).show();
                    nameDeafMute.setError("الإسم مطلوب");
                    nameDeafMute.requestFocus();
                } else if (TextUtils.isEmpty(EmailDeafMute)) {
                    Toast.makeText(Sign_Up_Deaf_Mute.this, "من فضلك البريد الإلكتروني الخاص بك", Toast.LENGTH_SHORT).show();
                    emailDeafMute.setError("الإيميل مطلوب");
                    emailDeafMute.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(EmailDeafMute).matches()) {
                    Toast.makeText(Sign_Up_Deaf_Mute.this, "من فضلك أعد إدخال البريد الإلكتروني", Toast.LENGTH_SHORT).show();
                    emailDeafMute.setError("البريد الإلكتروني غير صحيح");
                    emailDeafMute.requestFocus();
                } else if (TextUtils.isEmpty(bloodType)) {
                    bloadType.setError("من فضلك أدخل فصيلة دم صحيحة");
                    bloadType.requestFocus();
                } else if (TextUtils.isEmpty(AgeDeafMute)) {
                    ageDeafMute.setError("العمر غير صحيح");
                    ageDeafMute.requestFocus();
                }
//                else if (!(TextUtils.isEmpty(AgeDeafMute))) {
//                    Toast.makeText(Sign_Up_Deaf_Mute.this, "من فضلك أدخل عمرك", Toast.LENGTH_SHORT).show();
//                    ageDeafMute.setError("العمر غير صحيح");
//                    ageDeafMute.requestFocus();
//                }
//                else if (!(TextUtils.isEmpty(bloodType))) {
//                    if (!Arrays.asList(specifyBlood).contains(bloodType)) {
////                    Toast.makeText(Sign_Up_Deaf_Mute.this, "من فضلك أدخل فصيلة دم صحيحة", Toast.LENGTH_SHORT).show();
//                        bloadType.setError("من فضلك أدخل فصيلة دم صحيحة");
//                        bloadType.requestFocus();
//                    }
//                }

                else if (TextUtils.isEmpty(passWordDeafMute)) {
                    Toast.makeText(Sign_Up_Deaf_Mute.this, "من فضلك البريد الإلكتروني الخاص بك", Toast.LENGTH_SHORT).show();
                    inputPasswordDeafMute.setError("كلمة السر مطلوبة");
                    inputPasswordDeafMute.requestFocus();
                } else if (passWordDeafMute.length() < 6) {
                    Toast.makeText(Sign_Up_Deaf_Mute.this, "يجب عليك أن تدخل على الأقل 6 أرقام", Toast.LENGTH_SHORT).show();
                    inputPasswordDeafMute.setError("كلمة السر ضعيفة");
                    inputPasswordDeafMute.requestFocus();
                } else if (!(passWordDeafMute.matches(".*[a-z].*") | passWordDeafMute.matches(".*[A-Z].*"))) {
//                    Toast.makeText(Sign_Up_Deaf_Mute.this, "يجب عليك أن تحتوي كلمة السر على احرف وارقام", Toast.LENGTH_SHORT).show();
                    inputPasswordDeafMute.setError("يجب عليك أن تحتوي كلمة السر على احرف وارقام");
                    inputPasswordDeafMute.requestFocus();
                } else if (TextUtils.isEmpty(textConfermPassWordDeafMute)) {
                    Toast.makeText(Sign_Up_Deaf_Mute.this, "من فضلك تأكد من كلمة السر", Toast.LENGTH_SHORT).show();
                    conferminputPasswordDeafMute.setError(" تأكيد من كلمة السر مطلوب");
                    conferminputPasswordDeafMute.requestFocus();

                } else if (!passWordDeafMute.equals(textConfermPassWordDeafMute)) {
                    Toast.makeText(Sign_Up_Deaf_Mute.this, "من فضلك تأكد من تطابق كلمة السر", Toast.LENGTH_SHORT).show();
                    conferminputPasswordDeafMute.setError(" تأكيد من كلمة السر مطلوب");
                    conferminputPasswordDeafMute.requestFocus();
                    // Clear the entered passwords
                    inputPasswordDeafMute.clearComposingText();
                    conferminputPasswordDeafMute.clearComposingText();
                }
//                else if (!Arrays.asList(specifyBlood).contains(bloodType)) {
////                    Toast.makeText(Sign_Up_Deaf_Mute.this, "من فضلك أدخل فصيلة دم صحيحة", Toast.LENGTH_SHORT).show();
//                    bloadType.setError("من فضلك أدخل فصيلة دم صحيحة");
//                    bloadType.requestFocus();
//                }
                else {
                    progressBarDeafMute.setVisibility(View.VISIBLE);
                    registerUserDeafMute(userNameDeafMute, AgeDeafMute, bloodType, EmailDeafMute, passWordDeafMute);
                }
            }
        });
    }


    // Register User Deaf Mute Using the Credential Given
    public void registerUserDeafMute(String userNameDeafMute, String AgeDeafMute, String
            bloodType, String EmailDeafMute, String passWordDeafMute) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        // Create user profile
        auth.createUserWithEmailAndPassword(EmailDeafMute, passWordDeafMute)
                .addOnCompleteListener(Sign_Up_Deaf_Mute.this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseuser = auth.getCurrentUser();
                        String uid = Objects.requireNonNull(task.getResult().getUser()).getUid();

                        DatabaseReference referenceProfileD = FirebaseDatabase.getInstance().getReference("Registered User");
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                        // upddate Display name of user
                        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(userNameDeafMute).build();
                        firebaseuser.updateProfile(profileChangeRequest);
                        //once the user has been created we can save the data of the user i database
                        //enter user data into the firebase Reatime Database, just write ana read name user data
                        // parameter just that we want to save in the database
                        ReadWriteUserDetails
                                writeUserDetails = new ReadWriteUserDetails(uid, 0, userNameDeafMute, AgeDeafMute, bloodType, EmailDeafMute, passWordDeafMute, "Deaf_Mute");

                        // To read or write data from database , you need an instance f DatabaseReference
                        // Extracting user Data into the Firebase Realtime Database

                        firebaseDatabase.getReference().child("Registered User").child(uid).setValue(writeUserDetails);
                        referenceProfileD.child(firebaseuser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // send Verification email
                                if (task.isSuccessful()) {

                                    firebaseuser.sendEmailVerification();
                                    Toast.makeText(Sign_Up_Deaf_Mute.this, "تم انشاء حسابك بنجاح", Toast.LENGTH_LONG).show();
                                    //open another page after register
                                    Intent intent = new Intent(Sign_Up_Deaf_Mute.this, Main_category.class);
                                    //To prevent user from returning back to register activity after registration
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            | Intent.FLAG_ACTIVITY_NEW_TASK); // مثلا بال log out ما بصير اليوور يرجع ع الصفحه السابقه الي بكون مسجل حسابه فيها ، ف بنستخدم هالاوامر لحتى يفضي الستاك من اخر اكتيفيتي كان فيه
                                    startActivity(intent);
                                    finish();//to close register activity

                                } else {
                                    Toast.makeText(Sign_Up_Deaf_Mute.this, "User registered fails ", Toast.LENGTH_LONG).show();

                                }
                                // Hide progressBar whether user creation is successfu or failed
//                                    progressBarDeafMute.setVisibility(View.GONE);


                            }
                        });

                    } else {
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthWeakPasswordException e) {
                            inputPasswordDeafMute.setError("كلمة السر ضعيفة جدا, استخدم مجموعة من الحروف والأرقام");
                            inputPasswordDeafMute.requestFocus();

                        }// thrown when the user emai id and apssword do not match
                        catch (FirebaseAuthInvalidCredentialsException e) {
                            inputPasswordDeafMute.setError("الإيميل غير صالح أو لقد تم استخدامه من قبل");
                            inputPasswordDeafMute.requestFocus();
                            Toast.makeText(Sign_Up_Deaf_Mute.this, "الإيميل غير صالح أو لقد تم استخدامه من قبل", Toast.LENGTH_SHORT).show();

                        } catch (FirebaseAuthUserCollisionException e) {
//                                inputPasswordDeafMute.setError("هذا الإيميل مستخدم من قبل ,استخدم إيميل اخر");
                            inputPasswordDeafMute.requestFocus();
                            Toast.makeText(Sign_Up_Deaf_Mute.this, "هذا الإيميل مستخدم من قبل ,استخدم إيميل اخر", Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            // Log.e method is used to log errors
                            // we have two parameter , first tag to identify the source of a log message
                            // so in this tag variable we are going to save the name of the activity
                            // second parameter defines the error message
                            Log.e(TAG, e.getMessage());
                            Toast.makeText(Sign_Up_Deaf_Mute.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            // to stop progressBar when appear exceptin
                        }
                        progressBarDeafMute.setVisibility(View.GONE);
                    }


                });
    }


}