package com.example.jinkejinfuapplication.taojinke;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class TixianActivity extends BaseActivity implements View.OnClickListener {
    private String[] jines=new String[]{"100","200","300","400","500","600","700","800"};
    private Spinner spinner_jine;
    private TextView shouyi,jilu;
    private RadioGroup group_tixian;
    private String type="微信";
    private LinearLayout lin_weixin,lin_zhifubao;
    private Button tixian;
    private EditText ed_weixin,ed_weixin_phone,ed_zhifubao,ed_zhifubao_name;
    private float yue;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tixian);
        setTitle("提现");
        spinner_jine=findView(R.id.spinner_jine);
        SpinnerAdapater spinnerAdapater=new SpinnerAdapater(this,jines);
        spinner_jine .setAdapter(spinnerAdapater);
        shouyi=findView(R.id.shouyi);
        jilu=findView(R.id.jilu);
        jilu.setOnClickListener(this);
        tixian=findView(R.id.tixian);
        tixian.setOnClickListener(this);
        ed_weixin=findView(R.id.ed_weixin);
        ed_zhifubao=findView(R.id.ed_zhifubao);
        ed_weixin_phone=findView(R.id.ed_weixin_phone);
        ed_zhifubao_name=findView(R.id.ed_zhifubao_name);
        lin_weixin=findView(R.id.lin_weixin);
        lin_zhifubao=findView(R.id.lin_zhifubao);
        group_tixian=findView(R.id.group_tixian);
        group_tixian.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i== R.id.btn_tixian1){
                    type="微信";
                    lin_weixin.setVisibility(View.VISIBLE);
                    lin_zhifubao.setVisibility(View.GONE);
                }else {
                    type="支付宝";
                    lin_weixin.setVisibility(View.GONE);
                    lin_zhifubao.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    protected void initData() {
        String url1= AppBaseUrl.MYTIXIAN;
        RequestParams params1 = new RequestParams(url1);
        params1.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("提现",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    jObj=jObj.getJSONObject("user");
                    shouyi.setText(jObj.getString("balance")+"元");
                    yue= Float.parseFloat(jObj.getString("balance"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(TixianActivity.this,"网络发生错误!");
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
            case R.id.tixian:
                if (type.equals("微信")){
                    String weixin_number=ed_weixin.getText().toString();
                    String mobilephone=ed_weixin_phone.getText().toString();
                    final String jine=jines[spinner_jine.getSelectedItemPosition()];
                    LalaLog.d("金额",jine);
                    if (yue<Float.parseFloat(jine)){
                        showToastMsg("余额不足！");
                        return;
                    }
                    if (weixin_number.isEmpty()||mobilephone.isEmpty()){
                        showToastMsg("微信号和手机号不能为空！");
                        return;
                    }
                    String url1= AppBaseUrl.TIXIAN_WEIXIN;
                    RequestParams params1 = new RequestParams(url1);
                    params1.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
                    params1.addBodyParameter("money", jine);
                    params1.addBodyParameter("weixin_number", weixin_number);
                    params1.addBodyParameter("mobilephone", mobilephone);
                    x.http().post(params1, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            LalaLog.d("微信返回",result);
                            showToastMsg("正在处理中！");
                            yue=yue-Float.parseFloat(jine);
                            shouyi.setText(yue+"元");
                            ed_weixin.setText("");
                            ed_weixin_phone.setText("");
                        }
                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            LalaLog.d("错误详情",ex.toString());
                            ToastUtil.show(TixianActivity.this,"网络发生错误!");
                        }
                        @Override
                        public void onCancelled(CancelledException cex) {
                        }
                        @Override
                        public void onFinished() {
                        }
                    });
                }else{
                    String apply_number=ed_zhifubao.getText().toString();
                    String name=ed_zhifubao_name.getText().toString();
                    final String jine=jines[spinner_jine.getSelectedItemPosition()];
                    LalaLog.d("金额",jine);
                    if (yue<Float.parseFloat(jine)){
                        showToastMsg("余额不足！");
                        return;
                    }
                    if (apply_number.isEmpty()||name.isEmpty()){
                        showToastMsg("微信号和手机号不能为空！");
                        return;
                    }
                    String url1= AppBaseUrl.TIXIAN_ZHIFUBAO;
                    RequestParams params1 = new RequestParams(url1);
                    params1.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
                    params1.addBodyParameter("money", jine);
                    params1.addBodyParameter("apply_number", apply_number);
                    params1.addBodyParameter("name", name);
                    x.http().post(params1, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            LalaLog.d("支付宝返回",result);
                            showToastMsg("正在处理中！");
                            yue=yue-Float.parseFloat(jine);
                            shouyi.setText(yue+"元");
                            ed_zhifubao.setText("");
                            ed_zhifubao_name.setText("");
                        }
                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            LalaLog.d("错误详情",ex.toString());
                            ToastUtil.show(TixianActivity.this,"网络发生错误!");
                        }
                        @Override
                        public void onCancelled(CancelledException cex) {
                        }
                        @Override
                        public void onFinished() {
                        }
                    });
                }
                break;
            case R.id.jilu:
                Intent intent=new Intent(this,TixianJiluActivity.class);
                startActivity(intent);
                break;
        }
    }
}
