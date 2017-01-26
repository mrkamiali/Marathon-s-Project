package com.example.kamranali.campusrecruitmentsystem.ui;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kamranali.campusrecruitmentsystem.R;
import com.example.kamranali.campusrecruitmentsystem.adapter.TabAdapter;
import com.example.kamranali.campusrecruitmentsystem.ui.adminView.Tab1Fragment;
import com.example.kamranali.campusrecruitmentsystem.ui.adminView.Tab2Fragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TabAdapter adapter;
    private ArrayList<Fragment> mFragmentArrayList;
    private Tab1Fragment studentFragment;
    private Tab2Fragment companyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mAuth = FirebaseAuth.getInstance();

        studentFragment = new Tab1Fragment();
        companyFragment = new Tab2Fragment();
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mFragmentArrayList = new ArrayList<>();
           /*
        * add Fragment to ArrayList
        */
       viewpagerInitilization();

    }

    private void viewpagerInitilization() {
        mFragmentArrayList.add(studentFragment);
        mFragmentArrayList.add(companyFragment);

        mTabLayout.addTab(mTabLayout.newTab().setText("Students"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Companies"));

        adapter = new TabAdapter(getSupportFragmentManager(), mFragmentArrayList);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                mAuth.signOut();
                Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }
}
