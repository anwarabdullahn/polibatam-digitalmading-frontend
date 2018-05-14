package com.anwarabdullahn.polibatamdigitalmading.Activity.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anwarabdullahn.polibatamdigitalmading.API.API;
import com.anwarabdullahn.polibatamdigitalmading.API.APICallback;
import com.anwarabdullahn.polibatamdigitalmading.API.APIError;
import com.anwarabdullahn.polibatamdigitalmading.Activity.Adapter.byJadwalEventRecyclerVAdapter;
import com.anwarabdullahn.polibatamdigitalmading.Model.ListEvent;
import com.anwarabdullahn.polibatamdigitalmading.R;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    public static final String TITLE = "Acara";

    private static final String TAG = "AnnouncementFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 100;
    private static final int DATASET_COUNT = 10; // menampilkan data sebanyak value

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected EventFragment.LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView mRecyclerView;
    protected byJadwalEventRecyclerVAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    SwipeRefreshLayout swipeRefreshLayout;
    FirebaseAnalytics mFirebaseAnalytics;

    public static EventFragment newInstance() {

        return new EventFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_event, container, false);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this.getContext());
        mFirebaseAnalytics.setCurrentScreen(this.getActivity(), "Acara Home", this.getClass().getSimpleName());

        mRecyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(contentView.getContext());
        mCurrentLayoutManagerType = EventFragment.LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (EventFragment.LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        swipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.swipeLo);

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
                }, 2000);
            }
        });

        return contentView;


    }

    public void content(){
        API.service().getEvent().enqueue(new APICallback<ListEvent>() {
            @Override
            protected void onSuccess(ListEvent listEvent) {
                mAdapter = new byJadwalEventRecyclerVAdapter(listEvent.getEventList());
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            protected void onError(APIError error) {
                Toast.makeText(EventFragment.this.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setRecyclerViewLayoutManager(EventFragment.LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(EventFragment.this.getContext(), SPAN_COUNT);
                mCurrentLayoutManagerType = EventFragment.LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(EventFragment.this.getContext());
                mCurrentLayoutManagerType = EventFragment.LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(EventFragment.this.getContext());
                mCurrentLayoutManagerType = EventFragment.LayoutManagerType.LINEAR_LAYOUT_MANAGER;
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