package com.example.simplerssreader;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;
        import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import java.util.ArrayList;
        import java.util.List;

public class MainActivity extends AppCompatActivity {

    //MODIFICHETTA

    private RecyclerView mRecyclerView;
    private EditText mEditText;
    private Button mFetchFeedButton;
    private SwipeRefreshLayout mSwipeLayout;
    private TextView mFeedTitleTextView;
    private TextView mFeedLinkTextView;
    private TextView mFeedDescriptionTextView;

    private List<Article> mFeedModelList = new ArrayList<Article>();
    private Controller mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mEditText = (EditText) findViewById(R.id.rssFeedEditText);
        mFetchFeedButton = (Button) findViewById(R.id.fetchFeedButton);
        mSwipeLayout = (SwipeRefreshLayout) findViewById((R.id.swipeRefreshLayout));
        mFeedTitleTextView = (TextView) findViewById(R.id.feedTitle);
        mFeedLinkTextView = (TextView) findViewById((R.id.feedLink));
        mFeedDescriptionTextView = (TextView) findViewById((R.id.feedDescription));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new RssFeedListAdapter2(mFeedModelList));

        mController = new Controller();
        mController.setContext(MainActivity.this);
        mFetchFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mController.start(mEditText.getText().toString());
            }
        });
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mController.start(mEditText.getText().toString());
            }
        });


    }

    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("************sono nell'onReceive**************");

            mSwipeLayout.setRefreshing(true);
            RSSFeed feed = (RSSFeed) intent.getSerializableExtra("RssFeed");
            mFeedModelList = feed.getArticleList();

            for (Article article : mFeedModelList) {

                System.out.println("Title: " + article.getTitle() + " Link: " + article.getLink());
                mFeedTitleTextView.setText("Feed Title: " + article.getTitle());
                mFeedDescriptionTextView.setText("Feed Description: " + article.getDescription());
                mFeedLinkTextView.setText("Feed Link: " + article.getLink());
            }
            // Fill RecyclerView
            mRecyclerView.setAdapter(new RssFeedListAdapter2(mFeedModelList));
            mSwipeLayout.setRefreshing(false);
        }
    };

    @Override
    protected void onStart () {
        super.onStart();
        System.out.println("************sono nell'onStart**************");
        IntentFilter filter = new IntentFilter("com.example.AVVIA_CONTROLLER");
        registerReceiver(br, filter);
    }

    @Override
    protected void onStop () {
        super.onStop();
        unregisterReceiver(br);

    }
}