package com.example.jinkejinfuapplication.shouye.hudong;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.BaseRecycleAdapter;
import com.example.jinkejinfuapplication.base.BaseRecycleViewHolder;
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.xiangq.ShouyouActivity;
import com.example.jinkejinfuapplication.xiangq.StartActivity;
import com.example.jinkejinfuapplication.xiangq.VRActivity;
import com.example.jinkejinfuapplication.xiangq.YeyouActivity;

import org.xutils.x;

import java.util.List;
import java.util.Map;

public class Hudong_huatiAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public Hudong_huatiAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public Hudong_huatiAdapter(Context context) {
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
        final TextView reti,mytitle,zuozhe,mytime,num_liuyin,num_chakan,neirong;
        final ImageView img_icon,img1,img2;
        final LinearLayout lin_img;
        View view= LayoutInflater.from(context).inflate(R.layout.item_hudong_huati,parent,false);
        reti= (TextView) view.findViewById(R.id.reti);
        mytitle= (TextView) view.findViewById(R.id.mytitle);
        zuozhe= (TextView) view.findViewById(R.id.zuozhe);
        mytime= (TextView) view.findViewById(R.id.mytime);
        num_liuyin= (TextView) view.findViewById(R.id.num_liuyin);
        num_chakan= (TextView) view.findViewById(R.id.num_chakan);
        neirong= (TextView) view.findViewById(R.id.neirong);
        img_icon= (ImageView) view.findViewById(R.id.myicon);
        img1= (ImageView) view.findViewById(R.id.img1);
        img2= (ImageView) view.findViewById(R.id.img2);
        lin_img= (LinearLayout) view.findViewById(R.id.lin_img);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                x.image().bind(img_icon,mList.get(position).get("pic"), ImageOptions.getimageOptions());
                mytitle.setText(mList.get(position).get("title"));
                zuozhe.setText(mList.get(position).get("name"));
                mytime.setText(mList.get(position).get("datetime"));
                num_liuyin.setText(mList.get(position).get("commentnum"));
                num_chakan.setText(mList.get(position).get("clicknum"));
                mytitle.setText(mList.get(position).get("title"));
                neirong.setText(mList.get(position).get("content"));
                if (mList.get(position).get("hot").equals("0")){
                    reti.setVisibility(View.GONE);
                }else {
                    reti.setVisibility(View.VISIBLE);
                }
                if (mList.get(position).get("img1").isEmpty()&&mList.get(position).get("img2").isEmpty()){
                    lin_img.setVisibility(View.GONE);
                }else {
                    lin_img.setVisibility(View.VISIBLE);
                    x.image().bind(img1,mList.get(position).get("img1"), ImageOptions.getimageOptions_img());
                    x.image().bind(img2,mList.get(position).get("img2"), ImageOptions.getimageOptions_touming());
                }
            }

            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(context,HuatiActivity.class);
                intent.putExtra("id",mList.get(position).get("id"));
                context.startActivity(intent);
            }
        };
        return holder;
    }
}
