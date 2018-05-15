package com.example.jinkejinfuapplication.taojinke.hehuoren;

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

public class MyhehuorenAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public MyhehuorenAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public MyhehuorenAdapter(Context context) {
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
        final TextView name,type;
        View view= LayoutInflater.from(context).inflate(R.layout.item_myhehuoren,parent,false);
        name= (TextView) view.findViewById(R.id.name);
        type= (TextView) view.findViewById(R.id.type);

        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(int position) {
                name.setText(mList.get(position).get("name"));
                type.setText(mList.get(position).get("rank"));
            }

            @Override
            public void onItemClick(View view, int position) {
            }
        };
        return holder;
    }
}
