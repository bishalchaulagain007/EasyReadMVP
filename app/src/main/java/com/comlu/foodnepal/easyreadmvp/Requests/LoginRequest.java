package com.comlu.foodnepal.easyreadmvp.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://foodnepal.comlu.com/Login.php";
    private Map<String, String> params;


    //constructor
    public LoginRequest(String username, String password, Response.Listener<String> listener) {

        super(Method.POST, LOGIN_REQUEST_URL, listener, null); // instead of null, user error listener as parameter later
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
    }

    //when request is executed, volley will call getParams, gets the data
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
