package com.example.jinkejinfuapplication.shouye.dianjing;

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


public class Saishi_shipingAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public Saishi_shipingAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public Saishi_shipingAdapter(Context context) {
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
        final ImageView img_bk;
        final TextView  mytitle,mytime;

        View view= LayoutInflater.from(context).inflate(R.layout.item_zixun_saishi_shiping,parent,false);
        img_bk= (ImageView) view.findViewById(R.id.img);

        mytitle= (TextView) view.findViewById(R.id.mytitle);
        mytime= (TextView) view.findViewById(R.id.mytime);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                x.image().bind(img_bk,mList.get(position).get("background"), ImageOptions.getimageOptions_img());
                mytitle.setText(mList.get(position).get("title"));
                mytime.setText(mList.get(position).get("duration"));

            }

            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(context, ShipingActivity.class);
                intent.putExtra("id",mList.get(position).get("id"));
                intent.putExtra("gameId","0");
                context.startActivity(intent);
            }
        };
        return holder;
    }
}
