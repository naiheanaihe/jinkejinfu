package com.example.jinkejinfuapplication.taojinke;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseFragment;
import com.example.jinkejinfuapplication.taojinke.huiyuan.ChoujiangActivity;
import com.example.jinkejinfuapplication.taojinke.huiyuan.Fensi_jiangliActivity;
import com.example.jinkejinfuapplication.taojinke.huiyuan.Jifen_jiangliActivity;
import com.example.jinkejinfuapplication.taojinke.huiyuan.Mingxi_huiyuanActivity;
import com.example.jinkejinfuapplication.taojinke.huiyuan.MyVIPActivity;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by naihe on 2017/12/4.
 */

public class HuiyuanFragment extends BaseFragment implements View.OnClickListener {
    private TextView shouyi_leiji,vip,shouyi_jinri,fensi_jinri,fensi_leiji,tixian,xiaojin,jifen,yingpian,vr,myvip,choujiang;
    private Button zhuce1,zhuce2,zhuce3,zhuce4,zhuce5,zhuce6;
    private TextView qiandao;
    private RelativeLayout lin_qiandao;
    private String sign;

    @Override
    public void initData(Bundle savedInstanceState) {
        init_huiyuan();

    }

    private void init_huiyuan() {
        String url1= AppBaseUrl.TAOJINKE_HUIYUAN;
        RequestParams params1 = new RequestParams(url1);
        params1.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("个人",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    jObj=jObj.getJSONObject("member");
                    shouyi_leiji.setText(jObj.getString("member_income")+"元");
                    vip.setText(jObj.getString("member"));
                    shouyi_jinri.setText(jObj.getString("today_money"));
                    fensi_jinri.setText(jObj.getString("today_num"));
                    fensi_leiji.setText(jObj.getString("all_num")+"人");
                    tixian.setText(jObj.getString("balance")+"元");
                    xiaojin.setText(jObj.getString("chongxiao")+"元");
                    jifen.setText(jObj.getString("integral")+"分");
                    yingpian.setText(jObj.getString("movie_num")+"部");
                    if (jObj.getString("member").equals("普通用户")){
                        myvip.setText("开通VIP会员");
                    }else if (jObj.getString("member").equals("VIP6")){
                        myvip.setText("我的VIP会员");
                    }else{
                        myvip.setText("升级VIP会员");
                    }

                    switch (jObj.getString("member")){
                        case "普通用户":
                            lin_qiandao.setVisibility(View.GONE);
                            break;
                        case "VIP1":
                            lin_qiandao.setVisibility(View.VISIBLE);
                            qiandao.setText("签到领2积分");
                            break;
                        case "VIP2":
                            lin_qiandao.setVisibility(View.VISIBLE);
                            qiandao.setText("签到领4积分");
                            break;
                        case "VIP3":
                            lin_qiandao.setVisibility(View.VISIBLE);
                            qiandao.setText("签到领6积分");
                            break;
                        case "VIP4":
                            lin_qiandao.setVisibility(View.VISIBLE);
                            qiandao.setText("签到领8积分");
                            break;
                        case "VIP5":
                            lin_qiandao.setVisibility(View.VISIBLE);
                            qiandao.setText("签到领10积分");
                            break;
                        case "VIP6":
                            lin_qiandao.setVisibility(View.VISIBLE);
                            qiandao.setText("签到领12积分");
                            break;
                    }
                    sign=jObj.getString("sign");
                    if (sign.equals("1")){
                        qiandao.setText("已签到");
                    }
                    choujiang.setText("免费抽奖"+jObj.getString("integral_num")+"次");
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
        fensi_jinri= (TextView) view.findViewById(R.id.fensi_jinri);
        fensi_leiji= (TextView) view.findViewById(R.id.fensi_leiji);
        tixian= (TextView) view.findViewById(R.id.tixian);
        xiaojin= (TextView) view.findViewById(R.id.xiaojin);
        jifen= (TextView) view.findViewById(R.id.jifen);
        yingpian= (TextView) view.findViewById(R.id.yingpian);
        vr= (TextView) view.findViewById(R.id.vr);
        zhuce1= (Button) view.findViewById(R.id.zhuce1);
        zhuce2= (Button) view.findViewById(R.id.zhuce2);
        zhuce3= (Button) view.findViewById(R.id.zhuce3);
        zhuce4= (Button) view.findViewById(R.id.zhuce4);
        zhuce5= (Button) view.findViewById(R.id.zhuce5);
        zhuce6= (Button) view.findViewById(R.id.zhuce6);
        zhuce1.setOnClickListener(this);
        zhuce2.setOnClickListener(this);
        zhuce3.setOnClickListener(this);
        zhuce4.setOnClickListener(this);
        zhuce5.setOnClickListener(this);
        zhuce6.setOnClickListener(this);
        view.findViewById(R.id.lin_fensi).setOnClickListener(this);
        view.findViewById(R.id.lin_jifen).setOnClickListener(this);
        view.findViewById(R.id.lin_mingxi).setOnClickListener(this);
        view.findViewById(R.id.lin_tixian).setOnClickListener(this);
        view.findViewById(R.id.lin_chongzhi).setOnClickListener(this);
        view.findViewById(R.id.lin_huiyaun_zhuce).setOnClickListener(this);
        view.findViewById(R.id.lin_huiyaun_zhaomu).setOnClickListener(this);
        myvip= (TextView) view.findViewById(R.id.myvip);
        myvip.setOnClickListener(this);

        qiandao= (TextView) view.findViewById(R.id.qiandao);
        qiandao.setOnClickListener(this);
        lin_qiandao= (RelativeLayout) view.findViewById(R.id.lin_qiandao);
        choujiang= (TextView) view.findViewById(R.id.choujiang);
        choujiang.setOnClickListener(this);
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_huiyuan;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.lin_fensi:
                intent=new Intent(getContext(), Fensi_jiangliActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.lin_huiyaun_zhaomu:
                intent=new Intent(getContext(), Huiyuan_zhaomuActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.zhuce1:
            case R.id.zhuce2:
            case R.id.zhuce3:
            case R.id.zhuce4:
            case R.id.zhuce5:
            case R.id.zhuce6:
            case R.id.lin_huiyaun_zhuce:
            case R.id.myvip:
                intent=new Intent(getContext(), MyVIPActivity.class);
                intent.putExtra("vip",vip.getText().toString());
                getContext().startActivity(intent);
                break;
            case R.id.lin_mingxi:
                intent=new Intent(getContext(), Mingxi_huiyuanActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.lin_jifen:
                intent=new Intent(getContext(), Jifen_jiangliActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.lin_tixian:
                intent=new Intent(getContext(), TixianActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.lin_chongzhi:
                ToastUtil.show(getContext(),"暂未开放!");
                break;
            case R.id.qiandao:
                if (sign.equals("1")){
                    return;
                }
                String url1= AppBaseUrl.QIANDAO;
                RequestParams params1 = new RequestParams(url1);
                params1.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
                x.http().post(params1, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        LalaLog.i("签到",result);
                        try {
                            JSONObject jObj = new JSONObject(result);
                            jObj=jObj.getJSONObject("sign");
                            qiandao.setText("已签到");
                            choujiang.setText("免费抽奖"+jObj.getString("integral_num")+"次");
                            jifen.setText(jObj.getString("integral")+"分");
                            sign="0";
                            ToastUtil.show(getContext(),"签到成功!");
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
                break;
            case R.id.choujiang:
                intent=new Intent(getContext(), ChoujiangActivity.class);
                getContext().startActivity(intent);
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        init_huiyuan();
    }
}
