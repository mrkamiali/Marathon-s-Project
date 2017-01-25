package com.example.kamranali.campusrecruitmentsystem.ui;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kamranali.campusrecruitmentsystem.R;
import com.example.kamranali.campusrecruitmentsystem.model.Model;
import com.example.kamranali.campusrecruitmentsystem.utils.AppLog;
import com.example.kamranali.campusrecruitmentsystem.utils.Constants;
import com.example.kamranali.campusrecruitmentsystem.utils.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "SignupFragment";
    View view;
    EditText editTextEmail, editTextUserDob, editTexttName, editTextPassword, editTextGender;
    String userEmail, userDOB, userPassword, userGender, userName;
    Button signupButton;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference databastRef;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Spinner selectionSpinner;
    private InputMethodManager imm;
    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_signup, container, false);
         imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        initViews();

        mAuth = FirebaseAuth.getInstance();
        databastRef = FirebaseDatabase.getInstance().getReference();

        return view;
    }

    private void initViews() {
        editTexttName = (EditText) view.findViewById(R.id.signup_nameView);
        editTextEmail = (EditText) view.findViewById(R.id.signup_emailView);
        editTextUserDob = (EditText) view.findViewById(R.id.signup_DOBView);
        editTextPassword = (EditText) view.findViewById(R.id.signup_passwordView);
        editTextGender = (EditText) view.findViewById(R.id.signup_GenderView);
        signupButton = (Button) view.findViewById(R.id.signup_Button);
        selectionSpinner = (Spinner) view.findViewById(R.id.selection_spinner);
        signupButton.setOnClickListener(this);
        addFocusListeners();

    }

    private void addFocusListeners() {

        editTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String  text= ((EditText)v).getText().toString().trim();
                    if (text.length()==0){
                        editTextEmail.setError("Enter Email Address");
                    }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()){
                        editTextEmail.setError("Enter Valid Email Address");
                    }
                }
            }
        });
        editTexttName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String text = ((EditText) v).getText().toString().trim();
                    if (text.length() == 0) {
                        editTexttName.setError("Enter First Name");
                    } else if (!text.matches("[a-zA-Z ]+")) {
                        editTexttName.setError("Invalid Name");
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        userEmail = editTextEmail.getText().toString().trim();
        userDOB = editTextUserDob.getText().toString().trim();
        userName = editTexttName.getText().toString().trim();
        userPassword = editTextPassword.getText().toString();
        userGender = editTextGender.getText().toString();
        progressDialog = ProgressDialog.show(getActivity(), "Signing Up", "Connecting...", true, false);
        mAuth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> authResultTask) {

                if (authResultTask.isSuccessful()){
                    final String uid = authResultTask.getResult().getUser().getUid();
                    if (selectionSpinner.getSelectedItem().toString().equals("Signup as a Student")){
                        databastRef.child(Constants.STUDENT).child(uid).setValue(new Model(userName,userEmail,userPassword,userDOB,userGender,uid));
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.activity_main,new LoginFargment())
                                .addToBackStack(null)
                                .commit();
                        progressDialog.dismiss();
                    }else {
                        databastRef.child(Constants.COMPANY).child(uid).setValue(new Model(userName,userEmail,userPassword,userDOB,userGender,uid));
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.activity_main,new LoginFargment())
                                .addToBackStack(null)
                                .commit();
                        progressDialog.dismiss();

                    }


                }else {
                    Util.failureToast(getActivity(),"sigUP failed");
                    progressDialog.dismiss();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Util.failureToast(getActivity(),e.getMessage());
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
