package com.example.radhikayusuf.bakingapp.ui.fragment_ingredients;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.radhikayusuf.bakingapp.R;
import com.example.radhikayusuf.bakingapp.dao.IngredientsDao;
import com.example.radhikayusuf.bakingapp.databinding.RowIngredientsBinding;

import java.util.List;

/**
 * @author radhikayusuf.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private List<IngredientsDao> mData;
    private Context mContext;

    public IngredientsAdapter(List<IngredientsDao> mData, Context context) {
        this.mData = mData;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowIngredientsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.row_ingredients, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getBinding().setVm(new IngredientsRowVM(mContext, mData.get(position)));
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RowIngredientsBinding binding;

        public ViewHolder(RowIngredientsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public RowIngredientsBinding getBinding() {
            return binding;
        }
    }
}
