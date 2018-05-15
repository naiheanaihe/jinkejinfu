package com.example.jinkejinfuapplication.utils;


import android.widget.ImageView;

import com.example.jinkejinfuapplication.R;

/**
 * Created by Administrator on 2017/2/3.
 */
public class ImageOptions
{
    public static org.xutils.image.ImageOptions imageOptions;

    public static org.xutils.image.ImageOptions getimageOptions()
    {
        imageOptions=new org.xutils.image.ImageOptions.Builder().setIgnoreGif(false).setLoadingDrawableId(R.drawable.ic_defult_head).setFailureDrawableId(R.drawable.ic_defult_head).build();
        return imageOptions;
    }
    public static org.xutils.image.ImageOptions getimageOptions_img()
    {
        imageOptions=new org.xutils.image.ImageOptions.Builder().setIgnoreGif(false).setFailureDrawableId(R.drawable.img_loadfalse).setImageScaleType(ImageView.ScaleType.FIT_XY).build();
        return imageOptions;
    }
    public static org.xutils.image.ImageOptions getimageOptions_touming()
    {
        imageOptions=new org.xutils.image.ImageOptions.Builder().setIgnoreGif(false).setFailureDrawableId(R.drawable.touming_bk).setImageScaleType(ImageView.ScaleType.FIT_XY).build();
        return imageOptions;
    }
}
