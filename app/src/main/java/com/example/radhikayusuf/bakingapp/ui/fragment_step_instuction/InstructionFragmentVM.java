package com.example.radhikayusuf.bakingapp.ui.fragment_step_instuction;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.radhikayusuf.bakingapp.R;
import com.example.radhikayusuf.bakingapp.dao.RecipeDao;
import com.example.radhikayusuf.bakingapp.databinding.FragmentInstructionsBinding;
import com.example.radhikayusuf.bakingapp.ui.base.BaseVM;
import com.example.radhikayusuf.bakingapp.ui.detail.DetailActivity;
import com.example.radhikayusuf.bakingapp.ui.fullscreen_player.FullScreenPlayerActivity;
import com.example.radhikayusuf.bakingapp.ui.testing_full.TestingFullActivity;
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

import rx.Observable;

/**
 * @author radhikayusuf.
 */

public class InstructionFragmentVM extends BaseVM<RecipeDao>
    implements PlayerCallback {

    private int position = 0;
    private FragmentInstructionsBinding mBinding;
    public ObservableField<String> indexTitle = new ObservableField<>();
    public ObservableField<String> descStep = new ObservableField<>();
    public ObservableBoolean isVidAvaiable = new ObservableBoolean(false);
    public ObservableBoolean isShowThumbnail = new ObservableBoolean(true);
    public ObservableBoolean isBuffering = new ObservableBoolean(true);
    public ObservableField<String> thumbnail_url = new ObservableField<>("");


    private DefaultBandwidthMeter bandwidthMeter;
    private ComponentListener componentListener;
    private SimpleExoPlayer exoPlayer;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;

    public InstructionFragmentVM(Context context, RecipeDao mData, FragmentInstructionsBinding binding, InstructionsFragment instructionsFragment) {
        super(context, mData);
        mBinding = binding;
        indexTitle.set(mData.getSteps().get(position).getShortDescription());
        descStep.set(mData.getSteps().get(position).getDescription());
        thumbnail_url.set(mData.getSteps().get(position).getThumbnailURL());
        bandwidthMeter = new DefaultBandwidthMeter();
        componentListener = new ComponentListener(this);
    }

    public void setPosition(int position) {
        this.position = position;

        if(position == 0){
            mBinding.btnPrev.setVisibility(View.GONE);
        }else if(position == mData.getSteps().size() - 1){
            mBinding.btnNext.setVisibility(View.GONE);
        }else{
            mBinding.btnNext.setVisibility(View.VISIBLE);
            mBinding.btnPrev.setVisibility(View.VISIBLE);
        }

        indexTitle.set(mData.getSteps().get(position).getShortDescription());
        descStep.set(mData.getSteps().get(position).getDescription());

        if(!mData.getSteps().get(position).getVideoURL().isEmpty()){
            isVidAvaiable.set(true);
            initPlayer();
        }else{
            isVidAvaiable.set(false);
            isShowThumbnail.set(false);
        }
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
        MediaSource mediaSource = createMediaSource(Uri.parse(mData.getSteps().get(position).getVideoURL()));
        exoPlayer.prepare(mediaSource);

        mBinding.getRoot().findViewById(R.id.exo_full).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Bundle b = new Bundle();
                b.putString(mContext.getString(R.string.intent_video_url), mData.getSteps().get(position).getVideoURL());
                b.putInt(mContext.getString(R.string.intent_window_index), exoPlayer.getCurrentWindowIndex());
                b.putLong(mContext.getString(R.string.intent_state), exoPlayer.getCurrentPosition());
                releasePlayer();
                getActivity().openActivity(TestingFullActivity.class, b);
                getActivity().finish();
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

    public void releasePlayer(){
        if(exoPlayer != null){
            playbackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            playWhenReady = exoPlayer.getPlayWhenReady();

            exoPlayer.removeListener(componentListener);
            exoPlayer.setVideoListener(null);
            exoPlayer.setVideoDebugListener(null);
            exoPlayer.setAudioDebugListener(null);
            exoPlayer.release();

            exoPlayer = null;
        }
        mBinding.exoPlayer.setPlayer(null);
    }

    public void onClickNextOrPrevious(View v){
        switch (v.getId()) {
            case R.id.btn_next:
                releasePlayer();
                setPosition(position + 1);
                break;
            case R.id.btn_prev:
                releasePlayer();
                setPosition(position - 1);
                break;
        }

    }

    @Override
    public void onChangeStatus(int status) {
        isBuffering.set(false);
        switch (status) {
            case ExoPlayer.STATE_IDLE:
                isShowThumbnail.set(true);
                break;
            case ExoPlayer.STATE_ENDED:
                break;
            case ExoPlayer.STATE_BUFFERING:
                isBuffering.set(true);
                break;
            case ExoPlayer.STATE_READY:
                isShowThumbnail.set(false);
                break;
            default:
                break;
        }
    }

    public int getPosition() {
        return position;
    }

    public SimpleExoPlayer getExoPlayer() {
        return exoPlayer;
    }

    public long getPlaybackPosition() {
        if (exoPlayer != null) {
            return exoPlayer.getCurrentPosition();
        }
        return 0;
    }

    public void setPlaybackPosition(long playbackPosition) {
        this.playbackPosition = playbackPosition;
    }

    //    public void hideSystemUI(){
//
//        mBinding.exoPlayer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
//            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//        );
//    }
}
