package com.example.jinkejinfuapplication.dujia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseRecycleAdapter;
import com.example.jinkejinfuapplication.base.BaseRecycleViewHolder;
import com.example.jinkejinfuapplication.bean.Dujia_gameBean;
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.xiangq.ShouyouActivity;
import com.example.jinkejinfuapplication.xiangq.VRActivity;
import com.example.jinkejinfuapplication.xiangq.YeyouActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.xutils.x;

import java.util.List;
import java.util.Map;

public class ZhekouAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Dujia_gameBean> mList;

    public ZhekouAdapter(Context context, List<Dujia_gameBean> mList) {
        this.context = context;
        this.mList = mList;
    }
    public ZhekouAdapter(Context context) {
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
        final TextView name,type,zhekou;
        final ImageView icon;
        final Button fenxiang;
        View view= LayoutInflater.from(context).inflate(R.layout.item_tuijian,parent,false);
        name= (TextView) view.findViewById(R.id.name);
        type= (TextView) view.findViewById(R.id.type);
        zhekou= (TextView) view.findViewById(R.id.zhekou);
        icon= (ImageView) view.findViewById(R.id.icon);
        fenxiang= (Button) view.findViewById(R.id.xiazai);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                x.image().bind(icon,mList.get(position).getImg_icon(), ImageOptions.getimageOptions_img());
                name.setText(mList.get(position).getName());
                type.setText(mList.get(position).getType());
                zhekou.setText(mList.get(position).getDiscount());
                fenxiang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                });

            }

            @Override
            public void onItemClick(View view, int position) {

            }
        };
        return holder;
    }
}
