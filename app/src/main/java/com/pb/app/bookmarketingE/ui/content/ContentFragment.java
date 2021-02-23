package com.pb.app.bookmarketingE.ui.content;

import android.net.Uri;
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

public class ContentFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    private ContentViewModel contentViewModel;
    private AdapterContent adapter;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contentViewModel = new ViewModelProvider(this).get(ContentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_content, container, false);
        recyclerView = root.findViewById(R.id.content_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<String> items = new ArrayList<>();
        for(int i = 0; i < getResources().getStringArray(R.array.content_items).length; i++){
            items.add(getResources().getStringArray(R.array.content_items)[i]);
        }
        ArrayList<Fragment> fragments = FavoriteEntity.getInstance().getAllFragments();
        adapter = new AdapterContent(items, getFragmentManager(), fragments);
        recyclerView.setAdapter(adapter);
        return root;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}