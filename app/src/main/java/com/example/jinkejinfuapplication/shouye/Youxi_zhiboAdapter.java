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

import org.xutils.x;

import java.util.List;
import java.util.Map;


public class Youxi_zhiboAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public Youxi_zhiboAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public Youxi_zhiboAdapter(Context context) {
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
        final TextView mytitle,redu,biaoqian,dizhi;

        View view= LayoutInflater.from(context).inflate(R.layout.item_youxi_zhibo,parent,false);
        img_bk= (ImageView) view.findViewById(R.id.img);
        mytitle= (TextView) view.findViewById(R.id.mytitle);
        redu= (TextView) view.findViewById(R.id.redu);
        biaoqian= (TextView) view.findViewById(R.id.biaoqian);
        dizhi= (TextView) view.findViewById(R.id.dizhi);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                x.image().bind(img_bk,mList.get(position).get("img"), ImageOptions.getimageOptions_img());
                mytitle.setText(mList.get(position).get("anchor_name"));
                redu.setText(mList.get(position).get("hot_num")+"热度");
                biaoqian.setText(mList.get(position).get("label"));
                dizhi.setText(mList.get(position).get("city"));
            }

            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(context, Zhibo_xiangqActivity.class);
                intent.putExtra("url",mList.get(position).get("links"));
                context.startActivity(intent);
            }
        };
        return holder;
    }
}
