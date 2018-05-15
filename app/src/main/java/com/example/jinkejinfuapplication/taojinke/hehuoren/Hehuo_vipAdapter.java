package com.example.jinkejinfuapplication.taojinke.hehuoren;

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
import com.example.jinkejinfuapplication.xiangq.ShipingActivity;

import org.xutils.x;

import java.util.List;
import java.util.Map;

public class Hehuo_vipAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public Hehuo_vipAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public Hehuo_vipAdapter(Context context) {
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
        return mList.size();
    }

    @Override
    protected BaseRecycleViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
        final TextView num,vip;
        View view= LayoutInflater.from(context).inflate(R.layout.item_hehuo_vip,parent,false);
        num= (TextView) view.findViewById(R.id.num);
        vip= (TextView) view.findViewById(R.id.vip);

        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(int position) {
                num.setText(mList.get(position).get("num"));
                vip.setText(mList.get(position).get("FxLevel"));
            }

            @Override
            public void onItemClick(View view, int position) {
            }
        };
        return holder;
    }
}
