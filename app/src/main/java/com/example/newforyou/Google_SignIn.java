package com.example.newforyou;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newforyou.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Google_SignIn extends AppCompatActivity {
    public GoogleSignInClient googleSignInClient;
    public final static int RC_SIGN_IN = 100;
    public FirebaseAuth firebaseAuth;
    //    FirebaseAuth authProfileLogin;
    public static final String TAG = "GOOGLE_SIGN_IN_TAG";
    public ActivityMainBinding binding;
//    String uid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        setContentView(R.layout.login_page);
//        onclick();

        // Configure Google Sign In
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        // this inside oncick

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        // Firebase google authentication sign in
////        FirebaseUser user = firebaseAuth.getCurrentUser();
//
////        if (user != null){
////            startActivity(new Intent(Google_SignIn.this,DeafMute_Edit_Profile.class));
////
////
////        }
//
//    }


    // Start Firebase google authentication sign in

//
//    public void onclick() {
//        Log.d(TAG, "onClick: begin Google SignIn");
//        Intent intent = googleSignInClient.getSignInIntent();
//        startActivityForResult(intent, RC_SIGN_IN);
//    }

//    public void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
 public void checkUser(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            Log.d(TAG, "checkUser: Already logged in");
            startActivity(new Intent(getApplicationContext(), Edit_Profile_GoogleSignIn.class));
            finish();

        }
 }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Log.d(TAG, "onActivityResult:Google SignIn intent result");
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = accountTask.getResult(ApiException.class);
//                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogleAccount(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e);
                Log.d(TAG, "onActivityResult" + e.getMessage());

//                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogleAccount: being firebase auth with google account");

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //Login Success
                        Log.d(TAG, "onSuccess: Logged In");
                        // Get Logged in user
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

//                        get user info
                        String uid = firebaseUser.getUid();
                        String email = firebaseUser.getEmail();

                        Log.d(TAG, "onSuccess: Email"+email);
                        Log.d(TAG, "onSuccess: UID"+uid);

                        // check if user is new or exiting
                        if(authResult.getAdditionalUserInfo().isNewUser()){
                            //User is new - Account created
                            Log.d(TAG, "onSuccess: Account Created...\n"+email);
                            Toast.makeText(getApplicationContext(), "Account Created...\n"+email, Toast.LENGTH_SHORT).show();
                        }else {
                            Log.d(TAG, "onSuccess: Existing user...\n"+email);
                            Toast.makeText(getApplicationContext(), "Existing user...\n"+email, Toast.LENGTH_SHORT).show();

                        }
                        // Start Profile Activity
                        Intent intent = new Intent(getApplicationContext(), Edit_Profile_GoogleSignIn.class);
                        startActivity(intent);
                        finish();


                    }
                })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure:Loggin failed "+e.getMessage());




            }
        });

//                            uid = task.getResult().getUser().getUid();
//                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//                            firebaseDatabase.getReference().child("Registered User").child(uid).child("userTypeInt").addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    if (authProfileLogin.getCurrentUser() != null) {
//                                        int userTypeInt = snapshot.getValue(Integer.class);
//                                        Toast.makeText(Google_SignIn.this, "مرحبا", Toast.LENGTH_SHORT).show();
//                                        if (userTypeInt == 0) {
//                                            startActivity(new Intent(Google_SignIn.this, Main_category.class));
//                                        } else if (userTypeInt == 1) {
//                                            startActivity(new Intent(Google_SignIn.this, SuperMarket_Cat.class));
//                                        }
//                                    } else {
//                                        Toast.makeText(Google_SignIn.this, "تستطيع تسجيل الدخول الان", Toast.LENGTH_SHORT).show();
//
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//
//                            });


//                            updateUI(user);

//                        else {
//                            // If sign in fails, display a message to the user.
//                            Toast.makeText(getApplicationContext(), "عذرا الصادقة فشلت", Toast.LENGTH_SHORT).show();
////                            Log.w(TAG, "signInWithCredential:failure", task.getException());
////                            updateUI(null);
//                        }


        // End Firebase google authentication sign in

    }
}