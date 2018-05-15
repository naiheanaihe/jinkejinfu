package com.example.jinkejinfuapplication.mine;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.MobileUtils;
import com.example.jinkejinfuapplication.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

public class FindpwActivity extends BaseActivity implements View.OnClickListener {
    private EditText ed_phone,ed_yzm,ed_pw,ed_pw1;
    private Button btn_yzm,zhuce;
    private String mYzm="xxxx",mPhone;
    private int mTime=60;
    private boolean isverify=true;
    private Timer mTimer;
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 1:
                    btn_yzm.setText(mTime+"");
                    mTime--;
                    if (mTime==0)
                    {
                        isverify=true;
                        mTime=60;
                        btn_yzm.setText("获取验证码");
                        mTimer.cancel();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_findpw);
        setTitle("忘记密码");
        ed_phone= (EditText) findViewById(R.id.phone);
        ed_yzm= (EditText) findViewById(R.id.yzm);
        ed_pw= (EditText) findViewById(R.id.pw);
        ed_pw1= (EditText) findViewById(R.id.pw1);

        btn_yzm= (Button) findViewById(R.id.btn_yzm);
        zhuce= (Button) findViewById(R.id.zhuce);
        btn_yzm.setOnClickListener(this);
        zhuce.setOnClickListener(this);
    }

    @Override
    protected void initData() {

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
            case R.id.btn_yzm:
                if (isverify==false)
                {
                    return;
                }
                String myphone=ed_phone.getText().toString();
                if (!MobileUtils.isMobileNO(myphone))
                {
                    ToastUtil.show(this,"手机号码格式不对！");
                    return;
                }
                String url= AppBaseUrl.SENDVERIFYCODE_FINDPW;
                RequestParams params = new RequestParams(url);
                params.addBodyParameter("mobilephone",myphone);
                params.addBodyParameter("code","goldcs.net");
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        String verifycode = result.toString();
                        try {
                            LalaLog.i("验证码",verifycode);
                            JSONObject jObj = new JSONObject(verifycode);
                            if (jObj.getString("sms").equals("该用户已存在,请重新输入！"))
                            {
                                ToastUtil.show(FindpwActivity.this,jObj.getString("sms"));
                                return;
                            }
                            jObj = jObj.getJSONObject("sms");
                            mPhone = jObj.getString("mobilephone");
                            mYzm = jObj.getString("verifyCode");
                            isverify=false;
                            mTimer=new Timer();
                            mTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    mHandler.sendEmptyMessage(1);
                                }
                            }, 500, 1000/* 表示1000毫秒之後，每隔1000毫秒執行一次 */);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        ToastUtil.show(FindpwActivity.this,"网络发生错误!");
                    }
                    @Override
                    public void onCancelled(CancelledException cex) {
                    }
                    @Override
                    public void onFinished() {
                    }
                });
                break;
            case R.id.zhuce:
                String yzm=ed_yzm.getText().toString();
                String pw=ed_pw.getText().toString();
                String pw1=ed_pw1.getText().toString();
                if (!yzm.equals(mYzm)) {
                    ToastUtil.show(this,"验证码不正确！");
                    return;
                }
                if (pw.isEmpty()||pw1.isEmpty()){
                    ToastUtil.show(this,"用户名或密码不能为空！");
                    return;
                }
                if (!pw.equals(pw1)) {
                    ToastUtil.show(this,"两次密码不相同，请确认！");
                    return;
                }
                String url1= AppBaseUrl.FINDPW;
                RequestParams params1 = new RequestParams(url1);
                params1.addBodyParameter("username",mPhone);
                params1.addBodyParameter("password",pw);
                x.http().post(params1, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        showToastMsg("修改密码成功！");
                        finish();
                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        ToastUtil.show(FindpwActivity.this,"网络发生错误!");
                    }
                    @Override
                    public void onCancelled(CancelledException cex) {
                    }
                    @Override
                    public void onFinished() {
                    }
                });
                break;
        }
    }
}
