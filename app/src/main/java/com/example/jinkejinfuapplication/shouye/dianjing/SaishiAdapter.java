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


public class SaishiAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public SaishiAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public SaishiAdapter(Context context) {
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
        final TextView  title,jieshao,state,jiangjin;

        View view= LayoutInflater.from(context).inflate(R.layout.item_zixun_saishi,parent,false);
        img_bk= (ImageView) view.findViewById(R.id.img1);
        jieshao= (TextView) view.findViewById(R.id.jieshao);
        title= (TextView) view.findViewById(R.id.title);
        state= (TextView) view.findViewById(R.id.state);
        jiangjin= (TextView) view.findViewById(R.id.jiangjin);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                x.image().bind(img_bk,mList.get(position).get("img"), ImageOptions.getimageOptions_img());
                title.setText(mList.get(position).get("name"));
                jieshao.setText(mList.get(position).get("introduction"));
                state.setText(mList.get(position).get("state"));
                jiangjin.setText(mList.get(position).get("money")+"元");
            }

            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(context, Saishi_xiangqActivity.class);
                intent.putExtra("id",mList.get(position).get("id"));
                context.startActivity(intent);
            }
        };
        return holder;
    }
}
