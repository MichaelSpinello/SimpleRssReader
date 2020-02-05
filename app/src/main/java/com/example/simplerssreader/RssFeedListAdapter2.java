package com.example.simplerssreader;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class RssFeedListAdapter2 extends RecyclerView.Adapter <RssFeedListAdapter2.FeedModelViewHolder>{
    private List <Article> mArticles;

    public static class FeedModelViewHolder extends RecyclerView.ViewHolder{
        private View rssFeedView;

        public FeedModelViewHolder(View v){
            super(v);
            rssFeedView = v;
        }
    }

    public RssFeedListAdapter2(List<Article> rssArticles){
        mArticles = rssArticles;
    }

    @Override
    public FeedModelViewHolder onCreateViewHolder (ViewGroup parent, int type){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rss_feed, parent, false);
        FeedModelViewHolder holder = new FeedModelViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder (FeedModelViewHolder holder, int position){
        final Article rssArticle = mArticles.get(position);
        ((TextView)holder.rssFeedView.findViewById(R.id.titleText)).setText((rssArticle.getTitle()));
        ((TextView)holder.rssFeedView.findViewById(R.id.descriptionText)).setText(rssArticle.getDescription());
        ((TextView)holder.rssFeedView.findViewById(R.id.linkText)).setText(rssArticle.getLink());
    }
    @Override
    public int getItemCount(){
        return mArticles.size();
    }
}
