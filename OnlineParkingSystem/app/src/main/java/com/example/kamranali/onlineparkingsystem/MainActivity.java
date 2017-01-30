package com.example.kamranali.onlineparkingsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamranali.onlineparkingsystem.adminView.AdminActivity;
import com.example.kamranali.onlineparkingsystem.signupView.SignupFragment;
import com.example.kamranali.onlineparkingsystem.utils.AppLogs;
import com.example.kamranali.onlineparkingsystem.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainThread";
    private EditText email, pass;
    private Button loginbtn;
    private DatabaseReference firebaseDatabase;
    private TextView signupText;
    private FragmentManager fragmentManager;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(Constants.MY_PREFERENCE, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {
                    String user = sharedPreferences.getString(Constants.SharedKEY, null);
                    //User is signin
                    String uid = currentUser.getUid();

                    checkwhosSigninLast(user, uid);

                } else {
                    //User is signout
                }
            }
        };
        email = (EditText) findViewById(R.id.editText_Loginemail);
        pass = (EditText) findViewById(R.id.editText_loginpass);
        loginbtn = (Button) findViewById(R.id.login_btn);
        signupText = (TextView) findViewById(R.id.sign_up);
        fragmentManager = getSupportFragmentManager();


        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().add(R.id.container, new SignupFragment()).addToBackStack(null).commit();
            }
        });


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emails = email.getText().toString();
                String passo = pass.getText().toString();


                if (emails.length() == 0) {
                    email.setError("This is Required Field");
                } else if (passo.length() == 0 && passo.length() <= 6) {
                    pass.setError("This is Required Field");
                }

                if (email.getText().toString().equals("admin") && pass.getText().toString().equals("admin")) {
                    editor.clear();
                    editor.putString(Constants.SharedKEY, Constants.ADMIN);
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                    startActivity(intent);

                    Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "Sign In", "Connecting...", true, false);

                        mAuth.signInWithEmailAndPassword(emails, passo).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull final Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    //TODO User FRAGMENT OR ACTIVITY GOES HERE !
                                    editor.clear();
                                    editor.putString(Constants.SharedKEY, Constants.USER);
                                    editor.commit();
                                    AppLogs.logd("signInWithEmail:onComplete:" + task.isSuccessful());
                                    progressDialog.dismiss();


                                } else if (!task.isSuccessful()) {
                                    AppLogs.logw("signInWithEmail" + task.getException());
                                    Toast.makeText(MainActivity.this, "" + task.getException(),
                                            Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();

                                }
                            }
                        }).addOnFailureListener(MainActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                AppLogs.d("FailureSignin", e.getMessage());
                            }
                        });


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void checkwhosSigninLast(String user, String uid) {

        if (user.equals(Constants.ADMIN)) {
            Intent intent = new Intent(this, AdminActivity.class);
            startActivity(intent);
        } else if (user.equals(Constants.USER)) {
            //TODO: User Fragment Transcation HERE.
        }
    }
}
