package com.comlu.foodnepal.easyreadmvp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;


    private LinearLayout engineeringLayout;

    public static String USER_LOGGED_IN_1 = null;
    public static String USER_LOGGED_IN_2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting intent extras
        USER_LOGGED_IN_1 = getIntent().getStringExtra(LoginActivity.USER_LOGGED_IN);
        //Toast.makeText(MainActivity.this, "1st Value passed: " + USER_LOGGED_IN_1, Toast.LENGTH_LONG).show();
        USER_LOGGED_IN_2 = getIntent().getStringExtra(NavigationDrawerFragment.USER_LOGGED_IN);
        //Toast.makeText(MainActivity.this, "2nd Value passed: " + USER_LOGGED_IN_2, Toast.LENGTH_LONG).show();

        //for toolbar
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Category List");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        engineeringLayout = (LinearLayout) findViewById(R.id.engineeringLayout);
        engineeringLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, PreUserArea.class);
                intent.putExtra(USER_LOGGED_IN_1, USER_LOGGED_IN_2);
                startActivity(intent);

            }
        });
    }


/*
    @Override
    public void onBackPressed() {

//        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
*/


}

