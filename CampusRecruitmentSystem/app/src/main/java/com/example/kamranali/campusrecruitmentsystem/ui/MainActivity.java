package com.example.kamranali.campusrecruitmentsystem.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.kamranali.campusrecruitmentsystem.R;
import com.example.kamranali.campusrecruitmentsystem.utils.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button studentLogin, adminlogin, companyLogin;
    private TextView createAccount;
    private DatabaseReference firebaseDatabase;
    private FirebaseAuth mAuth;
    private Menu menu1;
    private int menuRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main, new LoginFargment())
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (menuRes) {
            case R.menu.action_menu:
                switch (item.getItemId()) {
                    case R.id.logout:
                        Util.successToast(this, "Logout");
                        mAuth.signOut();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.activity_main,new LoginFargment())
                                .commit();
                        break;
                    default:
                        Util.successToast(this, "Setting");
                        break;
                }
                break;

            default:
                break;
        }


        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menuRes, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public void setMenu(int val) {
        menuRes = val;
        invalidateOptionsMenu();
    }
}
