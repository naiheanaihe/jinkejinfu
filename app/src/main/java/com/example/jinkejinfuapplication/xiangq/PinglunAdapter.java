package com.example.jinkejinfuapplication.xiangq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.BaseRecycleAdapter;
import com.example.jinkejinfuapplication.base.BaseRecycleViewHolder;
import com.example.jinkejinfuapplication.bean.Shouye_gameBean;
import com.example.jinkejinfuapplication.utils.ImageOptions;

import org.xutils.x;

import java.util.List;
import java.util.Map;

public class PinglunAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public PinglunAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public PinglunAdapter(Context context) {
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
        final ImageView img_icon;
        final TextView name,time,pinglun,zan;
        View view= LayoutInflater.from(context).inflate(R.layout.item_pinglun,parent,false);
        img_icon= (ImageView) view.findViewById(R.id.img_icon);
        name= (TextView) view.findViewById(R.id.name);
        time= (TextView) view.findViewById(R.id.time);
        pinglun= (TextView) view.findViewById(R.id.pinglun);
        //zan= (TextView) view.findViewById(R.id.zan_number);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(int position) {
                x.image().bind(img_icon,mList.get(position).get("pic"), ImageOptions.getimageOptions());
                name.setText(mList.get(position).get("name"));
                time.setText(mList.get(position).get("datetime"));
                pinglun.setText(mList.get(position).get("content"));
                //zan.setText(mList.get(position).get("like"));

            }

            @Override
            public void onItemClick(View view, int position) {
            }
        };
        return holder;
    }
}
