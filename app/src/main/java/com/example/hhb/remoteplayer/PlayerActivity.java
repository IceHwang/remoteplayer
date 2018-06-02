package com.example.hhb.remoteplayer;


import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class PlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        VideoView videoView = findViewById(R.id.video_view);

        String videoUrl1 = Environment.getExternalStorageDirectory().getPath()+"/test.mp4" ;

        String videoUrl2= "https://raw.githubusercontent.com/IceHwang/filehub/master/test.mp4";

        String videoUrl3="http://159.65.111.107:8080/hls/stream.m3u8";

        String videoUrl4="http://120.77.255.28:8080/hls/stream.m3u8";


        videoView.setVideoURI(Uri.parse(videoUrl1));
        videoView.setMediaController(new MediaController(this));
        videoView.start();
        videoView.requestFocus();

    }
}
