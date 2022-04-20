package com.example.newforyou;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Login_Page extends Users {  //

    public static final String TAG = "Login_Page";//declare a TAG constant in this class,to use in the first parameter in Log.e.
    // From line 37-43 to declare variables for all views found in res/layout/ login_page and res/layout/toolbar_title_layout
    TextView createAccount, titleActinBar, forgetPass;
    AutoCompleteTextView inputemailLogin;
    EditText inputPasswordLogin;
    Button btnLogin;
    ProgressBar progressBarLogin;
    ImageView hidePassword;
    ImageView homeLogin;
    FirebaseAuth authProfileLogin; // line 44 Declare an instance of FirebaseAuth to authenticate users to the app by using passwords



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inside the onCreate() method, we set the value of each of these variables to a call for findViewById() to access it in res/layout/login_page
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);// to access login_page in res/layout
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);// From line 53-58 to custom elements of ActionBar found in res/layout/toolbar_title_layout
        getSupportActionBar().setCustomView(R.layout.toolbar_title_layout);// Custom toolbar access
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple)));//Set color purple for toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// Added icon to tool bar to return to previous page
        titleActinBar = (TextView) findViewById(R.id.titleActinBar);//Access the TextView toolbar element
        titleActinBar.setText("تسجيل الدخول");//Set this text for the toolbar element
        // From line 59-63 to access id of element in the res/layout/login_page and set an ImageView , AutoCompleteTextView , EditText , Button, ProgressBar
        inputemailLogin = (AutoCompleteTextView) findViewById(R.id.inputemailLogin);
        inputPasswordLogin = (EditText) findViewById(R.id.inputPasswordLogin);
        btnLogin = (Button) findViewById(R.id.btnLoginForLogin);
        progressBarLogin = (ProgressBar) findViewById(R.id.progressBarLogin);
        // line 66 initialize the FirebaseAuth instance, it will be used to check if the user is already logged in and the user returns to the application.
       // when the user closes the application and then opens it again, he will be directed to the personal page without having to enter the user's email and password every time
        authProfileLogin = FirebaseAuth.getInstance();

        // line 69 – 76 onClick logo icon application to redirect Main_Category
        homeLogin = (ImageView) findViewById(R.id.homeLogin);
        homeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Main_category.class);//when you click on the application logo icon, the user will be redirected to the Main_Category
                startActivity(intent);//Launch the activity
            }
        });

        // access id of element in res/layout/login_page and and then find view hidePassword to how/Hide Password using Eye Icon
        hidePassword = (ImageView) findViewById(R.id.hidePassword);
        hidePassword.setImageResource(R.drawable.ic_hide_pwd);//Set the image that will be the eye icon for this image view in initiates (hidden password)
        //onClick this Eye Icon
        hidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check whether the password is visible or hidden when the icon is clicked so for that edit text login password.
                if (inputPasswordLogin.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {//.getTransformationMethod() this will return whether the password
//                  is hidden or visible in the beginning
                    inputPasswordLogin.setTransformationMethod(PasswordTransformationMethod.getInstance());//if the password is visible we will just hide it so for that edit text login password
//                   .setTransformationMethod is going to hide the password
                    hidePassword.setImageResource(R.drawable.ic_hide_pwd);//after checking hiding the password we will change the icon and set the hiding eye icon

                } else {
                   //When password is hidden, the user clicks on the eye icon to display password and then change the eye icon
                    inputPasswordLogin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    hidePassword.setImageResource(R.drawable.ic_show_pwd);//set the show eye icon
                }
            }
        });

        // line 100 – 109 onClick Forget Password Text
        forgetPass = (TextView) findViewById(R.id.forgetPassForLogin);// access id of this element
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login_Page.this, "تستطيع تعيين كلمة المرور الان", Toast.LENGTH_SHORT).show(); //after click will be display Toast message
                //when you click on the  Forget Password , the user will be redirected to the ForgetPassword page to reset password
                startActivity(new Intent(Login_Page.this,ForgetPassword.class));
            }
        });

        // line 111 – 120 onClick create Account TextView
        createAccount = (TextView) findViewById(R.id.createAccountForLogin);// access id of this element
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when you click on the create Account, the user will be redirected to the Who_Are_You_Page page to Checking whether the user is deaf mute or a sign language learner
                Intent intent = new Intent(getApplicationContext(), Who_Are_You_Page.class);
                startActivity(intent);//Launch the activity
            }
        });


        // onClick login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Obtain ( email , password) from the responsive edit text and save them in String variables.
                String textEmail = inputemailLogin.getText().toString().trim();
                String textPassword = inputPasswordLogin.getText().toString().trim();

               //Check whether the user has entered the data or not if the user has not entered, in that case we should display an error message
                if (TextUtils.isEmpty(textEmail)) { //In the email edit text, if the user has not entered the email field, an error message will be displayed
                    Toast.makeText(Login_Page.this, "من فضلك أدخل ايميلك", Toast.LENGTH_SHORT).show();//an Toast message will be displayed
                    inputemailLogin.setError("الايميل مطلوب");//an error message will be displayed
                    inputemailLogin.requestFocus();//Focus on email Edit text
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {//if the user does not enter an email address that matches the correct form of the email address
                    Toast.makeText(Login_Page.this, "من فضلك أعد إدخال البريد الإلكتروني", Toast.LENGTH_SHORT).show();
                    inputemailLogin.setError("البريد الإلكتروني غير صحيح");//an error message will be displayed
                    inputemailLogin.requestFocus();
                } else if (TextUtils.isEmpty(textPassword)) { //In the Password edit text, if the user has not entered the Password field, an error message will be displayed
                    Toast.makeText(Login_Page.this, "من فضلك أدخل كلمة السر", Toast.LENGTH_SHORT).show();
                    inputPasswordLogin.setError("كلمة السر مطلوبة");
                    inputPasswordLogin.requestFocus();
                } else {
                    //Final case where the user has entered the login email and password correctly in that case we will set the progress bar to be visible.
                    progressBarLogin.setVisibility(View.VISIBLE);
                    loginUser(textEmail, textPassword);//There we will call this loginUser method. Once the user has login we  have to pass the user email and user password with this method
                }
            }
        });
    }

    // loginUser method
    public void loginUser(String textemail, String textpasswprd) {
    //we will use the firebase auth (authProfileLogin) variable to check if the user is already logged then within the brackets we have to pass the parameter email and password then
//  finally we will just add .addOnCompleteListener to listener for the completion of the task
        authProfileLogin.signInWithEmailAndPassword(textemail, textpasswprd).addOnCompleteListener(Login_Page.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Inside the onComplete method we will check whether the task is completed or not
                if (task.isSuccessful()) {
                    //We will declare FirebaseUser to access user's profile information in the database,and calling the getCurrentUser method to get the current user from it
                    FirebaseUser firebaseUser = authProfileLogin.getCurrentUser();

                    if (!(firebaseUser.isEmailVerified())) { // Check if user's email is not verified
                        Toast.makeText(Login_Page.this, "يرجى التحقق من الايميل المرسل لك على الايميل المسجل ", Toast.LENGTH_SHORT).show();//an Toast message will be displayed
                        firebaseUser.sendEmailVerification();// Firebase will send an address verification email to a user.
                        showAlertDialog();//This method extends from the users class to open Email Apps in new window
//                      If the user has many email apps on his phone the android is going to display the list of those email apps to choose

                    } else { //if the user email address is verified
                        Toast.makeText(Login_Page.this, "تم تسجيل الدخول", Toast.LENGTH_SHORT).show();//an Toast message will be displayed
                        //to get the users uid in the Firebase by calling the user .getUser().getUid().
                        //if the task has failed, then getResult() will throw an Exception
                        String uid = task.getResult().getUser().getUid();//getUid() Returns a string used to uniquely identify users in the Firebase database

                        //The entry point for accessing a Firebase Database. We call getInstance() to get an instance for the specified URL, using the specified FirebaseApp.
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        //Gets a Reference for the location at the specified path in the Database that represents .child("Registered User") for reading or writing data to that Database location.
                        //.child(uid).child("userTypeInt") access uid and userTypeInt from the database to check userTypeInt then redirecting user to specific page after successful login
                        //Add a listener for a single change in the data at this location.
                        firebaseDatabase.getReference().child("Registered User").child(uid).child("userTypeInt").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            //This method will be called with a snapshot of the data at this location. It will be called each time that data changes.
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (authProfileLogin.getCurrentUser() != null) { //Authentication user and Calling the getCurrentUser method.If user is not signed , getCurrentUser returns null:
                                    int userTypeInt = snapshot.getValue(Integer.class);//Cast dataSnapshot from Firebase to Integer because it receives userTypeInt parameters as integers.
                                    //check userTypeInt from the database to redirect the user to a specific page
                                    if (userTypeInt == 0) { //we passing argument userTypeInt as 0 with user type Deaf Mute to firebase
                                        Intent i = new Intent(getApplicationContext(), Main_category.class);// redirect the user to Main_category page
                                        //clear stack to prevent user coming back to Login_Page on pressing back button after logging
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);//Launch the activity
                                        finish(); // Close Login_Page Activity
                                    } else if (userTypeInt == 1) {//Passing argument userTypeInt as 1 with user type learner to firebase
                                        Intent intent = new Intent(getApplicationContext(), Learrn_Sign_Language.class);// redirect the user to Learrn_Sign_Language page
//                                       .FLAG_ACTIVITY_CLEAR TOP flag used for the intent, meaning if the activity being launched is already running in the current task (Login Activity),
//                                        then instead of launching a new instance of that activity, all of the other activities on top of it will be closed and this intent
//                                        will be delivered to the old activity (now on top) as a new intent.
//                                        And using FLAG_ACTIVITY_NEW_TASK and FLAG ACTIVITY_CLEAR_TASK will clear all the activities from the back stack.
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);//Launch the activity
                                        finish(); //Close Login_Page Activity
                                    }
                                } else {
                                    //If user is not signed, .getCurrentUser return null
                                    Toast.makeText(Login_Page.this, "حدث خطأ ما", Toast.LENGTH_SHORT).show();//an Toast message will be displayed

                                }
                            }
                            //This method will be triggered in the event that this listener either failed at the server, or is removed as a result of the security and Firebase Database rules.
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }

                        });
                    }

                } else { //if the task wasn't successful when logged in, an Exception Handling will be displayed for the user
                    try {
                        throw task.getException(); //get the exception that occurred with the task

                    } catch (FirebaseAuthInvalidUserException e) { //Thrown when performing an operation on a FirebaseUser instance that is no longer valid ex:ERROR_USER_NOT_FOUND.
                        inputemailLogin.setError("الحساب غير مسجل مسبقاً، الرجاء انشاء حساب");// if the user has been deleted an error message will be displayed
                        inputemailLogin.requestFocus();//Focus on email Edit text
                    } catch (FirebaseAuthInvalidCredentialsException e) {//Thrown when one or more of the credentials passed to a method fail to identify and/or authenticate
                        inputemailLogin.setError("بيانات الاعتماد غير صالحة، من فضلك اعد كتابة البيانات");//ex:The email and password do not match or the user does not have a password
                        inputemailLogin.requestFocus();
                    } catch (Exception e) {//This exception means that whatever exception is occurring we will get it
                        //Log.e: that means an error has occurred and therefore we will logging an error to get error message
 //                       each log method, the first parameter should be a unique tag as the name of the current class and the second parameter is the message ..
                        Log.e(TAG, e.getMessage());//TAG of a system log message is a short string indicating the system component from which the message originates
                        Toast.makeText(Login_Page.this,e.getMessage() , Toast.LENGTH_SHORT).show();//This message will contain the error or exception that occurred
                    }
                }
                progressBarLogin.setVisibility(View.GONE); // Whether the user was able to login or not. We have to stop the Progress Bar
            }
        });
    }
}
