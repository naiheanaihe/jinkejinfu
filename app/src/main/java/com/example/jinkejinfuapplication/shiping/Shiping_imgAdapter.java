package com.example.jinkejinfuapplication.shiping;

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


public class Shiping_imgAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public Shiping_imgAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public Shiping_imgAdapter(Context context) {
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
        final ImageView img_bk;
        final TextView mytitle;

        View view= LayoutInflater.from(context).inflate(R.layout.item_shipingimg,parent,false);
        img_bk= (ImageView) view.findViewById(R.id.img);
        mytitle= (TextView) view.findViewById(R.id.mytitle);

        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                x.image().bind(img_bk,mList.get(position).get("background"), ImageOptions.getimageOptions_img());
                mytitle.setText(mList.get(position).get("title"));
            }

            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(context, ShipingActivity.class);
                intent.putExtra("id",mList.get(position).get("id"));
                intent.putExtra("gameId",mList.get(position).get("gameId"));
                context.startActivity(intent);
            }
        };
        return holder;
    }
}
