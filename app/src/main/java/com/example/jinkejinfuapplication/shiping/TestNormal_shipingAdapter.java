package com.example.jinkejinfuapplication.shiping;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jinkejinfuapplication.MainActivity;
import com.example.jinkejinfuapplication.mine.LoginActivity;
import com.example.jinkejinfuapplication.mine.MineActivity;
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.xiangq.ShipingActivity;
import com.example.jinkejinfuapplication.xiangq.ShouyouActivity;
import com.example.jinkejinfuapplication.xiangq.VRActivity;
import com.example.jinkejinfuapplication.xiangq.YeyouActivity;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/19.
 */

public class TestNormal_shipingAdapter extends StaticPagerAdapter {
    private List<Map<String,String>> photoPaths = new ArrayList<Map<String,String>>();
    private Context context;
    public TestNormal_shipingAdapter(List<Map<String,String>> photoPaths, Context context) {
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
                Intent intent=new Intent(context, ShipingActivity.class);
                intent.putExtra("id",photoPaths.get(position).get("id"));
                intent.putExtra("gameId",photoPaths.get(position).get("gameId"));
                context.startActivity(intent);
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
