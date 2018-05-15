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

import java.util.List;
import java.util.Map;

public class Hudong_myliuyin_huifuAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public Hudong_myliuyin_huifuAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public Hudong_myliuyin_huifuAdapter(Context context) {
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
        final TextView myname,mytime;
        final ImageView img_icon,shanchu,huifu;
        View view= LayoutInflater.from(context).inflate(R.layout.item_hudong_myliuyin_huifu,parent,false);

        myname= (TextView) view.findViewById(R.id.myname);
        mytime= (TextView) view.findViewById(R.id.mytime);
        shanchu= (ImageView) view.findViewById(R.id.shanchu);
        huifu= (ImageView) view.findViewById(R.id.huifu);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                /*x.image().bind(img_icon,mList.get(position).get("game_pic"), ImageOptions.getimageOptions_img());*/

            }

            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(context,HuatiActivity.class);
                context.startActivity(intent);
            }
        };
        return holder;
    }
}
