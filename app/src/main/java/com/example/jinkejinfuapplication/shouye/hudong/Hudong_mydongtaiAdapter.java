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

public class Hudong_mydongtaiAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public Hudong_mydongtaiAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public Hudong_mydongtaiAdapter(Context context) {
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
        final TextView reti,myname,mytime,mytime1,neirong,mytitle;
        final ImageView img_icon,myicon1,shanchu,huifu;
        View view= LayoutInflater.from(context).inflate(R.layout.item_hudong_mydongtai,parent,false);
        reti= (TextView) view.findViewById(R.id.reti);
        myname= (TextView) view.findViewById(R.id.myname);
        mytime= (TextView) view.findViewById(R.id.mytime);
        mytime1= (TextView) view.findViewById(R.id.mytime1);

        mytitle= (TextView) view.findViewById(R.id.mytitle);
        neirong= (TextView) view.findViewById(R.id.neirong);
        img_icon= (ImageView) view.findViewById(R.id.myicon);
        myicon1= (ImageView) view.findViewById(R.id.myicon1);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                x.image().bind(img_icon,mList.get(position).get("comment_photo"), ImageOptions.getimageOptions_img());
                x.image().bind(myicon1,mList.get(position).get("topic_photo"), ImageOptions.getimageOptions_img());
                if (mList.get(position).get("topic_photo").equals("1")){
                    reti.setVisibility(View.VISIBLE);
                }else {
                    reti.setVisibility(View.GONE);
                }
                myname.setText(mList.get(position).get("comment_name"));
                mytime.setText(mList.get(position).get("comment_datetime"));
                neirong.setText(mList.get(position).get("comment_content"));
                mytitle.setText(mList.get(position).get("topic_title"));
                mytime1.setText(mList.get(position).get("topic_datetime"));

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
