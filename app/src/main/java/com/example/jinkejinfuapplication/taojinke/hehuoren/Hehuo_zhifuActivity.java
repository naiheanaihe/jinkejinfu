package com.example.jinkejinfuapplication.taojinke.hehuoren;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.taojinke.PayResult;
import com.example.jinkejinfuapplication.taojinke.huiyuan.MyVIPActivity;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.TishiDialog;
import com.example.jinkejinfuapplication.utils.ToastUtil;
import com.example.jinkejinfuapplication.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

public class Hehuo_zhifuActivity extends BaseActivity {
    private TextView hehuo_infor,hehuo_jiage;
    private CheckBox checkBox;
    private Button button_zhifu;
    public static Hehuo_zhifuActivity instance;
    private String city,rank;
    private IWXAPI msgApi;

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(Hehuo_zhifuActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(Hehuo_zhifuActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }

            }
        }
    };
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_hehuo_zhifu);
        setTitle("注册付费");
        instance=this;
        hehuo_infor=findView(R.id.hehuo_infor);
        hehuo_jiage=findView(R.id.jiage);
        checkBox=findView(R.id.check_zhifu);
        button_zhifu=findView(R.id.zhuce);

        button_zhifu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zhifu_dialog(rank);
            }
        });
    }
    private void zhifu_dialog(final String vip){
        if (!checkBox.isChecked()){
            ToastUtil.show(Hehuo_zhifuActivity.this,"请先同意金客金福淘金客推广协议!");
            return;
        }
        final TishiDialog dialog=new TishiDialog(this);
        View views=View.inflate(this, R.layout.dialog_zhifu_sel,null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(views);
        dialog.show();
        ImageView cha= (ImageView) views.findViewById(R.id.cha);
        cha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        LinearLayout lin_weixin= (LinearLayout) views.findViewById(R.id.lin_weixin);
        lin_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                weixin_zhifu(vip);
            }
        });
        LinearLayout lin_zhifubao= (LinearLayout) views.findViewById(R.id.lin_zhifubao);
        lin_zhifubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                zhifubao_zhifu(vip);
            }
        });
    }
    private void weixin_zhifu(String vip){

        if (!isWXAppInstalledAndSupported())
        {
            ToastUtil.show(this,"请先安装微信客户端！");
            return;
        }
        WXPayEntryActivity.zhifuactivity="2";
        String url= AppBaseUrl.WEIXIN_ZHIFU_HEHUOREN;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("userId",MyApplication.getInstance().getYonghuBean().getUserId());
        params.addBodyParameter("body", vip);
        x.http().post(params, new Callback.CommonCallback<String>()
        {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("微信参数",result);
                WXPayEntryActivity.zhifuactivity="1";
                try {
                    JSONObject obj=new JSONObject(result);
                    obj=obj.getJSONObject("pay");
                    String msg=obj.getString("msg");
                    String appid=obj.getString("appid");
                    String timestamp=obj.getString("timestamp");
                    String noncestr=obj.getString("noncestr");
                    String partnerid=obj.getString("partnerid");
                    String prepayid=obj.getString("prepayid");
                    String packages=obj.getString("package");
                    String sign=obj.getString("appid");
                    PayReq request = new PayReq();
                    request.appId = appid;
                    request.partnerId = partnerid;
                    request.prepayId= prepayid;
                    request.packageValue = packages;
                    request.nonceStr= noncestr;
                    request.timeStamp= timestamp;
                    request.sign= sign;
                    msgApi.sendReq(request);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.i("错误信息",ex.toString());
                ToastUtil.show(Hehuo_zhifuActivity.this,"网络错误");
            }
            @Override
            public void onCancelled(CancelledException cex) {

            }
            @Override
            public void onFinished() {

            }
        });

    }
    private void zhifubao_zhifu(String vip){
        String url= AppBaseUrl.ZHIFUBAO_ZHIFU_HEHUOREN;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("userId",MyApplication.getInstance().getYonghuBean().getUserId());
        params.addBodyParameter("body", vip);
        x.http().post(params, new Callback.CommonCallback<String>()
        {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("支付宝参数",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    final String orderInfo=jObj.getString("pay");
                    Runnable payRunnable = new Runnable() {
                        @Override
                        public void run() {
                            PayTask alipay = new PayTask(Hehuo_zhifuActivity.this);
                            Map<String, String> result = alipay.payV2(orderInfo, true);
                            LalaLog.i("支付宝参数", result.toString());

                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    };

                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.i("错误信息",ex.toString());
                ToastUtil.show(Hehuo_zhifuActivity.this,"网络错误");
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
    protected void initData() {
        String url1= AppBaseUrl.FUFEI_HEHUOREN;
        RequestParams params1= new RequestParams(url1);
        params1.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jObj = new JSONObject(result);
                    jObj = jObj.getJSONObject("proxy");
                    String money = jObj.getString("money");
                    city = jObj.getString("city");
                    rank = jObj.getString("rank");
                    String state = jObj.getString("state");
                    hehuo_infor.setText(city+" "+rank);
                    hehuo_jiage.setText(money+"元");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.show(Hehuo_zhifuActivity.this,"网络发生错误!");
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
    private boolean isWXAppInstalledAndSupported() {
        msgApi = WXAPIFactory.createWXAPI(this, null);
        msgApi.registerApp("wx0284fd47de760c90");

        boolean sIsWXAppInstalledAndSupported = msgApi.isWXAppInstalled()
                && msgApi.isWXAppSupportAPI();

        return sIsWXAppInstalledAndSupported;
    }

}
