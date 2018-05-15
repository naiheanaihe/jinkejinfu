package com.example.jinkejinfuapplication.shouye.hudong;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.BaseRecycleAdapter;
import com.example.jinkejinfuapplication.base.BaseRecycleViewHolder;
import com.example.jinkejinfuapplication.utils.ImageOptions;

import org.xutils.x;

import java.util.List;
import java.util.Map;

public class Hudong_liuyinAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public Hudong_liuyinAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public Hudong_liuyinAdapter(Context context) {
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
        final TextView myname;
        final ImageView img_icon;
        View view= LayoutInflater.from(context).inflate(R.layout.item_hudong_liuyin,parent,false);

        myname= (TextView) view.findViewById(R.id.myname);
        img_icon= (ImageView) view.findViewById(R.id.myicon);

        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                x.image().bind(img_icon,mList.get(position).get("photo"), ImageOptions.getimageOptions());
                myname.setText(mList.get(position).get("name"));

            }

            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(context,LiuyinActivity.class);
                intent.putExtra("id",mList.get(position).get("id"));
                context.startActivity(intent);
            }
        };
        return holder;
    }
}
