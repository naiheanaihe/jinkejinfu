package com.example.jinkejinfuapplication.shouye.dianjing;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinkejinfuapplication.MainActivity;
import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.SharedPreferencesUtils;
import com.example.jinkejinfuapplication.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DianjingActivity extends BaseActivity implements View.OnClickListener {
    private TextView textView1,textView2,textView3;
    private ImageView img1,img2,img3;
    private List<Map<String,String>> mList=new ArrayList<>();
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dianjing);
        setTitle("电竞高手");
        textView1=findView(R.id.text1);
        textView2=findView(R.id.text2);
        textView3=findView(R.id.text3);
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        img1=findView(R.id.img1);
        img2=findView(R.id.img2);
        img3=findView(R.id.img3);

    }

    @Override
    protected void initData() {
        String url1 = AppBaseUrl.BASEURL+"app/gaming/index";
        RequestParams params1 = new RequestParams(url1);
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                String s = result.toString();
                try {
                    JSONObject job = new JSONObject(s);
                    JSONArray jrry=job.getJSONArray("gaming");
                    for (int i=0;i<jrry.length();i++){
                        JSONObject temp=jrry.getJSONObject(i);
                        Map<String,String> map=new ArrayMap<String, String>();
                        map.put("img",temp.getString("img"));
                        map.put("title",temp.getString("title"));
                        mList.add(map);
                    }
                    textView1.setText(mList.get(0).get("title"));
                    textView2.setText(mList.get(1).get("title"));
                    textView3.setText(mList.get(2).get("title"));
                    x.image().bind(img1,mList.get(0).get("img"), ImageOptions.getimageOptions_img());
                    x.image().bind(img2,mList.get(1).get("img"), ImageOptions.getimageOptions_img());
                    x.image().bind(img3,mList.get(2).get("img"), ImageOptions.getimageOptions_img());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                ToastUtil.show(DianjingActivity.this, "获取数据失败");
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
        Intent intent;
        switch (view.getId()){
            case R.id.text1:
                intent=new Intent(this,SaishiActivity.class);
                startActivity(intent);
                break;
            case R.id.text2:
                intent=new Intent(this,XinwenActivity.class);
                startActivity(intent);
                break;
            case R.id.text3:
                intent=new Intent(this,Saishi_shipingActivity.class);
                startActivity(intent);
                break;
        }
    }
}
