package com.example.kamranali.onlineparkingsystem.signupView;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kamranali.onlineparkingsystem.MainActivity;
import com.example.kamranali.onlineparkingsystem.R;
import com.example.kamranali.onlineparkingsystem.model.UserModels;
import com.example.kamranali.onlineparkingsystem.utils.AppLogs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {

    private EditText email, mobileNumber, password, gender, name, dob;
    private Button signup;
    private FirebaseAuth mAuth;
    private FirebaseUser firebase_user;
    private DatabaseReference firebase;


    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, null);

        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance().getReference();
        email = (EditText) view.findViewById(R.id.editText_email);
        mobileNumber = (EditText) view.findViewById(R.id.editText_Mobilenumber);
        password = (EditText) view.findViewById(R.id.editText_password);
        gender = (EditText) view.findViewById(R.id.editText_gender);
        name = (EditText) view.findViewById(R.id.editText_fname);
        dob = (EditText) view.findViewById(R.id.editText_DOB);
        signup = (Button) view.findViewById(R.id.signup_btn);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pass = password.getText().toString();
                //Checking the length of pasword while registering new USER;
                if (pass.length() <= 6) {
                    main(pass);
                } else if ((name.getText().toString().equals("")
                        || dob.getText().toString().equals("")
                        || mobileNumber.getText().toString().equals("")
                        || pass.equals(""))) {
                    Toast.makeText(getActivity(), "Fields Should not be left Empty", Toast.LENGTH_SHORT).show();

                } else if (email.getText().length() == 0 || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    email.setError("Enter Valid Email Address");
                } else if (name.getText().length() == 0 || !name.getText().toString().matches("[a-zA-Z ]+")) {
                    name.setError("Invalid Name");
                }
                //Checking the length of pasword while registering new USER;
                else if (pass.length() <= 6) {
                    main(pass);
                } else {
                    try {
                        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "Sign Up", "Connecting...", true, false);

                        mAuth.createUserWithEmailAndPassword((email.getText().toString()), (password.getText().toString())).addOnCompleteListener(getActivity(),
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {


//
                                        if (task.isSuccessful()) {
                                            String uid = mAuth.getCurrentUser().getUid();
                                            firebase.child("users").child(uid).setValue(new UserModels(email.getText().toString(),password.getText().toString(),mobileNumber.getText().toString(),gender.getText().toString(),name.getText().toString(),dob.getText().toString()));
//
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), "Signup Successfull", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getActivity(), MainActivity.class);
                                            startActivity(intent);

                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), " " + task.getException(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                AppLogs.d("FailureSignup", e.getMessage());

                            }
                        });

                    } catch (Exception ex) {

                        ex.printStackTrace();
                    }
                }
            }
        });


        return view;
    }

    private void main(String pass) {

        Toast.makeText(getActivity(), pass + "\nYou Password is no longer Stronger \nPlease signup Again with \natleast 7 Character of Pasword.\nThanks ", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

}
