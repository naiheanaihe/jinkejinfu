package com.example.jinkejinfuapplication.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jinkejinfuapplication.MainActivity;
import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseFragment;
import com.example.jinkejinfuapplication.bean.YonghuBean;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.SharedPreferencesUtils;
import com.example.jinkejinfuapplication.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by naihe on 2017/11/21.
 */

public class Login_fragment extends BaseFragment{
    private EditText ed_phone,ed_pw;
    private Button denglu;
    private MyApplication app;
    private TextView wangji;
    private CheckBox checkBox;
    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView(View view) {
        app= (MyApplication) getContext().getApplicationContext();
        ed_phone= (EditText) view.findViewById(R.id.phone);
        ed_pw= (EditText) view.findViewById(R.id.pw);
        denglu= (Button) view.findViewById(R.id.denglu);
        checkBox= (CheckBox) view.findViewById(R.id.check_zhanghu);
        String ischeck= (String) SharedPreferencesUtils.getParam(getContext(),"user","checkBox","0");
        if (ischeck.equals("1")){
            ed_phone.setText((String) SharedPreferencesUtils.getParam(getContext(),"user","zhanghao",""));
            ed_pw.setText((String) SharedPreferencesUtils.getParam(getContext(),"user","pw",""));
            checkBox.setChecked(true);
        }
        wangji= (TextView) view.findViewById(R.id.wangji);
        wangji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),FindpwActivity.class);
                startActivity(intent);
            }
        });
        denglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=ed_phone.getText().toString();
                String pw=ed_pw.getText().toString();
                String url= AppBaseUrl.LOGIN;
                RequestParams params = new RequestParams(url);
                params.addBodyParameter("username",username);
                params.addBodyParameter("password",pw);
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        LalaLog.i("登录信息",result);
                        try {
                            JSONObject jObj = new JSONObject(result);
                            String user = jObj.getString("user");
                            if (user.equals("密码错误")||user.equals("账号错误"))
                            {
                                ToastUtil.show(getContext(),"账户或密码错误!");
                                return;
                            }else if (user.equals("账号不存在")){
                                ToastUtil.show(getContext(),"账号不存在!");
                                return;
                            }
                            ToastUtil.show(getContext(),"登录成功!");
                            jObj=jObj.getJSONObject("user");
                            String DD=jObj.getString("DD");
                            String tao=jObj.getString("tao");
                            String dada=jObj.getString("dada");
                            String mobilephone=jObj.getString("mobilephone");
                            String nickname=jObj.getString("nickname");
                            String photo=AppBaseUrl.isHttp(jObj.getString("photo"));
                            String type=jObj.getString("type");
                            String type1=jObj.getString("type1");
                            String userId=jObj.getString("userId");
                            String username=jObj.getString("username");
                            String status=jObj.getString("status");
                            String udcode=jObj.getString("udcode");
                            YonghuBean bean=new YonghuBean();
                            bean.setUserId(userId);
                            bean.setDD(DD);
                            bean.setTao(tao);
                            bean.setDada(dada);
                            bean.setMobilephone(mobilephone);
                            bean.setNickname(nickname);
                            bean.setPhoto(photo);
                            bean.setType(type);
                            bean.setType1(type1);
                            bean.setUsername(username);
                            bean.setStatus(status);
                            bean.setUdcode(udcode);
                            app.setYonghuBean(bean);

                            SharedPreferencesUtils.setParam(getContext(),"user","userId",userId);
                            SharedPreferencesUtils.setParam(getContext(),"user","DD",DD);
                            SharedPreferencesUtils.setParam(getContext(),"user","tao",tao);
                            SharedPreferencesUtils.setParam(getContext(),"user","dada",dada);
                            SharedPreferencesUtils.setParam(getContext(),"user","mobilephone",mobilephone);
                            SharedPreferencesUtils.setParam(getContext(),"user","nickname",nickname);
                            SharedPreferencesUtils.setParam(getContext(),"user","photo",photo);
                            SharedPreferencesUtils.setParam(getContext(),"user","type",type);
                            SharedPreferencesUtils.setParam(getContext(),"user","type1",type1);
                            SharedPreferencesUtils.setParam(getContext(),"user","username",username);
                            SharedPreferencesUtils.setParam(getContext(),"user","status",status);
                            SharedPreferencesUtils.setParam(getContext(),"user","udcode",udcode);

                            MainActivity.LOGINSTATE=MainActivity.LOGINSTATE_LOGIN;
                            SharedPreferencesUtils.setParam(getContext(),"user","loginstate", MainActivity.LOGINSTATE+"");
                            if (checkBox.isChecked()){
                                SharedPreferencesUtils.setParam(getContext(),"user","zhanghao", ed_phone.getText().toString());
                                SharedPreferencesUtils.setParam(getContext(),"user","pw", ed_pw.getText().toString());
                                SharedPreferencesUtils.setParam(getContext(),"user","checkBox", "1");
                            }else{
                                SharedPreferencesUtils.setParam(getContext(),"user","checkBox", "0");
                            }

                            MobclickAgent.onProfileSignIn(userId);
                            getActivity().finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        ToastUtil.show(getContext(),"网络发生错误!");
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
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_login;
    }
}
