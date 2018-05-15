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
import com.example.jinkejinfuapplication.xiangq.ZixunActivity;

import org.xutils.x;

import java.util.List;
import java.util.Map;


public class Saishi_xinwenAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public Saishi_xinwenAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public Saishi_xinwenAdapter(Context context) {
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
        final TextView  title,mytime,chakan_number;

        View view= LayoutInflater.from(context).inflate(R.layout.item_zixun_saishi_xinwen,parent,false);
        img_bk= (ImageView) view.findViewById(R.id.img1);

        title= (TextView) view.findViewById(R.id.title);
        mytime= (TextView) view.findViewById(R.id.mytime);
        chakan_number= (TextView) view.findViewById(R.id.chakan_number);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                x.image().bind(img_bk,mList.get(position).get("background"), ImageOptions.getimageOptions_img());
                title.setText(mList.get(position).get("title"));
                mytime.setText(mList.get(position).get("datetime"));
                chakan_number.setText(mList.get(position).get("clicknum"));

            }

            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(context, ZixunActivity.class);
                intent.putExtra("id",mList.get(position).get("id"));
                intent.putExtra("gameId","0");
                context.startActivity(intent);
            }
        };
        return holder;
    }
}
