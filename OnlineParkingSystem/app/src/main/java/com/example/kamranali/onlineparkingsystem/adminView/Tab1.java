package com.example.kamranali.onlineparkingsystem.adminView;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kamranali.onlineparkingsystem.R;
import com.example.kamranali.onlineparkingsystem.model.UserModels;
import com.example.kamranali.onlineparkingsystem.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1 extends Fragment {

    private View view;
    private ListView userListView;
    ArrayList<UserModels> arrayList;
    private UserListAdapter adapter;
    private DatabaseReference firebaseDatabase;

    public Tab1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab1, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        userListView = (ListView) view.findViewById(R.id.userListView);

        setupEmailList();

        return view;
    }

    private void setupEmailList() {

        arrayList = new ArrayList<UserModels>();


        adapter = new UserListAdapter(getActivity(), arrayList);


        userListView.setAdapter(adapter);
        firebaseDatabase.child(Constants.USER).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot!=null){
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        UserModels user = data.getValue(UserModels.class);
                        arrayList.add(new UserModels(user.getEmail(),user.getPassword(),user.getMobileNo(),user.getGender(),user.getName(),user.getDob()));
                        adapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                UserModels user = arrayList.get(position);
                builder.setTitle(user.getName());
                builder.setMessage(user.getEmail()+"\n"+user.getMobileNo()+"\n"+user.getGender());


                builder.create().show();
            }
        });
    }

}
