package com.example.jinkejinfuapplication.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.taojinke.TixianActivity;
import com.example.jinkejinfuapplication.taojinke.huiyuan.MyVIPActivity;
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class MyzhanghuActivity extends BaseActivity implements View.OnClickListener {
    private TextView caidan_text;
    private TextView jiage1,jiage2,jiage3,jiage4,jiage5,jiage6,jiage7,jiage8,jiage9;
    private Button button1,button2,button3,button4,button5,fenxiang_huiyuan,fenxiang_hehuo;
    private ImageView erweima_huiyuan,erweima_hehuo;
    private String member_URL,proxy_URL;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_myzhanghu);
        setTitle("个人账户");
        caidan_text=getCaidan_text();
        caidan_text.setText("账户明细");
        caidan_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyzhanghuActivity.this,MymingxiActivity.class);
                startActivity(intent);
            }
        });
        jiage1=findView(R.id.jiage1);
        jiage2=findView(R.id.jiage2);
        jiage3=findView(R.id.jiage3);
        jiage4=findView(R.id.jiage4);
        jiage5=findView(R.id.jiage5);
        jiage6=findView(R.id.jiage6);
        jiage7=findView(R.id.jiage7);
        jiage8=findView(R.id.jiage8);
        jiage9=findView(R.id.jiage9);
        button1=findView(R.id.button1);
        button2=findView(R.id.button2);
        button3=findView(R.id.button3);
        button4=findView(R.id.button4);
        button5=findView(R.id.button5);
        button2.setOnClickListener(this);
        button4.setOnClickListener(this);
        findView(R.id.button6).setOnClickListener(this);
        findView(R.id.button7).setOnClickListener(this);

        fenxiang_huiyuan=findView(R.id.fenxiang_huiyuan);
        fenxiang_hehuo=findView(R.id.fenxiang_hehuo);
        erweima_huiyuan=findView(R.id.erweima_huiyuan);
        erweima_hehuo=findView(R.id.erweima_hehuo);
        fenxiang_huiyuan.setOnClickListener(this);
        fenxiang_hehuo.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        String url1= AppBaseUrl.MYZHANGHU;
        RequestParams params1= new RequestParams(url1);
        params1.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.d("账号",result.toString());
                try {
                    JSONObject jObj = new JSONObject(result);
                    jObj = jObj.getJSONObject("user");
                    jiage1.setText(jObj.getString("balance"));
                    jiage2.setText(jObj.getString("member"));
                    jiage3.setText(jObj.getString("chongxiao"));
                    jiage4.setText(jObj.getString("proxy"));
                    jiage5.setText(jObj.getString("integral"));
                    jiage6.setText(jObj.getString("member_activity_income"));
                    jiage7.setText(jObj.getString("game_activity_income"));
                    jiage8.setText(jObj.getString("member_activity_chongxiao"));
                    jiage9.setText(jObj.getString("game_activity_chongxiao"));
                    x.image().bind(erweima_huiyuan,jObj.getString("member_code"), ImageOptions.getimageOptions_img());
                    x.image().bind(erweima_hehuo,jObj.getString("proxy_code"), ImageOptions.getimageOptions_img());
                    member_URL=jObj.getString("member_URL");
                    proxy_URL=jObj.getString("proxy_URL");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(MyzhanghuActivity.this,"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected boolean isShowToolBar() {
        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fenxiang_huiyuan:
                share_tuiguan(this,member_URL,"招募会员","邀请您加入淘金客VIP会员，轻松赚收益！");
                break;
            case R.id.fenxiang_hehuo:
                share_tuiguan(this,proxy_URL,"招募合伙人","邀请您加入淘金客招募合伙人，轻松赚收益！");
                break;
            case R.id.button2:
            case R.id.button4:
            case R.id.button6:
            case R.id.button7:
                Intent intent=new Intent(this, TixianActivity.class);
                startActivity(intent);
                break;
        }
    }
    public static void share_tuiguan(final Context mContext, String url,String title,String jianjie){
        UMWeb web = null;
        web = new UMWeb(url);
        web.setTitle(title);
        web.setDescription(MyApplication.getInstance().getYonghuBean().getNickname()+jianjie);

        UMImage thumb =  new UMImage(mContext, R.drawable.img_taojinke_hehuo);
        web.setThumb(thumb);  //缩略图
                /*web.setDescription("my description");//描述*/
        new ShareAction((Activity) mContext)
                .withMedia(web)
                .setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.SINA,SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }
                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        Toast.makeText(mContext,share_media + " 分享成功啦", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        Toast.makeText(mContext,share_media + " 分享失败啦", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                    }
                }).open();
    }
}
