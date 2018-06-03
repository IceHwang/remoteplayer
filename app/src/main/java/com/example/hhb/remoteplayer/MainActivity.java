package com.example.hhb.remoteplayer;

import android.content.Intent;
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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private long exitTime=0;

    private DrawerLayout drawerLayout;


    private Camera[] cameras=
            {
                    new Camera("1","江安",R.drawable.jiang_an1,"/storage/emulated/0/test.mp4"),
                    new Camera("2","江安",R.drawable.jiang_an2,"/storage/emulated/0/test.mp4"),
                    new Camera("3","江安",R.drawable.jiang_an3,"/storage/emulated/0/test.mp4"),
                    new Camera("4","江安",R.drawable.jiang_an4,"/storage/emulated/0/test.mp4"),
                    new Camera("5","江安",R.drawable.jiang_an5,"/storage/emulated/0/test.mp4"),
                    new Camera("6","江安",R.drawable.jiang_an6,"/storage/emulated/0/test.mp4"),
                    new Camera("7","江安",R.drawable.jiang_an7,"/storage/emulated/0/test.mp4"),
                    new Camera("8","江安",R.drawable.jiang_an8,"/storage/emulated/0/test.mp4"),
                    new Camera("9","江安",R.drawable.jiang_an9,"/storage/emulated/0/test.mp4"),
                    new Camera("10","江安",R.drawable.jiang_an10,"/storage/emulated/0/test.mp4"),
                    new Camera("11","江安",R.drawable.jiang_an11,"/storage/emulated/0/test.mp4"),
                    new Camera("12","江安",R.drawable.jiang_an12,"/storage/emulated/0/test.mp4"),
                    new Camera("13","江安",R.drawable.jiang_an13,"/storage/emulated/0/test.mp4"),
                    new Camera("14","江安",R.drawable.jiang_an14,"/storage/emulated/0/test.mp4"),
                    new Camera("15","江安",R.drawable.jiang_an15,"/storage/emulated/0/test.mp4"),
                    new Camera("16","江安",R.drawable.jiang_an16,"/storage/emulated/0/test.mp4"),
            };

    private List<Camera> cameraList = new ArrayList<>();

    private CameraAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {

            if((System.currentTimeMillis()-exitTime) > 2000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
            {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }
            else
            {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

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

        View navHeaderview = navigationView.getHeaderView(0);
        TextView name=navHeaderview.findViewById(R.id.nav_username);
        name.setText(LoginActivity.username);
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
        for (int i=0;i<LoginActivity.urlList.size()&& i<15 ;i++)
        {
            Camera t=cameras[i];
            cameras[i]=new Camera(t.getId(),t.getPlace(),t.getImageId(),LoginActivity.urlList.get(i));
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
                finish();
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
            default:

        }
        return true;
    }
}
