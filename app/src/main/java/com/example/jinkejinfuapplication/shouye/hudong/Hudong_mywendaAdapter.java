package com.example.jinkejinfuapplication.shouye.hudong;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.BaseRecycleAdapter;
import com.example.jinkejinfuapplication.base.BaseRecycleViewHolder;
import com.example.jinkejinfuapplication.bean.MyhuatiBean;
import com.example.jinkejinfuapplication.utils.ImageOptions;

import org.xutils.x;

import java.util.List;

public class Hudong_mywendaAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<MyhuatiBean> mList;

    public Hudong_mywendaAdapter(Context context, List<MyhuatiBean> mList) {
        this.context = context;
        this.mList = mList;
    }
    public Hudong_mywendaAdapter(Context context) {
        this.context = context;
    }

    public List<MyhuatiBean> getmList() {
        return mList;
    }

    public void setmList(List<MyhuatiBean> mList) {
        this.mList = mList;
    }

    @Override
    protected int getDataCount() {
        return mList==null?0:mList.size();
    }

    @Override
    protected BaseRecycleViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
        final TextView mytitle,mytime,myname,num_zan,num_chakan;
        final ImageView img_icon,img1,img2;
        final RecyclerView rec;
        View view= LayoutInflater.from(context).inflate(R.layout.item_hudong_mywenda,parent,false);
        mytitle= (TextView) view.findViewById(R.id.mytitle);
        mytime= (TextView) view.findViewById(R.id.mytime);
        myname= (TextView) view.findViewById(R.id.myname);
        num_zan= (TextView) view.findViewById(R.id.num_zan);
        num_chakan= (TextView) view.findViewById(R.id.num_chakan);
        rec= (RecyclerView) view.findViewById(R.id.rec);

        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {

                mytitle.setText(mList.get(position).getTitle());
                mytime.setText(mList.get(position).getDatetime());
                myname.setText(mList.get(position).getName());
                num_zan.setText(mList.get(position).getLikenum());
                num_chakan.setText(mList.get(position).getClicknum());

                LinearLayoutManager linManager1=new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
                rec.setLayoutManager(linManager1);
                Hudong_myhuati_huifuAdapter shouye_gameAdapter=new Hudong_myhuati_huifuAdapter(context, mList.get(position).getGameList());
                rec.setAdapter(shouye_gameAdapter);
            }

            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(context,WendaActivity.class);
                intent.putExtra("id",mList.get(position).getId());
                context.startActivity(intent);
            }
        };
        return holder;
    }
}
