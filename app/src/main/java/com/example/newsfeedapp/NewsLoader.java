package com.example.newsfeedapp;

import android.content.Context;
import android.util.Log;

import android.content.AsyncTaskLoader;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    String url;

    public NewsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        OkHttpClient client = new OkHttpClient();
        String Res;
        ArrayList<News> newsArrayList = new ArrayList<>();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            Res = response.body().string();
            try {
                JSONObject myObject = new JSONObject(Res);
                JSONObject object = myObject.getJSONObject("response");
                JSONArray jsonArray = object.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    StringBuilder author = new StringBuilder("Written by: ");
                    JSONArray tagsArray = jsonArray.getJSONObject(i).getJSONArray("tags");
                    if (tagsArray.length() != 0) {
                        author.append(tagsArray.getJSONObject(0).get("firstName").toString());
                        author.append(" ");
                        author.append(tagsArray.getJSONObject(0).get("lastName").toString());
                    }
                    newsArrayList.add(new News(jsonArray.getJSONObject(i).getString("webTitle")
                            , jsonArray.getJSONObject(i).getString("webUrl")
                            , jsonArray.getJSONObject(i).getString("sectionName")
                            , jsonArray.getJSONObject(i).getString("webPublicationDate")
                            , author.toString()));
                }
            } catch (Exception e) {
                Log.e("myLoader", Objects.requireNonNull(e.getMessage()));
            }
        } catch (IOException e) {
            Log.e("myLoader", Objects.requireNonNull(e.getMessage()));
        }
        return newsArrayList;
    }
}
