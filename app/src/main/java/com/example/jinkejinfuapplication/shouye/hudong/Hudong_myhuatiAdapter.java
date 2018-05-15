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
import java.util.Map;

public class Hudong_myhuatiAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<MyhuatiBean> mList;

    public Hudong_myhuatiAdapter(Context context, List<MyhuatiBean> mList) {
        this.context = context;
        this.mList = mList;
    }
    public Hudong_myhuatiAdapter(Context context) {
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
        final TextView mytitle,zuozhe,mytime,neirong,reti;
        final ImageView img_icon,img1,img2;
        final LinearLayout lin_img;
        final RecyclerView rec;
        View view= LayoutInflater.from(context).inflate(R.layout.item_hudong_myhuati,parent,false);
        reti= (TextView) view.findViewById(R.id.reti);
        mytitle= (TextView) view.findViewById(R.id.mytitle);
        mytime= (TextView) view.findViewById(R.id.mytime);
        neirong= (TextView) view.findViewById(R.id.neirong);
        img_icon= (ImageView) view.findViewById(R.id.myicon);
        rec= (RecyclerView) view.findViewById(R.id.rec);

        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                x.image().bind(img_icon,mList.get(position).getPic(), ImageOptions.getimageOptions());
                mytitle.setText(mList.get(position).getTitle());
                mytime.setText(mList.get(position).getDatetime());
                neirong.setText(mList.get(position).getContent());

                if (mList.get(position).getReti().equals("0")){
                    reti.setVisibility(View.GONE);
                }else {
                    reti.setVisibility(View.VISIBLE);
                }

                LinearLayoutManager linManager1=new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
                rec.setLayoutManager(linManager1);
                Hudong_myhuati_huifuAdapter shouye_gameAdapter=new Hudong_myhuati_huifuAdapter(context, mList.get(position).getGameList());
                rec.setAdapter(shouye_gameAdapter);
            }

            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(context,HuatiActivity.class);
                intent.putExtra("id",mList.get(position).getId());
                context.startActivity(intent);
            }
        };
        return holder;
    }
}
