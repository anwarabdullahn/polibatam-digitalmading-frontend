package com.anwarabdullahn.polibatamdigitalmading.Activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anwarabdullahn.polibatamdigitalmading.API.API;
import com.anwarabdullahn.polibatamdigitalmading.API.APICallback;
import com.anwarabdullahn.polibatamdigitalmading.API.APIError;
import com.anwarabdullahn.polibatamdigitalmading.Activity.Adapter.byJadwalEventRecyclerVAdapter;
import com.anwarabdullahn.polibatamdigitalmading.Activity.Utility.LoadingHelper;
import com.anwarabdullahn.polibatamdigitalmading.Model.ListEvent;
import com.anwarabdullahn.polibatamdigitalmading.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Calendar;

public class JadwalActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    TextView dateTxt;
    String date;

    private static final String TAG = "JadwalActivity";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 100;
    private static final int DATASET_COUNT = 10; // menampilkan data sebanyak value

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView mRecyclerView;
    protected byJadwalEventRecyclerVAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    RelativeLayout relativeLayout;

    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setCurrentScreen(this, "Jadwal", this.getClass().getSimpleName());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mToolbar.setTitle(mTitle.getText());
        mTitle.setText("JADWAL ACARA");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dateTxt = (TextView) findViewById(R.id.dateTxt);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        relativeLayout = (RelativeLayout) findViewById(R.id.baseTanggal);
        mCurrentLayoutManagerType = JadwalActivity.LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        TextView text = (TextView) findViewById(R.id.text);

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (JadwalActivity.LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        Calendar c = Calendar.getInstance();
        int thisyear = c.get(Calendar.YEAR);
        int thismonth = c.get(Calendar.MONTH);
        int thisday = c.get(Calendar.DAY_OF_MONTH);
        String thisdate = thisyear + "-" + (thismonth + 1) + "-" + thisday;

        LoadingHelper.loadingShow(JadwalActivity.this);
        API.service().getEventby(thisdate).enqueue(new APICallback<ListEvent>() {
            @Override
            protected void onSuccess(ListEvent listEvent) {
                LoadingHelper.loadingDismiss();
                mAdapter = new byJadwalEventRecyclerVAdapter(listEvent.getEventList());
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            protected void onError(APIError error) {
                LoadingHelper.loadingDismiss();
                Toast.makeText(JadwalActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        dateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                final int _year = calendar.get(Calendar.YEAR);
                int _month = calendar.get(Calendar.MONTH);
                int _day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(JadwalActivity.this, R.style.AlertDialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateTxt.setText(dayOfMonth + " : " + (month + 1) + " : " + year);
                        date = _year + "-" + (month + 1) + "-" + dayOfMonth;

                        LoadingHelper.loadingShow(JadwalActivity.this);
                        API.service().getEventby(date).enqueue(new APICallback<ListEvent>() {
                            @Override
                            protected void onSuccess(ListEvent listEvent) {
                                LoadingHelper.loadingDismiss();
                                mAdapter = new byJadwalEventRecyclerVAdapter(listEvent.getEventList());
                                mRecyclerView.setAdapter(mAdapter);
                            }

                            @Override
                            protected void onError(APIError error) {
                                LoadingHelper.loadingDismiss();
                                Toast.makeText(JadwalActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }, _year, _month, _day);
                datePickerDialog.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);

    }

    public void setRecyclerViewLayoutManager(JadwalActivity.LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
                mCurrentLayoutManagerType = JadwalActivity.LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(this);
                mCurrentLayoutManagerType = JadwalActivity.LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(this);
                mCurrentLayoutManagerType = JadwalActivity.LayoutManagerType.LINEAR_LAYOUT_MANAGER;
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
