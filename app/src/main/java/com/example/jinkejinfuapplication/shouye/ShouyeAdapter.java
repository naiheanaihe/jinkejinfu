package com.example.jinkejinfuapplication.shouye;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.BaseRecycleAdapter;
import com.example.jinkejinfuapplication.base.BaseRecycleViewHolder;
import com.example.jinkejinfuapplication.bean.Shouye_gameBean;
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.xiangq.ShipingActivity;
import com.example.jinkejinfuapplication.xiangq.ShouyouActivity;
import com.example.jinkejinfuapplication.xiangq.VRActivity;
import com.example.jinkejinfuapplication.xiangq.YeyouActivity;
import com.example.jinkejinfuapplication.xiangq.ZixunActivity;

import org.xutils.x;

import java.util.List;

public class ShouyeAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Shouye_gameBean> mList;

    public ShouyeAdapter(Context context, List<Shouye_gameBean> mList) {
        this.context = context;
        this.mList = mList;
    }
    public ShouyeAdapter(Context context) {
        this.context = context;
    }

    public List<Shouye_gameBean> getmList() {
        return mList;
    }

    public void setmList(List<Shouye_gameBean> mList) {
        this.mList = mList;
    }

    @Override
    protected int getDataCount() {
        return mList.size();
    }

    @Override
    protected BaseRecycleViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
        final ImageView img_bk,img_icon,img1,img2,img3;
        final TextView name,type,jieshao,time;
        final Button xiazai;
        final RecyclerView rec_xiangsi;
        final LinearLayout lin_shiping;
        final RelativeLayout rel_news;
        final TextView paihang;
        final LinearLayout lin_more,xuxian1,xuxian2;
        View view= LayoutInflater.from(context).inflate(R.layout.item_shouye,parent,false);
        img_bk= (ImageView) view.findViewById(R.id.img_bk);
        img_icon= (ImageView) view.findViewById(R.id.img_icon);
        img1= (ImageView) view.findViewById(R.id.img1);
        img2= (ImageView) view.findViewById(R.id.img2);
        img3= (ImageView) view.findViewById(R.id.img3);
        name= (TextView) view.findViewById(R.id.name);
        type= (TextView) view.findViewById(R.id.type);
        jieshao= (TextView) view.findViewById(R.id.jieshao);
        time= (TextView) view.findViewById(R.id.time);
        xiazai= (Button) view.findViewById(R.id.xiazai);
        rec_xiangsi= (RecyclerView) view.findViewById(R.id.rec_xiangshi);
        lin_shiping= (LinearLayout) view.findViewById(R.id.lin_shiping);
        rel_news= (RelativeLayout) view.findViewById(R.id.rel_news);
        paihang= (TextView) view.findViewById(R.id.paihang);
        lin_more= (LinearLayout) view.findViewById(R.id.lin_more);
        xuxian1= (LinearLayout) view.findViewById(R.id.xuxian1);
        xuxian2= (LinearLayout) view.findViewById(R.id.xuxian2);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                switch (position){
                    case 0:
                        paihang.setBackgroundResource(R.drawable.img_one);
                        paihang.setText("");
                        lin_more.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        paihang.setBackgroundResource(R.drawable.img_two);
                        paihang.setText("");
                        lin_more.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        paihang.setBackgroundResource(R.drawable.img_three);
                        paihang.setText("");
                        lin_more.setVisibility(View.VISIBLE);
                        break;
                    default:
                        paihang.setBackgroundResource(R.color.huise_paihang);
                        paihang.setText((position+1)+"");
                        lin_more.setVisibility(View.GONE);
                        break;
                }

                x.image().bind(img_bk,mList.get(position).getImg_bk(), ImageOptions.getimageOptions_img());
                x.image().bind(img_icon,mList.get(position).getImg_icon(), ImageOptions.getimageOptions_img());

                if (mList.get(position).isHasnews()){
                    rel_news.setVisibility(View.VISIBLE);
                    xuxian1.setVisibility(View.VISIBLE);
                    x.image().bind(img1,mList.get(position).getImg1(), ImageOptions.getimageOptions_img());
                    jieshao.setText(mList.get(position).getJiashao());
                    time.setText(mList.get(position).getTime());
                }else
                {
                    rel_news.setVisibility(View.GONE);
                    xuxian1.setVisibility(View.GONE);
                }
                rel_news.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(context, ZixunActivity.class);
                        intent.putExtra("gameId",mList.get(position).getId());
                        intent.putExtra("id",mList.get(position).getZixunid());
                        context.startActivity(intent);
                    }
                });

                switch (mList.get(position).getHasshiping()){
                    case 0:
                        lin_shiping.setVisibility(View.GONE);
                        break;
                    case 1:
                        lin_shiping.setVisibility(View.VISIBLE);
                        x.image().bind(img2,mList.get(position).getImg2(), ImageOptions.getimageOptions_img());
                        img3.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        lin_shiping.setVisibility(View.VISIBLE);
                        x.image().bind(img2,mList.get(position).getImg2(), ImageOptions.getimageOptions_img());
                        x.image().bind(img3,mList.get(position).getImg3(), ImageOptions.getimageOptions_img());
                        img3.setVisibility(View.VISIBLE);

                        break;
                }
                img2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(context, ShipingActivity.class);
                        intent.putExtra("id",mList.get(position).getShipingid1());
                        intent.putExtra("gameId",mList.get(position).getId());
                        context.startActivity(intent);
                    }
                });
                img3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(context, ShipingActivity.class);
                        intent.putExtra("id",mList.get(position).getShipingid2());
                        intent.putExtra("gameId",mList.get(position).getId());
                        context.startActivity(intent);
                    }
                });

                name.setText(mList.get(position).getName());
                type.setText(mList.get(position).getType());
                /*LinearLayoutManager linManager1=new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);
                rec_xiangsi.setLayoutManager(linManager1);
                Shouye_gameAdapter shouye_gameAdapter=new Shouye_gameAdapter(context, mList.get(position).getGameList());
                rec_xiangsi.setAdapter(shouye_gameAdapter);*/

                xiazai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mList.get(position).getZhujitype().equals("手游")){
                            Intent intent=new Intent(context, ShouyouActivity.class);
                            intent.putExtra("id",mList.get(position).getId());
                            context.startActivity(intent);
                        }else  if (mList.get(position).getZhujitype().equals("页游")){
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
