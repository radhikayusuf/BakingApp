package com.example.radhikayusuf.bakingapp.ui.fragment_step_instuction;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.radhikayusuf.bakingapp.R;
import com.example.radhikayusuf.bakingapp.databinding.FragmentInstructionsBinding;
import com.example.radhikayusuf.bakingapp.ui.base.BaseFragment;
import com.example.radhikayusuf.bakingapp.ui.detail.DetailActivity;
import com.example.radhikayusuf.bakingapp.utils.Utils;
import com.google.android.exoplayer2.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstructionsFragment extends BaseFragment<FragmentInstructionsBinding, InstructionFragmentVM> {


    private DetailActivity act;
    private int video_pos = -1;

    public InstructionsFragment() {
        // Required empty public constructor
    }


    @Override
    protected InstructionFragmentVM onPrepare() {
        act = (DetailActivity) getActivity();
        return new InstructionFragmentVM(getContext(), act.mData, getBinding(), this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_instructions;
    }

    @Override
    protected FragmentInstructionsBinding onCreateVM(InstructionFragmentVM vm, FragmentInstructionsBinding binding) {
        binding.setVm(vm);
        return binding;
    }

    public void changeIndexStep(int position) {
        getVm().setPosition(position);
    }

    public int getIndexStep() {
        return getVm().getPosition();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (act.isTab) {
            if (getVm().getPosition() == 0) {
                changeIndexStep(0);
            }
        }

//        if (Util.SDK_INT > 23) {
//            getVm().initPlayer();
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || getVm().getExoPlayer() == null) {
            if (((DetailActivity) getActivity()).isCanInitVideo()) {
                if (video_pos != -1) {
                    getVm().setPosition(video_pos);
                    video_pos = -1;

                    if (((DetailActivity) getActivity()).getBinding().identifierLayoutTab == null && Utils.isPotrait(getActivity())) {
                        ((DetailActivity) getActivity()).showFragment(this, true);
                        ((DetailActivity) getActivity()).setVideo(true);
                    }
                } else {
                    getVm().initPlayer();
                }
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            getVm().releasePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            getVm().releasePlayer();
        }
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            getVm().setPlaybackPosition(savedInstanceState.getLong("playback_position"));
            video_pos = savedInstanceState.getInt("video_pos");
        }
        super.onViewStateRestored(savedInstanceState);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong("playback_position", getVm().getPlaybackPosition());
        outState.putInt("video_pos", getVm().getPosition());
        super.onSaveInstanceState(outState);
    }
}
