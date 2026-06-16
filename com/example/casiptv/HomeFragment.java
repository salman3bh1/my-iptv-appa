package com.example.casiptv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rvContinueWatching, rvBeinChannels, rvPopularMovies, rvPopularSeries;
    private TextView titleContinue;

    private ChannelsAdapter continueAdapter;
    private ChannelsAdapter beinAdapter;
    private MoviesAdapter moviesAdapter;
    private MoviesAdapter seriesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvContinueWatching = view.findViewById(R.id.rvContinueWatching);
        rvBeinChannels = view.findViewById(R.id.rvBeinChannels);
        rvPopularMovies = view.findViewById(R.id.rvPopularMovies);
        rvPopularSeries = view.findViewById(R.id.rvPopularSeries);
        titleContinue = view.findViewById(R.id.titleContinue);

        setupHorizontalLayout(rvContinueWatching);
        setupHorizontalLayout(rvBeinChannels);
        setupHorizontalLayout(rvPopularMovies);
        setupHorizontalLayout(rvPopularSeries);

        refreshData();

        return view;
    }

    public void refreshData() {
        if (getContext() == null) return;

        // 1. القنوات الأخيرة (Recent Channels)
        List<ChannelModel> recents = MyIPTVManager.getInstance().getRecentChannels();
        if (recents == null || recents.isEmpty()) {
            if (titleContinue != null) titleContinue.setVisibility(View.GONE);
            if (rvContinueWatching != null) rvContinueWatching.setVisibility(View.GONE);
        } else {
            if (titleContinue != null) titleContinue.setVisibility(View.VISIBLE);
            if (rvContinueWatching != null) rvContinueWatching.setVisibility(View.VISIBLE);
            List<ChannelModel> safeRecents = (recents.size() > 5) ? new ArrayList<>(recents.subList(0, 5)) : recents;
            continueAdapter = new ChannelsAdapter(getContext(), safeRecents);
            rvContinueWatching.setAdapter(continueAdapter);
        }

        // 2. تخصيص قائمة Popular Channels (5 قنوات beIN ثم 5 FOX ثم عشوائي) - كود آمن بدون أخطاء ميثود
        List<ChannelModel> allLiveChannels = MyIPTVManager.getInstance().getLiveChannels();
        List<ChannelModel> finalPopularList = new ArrayList<>();

        if (allLiveChannels != null && !allLiveChannels.isEmpty()) {
            List<ChannelModel> beinTemp = new ArrayList<>();
            List<ChannelModel> foxTemp = new ArrayList<>();
            List<ChannelModel> remainingChannels = new ArrayList<>();

            for (ChannelModel channel : allLiveChannels) {
                if (channel == null) continue;

                // الاعتماد على الاسم فقط لتفادي أخطاء الموديل المتغيرة
                String name = channel.getName() != null ? channel.getName().toLowerCase() : "";

                if (name.contains("bein")) {
                    beinTemp.add(channel);
                } else if (name.contains("fox")) {
                    foxTemp.add(channel);
                } else {
                    remainingChannels.add(channel);
                }
            }

            // أخذ أول 5 قنوات beIN
            int beinCount = Math.min(beinTemp.size(), 5);
            for (int i = 0; i < beinCount; i++) {
                finalPopularList.add(beinTemp.get(i));
            }

            // أخذ أول 5 قنوات FOX
            int foxCount = Math.min(foxTemp.size(), 5);
            for (int i = 0; i < foxCount; i++) {
                finalPopularList.add(foxTemp.get(i));
            }

            // خلط باقي القنوات لضمان العشوائية وتعبئة القائمة
            Collections.shuffle(remainingChannels);

            // إكمال القائمة حتى 25 قناة لتعبئة الواجهة بالكامل
            int desiredTotal = 25;
            for (ChannelModel remaining : remainingChannels) {
                if (finalPopularList.size() >= desiredTotal) break;
                finalPopularList.add(remaining);
            }
        }

        // ربط القائمة المصفّحة بالـ Adapter الخاص بك
        beinAdapter = new ChannelsAdapter(getContext(), finalPopularList);
        rvBeinChannels.setAdapter(beinAdapter);

        // 3. الأفلام (Popular Movies)
        List<MovieModel> moviesList = MyIPTVManager.getInstance().getMoviesList();
        if (moviesList != null) {
            List<MovieModel> safeMovies = (moviesList.size() > 20) ? new ArrayList<>(moviesList.subList(0, 20)) : moviesList;
            moviesAdapter = new MoviesAdapter(getContext(), safeMovies);
            rvPopularMovies.setAdapter(moviesAdapter);
        }

        // 4. المسلسلات (Popular Series)
        List<MovieModel> seriesList = MyIPTVManager.getInstance().getSeriesList();
        if (seriesList != null) {
            List<MovieModel> safeSeries = (seriesList.size() > 20) ? new ArrayList<>(seriesList.subList(0, 20)) : seriesList;
            seriesAdapter = new MoviesAdapter(getContext(), safeSeries);
            rvPopularSeries.setAdapter(seriesAdapter);
        }
    }

    public void notifyAdapters() {
        if (continueAdapter != null) continueAdapter.notifyDataSetChanged();
        if (beinAdapter != null) beinAdapter.notifyDataSetChanged();
        if (moviesAdapter != null) moviesAdapter.notifyDataSetChanged();
        if (seriesAdapter != null) seriesAdapter.notifyDataSetChanged();
    }

    private void setupHorizontalLayout(RecyclerView rv) {
        if (rv != null) {
            rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        }
    }
}