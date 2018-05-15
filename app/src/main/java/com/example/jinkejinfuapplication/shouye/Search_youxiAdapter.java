package com.example.jinkejinfuapplication.shouye;

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
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.xiangq.ShouyouActivity;
import com.example.jinkejinfuapplication.xiangq.VRActivity;
import com.example.jinkejinfuapplication.xiangq.YeyouActivity;

import org.xutils.x;

import java.util.List;
import java.util.Map;

public class Search_youxiAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public Search_youxiAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public Search_youxiAdapter(Context context) {
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
        final TextView name,type;
        final ImageView img_icon;
        final Button xiazai;
        View view= LayoutInflater.from(context).inflate(R.layout.item_search_youxi,parent,false);
        name= (TextView) view.findViewById(R.id.name);
        type= (TextView) view.findViewById(R.id.type);
        img_icon= (ImageView) view.findViewById(R.id.img_icon);
        xiazai= (Button) view.findViewById(R.id.xiazai);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                x.image().bind(img_icon,mList.get(position).get("game_pic"), ImageOptions.getimageOptions_img());
                name.setText(mList.get(position).get("game_name"));
                type.setText(mList.get(position).get("game_type"));
                xiazai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mList.get(position).get("type").equals("手游")){
                            Intent intent=new Intent(context, ShouyouActivity.class);
                            intent.putExtra("id",mList.get(position).get("id"));
                            context.startActivity(intent);
                        }else if (mList.get(position).get("type").equals("页游")){
                            Intent intent=new Intent(context, YeyouActivity.class);
                            intent.putExtra("id",mList.get(position).get("id"));
                            context.startActivity(intent);
                        }else {
                            Intent intent=new Intent(context, VRActivity.class);
                            intent.putExtra("id",mList.get(position).get("id"));
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
