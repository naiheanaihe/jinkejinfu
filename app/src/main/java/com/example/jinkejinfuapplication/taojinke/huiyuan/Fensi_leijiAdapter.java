package com.example.jinkejinfuapplication.taojinke.huiyuan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.BaseRecycleAdapter;
import com.example.jinkejinfuapplication.base.BaseRecycleViewHolder;

import java.util.List;
import java.util.Map;

public class Fensi_leijiAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public Fensi_leijiAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public Fensi_leijiAdapter(Context context) {
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
        final TextView name,phone,vip,time;
        View view= LayoutInflater.from(context).inflate(R.layout.item_fensi_leiji,parent,false);
        name= (TextView) view.findViewById(R.id.name);
        phone= (TextView) view.findViewById(R.id.phone);
        vip= (TextView) view.findViewById(R.id.vip);
        time= (TextView) view.findViewById(R.id.time);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(int position) {
                name.setText(mList.get(position).get("name"));
                phone.setText(mList.get(position).get("mobilephone"));
                vip.setText(mList.get(position).get("member"));
                time.setText(mList.get(position).get("datetime"));
            }

            @Override
            public void onItemClick(View view, int position) {
            }
        };
        return holder;
    }
}
