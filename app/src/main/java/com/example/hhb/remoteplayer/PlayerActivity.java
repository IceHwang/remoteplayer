package com.example.hhb.remoteplayer;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class PlayerActivity extends AppCompatActivity {

    public static final String PLAYER_URL ="player_url";

    private VideoView videoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        videoView = findViewById(R.id.video_view);


        Intent intent =getIntent();
        String player_url=intent.getStringExtra(PLAYER_URL);


        videoView.setVideoURI(Uri.parse(player_url));
        videoView.setMediaController(new MediaController(this));
        videoView.start();
        videoView.requestFocus();

    }


    int mPlayingPos = 0;

    @Override
    protected void onPause() {
        mPlayingPos = videoView.getCurrentPosition(); //先获取再stopPlay(),原因自己看源码
        videoView.stopPlayback();
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mPlayingPos > 0) {
            //此处为更好的用户体验,可添加一个progressBar,有些客户端会在这个过程中隐藏底下控制栏,这方法也不错
            videoView.start();
            videoView.seekTo(mPlayingPos);
            mPlayingPos = 0;
        }
        super.onResume();
    }





}
