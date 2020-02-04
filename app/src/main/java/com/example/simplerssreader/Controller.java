package com.example.simplerssreader;

import android.content.Intent;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class Controller implements Callback<RSSFeed>{

    private String urlLink;
    MainActivity context;

    public void start(String urlLink) {

        this.urlLink = urlLink;

        if (!urlLink.startsWith("http://") && !urlLink.startsWith("https://"))
            urlLink = "https://" + urlLink;

        int i = urlLink.lastIndexOf("/");
        String base_url = urlLink.substring(0, i + 1);
        String resto_url = urlLink.substring(i + 1);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(base_url).addConverterFactory(SimpleXmlConverterFactory.create()).build();

        RSSAPI rssAPI = retrofit.create(RSSAPI.class);

        Call<RSSFeed> call = rssAPI.loadRSSFeed(resto_url);
        call.enqueue(this);
    }


    @Override
    public void onResponse(Call<RSSFeed> call, Response<RSSFeed> response) {
        if (response.isSuccessful()) {
            RSSFeed rss = response.body();
            System.out.println("Channel title: " + rss.getChannelTitle());
            Intent intent = new Intent("com.example.AVVIA_CONTROLLER");
            intent.putExtra("RssFeed", rss);
            context.sendBroadcast(intent);
        }
        else {
            Toast.makeText(context, "Enter a valid Rss feed url", Toast.LENGTH_LONG).show();
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<RSSFeed> call, Throwable t) {
        t.printStackTrace();
    }


    public void setContext(MainActivity context) {
        this.context = context;
    }

}