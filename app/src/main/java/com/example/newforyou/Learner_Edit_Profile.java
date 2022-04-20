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
import com.google.android.gms.tasks.OnCompleteListener;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class Learner_Edit_Profile extends Users {
    public static final int PICK_IMAGE_REQUEST = 1;
    ImageView homeLearnerEdit ;
    CircleImageView uploadImageL;
    EditText nameL ,emailL;
    Button btnDeleteL , btnSaveL;
    TextView resetpasswordL , pagedeclerationL;
    ProgressBar progressBarEditL;
    FirebaseAuth authProfile;
    String nameLe, emailLe, passwordL;
    public FirebaseUser firebaseUser;
    public Uri uriImage;
    public StorageReference storageReference;
//    DatabaseReference firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learner_edit_profile);
        nameL = (EditText)findViewById(R.id.nameL);
        emailL = (EditText)findViewById(R.id.emailL);
        emailL.setEnabled(false);
        btnDeleteL = (Button)findViewById(R.id.btnDeleteL);
        btnSaveL = (Button)findViewById(R.id.btnSaveL);
        progressBarEditL = (ProgressBar)findViewById(R.id.progressBarEditL);
        resetpasswordL = (TextView) findViewById(R.id.resetpasswordL);
        pagedeclerationL = (TextView) findViewById(R.id.pagedeclerationL);
        uploadImageL = (CircleImageView) findViewById(R.id.uploadImageL);

        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("PicturesProfile");

        Uri uri = firebaseUser.getPhotoUrl();

        Picasso.with(Learner_Edit_Profile.this).load(uri).into(uploadImageL);
        uploadImageL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChoser();
            }
        });

        homeLearnerEdit = (ImageView) findViewById(R.id.homeLearnerEdit);
        homeLearnerEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Main_category.class);
                startActivity(intent);
            }
        });

        if (firebaseUser == null) {
            Toast.makeText(Learner_Edit_Profile.this, "حدث خطأ ما", Toast.LENGTH_SHORT).show();

        } else {
            checkIfEmaiVerified(firebaseUser);
            progressBarEditL.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }

        btnSaveL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Learner_Edit_Profile.this, "انا كريه", Toast.LENGTH_SHORT).show();
                updateProfile(firebaseUser);
            }
        });

        btnDeleteL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDeleteUserProfile();

            }
        });


        resetpasswordL.setOnClickListener(new View.OnClickListener() {
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
                    progressBarEditL.setVisibility(View.GONE);
                    Toast.makeText(Learner_Edit_Profile.this, "تم رفع صورتك بنجاح", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Learner_Edit_Profile.this, Learner_Edit_Profile.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    public String getFileExtention(Uri uri) {

        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

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
            uploadImageL.setImageURI(uriImage);
            UploadPic();


        }
    }

    // User coming to DeafMute_Edit_ptofile after successful registration
    public void checkIfEmaiVerified(FirebaseUser firebaseUser) {
        if (!firebaseUser.isEmailVerified()){
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
                    nameLe = firebaseUser.getDisplayName().toUpperCase();
                    emailLe = readUserDetails.emailL;

                    pagedeclerationL.setText(nameLe.toUpperCase() + " مرحباً يا ");

                    nameL.setText(nameLe);
                    emailL.setText(emailLe);

                    // Set User Profile Picture , After user has uploaded
                    Uri uri = firebaseUser.getPhotoUrl();
                    // ImageViewr setTmagerURI() should not be used with regular URIs. so we are using Picasso
                    Picasso.with(Learner_Edit_Profile.this).load(uri).into(uploadImageL);

                }else {
                    Toast.makeText(Learner_Edit_Profile.this, "حدث خطأ ما", Toast.LENGTH_SHORT).show();

                }
                progressBarEditL.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Learner_Edit_Profile.this, "حدث خطأ ما", Toast.LENGTH_SHORT).show();
                progressBarEditL.setVisibility(View.GONE);

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
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish(); // Close UserProfileActivity

        }

        return super.onOptionsItemSelected(item);



    }

    // End Menu Bar

    public void updateProfile(FirebaseUser firebaseUser) {

        if (TextUtils.isEmpty(nameLe)) {
            Toast.makeText(Learner_Edit_Profile.this, "من فضلك أدخل اسمك", Toast.LENGTH_SHORT).show();
            nameL.setError("الإسم مطلوب");
            nameL.requestFocus();
        } else if (TextUtils.isEmpty(emailLe)) {
            Toast.makeText(Learner_Edit_Profile.this, "من فضلك البريد الإلكتروني الخاص بك", Toast.LENGTH_SHORT).show();
            emailL.setError("الإيميل مطلوب");
            emailL.requestFocus();
            } else {
                // obtion the enterd data
            nameLe = nameL.getText().toString().trim();
            emailLe = emailL.getText().toString().trim();

                // parameter just that we want to save in the database
                ReadWriteUserDetails
                        writeUserDetails = new ReadWriteUserDetails(1, nameLe, emailLe , "Learner");

                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered User");
                String userId = firebaseUser.getUid();
                progressBarEditL.setVisibility(View.VISIBLE);
                referenceProfile.child(userId).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(nameLe).build();
                            firebaseUser.updateProfile(profileUpdate);

                            Toast.makeText(Learner_Edit_Profile.this, "تم تحديث البيانات بنجاح", Toast.LENGTH_SHORT).show();


                            //Stop the user from return to update profile
                            Intent intent = new Intent(Learner_Edit_Profile.this, Learner_Edit_Profile.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            try {
                                throw task.getException();
                            } catch (Exception e) {
                                Toast.makeText(Learner_Edit_Profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        progressBarEditL.setVisibility(View.VISIBLE);
                    }
                });
            }
        }


    }
