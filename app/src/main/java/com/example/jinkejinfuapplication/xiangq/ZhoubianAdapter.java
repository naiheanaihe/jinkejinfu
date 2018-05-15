package com.example.jinkejinfuapplication.xiangq;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinkejinfuapplication.MainActivity;
import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.BaseRecycleAdapter;
import com.example.jinkejinfuapplication.base.BaseRecycleViewHolder;
import com.example.jinkejinfuapplication.bean.Shouye_gameBean;
import com.example.jinkejinfuapplication.mine.LoginActivity;
import com.example.jinkejinfuapplication.utils.ImageOptions;

import org.xutils.x;

import java.util.List;
import java.util.Map;

public class ZhoubianAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public ZhoubianAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public ZhoubianAdapter(Context context) {
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
        final TextView name,jiage;
        View view= LayoutInflater.from(context).inflate(R.layout.item_zhoubian,parent,false);
        img_bk= (ImageView) view.findViewById(R.id.img_bk);
        name= (TextView) view.findViewById(R.id.name);
        jiage= (TextView) view.findViewById(R.id.jiage);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(int position) {
                x.image().bind(img_bk,mList.get(position).get("commodity_img"), ImageOptions.getimageOptions_img());
                name.setText(mList.get(position).get("commodity_name"));
                jiage.setText("¥ "+mList.get(position).get("commodity_price"));
            }

            @Override
            public void onItemClick(View view, int position) {
                if (MainActivity.LOGINSTATE==MainActivity.LOGINSTATE_NOTLOGIN){
                    Intent intent=new Intent(context,LoginActivity.class);
                    context.startActivity(intent);
                    return;
                }
                Intent intent=new Intent(context,ZhoubianActivity.class);
                intent.putExtra("url","http://www.goldcs.net/shop/detail?id="+mList.get(position).get("commodity_Id")+"&app=goldcs.net&userId="+ MyApplication.getInstance().getYonghuBean().getUserId());
                context.startActivity(intent);
            }
        };
        return holder;
    }
}
