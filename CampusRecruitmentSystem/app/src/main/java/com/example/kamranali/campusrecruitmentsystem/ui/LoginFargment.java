package com.example.kamranali.campusrecruitmentsystem.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.kamranali.campusrecruitmentsystem.R;
import com.example.kamranali.campusrecruitmentsystem.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFargment extends Fragment {
    private Button studentLogin, adminlogin, companyLogin;
    private TextView createAccount;
    private DatabaseReference firebaseDatabase;
    private InputMethodManager imm;
    private View view;
    private FirebaseAuth mAuth;


    public LoginFargment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login_fargment, container, false);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        mAuth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        studentLogin = (Button) view.findViewById(R.id.student_login);
        adminlogin = (Button) view.findViewById(R.id.admin_login);
        companyLogin = (Button) view.findViewById(R.id.company_login);
        createAccount = (TextView) view.findViewById(R.id.signup_view);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginTranscartion(new SignupFragment());
            }
        });
        studentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String student = "student";
                beginTranscartion(new SigninFragment(), student);
            }
        });
        adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginTranscartion(new SigninFragment());
            }
        });
        companyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String company = "company";
                beginTranscartion(new SigninFragment(), company);
            }
        });
        return view;
    }

    private void beginTranscartion(Fragment fragment, String data) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.getUserProfile(), data);
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void beginTranscartion(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main, fragment)

                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
