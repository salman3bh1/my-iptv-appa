package com.example.casiptv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeriesFragment extends Fragment {
    private LinearLayout layoutSeriesCategories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_series, container, false);
        layoutSeriesCategories = view.findViewById(R.id.layoutSeriesCategories);
        loadSeriesData();
        return view;
    }

    public void loadSeriesData() {
        List<MovieModel> allSeries = MyIPTVManager.getInstance().getSeriesList();
        if (allSeries == null || allSeries.isEmpty()) return;

        Map<String, List<MovieModel>> categoryMap = new HashMap<>();
        for (MovieModel series : allSeries) {
            String cat = series.getCategory();
            if (!categoryMap.containsKey(cat)) categoryMap.put(cat, new ArrayList<>());
            categoryMap.get(cat).add(series);
        }

        layoutSeriesCategories.removeAllViews();
        for (Map.Entry<String, List<MovieModel>> entry : categoryMap.entrySet()) {
            addCategorySection(entry.getKey(), entry.getValue());
        }
    }

    private void addCategorySection(String title, List<MovieModel> items) {
        View sectionView = getLayoutInflater().inflate(R.layout.item_category_section, null);
        ((TextView) sectionView.findViewById(R.id.tvCategoryTitle)).setText(title);
        RecyclerView rv = sectionView.findViewById(R.id.rvCategoryItems);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv.setAdapter(new MoviesAdapter(getContext(), items));
        layoutSeriesCategories.addView(sectionView);
    }
}