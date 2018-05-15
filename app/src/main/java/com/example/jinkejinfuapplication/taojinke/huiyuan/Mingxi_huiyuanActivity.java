package com.example.jinkejinfuapplication.taojinke.huiyuan;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.mine.MymingxiActivity;
import com.example.jinkejinfuapplication.mine.Zhanghu_mingxiAdapter;
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

public class Mingxi_huiyuanActivity extends BaseActivity {
    private TextView all_shouyi;
    private RecyclerView rec_mingxi;
    private List<Map<String,String>> mList=new ArrayList<>();
    private Huiyuan_mingxiAdapter huiyuan_mingxiAdapter;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mingxi_huiyuan);
        setTitle("收入明细");
        all_shouyi=findView(R.id.all_shouyi);

        rec_mingxi= (RecyclerView) findViewById(R.id.rec_mingxi);
        LinearLayoutManager linManager1=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        rec_mingxi.setLayoutManager(linManager1);
        rec_mingxi.setHasFixedSize(true);
        huiyuan_mingxiAdapter =new Huiyuan_mingxiAdapter(this,mList);
        rec_mingxi.setAdapter(huiyuan_mingxiAdapter);
    }

    @Override
    protected void initData() {
        String url= AppBaseUrl.MINGXI_HUIYUAN;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("明细",result);
                mList.clear();
                try {
                    JSONObject jObj = new JSONObject(result);
                    jObj=jObj.getJSONObject("member");
                    all_shouyi.setText(jObj.getString("all_money")+"元");
                    JSONArray jrry=jObj.getJSONArray("mingxi");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject ob = jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("datetime",ob.getString("datetime"));
                        map.put("amount",ob.getString("amount"));
                        map.put("mobilephone",ob.getString("mobilephone"));
                        map.put("name",ob.getString("name"));
                        map.put("description",ob.getString("description"));
                        map.put("sort",ob.getString("sort"));
                        map.put("userId",ob.getString("userId"));
                        map.put("relation",ob.getString("relation"));
                        mList.add(map);
                    }
                    huiyuan_mingxiAdapter.setmList(mList);
                    huiyuan_mingxiAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(Mingxi_huiyuanActivity.this,"网络发生错误!");
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


}
