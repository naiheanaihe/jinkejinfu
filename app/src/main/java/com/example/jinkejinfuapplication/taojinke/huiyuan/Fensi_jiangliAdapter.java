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

public class Fensi_jiangliAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public Fensi_jiangliAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public Fensi_jiangliAdapter(Context context) {
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
        final TextView dengji,shouyi,num,num1;
        View view= LayoutInflater.from(context).inflate(R.layout.item_fensi_jiangli,parent,false);
        dengji= (TextView) view.findViewById(R.id.dengji);
        shouyi= (TextView) view.findViewById(R.id.shouyi);
        num= (TextView) view.findViewById(R.id.num);
        num1= (TextView) view.findViewById(R.id.num1);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(int position) {
                dengji.setText("累计"+mList.get(position).get("FxLevel")+"代粉丝");
                shouyi.setText("累计收益"+mList.get(position).get("money")+"元");
                num.setText(mList.get(position).get("num")+"人");
            }

            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(context,Fensi_leijiActivity.class);
                intent.putExtra("dengji",mList.get(position).get("FxLevel"));
                context.startActivity(intent);
            }
        };
        return holder;
    }
}
