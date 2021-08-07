package com.example.newsfeedapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<News>>, mAdapter.ItemClicked {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<News> mNews;
    TextView noDataAvailableTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noDataAvailableTextView = findViewById(R.id.noDataTextView);

        boolean connected;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        connected = Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)).getState() == NetworkInfo.State.CONNECTED ||
                Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)).getState() == NetworkInfo.State.CONNECTED;

        if (!connected) {
            noDataAvailableTextView.setVisibility(View.VISIBLE);
            noDataAvailableTextView.setText(R.string.no_connection);
        }

        final int LOADER_ID = 1;
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int id, @Nullable Bundle args) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("content.guardianapis.com")
                .appendEncodedPath("search")
                .appendQueryParameter("show-tags", "contributor")
                .appendQueryParameter("q", "news")
                .appendQueryParameter("api-key", "3d62b05a-0294-499b-8290-e5b83885aa18");

        return new NewsLoader(this, builder.toString());
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<News>> loader, List<News> news) {
        if (news.size() == 0) {
            noDataAvailableTextView.setVisibility(View.VISIBLE);
            noDataAvailableTextView.setText(R.string.no_data);
        } else {
            mNews = news;
            recyclerView = findViewById(R.id.list);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new mAdapter(news, this);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<News>> loader) {

    }

    @Override
    public void onItemClicked(int index) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.web_prefix) + mNews.get(index).getWebUrl()));
        startActivity(intent);
    }

}
