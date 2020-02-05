package com.example.simplerssreader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class RssFeedListAdapter extends RecyclerView.Adapter <RssFeedListAdapter.FeedModelViewHolder>{
    private List <Article> mRssFeedModels;

    public static class FeedModelViewHolder extends RecyclerView.ViewHolder{
        private View rssFeedView;

        public FeedModelViewHolder(View v){
            super(v);
            rssFeedView = v;
        }
    }

    /*public RssFeedListAdapter(List <Article> rssFeedModels){
        mRssFeedModels = rssFeedModels;
    }*/

    @Override
    public FeedModelViewHolder onCreateViewHolder (ViewGroup parent, int type){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rss_feed, parent, false);
        FeedModelViewHolder holder = new FeedModelViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder (FeedModelViewHolder holder, int position){
        final Article rssFeedModel = mRssFeedModels.get(position);
        ((TextView)holder.rssFeedView.findViewById(R.id.titleText)).setText((rssFeedModel.getTitle()));
        ((TextView)holder.rssFeedView.findViewById(R.id.descriptionText)).setText(rssFeedModel.getDescription());
        ((TextView)holder.rssFeedView.findViewById(R.id.linkText)).setText(rssFeedModel.getLink());
    }

    @Override
    public int getItemCount(){
        return mRssFeedModels.size();
    }
}
