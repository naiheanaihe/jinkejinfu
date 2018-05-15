package com.example.jinkejinfuapplication.shouye.dianjing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

public class Saishi_xiangqActivity extends BaseActivity{
    private RadioGroup rg;
    private LinearLayout lin_mingdan,lin_jieshao;
    private TextView mytitle,jibie,danwei,danwei1,danwei2,jiangjin,haixuan,qq,qqun,phone,jianjie,guize,mytime,name1,name2,name3;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_saishi_xiangq);
        setTitle("赛事详情");

        lin_mingdan=findView(R.id.lin_mingdan);
        lin_jieshao=findView(R.id.lin_jieshao);
        rg=findView(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb1:
                        lin_mingdan.setVisibility(View.GONE);
                        lin_jieshao.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rb2:
                        lin_mingdan.setVisibility(View.VISIBLE);
                        lin_jieshao.setVisibility(View.GONE);
                        break;
                }
            }
        });
        mytitle=findView(R.id.mytitle);
        jibie=findView(R.id.jibie);
        danwei=findView(R.id.danwei);
        danwei1=findView(R.id.danwei1);
        danwei2=findView(R.id.danwei2);
        jiangjin=findView(R.id.jiangjin);
        haixuan=findView(R.id.haixuan);
        qq=findView(R.id.qq);
        qqun=findView(R.id.qqun);
        phone=findView(R.id.phone);
        jianjie=findView(R.id.jianjie);
        guize=findView(R.id.guize);
        mytime=findView(R.id.mytime);
        name1=findView(R.id.name1);
        name2=findView(R.id.name2);
        name3=findView(R.id.name3);


    }

    @Override
    protected void initData() {
        String id=getIntent().getStringExtra("id");
        RequestParams params = new RequestParams(AppBaseUrl.BASEURL+"app/gaming/dateils");
        params.addBodyParameter("id",id);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("赛事",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    jObj=jObj.getJSONObject("gaming");
                    x.image().bind((ImageView) findView(R.id.img),AppBaseUrl.isHttp(jObj.getString("img")), ImageOptions.getimageOptions_img());
                    mytitle.setText(jObj.getString("name"));
                    jibie.setText(jObj.getString("rank"));
                    danwei.setText(jObj.getString("sponsor"));
                    danwei1.setText(jObj.getString("organizer"));
                    danwei2.setText(jObj.getString("organizer_sum"));
                    jiangjin.setText("奖金："+jObj.getString("money")+"元");
                    haixuan.setText(jObj.getString("auditions"));
                    qq.setText(jObj.getString("QQhao"));
                    qqun.setText(jObj.getString("QQqun"));
                    phone.setText(jObj.getString("phone"));
                    jianjie.setText(jObj.getString("introduction"));
                    guize.setText(jObj.getString("rule"));
                    mytime.setText(jObj.getString("datetime"));

                    JSONArray jrr=jObj.getJSONArray("winning");
                    JSONObject jobj1=jrr.getJSONObject(0);
                    name1.setText(jobj1.getString("game_username"));
                    JSONObject jobj2=jrr.getJSONObject(1);
                    name2.setText(jobj2.getString("game_username"));
                    JSONObject jobj3=jrr.getJSONObject(2);
                    name3.setText(jobj3.getString("game_username"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.i("错误信息",ex.toString());
                ToastUtil.show(Saishi_xiangqActivity.this,"网络发生错误!");
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


}
