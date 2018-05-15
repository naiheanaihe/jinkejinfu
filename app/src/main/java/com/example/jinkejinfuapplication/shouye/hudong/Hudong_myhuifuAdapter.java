package com.example.jinkejinfuapplication.shouye.hudong;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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

public class Hudong_myhuifuAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public Hudong_myhuifuAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public Hudong_myhuifuAdapter(Context context) {
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
        return mList==null?0:mList.size();
    }

    @Override
    protected BaseRecycleViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
        final TextView myname,mytime,neirong,name1,neirong1;
        final ImageView img_icon,img,shanchu;
        View view= LayoutInflater.from(context).inflate(R.layout.item_hudong_myhuifu,parent,false);

        myname= (TextView) view.findViewById(R.id.myname);
        mytime= (TextView) view.findViewById(R.id.mytime);
        name1= (TextView) view.findViewById(R.id.name1);
        neirong1= (TextView) view.findViewById(R.id.neirong1);
        neirong= (TextView) view.findViewById(R.id.neirong);
        img_icon= (ImageView) view.findViewById(R.id.myicon);
        img= (ImageView) view.findViewById(R.id.img);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                x.image().bind(img_icon,mList.get(position).get("comment_photo"), ImageOptions.getimageOptions());
                x.image().bind(img,mList.get(position).get("topic_img"), ImageOptions.getimageOptions_img());
                myname.setText(mList.get(position).get("comment_name"));
                mytime.setText(mList.get(position).get("comment_datetime"));
                name1.setText(mList.get(position).get("topic_name"));
                neirong.setText(mList.get(position).get("comment_content"));
                neirong1.setText(mList.get(position).get("topic_content"));


            }

            @Override
            public void onItemClick(View view, int position) {
                /*Intent intent=new Intent(context,HuatiActivity.class);
                context.startActivity(intent);*/
            }
        };
        return holder;
    }
}
