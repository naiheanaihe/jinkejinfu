package com.example.jinkejinfuapplication.taojinke.huiyuan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
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

public class Fensi_leijiActivity extends BaseActivity {
    private List<Map<String,String>> mList=new ArrayList<>();
    private RecyclerView rec_fensi;
    private Fensi_leijiAdapter fensi_leijiAdapter;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fensi_leiji);
        rec_fensi= (RecyclerView) findViewById(R.id.rec_fensi);
        LinearLayoutManager linManager1=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        rec_fensi.setLayoutManager(linManager1);
        rec_fensi.setHasFixedSize(true);
        fensi_leijiAdapter=new Fensi_leijiAdapter(this,mList);
        rec_fensi.setAdapter(fensi_leijiAdapter);
    }

    @Override
    protected void initData() {
        String dengji=getIntent().getStringExtra("dengji");
        setTitle("累计"+dengji+"代粉丝");
        String url1= AppBaseUrl.FENSI_LEIJI;
        RequestParams params1= new RequestParams(url1);
        params1.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        params1.addBodyParameter("id", dengji);
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.d("粉丝",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("fans");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject ob = jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("name",ob.getString("name"));
                        map.put("member",ob.getString("member"));
                        map.put("userId",ob.getString("userId"));
                        String datetime=ob.getString("datetime");
                        String[]  datetimes=datetime.split(" ");
                        map.put("datetime",datetimes[0]);
                        String phone=ob.getString("mobilephone");
                        String mobilephone= phone.substring(0,3)+"****"+phone.substring(7,phone.length());
                        map.put("mobilephone",mobilephone);
                        mList.add(map);
                    }
                    fensi_leijiAdapter.setmList(mList);
                    fensi_leijiAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(Fensi_leijiActivity.this,"网络发生错误!");
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
