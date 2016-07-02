package com.comlu.foodnepal.easyreadmvp.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://foodnepal.comlu.com/Register.php";
    private Map<String, String> params;


    //constructor
    public RegisterRequest(String name, String username, int age, String password, String email, Response.Listener<String> listener) {

        super(Method.POST, REGISTER_REQUEST_URL, listener, null); // instead of null, user error listener as parameter later
        params = new HashMap<>();
        params.put("name", name);
        params.put("username", username);
        params.put("password", password);
        params.put("age", age + ""); //adding the extra string to convert it from integer to a string
        params.put("email", email);
    }

    //when request is executed, volley will call getParams, gets the data
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
