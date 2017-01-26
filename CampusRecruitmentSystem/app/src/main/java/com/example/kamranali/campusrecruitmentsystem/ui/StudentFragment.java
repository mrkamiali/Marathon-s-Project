package com.example.kamranali.campusrecruitmentsystem.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.kamranali.campusrecruitmentsystem.R;
import com.example.kamranali.campusrecruitmentsystem.utils.AppLog;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentFragment extends Fragment {

    private InputMethodManager imm;
    private View view;

    public StudentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_student, container, false);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        ((MainActivity) getActivity()).setMenu(R.menu.action_menu);
        super.onResume();
    }
}


