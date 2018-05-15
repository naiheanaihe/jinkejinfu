package com.example.jinkejinfuapplication.VR;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.BaseRecycleAdapter;
import com.example.jinkejinfuapplication.base.BaseRecycleViewHolder;
import com.example.jinkejinfuapplication.bean.VR_gameBean;
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.xiangq.VRActivity;

import org.xutils.x;

import java.util.List;

public class VRAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<VR_gameBean> mList;

    public VRAdapter(Context context, List<VR_gameBean> mList) {
        this.context = context;
        this.mList = mList;
    }
    public VRAdapter(Context context) {
        this.context = context;
    }

    public List<VR_gameBean> getmList() {
        return mList;
    }

    public void setmList(List<VR_gameBean> mList) {
        this.mList = mList;
    }

    @Override
    protected int getDataCount() {
        return mList.size();
    }

    @Override
    protected BaseRecycleViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
        final ImageView img_bk,img_icon;
        final TextView name,type,jieshao;
        final Button xiazai;
        View view= LayoutInflater.from(context).inflate(R.layout.item_vr,parent,false);
        img_bk= (ImageView) view.findViewById(R.id.img_bk);
        img_icon= (ImageView) view.findViewById(R.id.img_icon);
        name= (TextView) view.findViewById(R.id.name);
        type= (TextView) view.findViewById(R.id.type);
        jieshao= (TextView) view.findViewById(R.id.jieshao);
        xiazai= (Button) view.findViewById(R.id.xiazai);

        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                x.image().bind(img_bk,mList.get(position).getImg_bk(), ImageOptions.getimageOptions_img());
                x.image().bind(img_icon,mList.get(position).getImg_icon(), ImageOptions.getimageOptions_img());
                name.setText(mList.get(position).getName());
                type.setText(mList.get(position).getType());
                jieshao.setText(mList.get(position).getJiashao());
                xiazai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(context, VRActivity.class);
                        intent.putExtra("id",mList.get(position).getId());
                        context.startActivity(intent);
                    }
                });
            }

            @Override
            public void onItemClick(View view, int position) {
            }
        };
        return holder;
    }
}
