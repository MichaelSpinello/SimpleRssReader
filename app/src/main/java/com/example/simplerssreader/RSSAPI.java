package com.example.simplerssreader;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RSSAPI {
    @GET
    Call<RSSFeed> loadRSSFeed(@Url String url);
}