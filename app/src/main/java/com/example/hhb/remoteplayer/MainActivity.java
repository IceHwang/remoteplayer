package com.example.hhb.remoteplayer;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private Camera[] cameras=
            {
                    new Camera("1","江安",R.drawable.jiang_an1,Environment.getExternalStorageDirectory().getPath()+"/test.mp4"),
                    new Camera("2","江安",R.drawable.jiang_an2,"https://raw.githubusercontent.com/IceHwang/filehub/master/test.mp4"),
                    new Camera("3","江安",R.drawable.jiang_an3,"http://159.65.111.107:8080/hls/stream.m3u8"),
                    new Camera("4","江安",R.drawable.jiang_an4,"http://120.77.255.28:8080/hls/stream.m3u8"),
                    new Camera("5","江安",R.drawable.jiang_an5,Environment.getExternalStorageDirectory().getPath()+"/test.mp4"),
                    new Camera("6","江安",R.drawable.jiang_an6,Environment.getExternalStorageDirectory().getPath()+"/test.mp4"),
                    new Camera("7","江安",R.drawable.jiang_an7,Environment.getExternalStorageDirectory().getPath()+"/test.mp4"),
                    new Camera("8","江安",R.drawable.jiang_an8,Environment.getExternalStorageDirectory().getPath()+"/test.mp4"),
                    new Camera("9","江安",R.drawable.jiang_an9,Environment.getExternalStorageDirectory().getPath()+"/test.mp4"),
                    new Camera("10","江安",R.drawable.jiang_an10,Environment.getExternalStorageDirectory().getPath()+"/test.mp4"),
                    new Camera("11","江安",R.drawable.jiang_an11,Environment.getExternalStorageDirectory().getPath()+"/test.mp4"),
                    new Camera("12","江安",R.drawable.jiang_an12,Environment.getExternalStorageDirectory().getPath()+"/test.mp4"),
                    new Camera("13","江安",R.drawable.jiang_an13,Environment.getExternalStorageDirectory().getPath()+"/test.mp4"),
                    new Camera("14","江安",R.drawable.jiang_an14,Environment.getExternalStorageDirectory().getPath()+"/test.mp4"),
                    new Camera("15","江安",R.drawable.jiang_an15,"https://txy.live-play.acgvideo.com/live-txy/269464/live_10957838_5741149.flv?wsSecret=c23e847c26d152d7f90d0749da74ecb1&wsTime=1528011326"),
                    new Camera("16","江安",R.drawable.jiang_an16,"https://upos-hz-mirrorkodo.acgvideo.com/upgcxcode/62/35/40733562/40733562-1-80.flv?e=ig8euxZM2rNcNbu17zKVhoM17whMnwdVNEVEuCIv29hEn0lqXg8Y2ENvNCImNEVEUJ1miI7MT96fqj3E9r1qNCNEto8g2ENvN03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B599M=&deadline=1528016128&dynamic=1&gen=playurl&oi=3701942303&os=kodo&platform=pc&rate=717400&trid=33b1f0a0487844c2a61eca4c04f62ee0&uipk=5&uipv=5&um_deadline=1528016128&um_sign=0d9ed7fad7939afa3c85abd840d6469a&upsig=bc748dc34ab4e2065580a6cbed41d018"),
            };

    private List<Camera> cameraList = new ArrayList<>();

    private CameraAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout=findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                return true;
            }
        });

        initCameras();
        RecyclerView recyclerView =findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager= new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new CameraAdapter(cameraList);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout=findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCameras();
            }
        });

    }

    private void refreshCameras()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Thread.sleep(2000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initCameras();
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initCameras()
    {
        String s="/storage/emulated/0/test.mp4";
        for (int i=0;i<LoginActivity.urlList.size();i++)
        {
            Camera t=cameras[4+i];
            cameras[4+i]=new Camera(t.getId(),t.getPlace(),t.getImageId(),LoginActivity.urlList.get(i));
        }
        cameraList.clear();
        for (int i=0;i<16;i++)
        {
            cameraList.add(cameras[i]);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.logout:
                Toast.makeText(this,"logout",Toast.LENGTH_SHORT).show();
                break;
            default:

        }
        return true;
    }
}
