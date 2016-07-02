package com.comlu.foodnepal.easyreadmvp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.comlu.foodnepal.easyreadmvp.Requests.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends ActionBarActivity {

    public static final String DEFAULT = "N/A";
    public static String USER_LOGGED_IN = "0";

    private RelativeLayout mRoot;
    private TextInputLayout mEmailLayout;
    private TextInputLayout mPasswordLayout;
    private EditText mInputEmail;
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
        setContentView(R.layout.activity_login);


        //for firstScreen
        SharedPreferences sharedPreferences = getSharedPreferences("ShaPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean firstTime = sharedPreferences.getBoolean("first", true);
        if (firstTime) {
            editor.putBoolean("first", false);
            editor.commit();
            Intent intent = new Intent(LoginActivity.this, FirstScreen.class);
            startActivity(intent);
        } else {
           // Toast.makeText(LoginActivity.this, "Else executed!", Toast.LENGTH_LONG).show();

        }        //finished




        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        Button bLogin = (Button) findViewById(R.id.bLogin);
        Button bRefister = (Button) findViewById(R.id.bRegister);

        Button bSkip = (Button) findViewById(R.id.bSkip);
        bSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                 intent.putExtra("user_logged_in",USER_LOGGED_IN);
                 startActivity(intent);

            }
        });

        //initialization

        mRoot = (RelativeLayout) findViewById(R.id.root_relative_layout);
        mEmailLayout = (TextInputLayout) findViewById(R.id.email_layout);
        mPasswordLayout = (TextInputLayout) findViewById(R.id.password_layout);
        mInputEmail = (EditText) findViewById(R.id.etUsername);
        mInputPassword = (EditText) findViewById(R.id.etPassword);

        //init finished


        //sharedPreferences Object
        SharedPreferences preferences = getSharedPreferences("userData", Context.MODE_PRIVATE);

        //loading data
        String username = preferences.getString("username", DEFAULT);
        String password = preferences.getString("password", DEFAULT);

        if (username.equals(DEFAULT) || password.equals(DEFAULT)) {
            //Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(getApplicationContext(), "Data loaded Successfully", Toast.LENGTH_SHORT).show();
            etUsername.setText(username);
            etPassword.setText(password);
        }


        bRefister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isEmptyEmail = isEmptyEmail();
                boolean isEmptyPassword = isEmptyPassword();
                if (isEmptyEmail && isEmptyPassword) {
                    Snackbar.make(mRoot, "One Or More Fields Are Blank", Snackbar.LENGTH_SHORT)
                            .setAction(getString(R.string.text_dismiss), mSnackBarClickListener)
                            .show();
                } else if (isEmptyEmail && !isEmptyPassword) {
                    mEmailLayout.setError("Email Cannot Be Empty");
                    mPasswordLayout.setError(null);
                } else if (!isEmptyEmail && isEmptyPassword) {
                    mPasswordLayout.setError("Password Cannot Be Empty");
                    mEmailLayout.setError(null);
                } else {
                    //All Good Here

                    //same as in registerRequest class but loginRequest is bit complex
                    final String username = etUsername.getText().toString();
                    final String password = etPassword.getText().toString();

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    //Toast.makeText(getApplicationContext(), "Response obtained as success", Toast.LENGTH_LONG).show();
                                    //getting name and age response
                                    String name = jsonResponse.getString("name");
                                    String email = jsonResponse.getString("email");
                                    int age = jsonResponse.getInt("age");

                                    //passing these information to userAreaActivity
                                    Intent intent = new Intent(LoginActivity.this, UserAreaActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("email", email);
                                    intent.putExtra("age", age);
                                    intent.putExtra("username", username);
                                    USER_LOGGED_IN= "1";
                                    intent.putExtra(USER_LOGGED_IN, USER_LOGGED_IN);

                                    LoginActivity.this.startActivity(intent);


                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("Login failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                    //adding it to the login queue
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);

                    //shared preferences
                    SharedPreferences preferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    //committing changes
                    editor.commit();
                    // Toast.makeText(getApplicationContext(), "Data Saved!", Toast.LENGTH_SHORT).show();
                    // KEY=true;


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

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure? ")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("no", null).show();
    }

}

