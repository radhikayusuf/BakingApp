package com.example.radhikayusuf.bakingapp.ui.testing_full;

import android.annotation.SuppressLint;
import android.databinding.ObservableBoolean;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;

import com.example.radhikayusuf.bakingapp.R;
import com.example.radhikayusuf.bakingapp.databinding.ActivityFullScreenPlayerBinding;
import com.example.radhikayusuf.bakingapp.utils.ComponentListener;
import com.example.radhikayusuf.bakingapp.utils.PlayerCallback;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class TestingFullActivity extends AppCompatActivity implements PlayerCallback {

    public SimpleExoPlayer exoPlayer;

    private ComponentListener componentListener;
    private DefaultBandwidthMeter bandwidthMeter;
    private boolean playWhenReady = true;
    private long playbackPosition;
    private int currentWindow;
    private ObservableBoolean isBuffering = new ObservableBoolean(true);
    private SimpleExoPlayerView exoPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_full);
        exoPlayerView = (SimpleExoPlayerView) findViewById(R.id.exo_player1);
        componentListener = new ComponentListener(this);
        bandwidthMeter = new DefaultBandwidthMeter();

        hideSystemUi();
        initPlayer();
    }

    public void initPlayer(){
        if(exoPlayer == null){
            TrackSelection.Factory factory = new AdaptiveTrackSelection.Factory(bandwidthMeter);

            exoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this), new DefaultTrackSelector(factory), new DefaultLoadControl());
            exoPlayer.addListener(componentListener);
            exoPlayer.setAudioDebugListener(componentListener);
            exoPlayer.setVideoDebugListener(componentListener);
            exoPlayer.setVideoSurfaceView(
                    (SurfaceView)exoPlayerView.getVideoSurfaceView());
            exoPlayer.setPlayWhenReady(playWhenReady);
            //exoPlayer.seekTo(getIntent().getIntExtra("WINDOW_INDEX", 0), getIntent().getLongExtra("STATE", 0));
            exoPlayer.seekTo(0, 0);

            exoPlayerView.setPlayer(exoPlayer);

        }
        MediaSource mediaSource = createMediaSource(Uri.parse("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4"));
        exoPlayer.prepare(mediaSource);

        findViewById(R.id.exo_full).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @SuppressLint("InlinedApi")
    public void hideSystemUi() {
        exoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    public void releasePlayer() {
        if (exoPlayer != null) {
            playbackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            playWhenReady = exoPlayer.getPlayWhenReady();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    private MediaSource createMediaSource(Uri parse) {
        DefaultExtractorsFactory extractorsFactory =
                new DefaultExtractorsFactory();
        DefaultHttpDataSourceFactory dataSourceFactory =
                new DefaultHttpDataSourceFactory("user-agent");

        ExtractorMediaSource videoSource  =
                new ExtractorMediaSource(parse, dataSourceFactory, extractorsFactory, null, null);

        return new ConcatenatingMediaSource(videoSource);
    }

    @Override
    public void onChangeStatus(int status) {

    }
}
