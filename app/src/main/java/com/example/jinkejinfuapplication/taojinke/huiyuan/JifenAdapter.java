package com.example.jinkejinfuapplication.taojinke.huiyuan;

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

public class JifenAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public JifenAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public JifenAdapter(Context context) {
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
        final TextView type,time,money,jieshao;
        View view= LayoutInflater.from(context).inflate(R.layout.item_huiyuan_mingxi,parent,false);
        type= (TextView) view.findViewById(R.id.type);
        time= (TextView) view.findViewById(R.id.time);
        money= (TextView) view.findViewById(R.id.money);
        jieshao= (TextView) view.findViewById(R.id.jieshao);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(int position) {
                switch (mList.get(position).get("sort")){
                    case "0":
                        type.setText("推广");
                        break;
                    case "1":
                        type.setText("签到");
                        break;
                    case "2":
                        type.setText("评论");
                        break;
                    case "3":
                        type.setText("抽奖");
                        break;
                }
                time.setText(mList.get(position).get("datetime"));
                jieshao.setText(mList.get(position).get("describe"));
                /*money.setText(mList.get(position).get("amount")+"分");*/
            }

            @Override
            public void onItemClick(View view, int position) {
            }
        };
        return holder;
    }
}
