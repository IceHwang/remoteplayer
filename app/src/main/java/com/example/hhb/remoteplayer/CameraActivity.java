package com.example.hhb.remoteplayer;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class CameraActivity extends AppCompatActivity {

    public static final String CAMERA_NAME ="camera_name";
    public static final String CAMERA_IMAGE_ID ="camera_image_id";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Intent intent =getIntent();
        String cameraName=intent.getStringExtra(CAMERA_NAME);
        int cameraImageId= intent.getIntExtra(CAMERA_IMAGE_ID,0);
        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        ImageView cameraImageView=(ImageView)findViewById(R.id.camera_image_view);
        TextView cameraContentText=(TextView)findViewById(R.id.camera_content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        collapsingToolbarLayout.setTitle(cameraName);
        Glide.with(this).load(cameraImageId).into(cameraImageView);
        String cameraContent = generateCameraContent(cameraName);
        cameraContentText.setText(cameraContent);
    }

    private String generateCameraContent(String cameraName) {
        StringBuilder cameraContent= new StringBuilder();
        for(int i=0 ; i<500;i++)
        {
            cameraContent.append(cameraName);
        }
        return cameraContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

}

