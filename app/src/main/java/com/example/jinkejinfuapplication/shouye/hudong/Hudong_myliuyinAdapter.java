package com.example.jinkejinfuapplication.shouye.hudong;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.BaseRecycleAdapter;
import com.example.jinkejinfuapplication.base.BaseRecycleViewHolder;
import com.example.jinkejinfuapplication.bean.MyliuyinBean;
import com.example.jinkejinfuapplication.utils.ImageOptions;

import org.xutils.x;

import java.util.List;
import java.util.Map;

public class Hudong_myliuyinAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<MyliuyinBean> mList;

    public Hudong_myliuyinAdapter(Context context, List<MyliuyinBean> mList) {
        this.context = context;
        this.mList = mList;
    }
    public Hudong_myliuyinAdapter(Context context) {
        this.context = context;
    }

    public List<MyliuyinBean> getmList() {
        return mList;
    }

    public void setmList(List<MyliuyinBean> mList) {
        this.mList = mList;
    }

    @Override
    protected int getDataCount() {
        return mList==null?0:mList.size();
    }

    @Override
    protected BaseRecycleViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
        final TextView neirong,mytime,num_zan,num_chakan;
        final ImageView img_icon,shanchu;
        final RecyclerView rec;
        View view= LayoutInflater.from(context).inflate(R.layout.item_hudong_myliuyin,parent,false);
        neirong= (TextView) view.findViewById(R.id.myname);
        mytime= (TextView) view.findViewById(R.id.mytime);
        num_zan= (TextView) view.findViewById(R.id.num_zan);
        num_chakan= (TextView) view.findViewById(R.id.num_chakan);
        img_icon= (ImageView) view.findViewById(R.id.myicon);
        shanchu= (ImageView) view.findViewById(R.id.shanchu);
        rec= (RecyclerView) view.findViewById(R.id.rec);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                x.image().bind(img_icon,mList.get(position).getPic(), ImageOptions.getimageOptions());
                neirong.setText(mList.get(position).getMessage());
                mytime.setText(mList.get(position).getDatetime());
                num_zan.setText(mList.get(position).getLike());
                num_chakan.setText(mList.get(position).getReplynum());

                LinearLayoutManager linManager1=new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
                rec.setLayoutManager(linManager1);
                Hudong_myhuati_huifuAdapter shouye_gameAdapter=new Hudong_myhuati_huifuAdapter(context, mList.get(position).getGameList());
                rec.setAdapter(shouye_gameAdapter);

            }

            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(context,LiuyinActivity.class);
                intent.putExtra("id",mList.get(position).getId());
                context.startActivity(intent);
            }
        };
        return holder;
    }
}
