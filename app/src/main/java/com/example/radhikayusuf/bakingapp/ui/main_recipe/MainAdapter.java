package com.example.radhikayusuf.bakingapp.ui.main_recipe;

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.radhikayusuf.bakingapp.R;
import com.example.radhikayusuf.bakingapp.dao.RecipeDao;
import com.example.radhikayusuf.bakingapp.databinding.RowRecipeBinding;

import java.util.List;

/**
 * @author radhikayusuf.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<RecipeDao> mData;
    private Context mContext;
    private MainVM vm;

    public MainAdapter(List<RecipeDao> mData, Context context, MainVM mainVM) {
        this.mData = mData;
        this.mContext = context;
        this.vm = mainVM;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowRecipeBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_recipe, parent, false);
        return new ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.getBinding().setVm(new MainRowVM(mData.get(position), mContext));
        holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.onClickItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private RowRecipeBinding mBinding;

        public ViewHolder(RowRecipeBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public RowRecipeBinding getBinding() {
            return mBinding;
        }
    }
}
