package com.example.jinkejinfuapplication.xiangq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.BaseRecycleAdapter;
import com.example.jinkejinfuapplication.base.BaseRecycleViewHolder;
import com.example.jinkejinfuapplication.utils.ImageOptions;

import org.xutils.x;

import java.util.List;

public class ImgAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<String> mList;

    public ImgAdapter(Context context, List<String> mList) {
        this.context = context;
        this.mList = mList;
    }
    public ImgAdapter(Context context) {
        this.context = context;
    }

    public List<String> getmList() {
        return mList;
    }

    public void setmList(List<String> mList) {
        this.mList = mList;
    }

    @Override
    protected int getDataCount() {
        return mList.size();
    }

    @Override
    protected BaseRecycleViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
        final ImageView img_bk;
        View view= LayoutInflater.from(context).inflate(R.layout.item_img,parent,false);
        img_bk= (ImageView) view.findViewById(R.id.img_bk);

        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(int position) {
                x.image().bind(img_bk,mList.get(position), new org.xutils.image.ImageOptions.Builder().setIgnoreGif(false).setFailureDrawableId(R.drawable.img_loadfalse).setImageScaleType(ImageView.ScaleType.FIT_XY).build());

            }

            @Override
            public void onItemClick(View view, int position) {
            }
        };
        return holder;
    }
}
