package com.example.jinkejinfuapplication.taojinke.huiyuan;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.TishiDialog;
import com.example.jinkejinfuapplication.utils.ToastUtil;
import com.example.jinkejinfuapplication.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

public class MyVIPActivity extends BaseActivity implements View.OnClickListener {
    private ImageView myicon;
    private TextView yeji,xinren,jiage1,jiage2,jiage3,jiage4,jiage5,jiage6,yuanjia2,yuanjia3,yuanjia4,yuanjia5,yuanjia6,zeze2,zeze3,zeze4,zeze5,zeze6;
    private Button chongzhi1,chongzhi2,chongzhi3,chongzhi4,chongzhi5,chongzhi6;
    private LinearLayout lin1,lin2,lin3,lin4,lin5,lin6,lin_xieyi;
    private String vip;
    private CheckBox checkBox;
    private TextView name,myvip;
    private IWXAPI msgApi;
    public static MyVIPActivity instance;
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
                        Toast.makeText(MyVIPActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(MyVIPActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }

            }
        }
    };

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_vip);
        setTitle("我的VIP会员");
        vip=getIntent().getStringExtra("vip");
        instance=this;
        myicon=findView(R.id.myicon);
        x.image().bind(myicon,MyApplication.getInstance().getYonghuBean().getPhoto(), ImageOptions.getimageOptions());
        yeji=findView(R.id.yeji);
        xinren=findView(R.id.xinren);
        name=findView(R.id.name);
        myvip=findView(R.id.myvip);
        jiage1=findView(R.id.jiage1);
        jiage2=findView(R.id.jiage2);
        jiage3=findView(R.id.jiage3);
        jiage4=findView(R.id.jiage4);
        jiage5=findView(R.id.jiage5);
        jiage6=findView(R.id.jiage6);
        chongzhi1=findView(R.id.chongzhi1);
        chongzhi2=findView(R.id.chongzhi2);
        chongzhi3=findView(R.id.chongzhi3);
        chongzhi4=findView(R.id.chongzhi4);
        chongzhi5=findView(R.id.chongzhi5);
        chongzhi6=findView(R.id.chongzhi6);
        chongzhi1.setOnClickListener(this);
        chongzhi2.setOnClickListener(this);
        chongzhi3.setOnClickListener(this);
        chongzhi4.setOnClickListener(this);
        chongzhi5.setOnClickListener(this);
        chongzhi6.setOnClickListener(this);
        lin1=findView(R.id.lin1);
        lin2=findView(R.id.lin2);
        lin3=findView(R.id.lin3);
        lin4=findView(R.id.lin4);
        lin5=findView(R.id.lin5);
        lin6=findView(R.id.lin6);
        lin_xieyi=findView(R.id.lin_xieyi);
        yuanjia2=findView(R.id.yuanjia2);
        yuanjia3=findView(R.id.yuanjia3);
        yuanjia4=findView(R.id.yuanjia4);
        yuanjia5=findView(R.id.yuanjia5);
        yuanjia6=findView(R.id.yuanjia6);
        zeze2=findView(R.id.zeze2);
        zeze3=findView(R.id.zeze3);
        zeze4=findView(R.id.zeze4);
        zeze5=findView(R.id.zeze5);
        zeze6=findView(R.id.zeze6);
        checkBox=findView(R.id.check_zhifu);
        updatevip(vip);
    }

    private void updatevip(String vip) {
        if (!vip.equals("普通用户")){
            chongzhi1.setText("升级");
            chongzhi2.setText("升级");
            chongzhi3.setText("升级");
            chongzhi4.setText("升级");
            chongzhi5.setText("升级");
            chongzhi6.setText("升级");
            yuanjia2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            yuanjia3.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            yuanjia4.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            yuanjia5.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            yuanjia6.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            zeze2.setText("元差价");
            zeze3.setText("元差价");
            zeze4.setText("元差价");
            zeze5.setText("元差价");
            zeze6.setText("元差价");
        }else {
            yuanjia2.setVisibility(View.GONE);
            yuanjia3.setVisibility(View.GONE);
            yuanjia4.setVisibility(View.GONE);
            yuanjia5.setVisibility(View.GONE);
            yuanjia6.setVisibility(View.GONE);
        }
        switch (vip){
            case "VIP1":
                lin1.setVisibility(View.GONE);
                jiage2.setText("24");
                jiage3.setText((100-6)+"");
                jiage4.setText((300-6)+"");
                jiage5.setText((800-6)+"");
                jiage6.setText((1500-6)+"");
                break;
            case "VIP2":
                lin1.setVisibility(View.GONE);
                lin2.setVisibility(View.GONE);
                jiage3.setText((100-30)+"");
                jiage4.setText((300-30)+"");
                jiage5.setText((800-30)+"");
                jiage6.setText((1500-30)+"");
                break;
            case "VIP3":
                lin1.setVisibility(View.GONE);
                lin2.setVisibility(View.GONE);
                lin3.setVisibility(View.GONE);
                jiage4.setText((300-100)+"");
                jiage5.setText((800-100)+"");
                jiage6.setText((1500-100)+"");
                break;
            case "VIP4":
                lin1.setVisibility(View.GONE);
                lin2.setVisibility(View.GONE);
                lin3.setVisibility(View.GONE);
                lin4.setVisibility(View.GONE);
                jiage5.setText((800-300)+"");
                jiage6.setText((1500-300)+"");
                break;
            case "VIP5":
                lin1.setVisibility(View.GONE);
                lin2.setVisibility(View.GONE);
                lin3.setVisibility(View.GONE);
                lin4.setVisibility(View.GONE);
                lin5.setVisibility(View.GONE);
                jiage6.setText((1500-800)+"");
                break;
            case "VIP6":
                lin1.setVisibility(View.GONE);
                lin2.setVisibility(View.GONE);
                lin3.setVisibility(View.GONE);
                lin4.setVisibility(View.GONE);
                lin5.setVisibility(View.GONE);
                lin6.setVisibility(View.GONE);
                lin_xieyi.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void initData() {
        String url1= AppBaseUrl.MYVIP_HUIYUAN;
        RequestParams params1= new RequestParams(url1);
        params1.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jObj = new JSONObject(result);
                    jObj = jObj.getJSONObject("user");
                    name.setText(jObj.getString("name"));
                    myvip.setText(jObj.getString("rank"));
                    yeji.setText(jObj.getString("all_money"));
                    xinren.setText(jObj.getString("all_num"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(MyVIPActivity.this,"网络发生错误!");
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

    private void weixin_zhifu(String vip){

        if (!isWXAppInstalledAndSupported())
        {
            ToastUtil.show(this,"请先安装微信客户端！");
            return;
        }
        String url= AppBaseUrl.WEIXIN_ZHIFU_HUIYUAN;
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
                ToastUtil.show(MyVIPActivity.this,"网络错误");
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
        String url= AppBaseUrl.ZHIFUBAO_ZHIFU_HUIYUAN;
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
                            PayTask alipay = new PayTask(MyVIPActivity.this);
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
                ToastUtil.show(MyVIPActivity.this,"网络错误");
            }
            @Override
            public void onCancelled(CancelledException cex) {

            }
            @Override
            public void onFinished() {

            }
        });
    }
    private void zhifu_dialog(final String vip){
        if (!checkBox.isChecked()){
            ToastUtil.show(MyVIPActivity.this,"请先同意金客金福淘金客推广协议!");
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
    private boolean isWXAppInstalledAndSupported() {
        msgApi = WXAPIFactory.createWXAPI(this, null);
        msgApi.registerApp("wx0284fd47de760c90");

        boolean sIsWXAppInstalledAndSupported = msgApi.isWXAppInstalled()
                && msgApi.isWXAppSupportAPI();

        return sIsWXAppInstalledAndSupported;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.chongzhi1:
                zhifu_dialog("VIP1");
                break;
            case R.id.chongzhi2:
                zhifu_dialog("VIP2");
                break;
            case R.id.chongzhi3:
                zhifu_dialog("VIP3");
                break;
            case R.id.chongzhi4:
                zhifu_dialog("VIP4");
                break;
            case R.id.chongzhi5:
                zhifu_dialog("VIP5");
                break;
            case R.id.chongzhi6:
                zhifu_dialog("VIP6");
                break;
        }
    }
}
