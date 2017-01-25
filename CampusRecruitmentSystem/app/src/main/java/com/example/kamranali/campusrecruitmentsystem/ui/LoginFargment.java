package com.example.kamranali.campusrecruitmentsystem.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kamranali.campusrecruitmentsystem.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFargment extends Fragment {
    private Button studentLogin, adminlogin, companyLogin;
    private TextView createAccount;
    private DatabaseReference firebaseDatabase;


    public LoginFargment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login_fargment, container, false);
        // Inflate the layout for this fragment
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        studentLogin = (Button) view.findViewById(R.id.student_login);
        adminlogin = (Button) view.findViewById(R.id.admin_login);
        companyLogin = (Button) view.findViewById(R.id.company_login);
        createAccount = (TextView) view.findViewById(R.id.signup_view);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginTranscartion();
            }
        });
        studentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        companyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    private void beginTranscartion() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main, new SignupFragment())
                .addToBackStack(null)
                .commit();
    }

}
