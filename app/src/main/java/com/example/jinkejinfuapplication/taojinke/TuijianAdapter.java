package com.example.jinkejinfuapplication.taojinke;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseRecycleAdapter;
import com.example.jinkejinfuapplication.base.BaseRecycleViewHolder;
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.xiangq.ShouyouActivity;
import com.example.jinkejinfuapplication.xiangq.VRActivity;
import com.example.jinkejinfuapplication.xiangq.YeyouActivity;
import com.example.jinkejinfuapplication.xiangq.ZixunActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.xutils.common.task.Priority;
import org.xutils.x;

import java.util.List;
import java.util.Map;

public class TuijianAdapter extends BaseRecycleAdapter {
    private Context context;
    private List<Map<String,String>> mList;

    public TuijianAdapter(Context context, List<Map<String,String>> mList) {
        this.context = context;
        this.mList = mList;
    }
    public TuijianAdapter(Context context) {
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
        final TextView name,type,zhekou;
        final ImageView icon;
        final LinearLayout fenxiang;
        View view= LayoutInflater.from(context).inflate(R.layout.item_tuijian1,parent,false);
        name= (TextView) view.findViewById(R.id.name);
        type= (TextView) view.findViewById(R.id.type);
        zhekou= (TextView) view.findViewById(R.id.zhekou);
        icon= (ImageView) view.findViewById(R.id.icon);
        fenxiang= (LinearLayout) view.findViewById(R.id.fenxiang);
        final BaseRecycleViewHolder holder=new BaseRecycleViewHolder(view) {
            @Override
            public void onBindViewHolder(final int position) {
                x.image().bind(icon,mList.get(position).get("game_pic"), ImageOptions.getimageOptions_img());
                name.setText(mList.get(position).get("game_name"));
                type.setText(mList.get(position).get("game_type"));
                zhekou.setText(mList.get(position).get("discount"));
                fenxiang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UMWeb web = null;
                        web = new UMWeb(AppBaseUrl.BASEURL+"game/new/details_share/" + mList.get(position).get("id")+"-"+ mList.get(position).get("code"));
                        web.setTitle(mList.get(position).get("game_name"));
                        web.setDescription(mList.get(position).get("game_type"));

                        UMImage thumb =  new UMImage(context, mList.get(position).get("game_pic"));
                        web.setThumb(thumb);  //缩略图
                        new ShareAction((Activity) context)
                                .withMedia(web)
                                .setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.SINA,SHARE_MEDIA.WEIXIN_CIRCLE)
                                .setCallback(new UMShareListener() {
                                    @Override
                                    public void onStart(SHARE_MEDIA share_media) {

                                    }
                                    @Override
                                    public void onResult(SHARE_MEDIA share_media) {
                                        Toast.makeText(context,share_media + " 分享成功啦", Toast.LENGTH_SHORT).show();
                                    }
                                    @Override
                                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                                        Toast.makeText(context,share_media + " 分享失败啦", Toast.LENGTH_SHORT).show();
                                    }
                                    @Override
                                    public void onCancel(SHARE_MEDIA share_media) {
                                    }
                                }).open();

                    }
                });

            }

            @Override
            public void onItemClick(View view, int position) {
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
        };
        return holder;
    }
}
