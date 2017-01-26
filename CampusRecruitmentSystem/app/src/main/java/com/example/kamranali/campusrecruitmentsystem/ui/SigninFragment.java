package com.example.kamranali.campusrecruitmentsystem.ui;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.kamranali.campusrecruitmentsystem.R;
import com.example.kamranali.campusrecruitmentsystem.utils.AppLog;
import com.example.kamranali.campusrecruitmentsystem.utils.Constants;
import com.example.kamranali.campusrecruitmentsystem.utils.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class SigninFragment extends Fragment {
    private static final String TAG = "SigninFragment";
    private EditText email_signin, email_password;
    private Button signinButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;
    private Bundle extras;
    private boolean checkforStudentCompany;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SigninFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREFEREBCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        extras = getArguments();

        email_signin = (EditText) view.findViewById(R.id.email_signinView);
        email_password = (EditText) view.findViewById(R.id.email_passwordView);
        signinButton = (Button) view.findViewById(R.id.signin_Button);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(getActivity(), "Signing-in", "Connecting...", true, false);

                if (email_signin.getText().length() != 0 && email_password.getText().length() != 0) {


                    if (extras != null) {

                        String email = email_signin.getText().toString();
                        String pasword = email_password.getText().toString();
                        String fromExtras = extras.getString(Constants.userProfile);
                        Util.successToast(getActivity(), "Sign in for " + fromExtras);
//                        if (checkStudentComapny(fromExtras, email)) {
//                            fromExtras = Constants.STUDENT;
//                            checkforSignin(fromExtras, email, pasword);
//                            progressDialog.dismiss();
//                        } else {
//                            fromExtras = Constants.COMPANY;
//                            checkforSignin(fromExtras, email, pasword);
//                            progressDialog.dismiss();
//                        }
                            checksignfor(fromExtras,email,pasword);
                    } else {
                        String email = email_signin.getText().toString();
                        String pasword = email_password.getText().toString();
                        Util.successToast(getActivity(), "Login For Admin");
                        checkforAdminSignin(email, pasword);
                        progressDialog.dismiss();

                    }
                }
            }
        });

        return view;
    }

    private void checksignfor(final String fromExtras, String email, String pasword) {
        mAuth.signInWithEmailAndPassword(email,pasword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()){
                    final String uid = task.getResult().getUser().getUid();
                    FirebaseDatabase.getInstance().getReference().child(fromExtras).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot!=null){
                                for (DataSnapshot data:dataSnapshot.getChildren()) {
                                    if (data.getKey().equals(uid)) {
                                        if (fromExtras.equals(Constants.STUDENT)) {
                                            editor.clear();
                                            editor.putString(Constants.SharedKEY,fromExtras);
                                            editor.commit();
                                            getActivity().getSupportFragmentManager().beginTransaction()
                                                    .replace(R.id.activity_main, new StudentFragment())
                                                    .addToBackStack(null)
                                                    .commit();
                                        } else if (fromExtras.equals(Constants.COMPANY)) {
                                            editor.clear();
                                            editor.putString(Constants.SharedKEY,fromExtras);
                                            editor.commit();
                                            getActivity().getSupportFragmentManager().beginTransaction()
                                                    .replace(R.id.activity_main, new CompanyFragment())
                                                    .addToBackStack(null)
                                                    .commit();
                                        } else {
                                            Util.successToast(getActivity(), "This is an Admin Account, You can not access it.");
                                        }

                                    }
                                }

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    Util.failureToast(getActivity(),"User not Found");
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }

    private synchronized boolean checkStudentComapny(String user, final String email) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        if (user != null) {
            DatabaseReference ref = databaseReference.child(user).getRef();
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        if (data.hasChild(Constants.EMAIL)) {
                            String emailFirebase = data.child(Constants.EMAIL).getValue().toString();
                            if (email.equals(emailFirebase)) {
                                checkforStudentCompany = true;
                                Util.successToast(getActivity(), "Login for Student Checked");
                            } else {
                                Util.successToast(getActivity(), "Login for Company Checked");
                                checkforStudentCompany = false;
                            }


                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        return checkforStudentCompany;

    }

    private synchronized void checkforSignin(final String user, final String email, final String password) {

        databaseReference.child(user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.hasChild(Constants.EMAIL)) {

                        String emailFirebase = data.child(Constants.EMAIL).getValue().toString();
                        if (email.equals(emailFirebase)) {

                            mAuth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                Util.successToast(getActivity(), "Sign-in Successfully");
                                                if (user.equals(Constants.STUDENT)) {
                                                    getActivity().getSupportFragmentManager().beginTransaction()
                                                            .replace(R.id.activity_main, new StudentFragment())
                                                            .addToBackStack(null)
                                                            .commit();
                                                    progressDialog.dismiss();
                                                } else {
                                                    getActivity().getSupportFragmentManager().beginTransaction()
                                                            .replace(R.id.activity_main, new CompanyFragment())
                                                            .addToBackStack(null)
                                                            .commit();
                                                    progressDialog.dismiss();
                                                }

                                            } else {
                                                progressDialog.dismiss();
                                                Util.successToast(getActivity(), "Sign-in Error");
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();

                                    Util.successToast(getActivity(), e.getMessage());
                                }
                            });
                        } else {
                            progressDialog.dismiss();
                            Util.failureToast(getActivity(), "User not found.");
                        }


                    } else {
                        progressDialog.dismiss();
                        Util.successToast(getActivity(), "User Not found in " + user);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private synchronized void checkforAdminSignin(final String mail, final String password) {
        //
        mAuth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            final String uid = task.getResult().getUser().getUid();
                            FirebaseDatabase.getInstance().getReference().child(Constants.ADMIN).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()){
                                        if (data.getKey().equals(uid)){
                                            Util.successToast(getActivity(), "Sign-in Successfully");
                                            editor.clear();
                                            editor.putString(Constants.SharedKEY,Constants.ADMIN);
                                            editor.commit();
                                            Intent intent = new Intent(getActivity(), AdminActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        } else {
                            progressDialog.dismiss();
                            Util.successToast(getActivity(), "You are not an admin");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();

                Util.successToast(getActivity(), e.getMessage());
            }
        });


        //
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

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }
}
