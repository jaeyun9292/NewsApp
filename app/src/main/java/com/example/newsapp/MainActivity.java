package com.example.newsapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] myDataset = {"abc", "def", "ghi"};
    RequestQueue queue;

    private String API_KEY = "b748923b494d4b41ba75e2d764be51e0";

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
        String url = "https://newsapi.org/v2/everything?q=apple&from=2023-04-06&to=2023-04-06&sortBy=popularity&apiKey=" + API_KEY;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "onResponse: " + response);

                        try {
                            JSONObject jsonObj = new JSONObject(response);                      //문자열을 JSON형태로 찾아가서 빼오는과정

                            JSONArray arrayArticles = jsonObj.getJSONArray("articles");

                            List<NewsData> news = new ArrayList<>();

                            for (int i = 0, j = arrayArticles.length(); i < j; i++) {
                                JSONObject obj = arrayArticles.getJSONObject(i);

                                //response ->> NewsData Class 분류
                                NewsData newsData = new NewsData();
                                newsData.setTitle(obj.getString("title"));
                                newsData.setUrlToImage(obj.getString("urlToImage"));
                                newsData.setContent(obj.getString("description"));

                                news.add(newsData);
                            }


                            mAdapter = new MyAdapter(news, MainActivity.this);
                            mRecyclerView.setAdapter(mAdapter);


                        } catch (JSONException e) {
                            Log.e(TAG, "onError: " + e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("User-Agent", "Mozilla/5.0");
                return headers;
            }
            // HTTP 통신 요청 시에는 클라이언트 측에서 서버에 전달할 헤더 정보를 추가할 수 있습니다.
            // 이때 User-Agent 헤더는 클라이언트가 어떤 종류의 브라우저나 애플리케이션에서 요청을 보내는지를 식별하는 정보입니다.
            // 즉, 서버에서는 해당 User-Agent 정보를 통해 요청을 보낸 클라이언트가 어떤 종류의 디바이스나 브라우저에서 접근하는지를 확인할 수 있습니다.
            // 따라서, 위 코드는 User-Agent 헤더에 "Mozilla/5.0" 값을 추가하여, 해당 요청이 웹 브라우저에서 보내는 것처럼 인식되도록 하는 코드입니다.
            // 이를 통해 서버에서 해당 요청을 더욱 정확하게 처리할 수 있습니다.

        };

        queue.add(stringRequest);           // 정상적으로 queue에 add가 되면 onResponse(String response)가 호출된다
    }


}