package com.example.radhikayusuf.bakingapp.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.example.radhikayusuf.bakingapp.R;
import com.squareup.picasso.Picasso;

/**
 * @author radhikayusuf.
 */

public class CustomBindingAdapter {

    @BindingAdapter("bind:imageUrl")
    public static void setImageUrl(ImageView view, String url){
        if (!url.isEmpty()) {
            Picasso.with(view.getContext())
                    .load(url)
                    .error(R.drawable.nuttela_pie)
                    .placeholder(R.drawable.nuttela_pie)
                    .into(view);
        }else{
            Picasso.with(view.getContext())
                    .load(R.drawable.nuttela_pie)
                    .into(view);
        }
    }
}
