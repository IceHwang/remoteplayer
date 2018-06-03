package com.example.hhb.remoteplayer;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class CameraActivity extends AppCompatActivity {

    public static final String CAMERA_NAME ="camera_name";
    public static final String CAMERA_IMAGE_ID ="camera_image_id";
    public static final String CAMERA_URL ="camera_url";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        if(ContextCompat.checkSelfPermission(CameraActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(CameraActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        }

        if(ContextCompat.checkSelfPermission(CameraActivity.this,
                Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(CameraActivity.this,new String[]{Manifest.permission.INTERNET},1);

        }


        Intent intent =getIntent();
        String cameraName=intent.getStringExtra(CAMERA_NAME);
        final String cameraUrl=intent.getStringExtra(CAMERA_URL);
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

        FloatingActionButton floatingActionButton=findViewById(R.id.player);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CameraActivity.this,PlayerActivity.class);
                intent.putExtra(PlayerActivity.PLAYER_URL,cameraUrl);
                startActivity(intent);

                Toast.makeText(CameraActivity.this,"play",Toast.LENGTH_SHORT).show();
            }
        });

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

