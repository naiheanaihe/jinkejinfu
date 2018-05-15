package com.example.jinkejinfuapplication.mine;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseFragment;
import com.example.jinkejinfuapplication.bean.ProvinceBean;
import com.example.jinkejinfuapplication.utils.JsonFileReader;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.MobileUtils;
import com.example.jinkejinfuapplication.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by naihe on 2017/11/21.
 */

public class Zhuce_fragment extends BaseFragment implements View.OnClickListener {
    private EditText ed_phone,ed_name,ed_yzm,ed_pw,ed_pw1, ed_chengshi,ed_mytuijian;
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

    OptionsPickerView pvOptions;
    //  省份
    ArrayList<ProvinceBean> provinceBeanList = new ArrayList<>();
    ArrayList<String> provinceList = new ArrayList<>();
    //  城市
    ArrayList<String> cities;
    ArrayList<List<String>> cityList = new ArrayList<>();
    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView(View view) {
        ed_phone= (EditText) view.findViewById(R.id.phone);
        ed_name= (EditText) view.findViewById(R.id.name);
        ed_yzm= (EditText) view.findViewById(R.id.yzm);
        ed_pw= (EditText) view.findViewById(R.id.pw);
        ed_pw1= (EditText) view.findViewById(R.id.pw1);
        ed_mytuijian= (EditText) view.findViewById(R.id.mytuijian);
        ed_chengshi = (EditText) view.findViewById(R.id.tuijian);
        ed_chengshi.setOnClickListener(this);
        btn_yzm= (Button) view.findViewById(R.id.btn_yzm);
        zhuce= (Button) view.findViewById(R.id.zhuce);
        btn_yzm.setOnClickListener(this);
        zhuce.setOnClickListener(this);

        initaddress();
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_zhuce;
    }
    private void initaddress()
    {
        //  创建选项选择器
        pvOptions = new OptionsPickerView(getContext());

        //  获取json数据
        String province_data_json = JsonFileReader.getJson(getContext(), "province_data.json");
        //  解析json数据
        parseJson(province_data_json);
        //  设置三级联动效果
        pvOptions.setPicker(provinceList, cityList, true);


        //  设置选择的三级单位
        //pvOptions.setLabels("省", "市", "区");
        //pvOptions.setTitle("选择城市");

        //  设置是否循环滚动
        pvOptions.setCyclic(false, false, false);


        // 设置默认选中的三级项目
        pvOptions.setSelectOptions(0, 0, 0);
        //  监听确定选择按钮
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String city = provinceList.get(options1);
                String address;
                //  如果是直辖市或者特别行政区只设置市和区/县
                if ("北京市".equals(city) || "上海市".equals(city) || "天津市".equals(city) || "重庆市".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                    address = provinceList.get(options1);
                } else {
                    address = provinceList.get(options1)
                            + "-" + cityList.get(options1).get(option2);
                }
                ed_chengshi.setText(address);
            }
        });
    }
    @Override
    public void onClick(View view) {
        String myphone;
        switch (view.getId()){
            case R.id.tuijian:
                pvOptions.show();
                break;
            case R.id.btn_yzm:
                if (isverify==false)
                {
                    return;
                }
                myphone=ed_phone.getText().toString();
                if (!MobileUtils.isMobileNO(myphone))
                {
                    ToastUtil.show(getContext(),"手机号码格式不对！");
                    return;
                }
                String url= AppBaseUrl.SENDVERIFYCODE;
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
                                ToastUtil.show(getContext(),jObj.getString("sms"));
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
                        ToastUtil.show(getContext(),"网络发生错误!");
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
                String name=ed_name.getText().toString();
                String yzm=ed_yzm.getText().toString();
                String pw=ed_pw.getText().toString();
                String pw1=ed_pw1.getText().toString();
                String chengshi= ed_chengshi.getText().toString();
                String tuijian= ed_mytuijian.getText().toString();
                if (!yzm.equals(mYzm)) {
                    ToastUtil.show(getContext(),"验证码不正确！");
                    return;
                }
                if (name.isEmpty()||pw.isEmpty()||pw1.isEmpty()){
                    ToastUtil.show(getContext(),"用户名或密码不能为空！");
                    return;
                }
                if (!pw.equals(pw1)) {
                    ToastUtil.show(getContext(),"两次密码不相同，请确认！");
                    return;
                }
                if (chengshi.isEmpty()){
                    ToastUtil.show(getContext(),"城市不能为空！");
                    return;
                }
                String url1= AppBaseUrl.REGISTER;
                RequestParams params1 = new RequestParams(url1);
                params1.addBodyParameter("username",mPhone);
                params1.addBodyParameter("pw",pw);
                params1.addBodyParameter("nickname",name);
                params1.addBodyParameter("city",chengshi);
                params1.addBodyParameter("recommend",tuijian);
                x.http().post(params1, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        showMessageDialog("注册信息","注册成功！");
                        ed_name.setText("");
                        ed_phone.setText("");
                        ed_pw.setText("");
                        ed_pw1.setText("");
                        ed_yzm.setText("");
                        ed_chengshi.setText("");
                        ed_mytuijian.setText("");
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
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer!=null){
            mTimer.cancel();
        }
    }
    //  解析json填充集合
    public void parseJson(String str) {
        try {
            //  获取json中的数组
            JSONArray jsonArray = new JSONArray(str);
            //  遍历数据组
            for (int i = 0; i < jsonArray.length(); i++) {
                //  获取省份的对象
                JSONObject provinceObject = jsonArray.optJSONObject(i);
                //  获取省份名称放入集合
                String provinceName = provinceObject.getString("name");
                provinceBeanList.add(new ProvinceBean(provinceName));
                provinceList.add(provinceName);
                //  获取城市数组
                JSONArray cityArray = provinceObject.optJSONArray("city");
                cities = new ArrayList<>();//   声明存放城市的集合
                //  遍历城市数组
                for (int j = 0; j < cityArray.length(); j++) {
                    //  获取城市对象
                    JSONObject cityObject = cityArray.optJSONObject(j);
                    //  将城市放入集合
                    String cityName = cityObject.optString("name");
                    cities.add(cityName);

                }

                cityList.add(cities);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
