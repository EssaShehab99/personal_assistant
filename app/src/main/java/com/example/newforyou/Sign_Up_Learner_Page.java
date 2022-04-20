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

public class Sign_Up_Learner_Page extends AppCompatActivity {

    public static final String TAG = "Sign_Up_Learner";
    AutoCompleteTextView nameLearner, emailLearner;
    EditText inputPasswordLearner, conferminputPasswordLearner;
    Button btnLoginLearner, btnregisterLearner;
    ProgressBar progressBarLearner;
    TextView titleActinBar;
    ImageView homeSignUpLearner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Custome ActionBar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title_layout);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple)));
        setContentView(R.layout.sign_up_learner_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        String titleName = "إنشاء حساب".toString();
//        titleName.setGravity(Gravity.CENTER_HORIZONTAL);
//        getSupportActionBar().setTitle("إنشاء حساب");
        Toast.makeText(Sign_Up_Learner_Page.this, "يمكنك الان إنشاء حساب", Toast.LENGTH_SHORT).show();

        nameLearner = (AutoCompleteTextView) findViewById(R.id.nameLearner);
        emailLearner = (AutoCompleteTextView) findViewById(R.id.emailLearner);
        inputPasswordLearner = (EditText) findViewById(R.id.inputPasswordLearner);
        conferminputPasswordLearner = (EditText) findViewById(R.id.conferminputPasswordLearner);
        btnLoginLearner = (Button) findViewById(R.id.btnLoginLearner);
        btnregisterLearner = (Button) findViewById(R.id.btnregisteLLearner);
        progressBarLearner = (ProgressBar) findViewById(R.id.progressBarLearner);
        titleActinBar = (TextView) findViewById(R.id.titleActinBar);
        titleActinBar.setText("إنشاء حساب");

        homeSignUpLearner = (ImageView) findViewById(R.id.homeSignUpLearner);
        homeSignUpLearner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Main_category.class);
                startActivity(intent);
            }
        });

        btnLoginLearner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_Up_Learner_Page.this, Login_Page.class);
                startActivity(intent);
            }
        });
        // button Register to get data from filed
        btnregisterLearner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // obtion the enterd data
                String userNameLearner = nameLearner.getText().toString();
                String EmailLearner = emailLearner.getText().toString();
                String passWordLearner = inputPasswordLearner.getText().toString();
                String textConfermPassWordLearner = conferminputPasswordLearner.getText().toString();

                if (TextUtils.isEmpty(userNameLearner)) {
                    Toast.makeText(Sign_Up_Learner_Page.this, "من فضلك أدخل اسمك", Toast.LENGTH_SHORT).show();
                    nameLearner.setError("الإسم مطلوب");
                    nameLearner.requestFocus();
                } else if (TextUtils.isEmpty(EmailLearner)) {
                    Toast.makeText(Sign_Up_Learner_Page.this, "من فضلك البريد الإلكتروني الخاص بك", Toast.LENGTH_SHORT).show();
                    emailLearner.setError("الإيميل مطلوب");
                    emailLearner.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(EmailLearner).matches()) {
                    Toast.makeText(Sign_Up_Learner_Page.this, "من فضلك أعد إدخال البريد الإلكتروني", Toast.LENGTH_SHORT).show();
                    emailLearner.setError("البريد الإلكتروني غير صحيح");
                    emailLearner.requestFocus();
                } else if (TextUtils.isEmpty(passWordLearner)) {
                    Toast.makeText(Sign_Up_Learner_Page.this, "من فضلك البريد الإلكتروني الخاص بك", Toast.LENGTH_SHORT).show();
                    inputPasswordLearner.setError("كلمة السر مطلوبة");
                    inputPasswordLearner.requestFocus();
                } else if (passWordLearner.length() < 6) {
                    Toast.makeText(Sign_Up_Learner_Page.this, "يجب عليك أن تدخل على الأقل 6 أرقام", Toast.LENGTH_SHORT).show();
                    inputPasswordLearner.setError("كلمة السر ضعيفة");
                    inputPasswordLearner.requestFocus();
                } else if (TextUtils.isEmpty(textConfermPassWordLearner)) {
                    Toast.makeText(Sign_Up_Learner_Page.this, "من فضلك تأكد من كلمة السر", Toast.LENGTH_SHORT).show();
                    conferminputPasswordLearner.setError(" تأكيد من كلمة السر مطلوب");
                    conferminputPasswordLearner.requestFocus();

                } else if (!(passWordLearner.matches(".*[a-z].*") | passWordLearner.matches(".*[A-Z].*"))) {
//                    Toast.makeText(Sign_Up_Deaf_Mute.this, "يجب عليك أن تحتوي كلمة السر على احرف وارقام", Toast.LENGTH_SHORT).show();
                    inputPasswordLearner.setError("يجب عليك أن تحتوي كلمة السر على احرف وارقام");
                    inputPasswordLearner.requestFocus();
                } else if (!passWordLearner.equals(textConfermPassWordLearner)) {
                    Toast.makeText(Sign_Up_Learner_Page.this, "من فضلك تأكد من تطابق كلمة السر", Toast.LENGTH_SHORT).show();
                    conferminputPasswordLearner.setError(" تأكيد من كلمة السر مطلوب");
                    conferminputPasswordLearner.requestFocus();
                    // Clear the entered passwords
                    inputPasswordLearner.clearComposingText();
                    conferminputPasswordLearner.clearComposingText();
                } else {
//                    String userType ="Learner";
                    progressBarLearner.setVisibility(View.VISIBLE);
                    registerUserLearner(userNameLearner, EmailLearner, passWordLearner);
                }


            }
        });
    }

    // Register User learner Using the Credential Given
    public void registerUserLearner(String userNameLearner, String EmailLearner, String passWordLearner) {

        FirebaseAuth autho = FirebaseAuth.getInstance();
        autho.createUserWithEmailAndPassword(EmailLearner, passWordLearner)
                .addOnCompleteListener(Sign_Up_Learner_Page.this
                        , new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Sign_Up_Learner_Page.this, "تم انشاء حسابك بنجاح", Toast.LENGTH_LONG).show();
//                                    Intent intent = new Intent(Sign_Up_Learner_Page.this, Hospital_Cat.class);
//                                    startActivity(intent);
                                    FirebaseUser firebaseuser = autho.getCurrentUser();

                                    // send Verification email
                                    firebaseuser.sendEmailVerification();

                                    // upddate Display name of user
                                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(userNameLearner).build();
                                    firebaseuser.updateProfile(profileChangeRequest);

                                    String uid = task.getResult().getUser().getUid();
                                    ReadWriteUserDetails
                                            writeUserDetails = new ReadWriteUserDetails(uid,1, userNameLearner, EmailLearner, passWordLearner,"Learner");
                                    // To read or write data from database , you need an instance f DatabaseReference
                                    // Extracting user Data into the Firebase Realtime Database
                                    DatabaseReference referenceProfileD = FirebaseDatabase.getInstance().getReference("Registered User");

                                    referenceProfileD.child(firebaseuser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            // send Verification email
                                            if (task.isSuccessful()) {

                                                firebaseuser.sendEmailVerification();
                                                Toast.makeText(Sign_Up_Learner_Page.this, "تم انشاء حسابك بنجاح", Toast.LENGTH_LONG).show();
////                                        open another page after register
                                                Intent intent = new Intent(Sign_Up_Learner_Page.this, Main_category.class);
                                                //To prevent user from returning back to register activity after registration
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                        | Intent.FLAG_ACTIVITY_NEW_TASK); // مثلا بال log out ما بصير اليوور يرجع ع الصفحه السابقه الي بكون مسجل حسابه فيها ، ف بنستخدم هالاوامر لحتى يفضي الستاك من اخر اكتيفيتي كان فيه
                                                startActivity(intent);

                                                finish();//to close register activity

                                            } else {
                                                Toast.makeText(Sign_Up_Learner_Page.this, "فشل انشاءالحساب ", Toast.LENGTH_LONG).show();

                                            }
                                            // Hide progressBar whether user creation is successfu or failed
//                                    progressBarDeafMute.setVisibility(View.GONE);


                                        }
                                    });

                                } else {
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthWeakPasswordException e) {
                                        inputPasswordLearner.setError("كلمة السر ضعيفة جدا, استخدم مجموعة من الحروف والأرقام");
                                        inputPasswordLearner.requestFocus();

                                    }// thrown when the user emai id and apssword do not match
                                    catch (FirebaseAuthInvalidCredentialsException e) {
                                        inputPasswordLearner.setError("الإيميل غير صالح أو لقد تم استخدامه من قبل");
                                        inputPasswordLearner.requestFocus();

                                    } catch (FirebaseAuthUserCollisionException e) {
//                                inputPasswordDeafMute.setError("هذا الإيميل مستخدم من قبل ,استخدم إيميل اخر");
                                        Toast.makeText(Sign_Up_Learner_Page.this, "هذا الإيميل مستخدم من قبل ,استخدم إيميل اخر", Toast.LENGTH_LONG).show();

                                    } catch (Exception e) {
                                        // Log.e method is used to log errors
                                        // we have two parameter , first tag to identify the source of a log message
                                        // so in this tag variable we are going to save the name of the activity
                                        // second parameter defines the error message
                                        Log.e(TAG, e.getMessage());
                                        Toast.makeText(Sign_Up_Learner_Page.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        // to stop progressBar when appear exceptin
                                        progressBarLearner.setVisibility(View.GONE);


                                    }
                                }

                            }
                        });
    }
}