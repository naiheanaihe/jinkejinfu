package com.example.jinkejinfuapplication.taojinke.hehuoren;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.mine.MyzhanghuActivity;
import com.example.jinkejinfuapplication.taojinke.huiyuan.Fensi_jiangliActivity;
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

public class MyhehuorenActivity extends BaseActivity implements View.OnClickListener {
    private TextView shouyi_leiji,shouyi_yue,chengshi,faqiren,hehuoren;
    private ImageView arrow1,arrow2,arrow3;
    private LinearLayout lin1,lin2,lin3,lin_erweima,lin_fenxiang,rec1;
    private RecyclerView rec2,rec3;
    private boolean show1=false,show2=false,show3=false;
    private List<Map<String,String>> mList2=new ArrayList<>();
    private List<Map<String,String>> mList3=new ArrayList<>();
    private MyhehuorenAdapter myhehuorenAdapter;
    private LinearLayout erweima,fenxiang;
    private String proxy_URL,code;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_myhehuoren);
        setTitle("我的合伙人");
        shouyi_leiji=findView(R.id.shouyi_leiji);
        shouyi_yue=findView(R.id.shouyi_yue);
        chengshi=findView(R.id.chengshi);
        faqiren=findView(R.id.faqiren);
        hehuoren=findView(R.id.hehuoren);
        arrow1=findView(R.id.arrow1);
        arrow2=findView(R.id.arrow2);
        arrow3=findView(R.id.arrow3);
        lin1=findView(R.id.lin1);
        lin2=findView(R.id.lin2);
        lin3=findView(R.id.lin3);
        lin_erweima=findView(R.id.erweima);
        lin_fenxiang=findView(R.id.fenxiang);
        rec1=findView(R.id.rec1);
        rec2=findView(R.id.rec2);
        rec3=findView(R.id.rec3);
        lin1.setOnClickListener(this);
        lin2.setOnClickListener(this);
        lin3.setOnClickListener(this);

        LinearLayoutManager linManager1=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        rec2.setLayoutManager(linManager1);
        rec2.setHasFixedSize(true);
        myhehuorenAdapter=new MyhehuorenAdapter(this,mList2);
        rec2.setAdapter(myhehuorenAdapter);

        erweima=findView(R.id.erweima);
        fenxiang=findView(R.id.fenxiang);
        erweima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UMImage thumb =  new UMImage(MyhehuorenActivity.this, code);
                /*web.setDescription("my description");//描述*/
                new ShareAction(MyhehuorenActivity.this)
                        .withMedia(thumb)
                        .setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.SINA,SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {

                            }
                            @Override
                            public void onResult(SHARE_MEDIA share_media) {
                                Toast.makeText(MyhehuorenActivity.this,share_media + " 分享成功啦", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                                Toast.makeText(MyhehuorenActivity.this,share_media + " 分享失败啦", Toast.LENGTH_SHORT).show();
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
                MyzhanghuActivity.share_tuiguan(MyhehuorenActivity.this,proxy_URL,"招募合伙人","邀请您加入淘金客合伙人，轻松赚收益！");
            }
        });
    }

    @Override
    protected void initData() {
        String url= AppBaseUrl.MYHEHUOREN;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("我的合伙人",result);
                mList2.clear();
                try {
                    JSONObject jObj = new JSONObject(result);
                    jObj=jObj.getJSONObject("proxy");
                    shouyi_leiji.setText(jObj.getString("money1"));
                    shouyi_yue.setText(jObj.getString("money2"));
                    faqiren.setText(jObj.getString("start_proxy_num"));
                    hehuoren.setText(jObj.getString("proxy_num"));
                    code=jObj.getString("code");
                    proxy_URL=jObj.getString("proxy_URL");
                    JSONArray jrry=jObj.getJSONArray("list");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject ob = jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("name",ob.getString("name"));
                        map.put("rank",ob.getString("rank"));
                        map.put("mobilephone",ob.getString("mobilephone"));
                        mList2.add(map);
                    }
                    myhehuorenAdapter.setmList(mList2);
                    myhehuorenAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(MyhehuorenActivity.this,"网络发生错误!");
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
            case R.id.lin1:
                if (show1){
                    show1=!show1;
                    rec1.setVisibility(View.GONE);
                    arrow1.setImageResource(R.drawable.arrow_bttom);
                }else {
                    show1=!show1;
                    rec1.setVisibility(View.VISIBLE);
                    arrow1.setImageResource(R.drawable.arrow_up);
                }
                break;
            case R.id.lin2:
                if (show2){
                    show2=!show2;
                    rec2.setVisibility(View.GONE);
                    arrow2.setImageResource(R.drawable.arrow_bttom);
                }else {
                    show2=!show2;
                    rec2.setVisibility(View.VISIBLE);
                    arrow2.setImageResource(R.drawable.arrow_up);
                }
                break;
            case R.id.lin3:
                if (show3){
                    show3=!show3;
                    rec3.setVisibility(View.GONE);
                    arrow3.setImageResource(R.drawable.arrow_bttom);
                }else {
                    show3=!show3;
                    rec3.setVisibility(View.VISIBLE);
                    arrow3.setImageResource(R.drawable.arrow_up);
                }
                break;
        }
    }
}
