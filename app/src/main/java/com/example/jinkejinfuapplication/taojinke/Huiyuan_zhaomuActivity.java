package com.example.jinkejinfuapplication.taojinke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.mine.MineActivity;
import com.example.jinkejinfuapplication.mine.MyzhanghuActivity;
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class Huiyuan_zhaomuActivity extends BaseActivity {
    private ImageView erweima;
    private Button fenxiang_huiyuan;
    private String member_URL;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_huiyuan_zhaomu);
        setTitle("招募会员");
        erweima=findView(R.id.erweima);

        fenxiang_huiyuan=findView(R.id.fenxiang_huiyuan);
        fenxiang_huiyuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyzhanghuActivity.share_tuiguan(Huiyuan_zhaomuActivity.this,member_URL,"招募会员","邀请您加入淘金客VIP会员，轻松赚收益！");
            }
        });
    }

    @Override
    protected void initData() {
        String url1= AppBaseUrl.ZHAOMU_HUIYUAN;
        RequestParams params1 = new RequestParams(url1);
        params1.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("个人",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    jObj=jObj.getJSONObject("member_code");
                    x.image().bind(erweima,jObj.getString("code"), ImageOptions.getimageOptions_img());
                    member_URL=jObj.getString("member_URL");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(Huiyuan_zhaomuActivity.this,"网络发生错误!");
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
