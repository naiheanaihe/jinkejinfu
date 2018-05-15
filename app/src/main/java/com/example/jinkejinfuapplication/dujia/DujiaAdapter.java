package com.example.jinkejinfuapplication.dujia;

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
import com.example.jinkejinfuapplication.bean.Dujia_gameBean;
import com.example.jinkejinfuapplication.bean.VR_gameBean;
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.xiangq.ShouyouActivity;
import com.example.jinkejinfuapplication.xiangq.VRActivity;
import com.example.jinkejinfuapplication.xiangq.YeyouActivity;

import org.xutils.x;

import java.util.List;

public class DujiaAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Dujia_gameBean> mList;

    public DujiaAdapter(Context context, List<Dujia_gameBean> mList) {
        this.context = context;
        this.mList = mList;
    }
    public DujiaAdapter(Context context) {
        this.context = context;
    }

    public List<Dujia_gameBean> getmList() {
        return mList;
    }

    public void setmList(List<Dujia_gameBean> mList) {
        this.mList = mList;
    }

    @Override
    protected int getDataCount() {
        return mList.size();
    }

    @Override
    protected BaseRecycleViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
        final ImageView img_bk,img_icon;
        final TextView name,type;

        View view= LayoutInflater.from(context).inflate(R.layout.item_dujia,parent,false);
        img_bk= (ImageView) view.findViewById(R.id.img);
        img_icon= (ImageView) view.findViewById(R.id.img_pic);
        name= (TextView) view.findViewById(R.id.name);
        type= (TextView) view.findViewById(R.id.type);

        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                x.image().bind(img_bk,mList.get(position).getImg_bk(), ImageOptions.getimageOptions_img());
                x.image().bind(img_icon,mList.get(position).getImg_icon(), ImageOptions.getimageOptions_img());
                name.setText(mList.get(position).getName());
                type.setText(mList.get(position).getType());

            }

            @Override
            public void onItemClick(View view, int position) {
                if (mList.get(position).getShouyou_type().equals("手游")){
                    Intent intent=new Intent(context, ShouyouActivity.class);
                    intent.putExtra("id",mList.get(position).getId());
                    context.startActivity(intent);
                }else if (mList.get(position).getShouyou_type().equals("页游")){
                    Intent intent=new Intent(context, YeyouActivity.class);
                    intent.putExtra("id",mList.get(position).getId());
                    context.startActivity(intent);
                }else {
                    Intent intent=new Intent(context, VRActivity.class);
                    intent.putExtra("id",mList.get(position).getId());
                    context.startActivity(intent);
                }
            }
        };
        return holder;
    }
}
