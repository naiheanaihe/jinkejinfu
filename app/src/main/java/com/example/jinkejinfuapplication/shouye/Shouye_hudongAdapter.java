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
import com.example.jinkejinfuapplication.shouye.hudong.HuatiActivity;
import com.example.jinkejinfuapplication.shouye.hudong.LiuyinActivity;
import com.example.jinkejinfuapplication.shouye.hudong.WendaActivity;
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.xiangq.ShouyouActivity;
import com.example.jinkejinfuapplication.xiangq.VRActivity;
import com.example.jinkejinfuapplication.xiangq.YeyouActivity;

import org.xutils.x;

import java.util.List;
import java.util.Map;

public class Shouye_hudongAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public Shouye_hudongAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public Shouye_hudongAdapter(Context context) {
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
        final ImageView img;
        final TextView title,zan_number,chakan_number,time;
        View view= LayoutInflater.from(context).inflate(R.layout.item_shouye_hudong,parent,false);
        img= (ImageView) view.findViewById(R.id.img);
        title= (TextView) view.findViewById(R.id.mytitle);
        zan_number= (TextView) view.findViewById(R.id.zan_number);
        chakan_number= (TextView) view.findViewById(R.id.chakan_number);
        time= (TextView) view.findViewById(R.id.time);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(int position) {
                x.image().bind(img,mList.get(position).get("img"), ImageOptions.getimageOptions_img());
                title.setText(mList.get(position).get("content"));
                zan_number.setText(mList.get(position).get("likenum"));
                chakan_number.setText(mList.get(position).get("clicknum"));
                time.setText(mList.get(position).get("time"));
            }

            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent();
                switch (mList.get(position).get("sort")){
                    case "topic":
                        intent.setClass(context, HuatiActivity.class);
                        break;
                    case "topic1":
                        intent.setClass(context, WendaActivity.class);
                        break;
                    case "message":
                        intent.setClass(context, LiuyinActivity.class);
                        break;
                }
                intent.putExtra("id",mList.get(position).get("id"));
                context.startActivity(intent);


            }
        };
        return holder;
    }
}
