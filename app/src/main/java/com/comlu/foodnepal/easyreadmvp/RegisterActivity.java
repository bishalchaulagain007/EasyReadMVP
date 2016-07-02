package com.comlu.foodnepal.easyreadmvp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.comlu.foodnepal.easyreadmvp.Requests.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    public static boolean success = true;

    private RelativeLayout mRoot;
    private TextInputLayout mEmailLayout;
    private TextInputLayout mNameLayout;
    private TextInputLayout mPasswordLayout;
    private TextInputLayout mAgeLayout;
    private TextInputLayout mUsernameLayout;
    private EditText mInputEmail;
    private EditText mInputAge;
    private EditText mInputName;
    private EditText mInputUsername;
    private EditText mInputPassword;

    private View.OnClickListener mSnackBarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Toast.makeText(getApplicationContext(), "Snack Bar Clicked! ", Toast.LENGTH_SHORT).show();

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        final EditText etAge = (EditText) findViewById(R.id.etAge);
        etAge.setText("0");
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        Button bRegister = (Button) findViewById(R.id.bRegister);

        //initialization

        mRoot = (RelativeLayout) findViewById(R.id.root_relative_layout_registerActivity);
        mEmailLayout = (TextInputLayout) findViewById(R.id.email_layout);
        mPasswordLayout = (TextInputLayout) findViewById(R.id.password_layout);
        mNameLayout = (TextInputLayout) findViewById(R.id.name_layout);
        mAgeLayout = (TextInputLayout) findViewById(R.id.age_layout);
        mUsernameLayout = (TextInputLayout) findViewById(R.id.username_layout);

        mInputEmail = (EditText) findViewById(R.id.etEmail);
        mInputPassword = (EditText) findViewById(R.id.etPassword);
        mInputName = (EditText) findViewById(R.id.etName);
        mInputAge = (EditText) findViewById(R.id.etAge);
        mInputUsername = (EditText) findViewById(R.id.etUsername);


        //init finished


        ImageView imageView = (ImageView) findViewById(R.id.profile_image);
        //imageView.setImageResource(R.drawable.ic_launcher);

        //onClick event
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isEmptyEmail = isEmptyEmail();
                boolean isEmptyPassword = isEmptyPassword();
                boolean isEmptyName = isEmptyName();
                boolean isEmptyAge = isEmptyAge();
                boolean isEmptyUsername = isEmptyUsername();
                if (isEmptyEmail && isEmptyPassword && isEmptyName && isEmptyAge && isEmptyUsername) {
                    Snackbar.make(mRoot, "One Or More Fields Are Blank", Snackbar.LENGTH_SHORT)
                            .setAction(getString(R.string.text_dismiss), mSnackBarClickListener)
                            .show();
                }
                if (isEmptyEmail) {
                    mEmailLayout.setError("Email Cannot Be Empty");
                }
                if (isEmptyName) {
                    mNameLayout.setError("Name Cannot Be Empty");
                }
                if (isEmptyPassword) {
                    mPasswordLayout.setError("Password Cannot Be Empty");
                }
                if (isEmptyUsername) {
                    mUsernameLayout.setError("Username Cannot Be Empty");
                }
                if (isEmptyAge) {
                    mAgeLayout.setError("Age Cannot Be Empty \n Your Information will be safe.");
                }
                if (!isEmptyUsername) {
                    mUsernameLayout.setError(null);
                }
                if (!isEmptyEmail) {
                    mEmailLayout.setError(null);
                }
                if (!isEmptyPassword) {
                    mPasswordLayout.setError(null);
                }
                if (!isEmptyName) {
                    mNameLayout.setError(null);
                }
                if (!isEmptyAge) {
                    mAgeLayout.setError(null);
                }
                if (mInputEmail.getText().toString() == null
                        || mInputUsername.getText().toString() == null
                        || mInputPassword.getText().toString() == null
                        || mInputName.getText().toString() == null) {
                    Toast.makeText(RegisterActivity.this, "Please fill up all the fields", Toast.LENGTH_SHORT).show();

                } else if (!isEmptyEmail && !isEmptyPassword && !isEmptyName && !isEmptyAge && !isEmptyUsername) {
                    //All Good Here

                    final String name = etName.getText().toString(); //gets name and converts to string
                    final String username = etUsername.getText().toString();
                    //Toast.makeText(getApplicationContext(),"Username is: " + username,Toast.LENGTH_LONG).show();
                    final String password = etPassword.getText().toString();
                    final String email = etEmail.getText().toString();
                    final int age = Integer.parseInt(etAge.getText().toString()); //to parse integer to string

                    if (name.equals("") || username.equals("") || password.equals("") || email.equals("")) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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

                                if (success2 && success) {
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    RegisterActivity.this.startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("Registration failed")
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
                    RegisterRequest registerRequest = new RegisterRequest(name, username, age, password, email, responseListener);

                    Toast.makeText(RegisterActivity.this, "SUCCESS!", Toast.LENGTH_SHORT).show();
                    //adding to the request queue
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                }


            }
        });

    }

    private boolean isEmptyEmail() {
        return mInputEmail.getText() == null
                || mInputEmail.getText().toString() == null
                || mInputEmail.getText().toString().isEmpty();

    }

    private boolean isEmptyPassword() {
        return mInputPassword.getText() == null
                || mInputPassword.getText().toString() == null
                || mInputPassword.getText().toString().isEmpty();

    }

    private boolean isEmptyName() {
        return mInputName.getText() == null
                || mInputName.getText().toString() == null
                || mInputName.getText().toString().isEmpty();

    }

    private boolean isEmptyAge() {
        return mInputAge.getText() == null
                || mInputAge.getText().toString() == null
                || mInputAge.getText().toString().isEmpty();

    }

    private boolean isEmptyUsername() {
        return mInputUsername.getText() == null
                || mInputUsername.getText().toString() == null
                || mInputUsername.getText().toString().isEmpty();

    }


}

