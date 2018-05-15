package com.example.jinkejinfuapplication.shouye.hudong;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.BaseRecycleAdapter;
import com.example.jinkejinfuapplication.base.BaseRecycleViewHolder;
import com.example.jinkejinfuapplication.utils.ImageOptions;

import org.xutils.x;

import java.util.List;
import java.util.Map;

public class Hudong_myhuati_huifuAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public Hudong_myhuati_huifuAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public Hudong_myhuati_huifuAdapter(Context context) {
        this.context = context;
    }

    public List<Map<String,String>> getmList() {
        return mList;
    }

    public void setmList(List<Map<String,String>> mList) {
        this.mList = mList;
    }

    @Override
    protected int getDataCount() {
        return mList==null?4:mList.size();
    }

    @Override
    protected BaseRecycleViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
        final TextView neirong;
        final ImageView img_icon;
        View view= LayoutInflater.from(context).inflate(R.layout.item_hudong_myhuat_huifu,parent,false);

        neirong= (TextView) view.findViewById(R.id.neirong);
        img_icon= (ImageView) view.findViewById(R.id.myicon);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                x.image().bind(img_icon,mList.get(position).get("pic"), ImageOptions.getimageOptions());
                neirong.setText(mList.get(position).get("content"));

            }

            @Override
            public void onItemClick(View view, int position) {

            }
        };
        return holder;
    }
}
