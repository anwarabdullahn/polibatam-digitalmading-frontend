package com.anwarabdullahn.polibatamdigitalmading.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anwarabdullahn.polibatamdigitalmading.API.API;
import com.anwarabdullahn.polibatamdigitalmading.API.APICallback;
import com.anwarabdullahn.polibatamdigitalmading.API.APIError;
import com.anwarabdullahn.polibatamdigitalmading.Activity.Adapter.BeritaRecyclerViewAdapter;
import com.anwarabdullahn.polibatamdigitalmading.Activity.Utility.LoadingHelper;
import com.anwarabdullahn.polibatamdigitalmading.Model.ListBerita;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.anwarabdullahn.polibatamdigitalmading.R;
import com.google.firebase.analytics.FirebaseAnalytics;

public class ByCategoryActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    String categoryId,categoryName,categoryBg;
    ImageView backgroundView;

    private static final String TAG = "ByCategoryActivity";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 15;
    private static final int DATASET_COUNT = 10; // menampilkan data sebanyak value

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }
    protected ByCategoryActivity.LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView mRecyclerView;
    protected BeritaRecyclerViewAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    SwipeRefreshLayout swipeRefreshLayout;
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bykategori);

        categoryName = getIntent().getExtras().getString("categoryName");

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setCurrentScreen(this, categoryName, this.getClass().getSimpleName());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mToolbar.setTitle(mTitle.getText());

        categoryBg = getIntent().getExtras().getString("categoryBg");
        backgroundView = (ImageView) findViewById(R.id.backgroundView);

        RequestOptions options = new RequestOptions();
        options.centerCrop();

        Glide.with(this).load(categoryBg).apply(options).into(backgroundView);

        mTitle.setText(categoryName);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);

        mCurrentLayoutManagerType = ByCategoryActivity.LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (ByCategoryActivity.LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLo);
        LoadingHelper.loadingShow(this);
        content();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                content();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },2000);
            }
        });

    }

    public void content(){
        categoryId = getIntent().getExtras().getString("categoryId");
        Log.d("categori" , categoryId);

        API.service().getKategoriBy(categoryId).enqueue(new APICallback<ListBerita>() {
            @Override
            protected void onSuccess(ListBerita listBerita) {
                LoadingHelper.loadingDismiss();

                mAdapter = new BeritaRecyclerViewAdapter(listBerita.getBeritaList());
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            protected void onError(APIError error) {
                LoadingHelper.loadingDismiss();
                Toast.makeText(ByCategoryActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);

    }

    public void setRecyclerViewLayoutManager(ByCategoryActivity.LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
                mCurrentLayoutManagerType = ByCategoryActivity.LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(this);
                mCurrentLayoutManagerType = ByCategoryActivity.LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(this);
                mCurrentLayoutManagerType = ByCategoryActivity.LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }
}
