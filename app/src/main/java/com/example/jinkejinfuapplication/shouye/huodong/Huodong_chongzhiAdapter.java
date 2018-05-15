package com.example.jinkejinfuapplication.shouye.huodong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.BaseRecycleAdapter;
import com.example.jinkejinfuapplication.base.BaseRecycleViewHolder;
import com.example.jinkejinfuapplication.utils.ImageOptions;

import org.xutils.x;

import java.util.List;
import java.util.Map;

public class Huodong_chongzhiAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public Huodong_chongzhiAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public Huodong_chongzhiAdapter(Context context) {
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
        final TextView myname,feiyong,mytime;
        final ImageView img_icon;
        View view= LayoutInflater.from(context).inflate(R.layout.item_huodong_chongzhi,parent,false);
        myname= (TextView) view.findViewById(R.id.myname);
        feiyong= (TextView) view.findViewById(R.id.feiyong);
        mytime= (TextView) view.findViewById(R.id.mytime);
        img_icon= (ImageView) view.findViewById(R.id.myicon);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                x.image().bind(img_icon,mList.get(position).get("game_pic"), ImageOptions.getimageOptions_img());
                myname.setText(mList.get(position).get("game_name"));
                feiyong.setText(mList.get(position).get("amount")+"元");
                mytime.setText(mList.get(position).get("datetime"));
            }

            @Override
            public void onItemClick(View view, int position) {
            }
        };
        return holder;
    }
}
