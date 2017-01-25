package com.example.kamranali.campusrecruitmentsystem.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.kamranali.campusrecruitmentsystem.R;
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
public class SigninFragment extends Fragment {
    private EditText email_signin,email_password;
    private Button signinButton;
    private FirebaseAuth mAuth;
    private DatabaseReference database;

    public SigninFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        Bundle extras = getArguments();
        String string = extras.getString(Constants.getUserProfile());
        Util.successToast(getActivity(),string);
        email_signin = (EditText) view.findViewById(R.id.email_signinView);
        email_password= (EditText) view.findViewById(R.id.email_passwordView);
        signinButton = (Button) view.findViewById(R.id.signin_Button);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkforSignin();
            }
        });

        return view;
    }

    private void checkforSignin() {
        mAuth.signInWithEmailAndPassword(email_signin.getText().toString(),email_password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            String uid = task.getResult().getUser().getUid();
                            Util.successToast(getActivity(),"Sign-in Successfully");
                        }else {
                            Util.successToast(getActivity(),"Sign-in Error");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                        Util.successToast(getActivity(),e.getMessage());
            }
        });
    }


}
