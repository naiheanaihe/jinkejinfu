package com.example.jinkejinfuapplication.shouye.huodong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.jinkejinfuapplication.MainActivity;
import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.ToastUtil;
import com.example.jinkejinfuapplication.view.FullDialog;
import com.example.jinkejinfuapplication.view.TextSwitchView;

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

public class HuodongActivity extends BaseActivity {
    private TextSwitchView textSwitchView;
    private ImageButton huodong_img1,huodong_img2;
    private List<String> res=new ArrayList<>();
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_huodong);
        setTitle("活动专区");

        textSwitchView=findView(R.id.huodong_text);


        huodong_img1=findView(R.id.huodong_img1);
        huodong_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HuodongActivity.this,Shouyou_libaoActivity.class);
                startActivity(intent);
            }
        });
        huodong_img2=findView(R.id.huodong_img2);
        huodong_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HuodongActivity.this,Vip_libaoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        String url= AppBaseUrl.BASEURL+"app/activity";
        RequestParams params = new RequestParams(url);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("名单",result);
                res.clear();
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("activity");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject ob = jrry.getJSONObject(i);
                        res.add("恭喜"+ob.getString("name")+"获得"+ob.getString("money")+"元奖励");
                    }
                    textSwitchView.setResources(res);
                    textSwitchView.setTextStillTime(2000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(HuodongActivity.this,"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });

        String url1 = AppBaseUrl.BASEURL+"app/activity/user/ajax";
        RequestParams params1 = new RequestParams(url1);
        params1.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("信息",result);
                String s = result.toString();
                try {
                    JSONObject job = new JSONObject(s);
                    job=job.getJSONObject("user");
                    String proxy=job.getString("proxy");
                    String name=job.getString("name");
                    String member=job.getString("member");
                    String rank=job.getString("rank");
                    if (member.equals("0")){
                        final FullDialog dialog = new FullDialog(HuodongActivity.this);
                        final View views=View.inflate(HuodongActivity.this, R.layout.lingqu_dialog,null);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(views);
                        ImageView lingqu= (ImageView) views.findViewById(R.id.lingqu);
                        lingqu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String url2 = AppBaseUrl.BASEURL+"app/activity/VIP";
                                RequestParams params2 = new RequestParams(url2);
                                params2.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
                                x.http().post(params2, new Callback.CommonCallback<String>() {
                                    @Override
                                    public void onSuccess(String result) {
                                        LalaLog.i("信息",result);
                                        showToastMsg("领取成功！");
                                        dialog.dismiss();
                                    }
                                    @Override
                                    public void onError(Throwable ex, boolean isOnCallback) {
                                        LalaLog.d("错误信息",ex.toString());
                                        ToastUtil.show(HuodongActivity.this, "获取数据失败");
                                    }
                                    @Override
                                    public void onCancelled(CancelledException cex) {
                                    }
                                    @Override
                                    public void onFinished() {
                                    }
                                });
                            }
                        });
                        dialog.show();
                        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
                        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
                        layoutParams.height= WindowManager.LayoutParams.MATCH_PARENT;
                        dialog.getWindow().setAttributes(layoutParams);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误信息",ex.toString());
                ToastUtil.show(HuodongActivity.this, "获取数据失败");
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
