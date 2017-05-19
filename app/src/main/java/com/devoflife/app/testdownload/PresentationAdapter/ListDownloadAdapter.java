package com.devoflife.app.testdownload.PresentationAdapter;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.devoflife.app.testdownload.Entity.DownloadInfo;
import com.devoflife.app.testdownload.R;
import com.tonyodev.fetch.Fetch;
import com.tonyodev.fetch.listener.FetchListener;
import com.tonyodev.fetch.request.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by n on 14/12/2016.
 */

public class ListDownloadAdapter extends RecyclerView.Adapter<ListDownloadAdapter.MyViewHolder> {
    private Fetch mFetchDownloader;
    private List<DownloadInfo> mListDownloadInfo;

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mIcon;
        TextView mFileName;
        RoundCornerProgressBar mProgressBar;
        TextView mState;

        MyViewHolder(View view) {
            super(view);
            mIcon = (ImageView) view.findViewById(R.id.download_item_icon);
            mFileName = (TextView) view.findViewById(R.id.download_item_file_name);
            mProgressBar = (RoundCornerProgressBar) view.findViewById(R.id.download_item_progress_bar);
            mState = (TextView) view.findViewById(R.id.download_item_state_text);
        }
    }

    public ListDownloadAdapter(Context _context, Fetch _fetchDownloader) {
        mFetchDownloader= _fetchDownloader;
        DownloadInfo newDownload= new DownloadInfo(
                -1
                , "https://www.w3schools.com/html/pic_mountain.jpg"
                , "pic_mountain.jpg"
                , Fetch.STATUS_NOT_QUEUED
                , 0
        );
        mListDownloadInfo= new ArrayList<>();
        mListDownloadInfo.add(newDownload);

        _fetchDownloader.addFetchListener(new FetchListener() {
            @Override
            public void onUpdate(long id, int status, int progress, long downloadedBytes, long fileSize, int error) {
                for(DownloadInfo toDownload : mListDownloadInfo){
                    if(toDownload.getDownloadId() == id) { // && status == Fetch.STATUS_DOWNLOADING
                        toDownload.setState(status);
                        if(status == Fetch.STATUS_DOWNLOADING) {
                            toDownload.setProgress(progress);
                        }
                    }
                }
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.main_list_download_item_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mFileName.setText(mListDownloadInfo.get(position).getFileName());
        mListDownloadInfo.get(position).setonInfoChangeListener(new DownloadInfo.OnInfoChangeListener() {
            @Override
            public void onStateChange(String stateString) {
                holder.mState.setText(stateString);
            }
            @Override
            public void onProgressChange(int progress) {
                holder.mProgressBar.setProgress(progress);
            }
        });
        holder.mIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadInfo toDownload= mListDownloadInfo.get(holder.getAdapterPosition());
                Request request = new Request(toDownload.getUrl()
                        , Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()
                        ,toDownload.getFileName());
                toDownload.setDownloadId(mFetchDownloader.enqueue(request));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListDownloadInfo.size();
    }

}