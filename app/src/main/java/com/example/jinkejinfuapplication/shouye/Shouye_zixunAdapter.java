package com.example.jinkejinfuapplication.shouye;

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
import com.example.jinkejinfuapplication.xiangq.ZixunActivity;

import org.xutils.x;

import java.util.List;
import java.util.Map;


public class Shouye_zixunAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public Shouye_zixunAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public Shouye_zixunAdapter(Context context) {
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
        final TextView jieshao,zuozhe,time,zan,chakan;

        View view= LayoutInflater.from(context).inflate(R.layout.item_zixun_shouye,parent,false);
        img_bk= (ImageView) view.findViewById(R.id.img1);
        jieshao= (TextView) view.findViewById(R.id.jieshao);
        zuozhe= (TextView) view.findViewById(R.id.zuozhe);
        time= (TextView) view.findViewById(R.id.time);
        zan= (TextView) view.findViewById(R.id.zan_number);
        chakan= (TextView) view.findViewById(R.id.chakan_number);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                x.image().bind(img_bk,mList.get(position).get("background"), ImageOptions.getimageOptions_img());
                jieshao.setText(mList.get(position).get("title"));
                zuozhe.setText(mList.get(position).get("name"));
                time.setText(mList.get(position).get("datetime"));
                zan.setText(mList.get(position).get("likenum"));
                chakan.setText(mList.get(position).get("clicknum"));
            }

            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(context, ZixunActivity.class);
                intent.putExtra("id",mList.get(position).get("id"));
                intent.putExtra("gameId",mList.get(position).get("gameId"));
                context.startActivity(intent);
            }
        };
        return holder;
    }
}
