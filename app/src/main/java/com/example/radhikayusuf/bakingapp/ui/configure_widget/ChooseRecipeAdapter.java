package com.example.radhikayusuf.bakingapp.ui.configure_widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.radhikayusuf.bakingapp.R;
import com.example.radhikayusuf.bakingapp.dao.RecipeDao;
import com.example.radhikayusuf.bakingapp.databinding.RowChooseRecipeBinding;
import com.example.radhikayusuf.bakingapp.databinding.RowRecipeBinding;
import com.example.radhikayusuf.bakingapp.utils.OnClickItemListener;

import java.util.List;

/**
 * @author radhikayusuf.
 */

public class ChooseRecipeAdapter extends RecyclerView.Adapter<ChooseRecipeAdapter.ViewHolder> {

    private Context mContext;
    private List<RecipeDao> mData;
    private OnClickItemListener evt;

    public ChooseRecipeAdapter(Context mContext, List<RecipeDao> mData) {
        this.mContext = mContext;
        this.mData = mData;
        evt = (OnClickItemListener) mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowChooseRecipeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.row_choose_recipe, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ChooseRecipeRowVM vm = new ChooseRecipeRowVM(mContext, mData.get(position));
        holder.getBinding().setVm(vm);
        holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evt.onClickItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RowChooseRecipeBinding binding;

        public ViewHolder(RowChooseRecipeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public RowChooseRecipeBinding getBinding() {
            return binding;
        }
    }
}
