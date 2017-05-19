package com.devoflife.app.testdownload.Entity;

import com.tonyodev.fetch.Fetch;

/**
 * Created by nam on 5/14/2017.
 */

public class DownloadInfo {
    private long downloadId;
    private String url;
    private String fileName;
    private int state;
    private int progress;

    public DownloadInfo(int downloadId, String url, String fileName, int state, int progress) {
        this.downloadId = downloadId;
        this.url = url;
        this.fileName = fileName;
        this.state = state;
        this.progress = progress;
    }

    public void setDownloadId(long downloadId) {
        this.downloadId = downloadId;
    }

    public long getDownloadId() {
        return downloadId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getUrl() {
        return url;
    }

    public int getState() {
        return state;
    }

    public String getStateString() {
        switch (state){
            case Fetch.STATUS_NOT_QUEUED:
                return "Waiting...";
            case Fetch.STATUS_QUEUED:
                return "Pending...";
            case Fetch.STATUS_PAUSED:
                return "Paused";
            case Fetch.STATUS_DOWNLOADING:
                return "Downloading";
            case Fetch.STATUS_DONE:
                return "Complete";
        }

        return "Pending...";
    }

    public void setState(int state) {
        this.state = state;
        if(mOnInfoChangeListener!= null){
            mOnInfoChangeListener.onStateChange(getStateString());
        }
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        if(mOnInfoChangeListener!= null){
            mOnInfoChangeListener.onProgressChange(progress);
        }
    }

    private OnInfoChangeListener mOnInfoChangeListener;
    public interface OnInfoChangeListener{
        void onStateChange(String stateString);
        void onProgressChange(int progress);
    }
    public void setonInfoChangeListener(OnInfoChangeListener _onInfoChangeListener){
        mOnInfoChangeListener= _onInfoChangeListener;
    }
}
