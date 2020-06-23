package com.example.restapivideostreaming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    VideoAdapter adapter;
    List<Video> allvideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allvideos = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new VideoAdapter(allvideos,this);
        recyclerView.setAdapter(adapter);



        getJsonData();
    }

    private void getJsonData() {

        String URL = "https://raw.githubusercontent.com/bikashthapa01/myvideos-android-app/master/data.json";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray categories = response.getJSONArray("categories");
                    JSONObject categoriesdata = categories.getJSONObject(0);
                    JSONArray videos = categoriesdata.getJSONArray("videos");

                    for (int i = 0; i < videos.length(); i++){

                        JSONObject video = videos.getJSONObject(i);

                        Video v = new Video();

                        v.setTitle(video.getString("title"));
                        v.setDescription(video.getString("description"));
                        v.setAuthor(video.getString("subtitle"));
                        v.setImageurl(video.getString("thumb"));
                        JSONArray videourl = video.getJSONArray("sources");
                        v.setVideourl(videourl.getString(0));

                        allvideos.add(v);
                        adapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(objectRequest);
    }
}
