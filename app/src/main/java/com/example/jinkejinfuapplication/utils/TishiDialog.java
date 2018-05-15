package com.example.jinkejinfuapplication.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import org.xutils.common.util.DensityUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */
public class TishiDialog extends Dialog
{
    Context mContext;
    private List<TextBean> mTextlist;
    String s=null;

    public TishiDialog(Context context) {
        super(context);
        this.mContext=context;
    }


    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.CENTER;
        layoutParams.width= WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height= DensityUtil.dip2px(180);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);

    }
}
