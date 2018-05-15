package com.example.jinkejinfuapplication.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2016/10/14.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration
{
    private int space;
    private Boolean mVertical;

    public SpaceItemDecoration(int space, Boolean mVertical) {
        this.space = space;
        this.mVertical = mVertical;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (mVertical == true)
        {
            outRect.left = 0;
            outRect.right = 0;
            outRect.bottom = space;
        }else {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = 0;
            outRect.top = 0;
        }
        // Add top margin only for the first item to avoid double space between items
        /*if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space;
        }*/
    }
}
