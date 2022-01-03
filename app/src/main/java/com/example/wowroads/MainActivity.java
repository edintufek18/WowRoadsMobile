package com.example.wowroads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private TextView hotel;
    private TextView place;
    private String url = "https://wowroads2.azurewebsites.net/api/HotelApi";
    private String url1 = "https://wowroads2.azurewebsites.net/api/PlaceApi"    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        hotel = (TextView) findViewById(R.id.hotel);
        place = (TextView) findViewById(R.id.place);
    }
    public  void showHotels(View view) {
        if (view != null) {
            JsonArrayRequest request = new JsonArrayRequest(url, jsonArrayListener, errorListener);
            requestQueue.add(request);
        }

    }
    public void showPlaces(View view){
        if (view != null) {
            JsonArrayRequest request = new JsonArrayRequest(url1, jsonArrayListener1, errorListener1);
            requestQueue.add(request);
        }
    }
    // prv listener e za hoteli
    private Response.Listener<JSONArray> jsonArrayListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response){
            ArrayList<String> data = new ArrayList<>();

            for (int i = 0; i < response.length(); i++){
                try {
                    JSONObject object =response.getJSONObject(i);
                    String name = object.getString("name");
                    String stars = object.getString("stars");


                    data.add(name + " " + stars );

                } catch (JSONException e){
                    e.printStackTrace();
                    return;

                }
            }

            hotel.setText("");


            for (String row: data){
                String currentText = hotel.getText().toString();
                hotel.setText(currentText + "\n\n" + row);
            }

        }

    };
    // vtor listener e za mesta
    private Response.Listener<JSONArray> jsonArrayListener1 = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response){
            ArrayList<String> data = new ArrayList<>();

            for (int i = 0; i < response.length(); i++){
                try {
                    JSONObject object =response.getJSONObject(i);
                    String name = object.getString("placeName");


                    data.add(name);

                } catch (JSONException e){
                    e.printStackTrace();
                    return;

                }
            }

            place.setText("");


            for (String row: data){
                String currentText = place.getText().toString();
                place.setText(currentText + "\n\n" + row);
            }

        }

    };
    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("REST error", error.getMessage());
        }
    };
    private Response.ErrorListener errorListener1 = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("REST error", error.getMessage());
        }
    };
    // 2 del
    public static final String EXTRA_MESSAGE = "com.example.wowroads.MESSAGE";
    public void addActivity2 (View view) {
        Intent intent = new Intent(this,addActivity2.class);
        String message = "Add customers to queue";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}