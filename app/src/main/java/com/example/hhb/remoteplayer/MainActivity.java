package com.example.hhb.remoteplayer;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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
                    new Camera("1","江安",R.drawable.jiang_an1),
                    new Camera("2","江安",R.drawable.jiang_an2),
                    new Camera("3","江安",R.drawable.jiang_an3),
                    new Camera("4","江安",R.drawable.jiang_an4),
                    new Camera("5","江安",R.drawable.jiang_an5),
                    new Camera("6","江安",R.drawable.jiang_an6),
                    new Camera("7","江安",R.drawable.jiang_an7),
                    new Camera("8","江安",R.drawable.jiang_an8),
                    new Camera("9","江安",R.drawable.jiang_an9),
                    new Camera("10","江安",R.drawable.jiang_an10),
                    new Camera("11","江安",R.drawable.jiang_an11),
                    new Camera("12","江安",R.drawable.jiang_an12),
                    new Camera("13","江安",R.drawable.jiang_an13),
                    new Camera("14","江安",R.drawable.jiang_an14),
                    new Camera("15","江安",R.drawable.jiang_an15),
                    new Camera("16","江安",R.drawable.jiang_an16),
            };

    private List<Camera> cameraList = new ArrayList<>();

    private CameraAdapter adapter;

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

    }

    private void initCameras()
    {
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
