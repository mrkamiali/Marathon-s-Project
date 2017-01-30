package com.example.kamranali.onlineparkingsystem.adminView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.kamranali.onlineparkingsystem.R;
import com.example.kamranali.onlineparkingsystem.model.UserModels;

import java.util.ArrayList;

/**
 * Created by kamranali on 27/01/2017.
 */

public class UserListAdapter extends BaseAdapter implements ListAdapter {

    private Context mContext;
    private ArrayList<UserModels> arrayList;
    AdminActivity adminActivity;

    public UserListAdapter(Context mContext, ArrayList<UserModels> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        adminActivity = (AdminActivity) mContext;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

         LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.user_list_item, null);
            TextView nameView = (TextView) view.findViewById(R.id.user_nameView);
            TextView emailView = (TextView) view.findViewById(R.id.user_emailViewl);
            UserModels userModels = arrayList.get(position);
            nameView.setText(userModels.getName());
            emailView.setText(userModels.getEmail());


        return view;
    }
}
