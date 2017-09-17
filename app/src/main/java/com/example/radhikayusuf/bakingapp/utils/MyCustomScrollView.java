package com.example.radhikayusuf.bakingapp.utils;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @author radhikayusuf.
 */

public class MyCustomScrollView extends NestedScrollView {

    private MyCustomScrollViewListener scrollViewListener = null;

    public MyCustomScrollView(Context context) {
        super(context);
    }

    public MyCustomScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyCustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(MyCustomScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if(scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }


    public interface MyCustomScrollViewListener{
        void onScrollChanged(MyCustomScrollView scrollView, int x, int y, int oldx, int oldy);
    }

}
