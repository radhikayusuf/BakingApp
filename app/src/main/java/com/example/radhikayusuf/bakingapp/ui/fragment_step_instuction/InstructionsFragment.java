package com.example.radhikayusuf.bakingapp.ui.fragment_step_instuction;


import android.support.v4.app.Fragment;

import com.example.radhikayusuf.bakingapp.R;
import com.example.radhikayusuf.bakingapp.databinding.FragmentInstructionsBinding;
import com.example.radhikayusuf.bakingapp.ui.base.BaseFragment;
import com.example.radhikayusuf.bakingapp.ui.detail.DetailActivity;
import com.google.android.exoplayer2.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstructionsFragment extends BaseFragment<FragmentInstructionsBinding, InstructionFragmentVM> {


    private DetailActivity act;

    public InstructionsFragment() {
        // Required empty public constructor
    }


    @Override
    protected InstructionFragmentVM onPrepare() {
        act = (DetailActivity) getActivity();
        return new InstructionFragmentVM(getContext(), act.mData, getBinding(), this);
    }

    @Override
    protected int getLayoutId() {
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
//        getVm().hideSystemUI();
//        if(Util.SDK_INT <= 23 || getVm().getExoPlayer() == null){
//            getVm().initPlayer();
//        }
    }

    @Override
    public void onStop() {

        super.onStop();
        if(Util.SDK_INT > 23){
            getVm().releasePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(Util.SDK_INT <= 23){
            getVm().releasePlayer();
        }
    }
}
