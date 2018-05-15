package com.example.jinkejinfuapplication.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.jinkejinfuapplication.taojinke.hehuoren.Hehuo_zhifuActivity;
import com.example.jinkejinfuapplication.taojinke.huiyuan.MyVIPActivity;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.ToastUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;

	public static String zhifuactivity;        //判断当前支付Activity
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* setContentView(R.layout.pay_result);*/
        
    	api = WXAPIFactory.createWXAPI(this, "wx0284fd47de760c90");
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		LalaLog.i("返回码：",resp.errCode+"");
		switch (resp.errCode)
		{
			case 0:
				ToastUtil.show(this,"支付成功！");
				if (zhifuactivity.equals("1")){
					if (MyVIPActivity.instance!=null){
						MyVIPActivity.instance.finish();
					}
				}else if (zhifuactivity.equals("2")){
					if (Hehuo_zhifuActivity.instance!=null){
						Hehuo_zhifuActivity.instance.finish();
					}
				}

				break;
			case -1:
				ToastUtil.show(this,"支付失败！尝试清理微信缓存再支付");
				break;
			case -2:
				ToastUtil.show(this,"支付取消！");
				break;
		}
		finish();
	}
}
