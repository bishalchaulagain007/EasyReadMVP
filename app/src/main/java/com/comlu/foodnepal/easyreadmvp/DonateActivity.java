package com.comlu.foodnepal.easyreadmvp;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.comlu.foodnepal.easyreadmvp.Requests.SellBookRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class DonateActivity extends AppCompatActivity {


    public static boolean success = true;

    private int FACULTY = 1;
    private int YEAR_PART = 1;
    private String BOOK_CONDITION = null;


    private TextInputLayout mTitleLayout;
    private TextInputLayout mAuthorLayout;
    private TextInputLayout mPriceLayout;
    private EditText mInputTitle;
    private EditText mInputAuthor;
    private EditText mInputPrice;

    Spinner spinner1, spinner2, spinner3;

    String[] faculty = {
            "Civil",
            "Mechanical",
            "Architecture",
            "Computer",
            "Electronics",
            "Electrical"
    };
    String[] book_condition = {
            "Best",
            "Good",
            "Bad"
    };


    String[] year_part = {
            "I | I",
            "I | II",
            "II | I",
            "II | II",
            "III | I",
            "III | II",
            "IV | I",
            "IV | II",
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        //new display matrix
/*
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //Very Important
        //create custom theme for this activity in styles.xml
        //and change the manifest too

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 1), (int) (height * 1));
*/

        //matrix finished
        //initialization


        mTitleLayout = (TextInputLayout) findViewById(R.id.title_layout);
        mAuthorLayout = (TextInputLayout) findViewById(R.id.author_layout);
        mPriceLayout = (TextInputLayout) findViewById(R.id.price_layout);
        mInputTitle = (EditText) findViewById(R.id.etTitle);
        mInputAuthor = (EditText) findViewById(R.id.etAuthor);
        mInputPrice = (EditText) findViewById(R.id.etPrice);

        //referencing the spinner1
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        //setting the adapter
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this, faculty);
        spinner1.setAdapter(spinnerAdapter);

        //events
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) FACULTY = 1;
                else if (position == 1) FACULTY = 2;
                else if (position == 2) FACULTY = 3;
                else if (position == 3) FACULTY = 4;
                else if (position == 4) FACULTY = 5;
                else if (position == 5) FACULTY = 6;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //events finished
        //referencing the spinner2
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        //setting the adapter
        SpinnerAdapter spinnerAdapter2 = new SpinnerAdapter(this, year_part);
        spinner2.setAdapter(spinnerAdapter2);

        //events
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) YEAR_PART = 1;
                else if (position == 1) YEAR_PART = 2;
                else if (position == 2) YEAR_PART = 3;
                else if (position == 3) YEAR_PART = 4;
                else if (position == 4) YEAR_PART = 5;
                else if (position == 5) YEAR_PART = 6;
                else if (position == 6) YEAR_PART = 7;
                else if (position == 7) YEAR_PART = 8;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //events finished


        //referencing the spinner2
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        //setting the adapter
        SpinnerAdapter spinnerAdapter3 = new SpinnerAdapter(this, book_condition);
        spinner3.setAdapter(spinnerAdapter3);
        //events
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BOOK_CONDITION = book_condition[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //events finished


        //init finished


    }

    private boolean isEmptyTitle() {
        return mInputTitle.getText() == null
                || mInputTitle.getText().toString() == null
                || mInputTitle.getText().toString().isEmpty();

    }

    private boolean isEmptyAuthor() {
        return mInputAuthor.getText() == null
                || mInputAuthor.getText().toString() == null
                || mInputAuthor.getText().toString().isEmpty();

    }

    private boolean isEmptyPrice() {
        return mInputPrice.getText() == null
                || mInputPrice.getText().toString() == null
                || mInputPrice.getText().toString().isEmpty();

    }


    public void registerBookMethod(View view) {

        boolean isEmptyTitle = isEmptyTitle();
        boolean isEmptyAuthor = isEmptyAuthor();
        boolean isEmptyPrice = isEmptyPrice();
        if (isEmptyTitle && isEmptyAuthor && isEmptyPrice) {
            Toast.makeText(DonateActivity.this, "One or more FIELDS are blank", Toast.LENGTH_SHORT).show();
        }
        if (isEmptyTitle) {
            mTitleLayout.setError("Title Cannot Be Empty");
        }
        if (isEmptyAuthor) {
            mAuthorLayout.setError("Author Cannot Be Empty");
        }
        if (isEmptyPrice) {
            mPriceLayout.setError("Price Cannot Be Empty");
        }
        if (!isEmptyTitle) {
            mTitleLayout.setError(null);
        }
        if (!isEmptyAuthor) {
            mAuthorLayout.setError(null);
        }
        if (!isEmptyPrice) {
            mPriceLayout.setError(null);
        }
        if (mInputPrice.getText().toString() == null
                || mInputTitle.getText().toString() == null
                || mInputAuthor.getText().toString() == null) {
            Toast.makeText(DonateActivity.this, "Please fill up all the fields", Toast.LENGTH_SHORT).show();

        } else if (!isEmptyTitle && !isEmptyAuthor && !isEmptyPrice) {
            //All Good Here

            final String title = mInputTitle.getText().toString(); //gets name and converts to string
            final String author = mInputAuthor.getText().toString();
            final int price = Integer.parseInt(mInputPrice.getText().toString());
            final int facultyFinal = FACULTY;
            final int year_partFinal = YEAR_PART;

            final String book_condition_final = BOOK_CONDITION;

            if (title.equals("") || author.equals("") || book_condition_final.equals("")) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DonateActivity.this);
                builder.setMessage("Please fill up all the required fields")
                        .setNegativeButton("Retry", null)
                        .create()
                        .show();
                success = false;
            } else {
                success = true;
            }

            //creating the response listener
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //converting the response to JSONObject
                    //surrounding with try catch block because this can fail if the response is not in the form of JSON String
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        //getting the response
                        boolean success2 = jsonResponse.getBoolean("success"); //passed "success" because we have set success as true in php

                        if (!success2 && !success) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(DonateActivity.this);
                            builder.setMessage("Book Registration failed!")
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };

            //creating the registerRequest object
            SellBookRequest registerRequest = new SellBookRequest(title, author, facultyFinal, year_partFinal,
                    price, book_condition_final,
                    responseListener);

            //adding to the request queue
            RequestQueue queue = Volley.newRequestQueue(DonateActivity.this);
            queue.add(registerRequest);

            Toast.makeText(DonateActivity.this, "SUCCESS!", Toast.LENGTH_SHORT).show();
            finish();
        } // else finished

    }//onClick finished
}
