package com.pb.app.bookmarketingE.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pb.app.bookmarketingE.R;
import com.pb.app.bookmarketingE.data.favorite.FavoriteEntity;
import com.pb.app.bookmarketingE.ui.adapters.AdapterContent;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    private FavoriteViewModel favoriteViewModel;
    private AdapterContent adapter;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoriteViewModel =
                new ViewModelProvider(this).get(FavoriteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = root.findViewById(R.id.favorite_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<String> items = (ArrayList<String>) FavoriteEntity.getInstance().getFavorites();
        ArrayList<Fragment> fragments = FavoriteEntity.getInstance().getFavoritesFragments();
        adapter = new AdapterContent(items, getFragmentManager(), fragments);
        recyclerView.setAdapter(adapter);
        return root;
    }
}