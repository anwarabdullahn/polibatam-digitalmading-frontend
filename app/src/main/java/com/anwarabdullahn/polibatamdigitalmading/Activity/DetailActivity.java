package com.anwarabdullahn.polibatamdigitalmading.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anwarabdullahn.polibatamdigitalmading.Activity.Utility.CircleTransform;
import com.anwarabdullahn.polibatamdigitalmading.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    ImageView detailImage , authorImage;
    TextView detailTitle, detailDetail, detailDescription, auhorName,detailFile;
    String descriptionHolder;
//    String urlFile = "http://digitalmading.xyz/file";
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setCurrentScreen(this, "Kategori", this.getClass().getSimpleName());

        RequestOptions options = new RequestOptions();
        options.centerCrop();

        detailImage = (ImageView) findViewById(R.id.detailImage);
        detailTitle = (TextView) findViewById(R.id.detailTitle);
        detailDescription = (TextView) findViewById(R.id.detailDescription);
        authorImage = (ImageView) findViewById(R.id.authorImage);
        detailDetail = (TextView) findViewById(R.id.detailDetail);
        auhorName = (TextView) findViewById(R.id.authorName);
        detailFile = (TextView) findViewById(R.id.detailFile);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mToolbar.setTitle(mTitle.getText());
        mTitle.setText("DETAIL");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auhorName.setText(getIntent().getExtras().getString("AuthorName"));
        detailDetail.setText(getIntent().getExtras().getString("Detail"));

        Bundle file = getIntent().getExtras();
        String urlFile = file.getString("File");

        Log.d("UrlFile",urlFile);

        if(urlFile.equals("null")){
            detailFile.setVisibility(View.GONE);
        }else {
            detailFile.setVisibility(View.VISIBLE);
            detailDetail.setText("File");
        }

//        if (urlFile != null){
//            if (urlFile=="http://digitalmading.xyz/file"){
//                detailFile.setText("Tidak Ada File");
//                detailFile.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(DetailActivity.this, "Berita Ini Tidak Memiliki File", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }else if(urlFile!="http://digitalmading.xyz/file"){
//                detailFile.setText(getIntent().getExtras().getString("File"));
//                detailFile.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(DetailActivity.this, FileActivity.class);
//                        startActivity(intent);
//                    }
//                });
//            }
//        }else if (urlFile==null){
//            detailFile.setVisibility(View.GONE);
//        }

        descriptionHolder = getIntent().getExtras().getString("Description");
        detailDescription.setText(Html.fromHtml((getIntent().getExtras().getString("Description"))));
        detailTitle.setText(getIntent().getExtras().getString("Title"));
        Picasso.with(getBaseContext()).load(getIntent().getExtras().getString("AuthorImage")).centerCrop().resize(100, 100).placeholder(R.drawable.ic_profile).transform(new CircleTransform()).into(authorImage);
        Glide.with(this).load(getIntent().getExtras().getString("Image")).apply(options).into(detailImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.last_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.selesai:
                Intent intent = new Intent(DetailActivity.this, HomeActivity.class);
                startActivity(intent);
                return true;
            default:
                super.onBackPressed();
                return super.onOptionsItemSelected(item);
        }

    }
}
