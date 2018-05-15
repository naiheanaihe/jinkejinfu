package com.example.jinkejinfuapplication.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;


/**
 * Created by Administrator on 2016/12/1.
 */
public class FullDialog extends Dialog
{
    Context mContext;
    String s=null;

    public FullDialog(Context context) {
        super(context);
        this.mContext=context;
    }

   /* public View getview()
    {
        return View.inflate(mContext, R.layout.dialog_fengxian,null);
    }*/


    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.CENTER;
        layoutParams.width= WindowManager.LayoutParams.WRAP_CONTENT;
        /*layoutParams.height= DisplayUtil.dip2px(mContext,280);*/
        layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);

    }
}
