package com.treats.treats.activities;


import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.TextView;

import com.treats.treats.R;

/**
 * Created by david.uzan on 9/1/2016.
 */
public class CustomToolbar extends Toolbar {

    private TextView mTitleTv;

    public CustomToolbar(Context context) {
        super(context);
    }

    public CustomToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews();
    }

    private void initViews() {
        mTitleTv = (TextView) findViewById(R.id.tv_title);
    }

    public void setTitle(String title) {
        mTitleTv.setText(title);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        destroy();
    }

    private void destroy() {
        mTitleTv = null;
    }

//    public interface ToolbarClickListener {
//        void onBackPressClick();
//
//        void onDrawerToggleClick();
//    }
//
//    public interface ToolbarController {
//        void setToolbarState(ToolbarMode state);
//
//        void setToolbarTitle(CharSequence text);
//
//        void setToolbarTitle(@StringRes int resId);
//
//        void setSearchBarHint(String text);
//
//        void setSearchQueryListener(SearchQueryListener listener);
//    }

//    public interface SearchQueryListener {
//        void onSearchQueryChange(CharSequence text);
//    }
}

