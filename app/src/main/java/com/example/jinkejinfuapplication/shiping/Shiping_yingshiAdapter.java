package com.example.jinkejinfuapplication.shiping;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinkejinfuapplication.MainActivity;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.BaseRecycleAdapter;
import com.example.jinkejinfuapplication.base.BaseRecycleViewHolder;
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.utils.ToastUtil;

import org.xutils.x;

import java.util.List;
import java.util.Map;


public class Shiping_yingshiAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public Shiping_yingshiAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public Shiping_yingshiAdapter(Context context) {
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
        final TextView num,title;

        View view= LayoutInflater.from(context).inflate(R.layout.item_shipingyingshi,parent,false);
        img_bk= (ImageView) view.findViewById(R.id.img);
        title= (TextView) view.findViewById(R.id.mytitle);
        num= (TextView) view.findViewById(R.id.num);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                x.image().bind(img_bk,mList.get(position).get("cover"), ImageOptions.getimageOptions_img());
                title.setText(mList.get(position).get("movie_name"));
                num.setText(mList.get(position).get("update_num")+"集");
                if (mList.get(position).get("yingshi").equals("电影"))
                {
                    num.setVisibility(View.GONE);
                }else {
                    num.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onItemClick(View view, int position) {
                if (MainActivity.LOGINSTATE==MainActivity.LOGINSTATE_NOTLOGIN){
                    ToastUtil.show(context,"请先登录!");
                }else {
                    Intent intent = new Intent(context, DianyingActivity.class);
                    intent.putExtra("id",mList.get(position).get("id"));
                    context.startActivity(intent);
                }

            }
        };
        return holder;
    }
}
