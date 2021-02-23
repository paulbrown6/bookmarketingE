package com.pb.app.bookmarketingE.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.pb.app.bookmarketingE.MainActivity;
import com.pb.app.bookmarketingE.R;
import com.pb.app.bookmarketingE.data.favorite.FavoriteEntity;

import java.util.List;

public class AdapterContent extends RecyclerView.Adapter<AdapterContent.ViewHolder> {

    private List<String> contents;
    private FragmentManager manager;
    private List<Fragment> fragments;

    public AdapterContent(List<String> contents, FragmentManager manager, List<Fragment> fragments) {
        this.contents = contents;
        this.manager = manager;
        this.fragments = fragments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_adapter, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String post = contents.get(position);
        holder.name.setText(post);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragments.get(position));
                transaction.addToBackStack(null).commit();
                MainActivity.getFavoriteButton().setVisibility(View.VISIBLE);
                MainActivity.getShareButton().setVisibility(View.VISIBLE);
                MainActivity.setCurrentFragment(post);
                MainActivity.OnFavorite(FavoriteEntity.getInstance().isFavorite(post));
                MainActivity.getActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                MainActivity.getActivity().getSupportActionBar().setDisplayShowTitleEnabled(false);
                MainActivity.getBackButton().setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (contents == null)
            return 0;
        return contents.size();
    }

    public void setItems(List<String> prod) {
        contents.clear();
        contents.addAll(prod);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_name);
        }
    }
}
