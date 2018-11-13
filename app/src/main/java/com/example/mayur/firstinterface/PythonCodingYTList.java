package com.example.mayur.firstinterface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PythonCodingYTList extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,YouTubePlayer.PlayerStateChangeListener, YouTubePlayer.PlaybackEventListener {

    private YouTubePlayerView PythonPlayer;
    private String API = "AIzaSyD4a5qZCNfXIq7j0EcOB6pbonCY7eeEyFg";
    private String Video_Id = "MBj3DmC74EE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_python_coding_ytlist);

        PythonPlayer = (YouTubePlayerView)findViewById(R.id.Pythonplayerview);

        PythonPlayer.initialize(API,this);



    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        youTubePlayer.setPlayerStateChangeListener(this);
        youTubePlayer.setPlaybackEventListener(this);

        if(!b)
        {
            youTubePlayer.cueVideo(Video_Id);
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }


    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoaded(String s) {

    }

    @Override
    public void onAdStarted() {

    }

    @Override
    public void onVideoStarted() {

    }

    @Override
    public void onVideoEnded() {

    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {

    }
}
