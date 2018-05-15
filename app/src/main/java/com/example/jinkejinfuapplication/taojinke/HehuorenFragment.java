package com.example.jinkejinfuapplication.taojinke;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseFragment;
import com.example.jinkejinfuapplication.taojinke.hehuoren.Hehuo_vipAdapter;
import com.example.jinkejinfuapplication.taojinke.hehuoren.Hehuo_zhifuActivity;
import com.example.jinkejinfuapplication.taojinke.hehuoren.Hehuo_zhuceActivity;
import com.example.jinkejinfuapplication.taojinke.hehuoren.Mingxi_hehuorenActivity;
import com.example.jinkejinfuapplication.taojinke.hehuoren.MyhehuorenActivity;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.ToastUtil;

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

/**
 * Created by naihe on 2017/12/4.
 */

public class HehuorenFragment extends BaseFragment implements View.OnClickListener {
    private TextView shouyi_leiji,vip,shouyi_jinri,hehuoren_yue,tixian,tuijian_huiyuan,jihuo;
    private LinearLayout lin_mingxi,lin_tixian,lin_zhuce,lin_hehuoren,lin_zhaomu;
    private Button zhuce1,zhuce2;
    private RecyclerView rec_hehuo;
    private List<Map<String,String>> mList=new ArrayList<>();
    private Hehuo_vipAdapter hehuo_vipAdapter;
    private ImageView arrow;
    private boolean isshow=false;
    @Override
    public void initData(Bundle savedInstanceState) {
        initHehuoren();

        String url= AppBaseUrl.TAOJINKE_HEHUOREN_TUIJIAN;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("推荐vip",result);
                mList.clear();
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("proxy");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject ob = jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("num",ob.getString("num"));
                        map.put("FxLevel",ob.getString("FxLevel"));
                        mList.add(map);
                    }
                    hehuo_vipAdapter.setmList(mList);
                    hehuo_vipAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
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

    private void initHehuoren() {
        String url1= AppBaseUrl.TAOJINKE_HEHUOREN;
        RequestParams params1 = new RequestParams(url1);
        params1.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("合伙人",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    jObj=jObj.getJSONObject("proxy");
                    shouyi_leiji.setText(jObj.getString("proxy_income")+"元");
                    vip.setText(jObj.getString("proxy"));
                    shouyi_jinri.setText(jObj.getString("today_money"));
                    hehuoren_yue.setText(jObj.getString("month_proxy"));
                    tixian.setText(jObj.getString("balance")+"元");
                    tuijian_huiyuan.setText(jObj.getString("month_member")+"人");
                    if (jObj.getString("proxy").equals("非合伙人")){
                        jihuo.setText("未激活");
                    }else{
                        jihuo.setText("已激活");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
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

    @Override
    public void initView(View view) {
        shouyi_leiji= (TextView) view.findViewById(R.id.shouyi_leiji);
        vip= (TextView) view.findViewById(R.id.vip);
        shouyi_jinri= (TextView) view.findViewById(R.id.shouyi_jinri);
        hehuoren_yue= (TextView) view.findViewById(R.id.hehuoren_yue);
        tixian= (TextView) view.findViewById(R.id.tixian);
        tuijian_huiyuan= (TextView) view.findViewById(R.id.tuijian_huiyuan);
        jihuo= (TextView) view.findViewById(R.id.jihuo);
        zhuce1= (Button) view.findViewById(R.id.zhuce1);
        zhuce1.setOnClickListener(this);
        zhuce2= (Button) view.findViewById(R.id.zhuce2);
        zhuce2.setOnClickListener(this);
        arrow= (ImageView) view.findViewById(R.id.arrow_tuijian);
        arrow.setOnClickListener(this);
        view.findViewById(R.id.lin_mingxi).setOnClickListener(this);
        view.findViewById(R.id.lin_tixian).setOnClickListener(this);
        view.findViewById(R.id.lin_hehuo_zhuc).setOnClickListener(this);
        view.findViewById(R.id.lin_myhehuo).setOnClickListener(this);
        view.findViewById(R.id.lin_zhaomuhehuo).setOnClickListener(this);

        rec_hehuo= (RecyclerView) view.findViewById(R.id.rec_hehuo);
        LinearLayoutManager linManager1=new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        rec_hehuo.setLayoutManager(linManager1);
        rec_hehuo.setHasFixedSize(true);
        hehuo_vipAdapter=new Hehuo_vipAdapter(getContext(),mList);
        rec_hehuo.setAdapter(hehuo_vipAdapter);

    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_hehuoren;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.arrow_tuijian:
                if (isshow){
                    isshow=!isshow;
                    rec_hehuo.setVisibility(View.GONE);
                    arrow.setImageResource(R.drawable.arrow_bttom);
                }else {
                    isshow=!isshow;
                    rec_hehuo.setVisibility(View.VISIBLE);
                    arrow.setImageResource(R.drawable.arrow_up);
                }
                break;
            case R.id.zhuce2:
            case R.id.zhuce1:
            case R.id.lin_hehuo_zhuc:
                String url1= AppBaseUrl.PANDUAN_HEHUOREN;
                RequestParams params1= new RequestParams(url1);
                params1.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
                x.http().post(params1, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject jObj = new JSONObject(result);
                            if (!jObj.getString("proxy").equals("合伙人不存在")){
                                jObj = jObj.getJSONObject("proxy");
                                String state=jObj.getString("state");
                                if (state.equals("0")){
                                    Intent intent=new Intent(getContext(),Hehuo_zhifuActivity.class);
                                    getContext().startActivity(intent);
                                }else if ((state.equals("1"))){
                                    showToast("你已经是合伙人！");
                                }
                            }else {
                                Intent intent=new Intent(getContext(),Hehuo_zhuceActivity.class);
                                getContext().startActivity(intent);
                            }

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
            case R.id.lin_tixian:
                intent=new Intent(getContext(), TixianActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.lin_myhehuo:
                if (vip.getText().toString().equals("合伙人")){
                    intent=new Intent(getContext(),MyhehuorenActivity.class);
                    getContext().startActivity(intent);
                }else {
                    ToastUtil.show(getContext(),"你还不是合伙人!");
                }

                break;
            case R.id.lin_zhaomuhehuo:
                intent=new Intent(getContext(),Hehuo_zhaomuActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.lin_mingxi:
                intent=new Intent(getContext(),Mingxi_hehuorenActivity.class);
                getContext().startActivity(intent);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initHehuoren();
    }
}
