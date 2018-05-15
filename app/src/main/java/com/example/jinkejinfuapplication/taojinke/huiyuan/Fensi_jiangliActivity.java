package com.example.jinkejinfuapplication.taojinke.huiyuan;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.mine.MyzhanghuActivity;
import com.example.jinkejinfuapplication.taojinke.Huiyuan_zhaomuActivity;
import com.example.jinkejinfuapplication.taojinke.hehuoren.Hehuo_vipAdapter;
import com.example.jinkejinfuapplication.taojinke.hehuoren.Hehuo_zhifuActivity;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fensi_jiangliActivity extends BaseActivity {
    private TextView shouyi_leiji,shouyi_yue;
    private RecyclerView rec_fensi;
    private Fensi_jiangliAdapter fensi_jiangliAdapter;
    private List<Map<String,String>> mList=new ArrayList<>();
    private LinearLayout erweima,fenxiang;
    private String member_URL,member_code;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fensi_jiangli);
        setTitle("粉丝奖励");
        shouyi_leiji=findView(R.id.shouyi_leiji);
        shouyi_yue=findView(R.id.shouyi_yue);
        erweima=findView(R.id.erweima);
        fenxiang=findView(R.id.fenxiang);
        erweima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UMImage thumb =  new UMImage(Fensi_jiangliActivity.this, member_code);
                /*web.setDescription("my description");//描述*/
                new ShareAction(Fensi_jiangliActivity.this)
                        .withMedia(thumb)
                        .setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.SINA,SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {

                            }
                            @Override
                            public void onResult(SHARE_MEDIA share_media) {
                                Toast.makeText(Fensi_jiangliActivity.this,share_media + " 分享成功啦", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                                Toast.makeText(Fensi_jiangliActivity.this,share_media + " 分享失败啦", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {
                            }
                        }).open();
            }
        });
        fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyzhanghuActivity.share_tuiguan(Fensi_jiangliActivity.this,member_URL,"招募会员","邀请您加入淘金客VIP会员，轻松赚收益！");
            }
        });

        rec_fensi= (RecyclerView) findViewById(R.id.rec_fensi);
        LinearLayoutManager linManager1=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        rec_fensi.setLayoutManager(linManager1);
        rec_fensi.setHasFixedSize(true);
        fensi_jiangliAdapter=new Fensi_jiangliAdapter(this,mList);
        rec_fensi.setAdapter(fensi_jiangliAdapter);
    }

    @Override
    protected void initData() {
        String url1= AppBaseUrl.FENSI_JIANGLI;
        RequestParams params1= new RequestParams(url1);
        params1.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jObj = new JSONObject(result);
                    jObj = jObj.getJSONObject("fans");
                    shouyi_leiji.setText(jObj.getString("money1"));
                    shouyi_yue.setText(jObj.getString("money2"));
                    member_URL=jObj.getString("member_URL");
                    member_code=jObj.getString("member_code");
                    JSONArray jrry=jObj.getJSONArray("list");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject ob = jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("num",ob.getString("num"));
                        map.put("FxLevel",ob.getString("FxLevel"));
                        map.put("money",ob.getString("money"));
                        mList.add(map);
                    }
                    fensi_jiangliAdapter.setmList(mList);
                    fensi_jiangliAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(Fensi_jiangliActivity.this,"网络发生错误!");
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
