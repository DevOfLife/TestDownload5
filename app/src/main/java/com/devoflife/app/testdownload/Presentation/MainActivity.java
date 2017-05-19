package com.devoflife.app.testdownload.Presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.devoflife.app.testdownload.PresentationAdapter.ListDownloadAdapter;
import com.devoflife.app.testdownload.R;
import com.tonyodev.fetch.Fetch;

public class MainActivity extends AppCompatActivity {
    RecyclerView mDownloadList;
    Fetch mFetchDownloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mFetchDownloader= Fetch.getInstance(this);
        new Fetch.Settings(getApplicationContext())
                .setAllowedNetwork(Fetch.NETWORK_ALL)
                .enableLogging(true)
                .setConcurrentDownloadsLimit(2)
                .apply();

        mDownloadList= (RecyclerView) findViewById(R.id.main_list_download);
        mDownloadList.setLayoutManager(new LinearLayoutManager(getApplicationContext()
                , LinearLayoutManager.VERTICAL, false));
        ListDownloadAdapter mListDownloadAdapter = new ListDownloadAdapter(getApplicationContext()
                , mFetchDownloader);
        mDownloadList.setAdapter(mListDownloadAdapter);
    }

    @Override
    protected void onDestroy() {
        mFetchDownloader.release();
        super.onDestroy();
    }
}
