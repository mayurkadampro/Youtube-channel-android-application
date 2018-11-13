package com.example.mayur.firstinterface;

import android.app.VoiceInteractor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JavaCodingYTList extends AppCompatActivity{

    private String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelid=UCeVmancWx92vTZ9IPYOKnKg&maxResults=3&key=AIzaSyD4a5qZCNfXIq7j0EcOB6pbonCY7eeEyFg";
    private ListView mlistView;
    private String API = "AIzaSyD4a5qZCNfXIq7j0EcOB6pbonCY7eeEyFg";
    private ArrayList<VideoDetails> videoDetailsArrayList;
    private VideoDetailsAdapter videoDetailsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_coding_ytlist);
        mlistView = (ListView)findViewById(R.id.listview);
        videoDetailsArrayList = new ArrayList<>();
        videoDetailsAdapter = new VideoDetailsAdapter(JavaCodingYTList.this,videoDetailsArrayList);

        displayvideos();
    }

    private void displayvideos() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("items");
                    for(int i=0; i<=jsonArray.length();i++)
                    {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject jsonObject2 = jsonObject1.getJSONObject("id");

                        JSONObject jsonObject4 = jsonObject1.getJSONObject("snippet");
                        JSONObject jsonObject5 = jsonObject4.getJSONObject("thumbnails");
                        JSONObject jsonObject6 = jsonObject5.getJSONObject("medium");

                        String Video_id = jsonObject2.getString("videoId");


                        VideoDetails videoDetails = new VideoDetails();
                        videoDetails.setVideo_id(Video_id);
                        videoDetails.setTitle(jsonObject4.getString("title"));
                        videoDetails.setDescription(jsonObject4.getString("description"));
                        videoDetails.setUrl(jsonObject6.getString("url"));


                        videoDetailsArrayList.add(videoDetails);
                    }

                    mlistView.setAdapter(videoDetailsAdapter);
                    videoDetailsAdapter.notifyDataSetChanged();



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        requestQueue.add(stringRequest);

    }


}
