package com.example.jinkejinfuapplication.taojinke.hehuoren;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseFragment;
import com.example.jinkejinfuapplication.bean.ProvinceBean;
import com.example.jinkejinfuapplication.taojinke.hehuoren.Hehuo_zhifuActivity;
import com.example.jinkejinfuapplication.taojinke.hehuoren.Hehuo_zhuceActivity;
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
 * Created by naihe on 2017/12/4.
 */

public class Hehuo_zhuceFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout lin_chengshitype,lin_type1,lin_type2,lin_type3,lin_type4,lin_selchengshi;
    private EditText ed_yzm,ed_name,ed_phone,ed_zhengjian;
    private ImageView arrow;
    private boolean isshow=false;
    private TextView name_chengshi,chengshitype;
    private Button btn_yzm,zhuce;
    private RadioGroup group_zhangjian;
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

    private String citytype;
    OptionsPickerView pvOptions;
    //  省份
    ArrayList<ProvinceBean> provinceBeanList = new ArrayList<>();
    ArrayList<String> provinceList = new ArrayList<>();
    //  城市
    ArrayList<String> cities;
    ArrayList<List<String>> cityList = new ArrayList<>();
    //  省会城市
    ArrayList<String> shenghuicities=new ArrayList<>();
    //  直辖市
    ArrayList<String> zhixiacities=new ArrayList<>();
    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.lin_seltype).setOnClickListener(this);
        view.findViewById(R.id.lin_type1).setOnClickListener(this);
        view.findViewById(R.id.lin_type2).setOnClickListener(this);
        view.findViewById(R.id.lin_type3).setOnClickListener(this);
        view.findViewById(R.id.lin_type4).setOnClickListener(this);
        lin_chengshitype= (LinearLayout) view.findViewById(R.id.lin_chengshitype);
        arrow= (ImageView) view.findViewById(R.id.arrow_tuijian);
        lin_selchengshi=(LinearLayout) view.findViewById(R.id.lin_selchengshi);
        lin_selchengshi.setOnClickListener(this);
        name_chengshi= (TextView) view.findViewById(R.id.name_chengshi);
        chengshitype= (TextView) view.findViewById(R.id.chengshitype);
        ed_name= (EditText) view.findViewById(R.id.myname);
        ed_phone= (EditText) view.findViewById(R.id.myphone);
        ed_zhengjian= (EditText) view.findViewById(R.id.myzhengjian);
        ed_yzm= (EditText) view.findViewById(R.id.yzm);
        btn_yzm= (Button) view.findViewById(R.id.btn_yzm);
        zhuce= (Button) view.findViewById(R.id.zhuce);
        btn_yzm.setOnClickListener(this);
        zhuce.setOnClickListener(this);
        group_zhangjian= (RadioGroup) view.findViewById(R.id.group_zhengjian);

        initaddress();
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_hehuozhuce;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lin_seltype:
                if (isshow){
                    isshow=!isshow;
                    lin_chengshitype.setVisibility(View.GONE);
                    arrow.setImageResource(R.drawable.arrow_bttom);
                }else {
                    isshow=!isshow;
                    lin_chengshitype.setVisibility(View.VISIBLE);
                    arrow.setImageResource(R.drawable.arrow_up);
                }
                break;
            case R.id.lin_type1:
                chengshitype.setText("地级城市");
                isshow=false;
                citytype="1";
                lin_chengshitype.setVisibility(View.GONE);
                arrow.setImageResource(R.drawable.arrow_bttom);
                lin_selchengshi.setVisibility(View.VISIBLE);
                pvOptions.setPicker(provinceList, cityList, true);
                pvOptions.setSelectOptions(0, 0, 0);
                break;
            case R.id.lin_type2:
                chengshitype.setText("省会城市");
                isshow=false;
                citytype="2";
                lin_chengshitype.setVisibility(View.GONE);
                arrow.setImageResource(R.drawable.arrow_bttom);
                lin_selchengshi.setVisibility(View.VISIBLE);
                pvOptions.setPicker(shenghuicities);
                pvOptions.setSelectOptions(0, 0, 0);
                break;
            case R.id.lin_type3:
                chengshitype.setText("直辖市");
                isshow=false;
                citytype="3";
                lin_chengshitype.setVisibility(View.GONE);
                arrow.setImageResource(R.drawable.arrow_bttom);
                lin_selchengshi.setVisibility(View.VISIBLE);
                pvOptions.setPicker(zhixiacities);
                pvOptions.setSelectOptions(0, 0, 0);
                break;
            case R.id.lin_type4:
                chengshitype.setText("北京市");
                isshow=false;
                citytype="4";
                lin_chengshitype.setVisibility(View.GONE);
                arrow.setImageResource(R.drawable.arrow_bttom);
                lin_selchengshi.setVisibility(View.GONE);
                name_chengshi.setText("北京市");
                break;
            case R.id.lin_selchengshi:
                pvOptions.show();
                break;

            case R.id.btn_yzm:
                if (isverify==false)
                {
                    return;
                }
                String myphone=ed_phone.getText().toString();
                if (!MobileUtils.isMobileNO(myphone))
                {
                    ToastUtil.show(getContext(),"手机号码格式不对！");
                    return;
                }
                String url= AppBaseUrl.SENDVERIFYCODE_HEHUOREN;
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
                String type;
                if (group_zhangjian.getCheckedRadioButtonId()== R.id.rbtn_zhengjian1){
                    type="身份证";
                }else {
                    type="营业执照注册号";
                }
                String name=ed_name.getText().toString();
                String phone=ed_phone.getText().toString();
                String city=name_chengshi.getText().toString();
                String zhengjian=ed_zhengjian.getText().toString();
                String yzm=ed_yzm.getText().toString();
                if (!yzm.equals(mYzm)) {
                    ToastUtil.show(getContext(),"验证码不正确！");
                    return;
                }
                if (city.equals("具体城市选择")){
                    ToastUtil.show(getContext(),"请选择城市！");
                    return;
                }
                if (name.isEmpty()||phone.isEmpty()||zhengjian.isEmpty()){
                    ToastUtil.show(getContext(),"必填项不能为空！");
                    return;
                }
                String url1= AppBaseUrl.ZHUCE_HEHUOREN;
                RequestParams params1= new RequestParams(url1);
                params1.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
                params1.addBodyParameter("mobilephone",phone);
                params1.addBodyParameter("name",name);
                params1.addBodyParameter("city",city);
                params1.addBodyParameter("Document_number",zhengjian);
                params1.addBodyParameter("Document_type",type);
                params1.addBodyParameter("rank", Hehuo_zhuceActivity.hehuotype+"");
                LalaLog.i("参数",params1.toString());
                x.http().post(params1, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        LalaLog.i("返回",result);
                        getActivity().finish();
                        Intent intent=new Intent(getContext(),Hehuo_zhifuActivity.class);
                        getContext().startActivity(intent);
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
    private void initaddress()
    {
        //  创建选项选择器
        pvOptions = new OptionsPickerView(getContext());

        //  获取json数据
        String province_data_json = JsonFileReader.getJson(getContext(), "p_city.json");
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
                //String city = provinceList.get(options1);
                String address="";
                //  如果是直辖市或者特别行政区只设置市和区/县
                /*if ("北京市".equals(city) || "上海市".equals(city) || "天津市".equals(city) || "重庆市".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                    address = provinceList.get(options1);
                } else {
                    address = provinceList.get(options1)
                            + " " + cityList.get(options1).get(option2);
                }*/
                switch (citytype){
                    case "1":
                        address = provinceList.get(options1)
                                + "-" + cityList.get(options1).get(option2);
                        break;
                    case "2":
                        address = shenghuicities.get(options1);
                        break;
                    case "3":
                        address = zhixiacities.get(options1);
                        break;
                    case "4":
                        address = "北京";
                        break;
                }
                name_chengshi.setText(address);
            }
        });
    }
    //  解析json填充集合
    public void parseJson(String str) {
        try {
            //  获取json中的数组
            JSONArray jsonArray = new JSONArray(str);
            //  遍历数据组
            for (int i = 0; i < jsonArray.length(); i++) {
                //  获取城市对象
                JSONObject provinceObject = jsonArray.optJSONObject(i);
                if (provinceObject.getString("level").equals("1"))
                {
                    //  获取省份名称放入集合
                    String provinceName = provinceObject.getString("name");
                    String ids = provinceObject.getString("ids");
                    provinceBeanList.add(new ProvinceBean(provinceName,ids));
                    provinceList.add(provinceName);
                }

                if (provinceObject.getString("level").equals("4"))
                {
                    shenghuicities.add(provinceObject.getString("name"));
                }
                if (provinceObject.getString("level").equals("3"))
                {
                    zhixiacities.add(provinceObject.getString("name"));
                }
            }

            for (int n = 0; n < provinceBeanList.size(); n++) {
                cities = new ArrayList<>();
                for (int y = 0; y < jsonArray.length(); y++) {
                    //  获取城市对象
                    JSONObject Object = jsonArray.optJSONObject(y);
                    if (provinceBeanList.get(n).getIds().equals( Object.getString("parentId"))&Object.getString("level").equals("5")){
                        //  将城市放入集合
                        String cityName = Object.optString("name");
                        cities.add(cityName);
                    }
                }
                //  将存放城市的集合放入集合
                cityList.add(cities);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
