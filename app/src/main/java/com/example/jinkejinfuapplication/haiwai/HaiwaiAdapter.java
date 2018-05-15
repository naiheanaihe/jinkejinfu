package com.example.jinkejinfuapplication.haiwai;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.shouye.Shouye_gameAdapter;
import com.example.jinkejinfuapplication.base.BaseRecycleAdapter;
import com.example.jinkejinfuapplication.base.BaseRecycleViewHolder;
import com.example.jinkejinfuapplication.bean.Haiwai_gameBean;
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.xiangq.ShouyouActivity;
import com.example.jinkejinfuapplication.xiangq.VRActivity;
import com.example.jinkejinfuapplication.xiangq.YeyouActivity;

import org.xutils.x;

import java.util.List;

public class HaiwaiAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Haiwai_gameBean> mList;

    public HaiwaiAdapter(Context context, List<Haiwai_gameBean> mList) {
        this.context = context;
        this.mList = mList;
    }
    public HaiwaiAdapter(Context context) {
        this.context = context;
    }

    public List<Haiwai_gameBean> getmList() {
        return mList;
    }

    public void setmList(List<Haiwai_gameBean> mList) {
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
        final RecyclerView rec_xiangsi;
        View view= LayoutInflater.from(context).inflate(R.layout.item_haiwai,parent,false);
        img_bk= (ImageView) view.findViewById(R.id.img_bk);
        img_icon= (ImageView) view.findViewById(R.id.img_icon);
        name= (TextView) view.findViewById(R.id.name);
        type= (TextView) view.findViewById(R.id.type);
        jieshao= (TextView) view.findViewById(R.id.jieshao);
        xiazai= (Button) view.findViewById(R.id.xiazai);
        rec_xiangsi= (RecyclerView) view.findViewById(R.id.rec_xiangshi);

        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                x.image().bind(img_bk,mList.get(position).getImg_bk(), ImageOptions.getimageOptions_img());
                x.image().bind(img_icon,mList.get(position).getImg_icon(), ImageOptions.getimageOptions_img());
                name.setText(mList.get(position).getName());
                type.setText(mList.get(position).getType());
                jieshao.setText(mList.get(position).getJiashao());

                LinearLayoutManager linManager1=new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);
                rec_xiangsi.setLayoutManager(linManager1);
                Shouye_gameAdapter shouye_gameAdapter=new Shouye_gameAdapter(context, mList.get(position).getGameList());
                rec_xiangsi.setAdapter(shouye_gameAdapter);

                xiazai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mList.get(position).getZhujitype().equals("手游")){
                            Intent intent=new Intent(context, ShouyouActivity.class);
                            intent.putExtra("id",mList.get(position).getId());
                            context.startActivity(intent);
                        }else if (mList.get(position).getZhujitype().equals("页游")){
                            Intent intent=new Intent(context, YeyouActivity.class);
                            intent.putExtra("id",mList.get(position).getId());
                            context.startActivity(intent);
                        }else {
                            Intent intent=new Intent(context, VRActivity.class);
                            intent.putExtra("id",mList.get(position).getId());
                            context.startActivity(intent);
                        }
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
