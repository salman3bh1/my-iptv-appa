package com.example.casiptv;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class MyIPTVManager {
    private static MyIPTVManager instance;
    private List<ChannelModel> liveChannels = new ArrayList<>();
    private List<MovieModel> moviesList = new ArrayList<>();
    private List<MovieModel> seriesList = new ArrayList<>();
    private List<ChannelModel> recentChannels = new ArrayList<>();

    private MyIPTVManager() {}

    public static synchronized MyIPTVManager getInstance() {
        if (instance == null) {
            instance = new MyIPTVManager();
        }
        return instance;
    }

    public interface IPTVCallback {
        void onDataLoaded();
    }

    public void loadAllIPTVData(Context context, String url, String user, String pass, IPTVCallback callback) {
        // Here you should implement the networking logic (Volley or Retrofit)
        // to fetch data from the IPTV server.
        if (callback != null) {
            callback.onDataLoaded();
        }
    }

    public List<ChannelModel> getLiveChannels() {
        return liveChannels;
    }

    public List<MovieModel> getMoviesList() {
        return moviesList;
    }

    public List<MovieModel> getSeriesList() {
        return seriesList;
    }

    public List<ChannelModel> getRecentChannels() {
        return recentChannels;
    }

    public void addChannelToRecent(Context context, ChannelModel channel) {
        if (channel == null) return;
        
        // Remove if it already exists to move it to the top
        for (int i = 0; i < recentChannels.size(); i++) {
            if (recentChannels.get(i).getStreamId() == channel.getStreamId()) {
                recentChannels.remove(i);
                break;
            }
        }
        
        recentChannels.add(0, channel);
        
        // Keep only the last 20 recent channels
        if (recentChannels.size() > 20) {
            recentChannels.remove(recentChannels.size() - 1);
        }
    }
}
