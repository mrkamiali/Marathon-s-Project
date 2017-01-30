package com.example.kamranali.onlineparkingsystem.adminView;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kamranali.onlineparkingsystem.MainActivity;
import com.example.kamranali.onlineparkingsystem.R;
import com.example.kamranali.onlineparkingsystem.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TabAdapter adapter;
    private ArrayList<Fragment> mFragmentArrayList;
    private Tab1 mTab1;
    private Tab2 mTab2;
    private Tab3 mTab3;
    private Tab4 mTab4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mAuth = FirebaseAuth.getInstance();

        mTab1 = new Tab1();
        mTab2 = new Tab2();
        mTab3 = new Tab3();
        mTab4 = new Tab4();
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mFragmentArrayList = new ArrayList<>();
        adapter = new TabAdapter(getSupportFragmentManager(), mFragmentArrayList);

           /*
        * add Fragment to ArrayList
        */
        initilizeViewPAger();


    }

    public void initilizeViewPAger() {

        mFragmentArrayList.add(mTab1);
        mFragmentArrayList.add(mTab2);
        mFragmentArrayList.add(mTab3);
        mFragmentArrayList.add(mTab4);

        mTabLayout.addTab(mTabLayout.newTab().setText(Constants.FIRST_TAB));
        mTabLayout.addTab(mTabLayout.newTab().setText(Constants.SECOND_TAB));
        mTabLayout.addTab(mTabLayout.newTab().setText(Constants.THIRD_TAB));
        mTabLayout.addTab(mTabLayout.newTab().setText(Constants.FOURTH_TAB));


        mViewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.activity_admin_actions:
                mAuth.signOut();
                Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }
}
