package com.example.newforyou;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

//import com.example.newforyou.databinding.ActivityMainBinding;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.example.newforyou.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class DeafMute_Edit_Profile extends Users {

    public static final int PICK_IMAGE_REQUEST = 1;
    public EditText nameD, emailD, ageD, bloodtypeD;
    public Button btnSaveD, btnDeleteD;
    public TextView resetpasswordD, pagedecleration;
    public ProgressBar progressBarDM;
    public ImageView homeDeafMuteEdit;
    CircleImageView uploadImageD;
    public FirebaseAuth authProfile;
    public String nameDM, ageDM, bloodtypeDM, emailDM;
    public FirebaseUser firebaseUser;
    public Uri uriImage;
//    public StorageReference storageReference;
//    DatabaseReference firebaseDatabase;


    String UID;


    String[] specifyBlood = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deaf_mute_edit_profile);



        nameD = (EditText) findViewById(R.id.nameD);
        ageD = (EditText) findViewById(R.id.ageD);
        bloodtypeD = (EditText) findViewById(R.id.bloodtypeD);
        btnSaveD = (Button) findViewById(R.id.btnSaveD);
        btnDeleteD = (Button) findViewById(R.id.btnDeleteD);
        emailD = (EditText) findViewById(R.id.emailD);
        emailD.setEnabled(false);
        resetpasswordD = (TextView) findViewById(R.id.resetpasswordD);
        pagedecleration = (TextView) findViewById(R.id.pagedecleration);
        progressBarDM = (ProgressBar) findViewById(R.id.progressBarDM);
        uploadImageD = (CircleImageView ) findViewById(R.id.uploadImageD);


        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("PicturesProfile");

//        Uri uri = firebaseUser.getPhotoUrl();
//        Picasso.with(DeafMute_Edit_Profile.this).load(uri).into(uploadImageD);

        uploadImageD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChoser();

            }
        });

        homeDeafMuteEdit = (ImageView) findViewById(R.id.homeDeafMuteEdit);
        homeDeafMuteEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Main_category.class);
                startActivity(intent);
            }
        });


        if (firebaseUser == null) {
            Toast.makeText(DeafMute_Edit_Profile.this, "حدث خطأ ما", Toast.LENGTH_SHORT).show();

        } else {
            checkIfEmaiVerified(firebaseUser);
            progressBarDM.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }

        btnSaveD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile(firebaseUser);
            }
        });

        btnDeleteD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDeleteUserProfile();


            }
        });

        resetpasswordD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Change_Password.class);
                startActivity(i);
            }
        });

    }

    public void UploadPic() {
        if (uriImage != null) {
            //Save the image with uid of the currently logged user
            StorageReference fileReference = storageReference.child(authProfile.getCurrentUser().getUid() + "."
                    + getFileExtention(uriImage));

            //Upload Image to Storage
            fileReference.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloadUri = uri;
                            firebaseUser = authProfile.getCurrentUser();

                            // display the image to user after uploaded
                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(downloadUri).build();
                            firebaseUser.updateProfile(profileUpdate);

                        }
                    });
                    progressBarDM.setVisibility(View.GONE);
                    Toast.makeText(DeafMute_Edit_Profile.this, "تم رفع صورتك بنجاح", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(DeafMute_Edit_Profile.this, DeafMute_Edit_Profile.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    public String getFileExtention(Uri uri) {
//        grantUriPermission(null, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        uri = MediaStore.Audio.Media.getContentUri("EXTERNAL_CONTENT_URI”);
//                uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;

        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
//        grantUriPermission(null, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

    }

    public void openFileChoser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriImage = data.getData();
            uploadImageD.setImageURI(uriImage);
            UploadPic();


        }
    }

    // User coming to DeafMute_Edit_ptofile after successful registration
    public void checkIfEmaiVerified(FirebaseUser firebaseUser) {
        if (!firebaseUser.isEmailVerified()) {
            showAlertDialog();

        }
    }

    public void showUserProfile(FirebaseUser firebaseUser) {
        String userId = firebaseUser.getUid();

        DatabaseReference referenceProfileD = FirebaseDatabase.getInstance().getReference("Registered User");
        referenceProfileD.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readUserDetails != null) {
////                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                    FirebaseDatabase.getInstance().getReference("Registered User")
//                            .child(userId).child("passWordDeafMute");

                    nameDM = firebaseUser.getDisplayName().toUpperCase();
                    ageDM = readUserDetails.ageD;
                    bloodtypeDM = readUserDetails.bloodTypeD;
                    emailDM = readUserDetails.emailD;

                    pagedecleration.setText(nameDM.toUpperCase() + " مرحباً يا ");

                    nameD.setText(nameDM);
                    ageD.setText(ageDM);
                    bloodtypeD.setText(bloodtypeDM);
                    emailD.setText(emailDM);

//                    Change_Password change_password = new Change_Password();
//                    change_password.userPwdNew;



                    // Set User Profile Picture , After user has uploaded
//                    Uri uri = firebaseUser.getPhotoUrl();
//                    // ImageViewr setTmagerURI() should not be used with regular URIs. so we are using Picasso
//                    Picasso.with(DeafMute_Edit_Profile.this).load(uri).into(uploadImageD);

                } else {
                    Toast.makeText(DeafMute_Edit_Profile.this, "حدث خطأ ما", Toast.LENGTH_SHORT).show();

                }
                progressBarDM.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DeafMute_Edit_Profile.this, "حدث خطأ ما", Toast.LENGTH_SHORT).show();
                progressBarDM.setVisibility(View.GONE);

            }
        });
    }


    // Action Method Once you click on an item in the menu bar icon
    // Start Menu Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inside, menu);
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
            Intent i = new Intent(getApplicationContext(), Sign_Up_Learner_Page.class);
            startActivity(i);

        }
        if (item_id == R.id.favorite) {
            Intent i = new Intent(getApplicationContext(), Welcome_Video.class);
            startActivity(i);

        }
        if (item_id == R.id.logout) {
            authProfile.signOut();
            Toast.makeText(getApplicationContext(), "لقد تم تسجيل الخروج", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), Main_category.class);
            //clear stack to prevent user coming back to UserProfileActivity onpressing back button after logging out
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish(); // Close UserProfileActivity

        }



        return super.onOptionsItemSelected(item);


    }

    public void updateProfile(FirebaseUser firebaseUser) {

        if (TextUtils.isEmpty(nameDM)) {
            Toast.makeText(DeafMute_Edit_Profile.this, "من فضلك أدخل اسمك", Toast.LENGTH_SHORT).show();
            nameD.setError("الإسم مطلوب");
            nameD.requestFocus();
        } else if (TextUtils.isEmpty(emailDM)) {
            Toast.makeText(DeafMute_Edit_Profile.this, "من فضلك البريد الإلكتروني الخاص بك", Toast.LENGTH_SHORT).show();
            emailD.setError("الإيميل مطلوب");
            emailD.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailDM).matches()) {
            Toast.makeText(DeafMute_Edit_Profile.this, "من فضلك أعد إدخال البريد الإلكتروني", Toast.LENGTH_SHORT).show();
            emailD.setError("البريد الإلكتروني غير صحيح");
            emailD.requestFocus();
        } else if (!(TextUtils.isEmpty(bloodtypeDM))) {
            if (!Arrays.asList(specifyBlood).contains(bloodtypeDM)) {
//                    Toast.makeText(Sign_Up_Deaf_Mute.this, "من فضلك أدخل فصيلة دم صحيحة", Toast.LENGTH_SHORT).show();
                bloodtypeD.setError("من فضلك أدخل فصيلة دم صحيحة");
                bloodtypeD.requestFocus();
            } else {
                // obtion the enterd data
                nameDM = nameD.getText().toString().trim();
                ageDM = ageD.getText().toString().trim();
                emailDM = emailD.getText().toString().trim();
                bloodtypeDM = bloodtypeD.getText().toString().trim().toUpperCase();


                // parameter just that we want to save in the database
                ReadWriteUserDetails
                        writeUserDetails = new ReadWriteUserDetails(0, nameDM, ageDM, bloodtypeDM, emailDM,"Deaf_Mute");

                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseDatabase.getInstance().getReference("Registered User")
                        .child(uid).child("passWordD");
//                FirebaseDatabase.getInstance().getReference("Registered User")
//                        .child("passWordD").setValue("new password");


//                String uid;

                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered User");
                String userId = firebaseUser.getUid();
                progressBarDM.setVisibility(View.VISIBLE);
                referenceProfile.child(userId).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

//                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                    FirebaseDatabase.getInstance().getReference("Registered User")
//                            .child(uid).child("passWordD").setValue(passWordD);

                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(nameDM).build();
                            firebaseUser.updateProfile(profileUpdate);


                            Toast.makeText(DeafMute_Edit_Profile.this, "تم تحديث البيانات بنجاح", Toast.LENGTH_SHORT).show();


                            //Stop the user from return to update profile
                            Intent intent = new Intent(DeafMute_Edit_Profile.this, DeafMute_Edit_Profile.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            try {
                                throw task.getException();
                            } catch (Exception e) {
                                Toast.makeText(DeafMute_Edit_Profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        progressBarDM.setVisibility(View.VISIBLE);
                    }
                });
            }
        }
    }

    // End Menu Bar


    //DeleteUserProfile
//    private void showDialogDeleteUserProfile() {
////        Custom  Dialog For Email Verified
//        final Dialog DialogDeleteUserProfile = new Dialog(DeafMute_Edit_Profile.this);
//        DialogDeleteUserProfile.setContentView(R.layout.dialog_delert_user);
//        DialogDeleteUserProfile.show();
//        //open Email App
//        Button Yes = (Button) DialogDeleteUserProfile.findViewById(R.id.yesDelete);
//        Yes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                progressBarDM.setVisibility(View.VISIBLE);
//                firebaseUser.delete()
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    progressBarDM.setVisibility(View.GONE);
//                                    Toast.makeText(DeafMute_Edit_Profile.this, "حذف", Toast.LENGTH_SHORT).show();
//                                    Intent i = new Intent(getApplicationContext(), Main_category.class);
//                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    startActivity(i);
//
//                                    firebaseDatabase = FirebaseDatabase.getInstance().getReference();
//                                    firebaseDatabase.child("Registered User").child(firebaseUser.getUid()).removeValue();
////                                    .child(storageReference.getPath())
////                                    Log.d(TAG, "User account deleted.");
//                                     deleteImageProfile(view);
////                                    String urI = "ggs://newforyou-882fa.appspot.com/PicturesProfile";
////                                    StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(urI);
////                                    reference.delete();
//                                } else {
//                                    Toast.makeText(DeafMute_Edit_Profile.this, "ما انحذف", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//
//            }
//        });
//        Button No = (Button) DialogDeleteUserProfile.findViewById(R.id.noDelete);
//        No.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DialogDeleteUserProfile.dismiss();
//
//            }
//        });
//
//    }



}