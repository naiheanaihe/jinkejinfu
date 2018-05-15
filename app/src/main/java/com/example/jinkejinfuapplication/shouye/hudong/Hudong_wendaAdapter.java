package com.example.jinkejinfuapplication.shouye.hudong;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.BaseRecycleAdapter;
import com.example.jinkejinfuapplication.base.BaseRecycleViewHolder;
import com.example.jinkejinfuapplication.utils.ImageOptions;

import org.xutils.x;

import java.util.List;
import java.util.Map;

public class Hudong_wendaAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public Hudong_wendaAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public Hudong_wendaAdapter(Context context) {
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
        final TextView xuhao,mytitle,jieshao,chakan_number,pinglun_number;
        final ImageView img_icon;
        final ImageButton huida;
        View view= LayoutInflater.from(context).inflate(R.layout.item_hudong_wenda,parent,false);
        xuhao= (TextView) view.findViewById(R.id.xuhao);
        mytitle= (TextView) view.findViewById(R.id.mytitle);
        jieshao= (TextView) view.findViewById(R.id.jieshao);
        chakan_number= (TextView) view.findViewById(R.id.chakan_number);
        pinglun_number= (TextView) view.findViewById(R.id.pinglun_number);
        img_icon= (ImageView) view.findViewById(R.id.img);
        huida= (ImageButton) view.findViewById(R.id.huida);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                x.image().bind(img_icon,mList.get(position).get("img1"), ImageOptions.getimageOptions_touming());
                xuhao.setText((position+1)+"");
                mytitle.setText(mList.get(position).get("title"));
                jieshao.setText(mList.get(position).get("content"));
                chakan_number.setText(mList.get(position).get("clicknum"));
                pinglun_number.setText(mList.get(position).get("commentnum"));
                huida.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }

            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(context,WendaActivity.class);
                intent.putExtra("id",mList.get(position).get("id"));
                context.startActivity(intent);
            }
        };
        return holder;
    }
}
