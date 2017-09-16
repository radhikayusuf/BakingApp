package com.example.radhikayusuf.bakingapp.ui.fragment_detail;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.radhikayusuf.bakingapp.R;
import com.example.radhikayusuf.bakingapp.dao.RecipeDao;
import com.example.radhikayusuf.bakingapp.databinding.RowStepBinding;

/**
 * @author radhikayusuf.
 */

public class ListStepAdapter extends RecyclerView.Adapter<ListStepAdapter.ViewHolder> {

    private RecipeDao mData;
    private Context mContext;

    public ListStepAdapter(RecipeDao mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), viewType, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder.getBinding() instanceof RowStepBinding) {
            ((RowStepBinding) holder.getBinding()).setVm(new ListRowStep(mContext, mData, position));
        }
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.getSteps().size(): 0;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.row_step;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }
}
