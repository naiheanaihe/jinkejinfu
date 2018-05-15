package com.example.jinkejinfuapplication.shouye.huodong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.BaseRecycleAdapter;
import com.example.jinkejinfuapplication.base.BaseRecycleViewHolder;
import com.example.jinkejinfuapplication.utils.ImageOptions;

import org.xutils.x;

import java.util.List;
import java.util.Map;

public class Huodong_tuijianAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public Huodong_tuijianAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public Huodong_tuijianAdapter(Context context) {
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
        final TextView myname,vip,feiyong,mytime;
        final ImageView img_icon;
        View view= LayoutInflater.from(context).inflate(R.layout.item_huodong_tuijian,parent,false);
        myname= (TextView) view.findViewById(R.id.myname);
        vip= (TextView) view.findViewById(R.id.vip);
        feiyong= (TextView) view.findViewById(R.id.feiyong);
        mytime= (TextView) view.findViewById(R.id.mytime);
        img_icon= (ImageView) view.findViewById(R.id.myicon);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                x.image().bind(img_icon,mList.get(position).get("photo"), ImageOptions.getimageOptions());
                myname.setText(mList.get(position).get("name"));
                vip.setText(mList.get(position).get("rank"));
                feiyong.setText(mList.get(position).get("money"));
                mytime.setText(mList.get(position).get("datetime"));
            }

            @Override
            public void onItemClick(View view, int position) {
            }
        };
        return holder;
    }
}
