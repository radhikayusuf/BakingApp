package com.example.radhikayusuf.bakingapp.ui.fullscreen_player;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.radhikayusuf.bakingapp.R;
import com.example.radhikayusuf.bakingapp.databinding.ActivityFullScreenPlayerBinding;
import com.example.radhikayusuf.bakingapp.ui.base.BaseVM;
import com.example.radhikayusuf.bakingapp.utils.ComponentListener;
import com.example.radhikayusuf.bakingapp.utils.PlayerCallback;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

/**
 * @author radhikayusuf.
 */

public class FullScreenPlayerVM extends BaseVM<String> implements PlayerCallback {

    public SimpleExoPlayer exoPlayer;

    private final ComponentListener componentListener;
    private DefaultBandwidthMeter bandwidthMeter;
    private ActivityFullScreenPlayerBinding mBinding;
    private boolean playWhenReady = true;
    private long playbackPosition;
    private int currentWindow;
    private ObservableBoolean isBuffering = new ObservableBoolean(true);

    public FullScreenPlayerVM(Context mContext, String mData, ActivityFullScreenPlayerBinding binding) {
        super(mContext, mData);
        bandwidthMeter = new DefaultBandwidthMeter();
        mBinding = binding;
        componentListener = new ComponentListener(this);
        hideSystemUi();
    }


    public void initPlayer(){
        if(exoPlayer == null){
            TrackSelection.Factory factory = new AdaptiveTrackSelection.Factory(bandwidthMeter);

            exoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(mContext), new DefaultTrackSelector(factory), new DefaultLoadControl());
            exoPlayer.addListener(componentListener);
            exoPlayer.setAudioDebugListener(componentListener);
            exoPlayer.setVideoDebugListener(componentListener);

            mBinding.exoPlayer.setPlayer(exoPlayer);
            exoPlayer.setPlayWhenReady(playWhenReady);
            exoPlayer.seekTo(currentWindow, playbackPosition);
        }
        MediaSource mediaSource = createMediaSource(Uri.parse(mData));
        exoPlayer.prepare(mediaSource);

        mBinding.getRoot().findViewById(R.id.exo_full).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

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

    public void releasePlayer() {
        if (exoPlayer != null) {
            playbackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            playWhenReady = exoPlayer.getPlayWhenReady();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onChangeStatus(int status) {
        isBuffering.set(false);
        switch (status) {
            case ExoPlayer.STATE_IDLE:
                Log.wtf("onChangeStatus: ", "IDLE");
                break;
            case ExoPlayer.STATE_ENDED:
                Log.wtf("onChangeStatus: ", "ENDED");
                break;
            case ExoPlayer.STATE_BUFFERING:
                isBuffering.set(true);
                Log.wtf("onChangeStatus: ", "BUFF");
                break;
            case ExoPlayer.STATE_READY:
                Log.wtf("onChangeStatus: ", "READY");
                break;
            default:
                break;
        }
    }

    @SuppressLint("InlinedApi")
    public void hideSystemUi() {
        mBinding.exoPlayer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    public void setPlaybackPosition(long playbackPosition) {
        this.playbackPosition = playbackPosition;
    }

    public void setCurrentWindow(int currentWindow) {
        this.currentWindow = currentWindow;
    }
}
