package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] myDataset = {"abc", "def", "ghi"};
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        queue = Volley.newRequestQueue(this);
        getNews();
    }


    /*       "abc"             -> String
     *       "{"abc":"abc"}"   -> json String
     *       JSON String 형태 그 자체는 지지고 볶기 어려우므로 JSON 오브젝트 형태로 바꾸어야 쉽다.
     */

    public void getNews() {
        String url = "http://newsapi.org/v2/top-headlines?country=kr&apiKey=b748923b494d4b41ba75e2d764be51e0";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("NEWS", response);

                        try {
                            JSONObject jsonObj = new JSONObject(response);                      //문자열을 JSON형태로 찾아가서 빼오는과정

                            JSONArray arrayArticles = jsonObj.getJSONArray("articles");

                            List<NewsData> news = new ArrayList<>();

                            for (int i = 0, j = arrayArticles.length(); i < j ; i++) {
                                JSONObject obj = arrayArticles.getJSONObject(i);

                                //response ->> NewsData Class 분류
                                NewsData newsData = new NewsData();
                                newsData.setTitle(obj.getString("title"));
                                newsData.setUrlToImage(obj.getString("urlToImage"));
                                newsData.setContent(obj.getString("content"));

                                news.add(newsData);
                            }


                            mAdapter = new MyAdapter(news,MainActivity.this);
                            mRecyclerView.setAdapter(mAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);           // 정상적으로 queue에 add가 되면 onResponse(String response)가 호출된다
    }


}