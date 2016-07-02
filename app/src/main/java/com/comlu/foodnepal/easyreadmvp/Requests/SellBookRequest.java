package com.comlu.foodnepal.easyreadmvp.Requests;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SellBookRequest extends StringRequest {
    private static final String SELL_BOOKS_REQUEST_URL = "http://foodnepal.comlu.com/all_books_2.php";
    private Map<String, String> params;


    public SellBookRequest(String title, String author, int faculty, int year_part, int price, String book_condition,
                           Response.Listener<String> listener) {
        super(Method.POST, SELL_BOOKS_REQUEST_URL, listener, null); //create errorListener instead of null

        params = new HashMap<>();
        params.put("title", title);
        params.put("author", author);
        params.put("faculty", faculty + ""); //adding the extra string to convert it from integer to a string
        params.put("year_part", year_part + "");
        params.put("price", price + "");
        params.put("book_condition", book_condition);
    }

    //when request is executed, volley will call getParams, gets the data
    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
