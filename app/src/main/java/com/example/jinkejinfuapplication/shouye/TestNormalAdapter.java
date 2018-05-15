package com.example.jinkejinfuapplication.shouye;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jinkejinfuapplication.MainActivity;
import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.mine.LoginActivity;
import com.example.jinkejinfuapplication.mine.Login_fragment;
import com.example.jinkejinfuapplication.mine.MineActivity;
import com.example.jinkejinfuapplication.shiping.DianyingActivity;
import com.example.jinkejinfuapplication.taojinke.TaojinkeActivity;
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.xiangq.ShouyouActivity;
import com.example.jinkejinfuapplication.xiangq.VRActivity;
import com.example.jinkejinfuapplication.xiangq.YeyouActivity;
import com.example.jinkejinfuapplication.xiangq.ZhoubianActivity;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/19.
 */

public class  TestNormalAdapter extends StaticPagerAdapter {
    private List<Map<String,String>> photoPaths = new ArrayList<Map<String,String>>();
    private Context context;
    public TestNormalAdapter(List<Map<String,String>> photoPaths,Context context) {
        this.photoPaths = photoPaths;
        this.context=context;
    }

    public void setPhotoPaths(List<Map<String, String>> photoPaths) {
        this.photoPaths = photoPaths;
    }

    @Override
    public View getView(final ViewGroup container, final int position) {
        ImageView view = new ImageView(container.getContext());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (photoPaths.get(position).get("sort")){
                    case "1":
                    case "2":
                    case "3":
                    case "6":
                        if (photoPaths.get(position).get("id").equals("0"))
                        {
                            if (MainActivity.LOGINSTATE==MainActivity.LOGINSTATE_NOTLOGIN){
                                intent=new Intent(context, LoginActivity.class);
                                context.startActivity(intent);
                            }else {
                                intent=new Intent(context, MineActivity.class);
                                context.startActivity(intent);
                            }
                            return;
                        }
                        if (photoPaths.get(position).get("type").equals("手游")){
                            intent=new Intent(context, ShouyouActivity.class);
                            intent.putExtra("id",photoPaths.get(position).get("id"));
                            context.startActivity(intent);
                        }else if (photoPaths.get(position).get("type").equals("页游")){
                            intent=new Intent(context, YeyouActivity.class);
                            intent.putExtra("id",photoPaths.get(position).get("id"));
                            context.startActivity(intent);
                        }else {
                            intent=new Intent(context, VRActivity.class);
                            intent.putExtra("id",photoPaths.get(position).get("id"));
                            context.startActivity(intent);
                        }
                        break;
                    case "4":
                    case "5":
                        intent = new Intent(context, DianyingActivity.class);
                        intent.putExtra("id",photoPaths.get(position).get("id"));
                        context.startActivity(intent);
                        break;
                    case "9":
                        if (MainActivity.LOGINSTATE==MainActivity.LOGINSTATE_NOTLOGIN){
                            intent=new Intent(context,LoginActivity.class);
                            context.startActivity(intent);
                            return;
                        }
                        intent=new Intent(context,ZhoubianActivity.class);
                        intent.putExtra("url","http://www.goldcs.net/shop/detail?id="+photoPaths.get(position).get("id")+"&app=goldcs.net&userId="+ MyApplication.getInstance().getYonghuBean().getUserId());
                        context.startActivity(intent);

                        break;
                    case "10":
                        intent=new Intent(context,Zhibo_xiangqActivity.class);
                        intent.putExtra("url",photoPaths.get(position).get("links"));
                        context.startActivity(intent);
                        break;
                }

            }
        });
        x.image().bind(view,photoPaths.get(position).get("url"), ImageOptions.getimageOptions_img());
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }
    @Override
    public int getCount() {
        return photoPaths.size();
    }
}
