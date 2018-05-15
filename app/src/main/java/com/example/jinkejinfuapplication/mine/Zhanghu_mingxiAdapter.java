package com.example.jinkejinfuapplication.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.BaseRecycleAdapter;
import com.example.jinkejinfuapplication.base.BaseRecycleViewHolder;

import java.util.List;
import java.util.Map;

public class Zhanghu_mingxiAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public Zhanghu_mingxiAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public Zhanghu_mingxiAdapter(Context context) {
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
        final TextView type,time,money;
        View view= LayoutInflater.from(context).inflate(R.layout.item_zhanghu_mingxi,parent,false);
        type= (TextView) view.findViewById(R.id.type);
        time= (TextView) view.findViewById(R.id.time);
        money= (TextView) view.findViewById(R.id.money);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(int position) {
                type.setText(mList.get(position).get("sort"));
                time.setText(mList.get(position).get("datetime"));
                money.setText(mList.get(position).get("amount")+"元");
            }

            @Override
            public void onItemClick(View view, int position) {
            }
        };
        return holder;
    }
}
