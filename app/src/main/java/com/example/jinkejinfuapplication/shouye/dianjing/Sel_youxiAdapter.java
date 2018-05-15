package com.example.jinkejinfuapplication.shouye.dianjing;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.bean.TextBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/1.
 */
public class Sel_youxiAdapter extends RecyclerView.Adapter<Sel_youxiAdapter.MyViewHolder> {
    private List<TextBean> mName;
    private Context mContext;
    private TextView text;
    private int mSelectedPos =0;//实现单选  方法二，变量保存当前选中的position

    private RecyclerView mRv;//实现单选方法三： RecyclerView另一种定向刷新方法：
    public Sel_youxiAdapter(List<TextBean> mName, Context mContext, RecyclerView mRv) {
        this.mName = mName;
        this.mContext = mContext;
        this.mRv=mRv;
        //实现单选方法二： 设置数据集时，找到默认选中的pos
        for (int i = 0; i < mName.size(); i++) {
            if (mName.get(i).isSelected()) {
                mSelectedPos = i;
            }
        }
    }

    public List<TextBean> getmName() {
        return mName;
    }

    public void setmName(List<TextBean> mName) {
        this.mName = mName;
    }

    @Override
    public Sel_youxiAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        /*View view;
        view=View.inflate(mContext,R.layout.text_item,null);*/

        MyViewHolder holder=new MyViewHolder(LayoutInflater.from( mContext).inflate(R.layout.text_item_youxi,
                viewGroup, false));
        return holder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {
        myViewHolder.text.setSelected(mName.get(i).isSelected());
        TextBean textbean=mName.get(i);
        myViewHolder.text.setText(textbean.getName());
        myViewHolder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //实现单选方法三： RecyclerView另一种定向刷新方法：不会有白光一闪动画 也不会重复onBindVIewHolder
                MyViewHolder couponVH = (MyViewHolder) mRv.findViewHolderForLayoutPosition(mSelectedPos);
                if (couponVH != null) {//还在屏幕里
                    couponVH.text.setSelected(false);
                }else {
                    //add by 2016 11 22 for 一些极端情况，holder被缓存在Recycler的cacheView里，
                    //此时拿不到ViewHolder，但是也不会回调onBindViewHolder方法。所以add一个异常处理
                    notifyItemChanged(mSelectedPos);
                }
                mName.get(mSelectedPos).setSelected(false);//不管在不在屏幕里 都需要改变数据
                //设置新Item的勾选状态
                mSelectedPos = i;
                mName.get(mSelectedPos).setSelected(true);
                myViewHolder.text.setSelected(true);

                Saishi_shipingActivity activity= (Saishi_shipingActivity) mContext;
                activity.type=mName.get(i).getName();
                activity.redate();

            }
        });

    }




    @Override
    public int getItemCount() {
        return mName.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public MyViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);

        }
    }
}
