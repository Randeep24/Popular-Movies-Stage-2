package com.randeep.popularmovies.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.randeep.popularmovies.R;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YoutubeVideoActivity extends YoutubeFailureRecoveryActivity {

    ImageView cancelImage;
    YouTubePlayer mYouTubePlayer;
    Intent intent;
    String media_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_youtube_video);
        intent = getIntent();
        media_url = intent.getStringExtra("MEDIA_URL");

//        mWebView = (WebView)findViewById(R.id.video);
//
//        mWebView.getSettings().setJavaScriptEnabled(true);
//        // mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
////        mWebView.loadUrl("http://www.youtube.com/embed/" + extractYTId("https://www.youtube.com/watch?v=eNpDqYoHFZk") + "?autoplay=1&vq=small");
//        mWebView.loadUrl("https://ia700401.us.archive.org/19/items/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4");
//        mWebView.setWebChromeClient(new MyWebViewClient());
        YouTubePlayerFragment youTubePlayerFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
        youTubePlayerFragment.initialize(getResources().getString(R.string.dev_key), this);
        cancelImage = (ImageView) findViewById(R.id.cancelImage);
        cancelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer,
                                        boolean wasRestored) {
        if (!wasRestored) {
            youTubePlayer.loadVideo(extractYTId(media_url));
            mYouTubePlayer = youTubePlayer;
        }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
    }


    public static String extractYTId(String ytUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(ytUrl);
        if(matcher.find()){
            return matcher.group();
        } else {
            return "error";
        }


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e("Called","...............");
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ){
            cancelImage.setVisibility(View.GONE);
        }else {
            cancelImage.setVisibility(View.VISIBLE);
        }
    }


}
