package com.example.jinkejinfuapplication.shouye;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.shiping.Shiping_zhuyeAdapter;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.ToastUtil;
import com.example.jinkejinfuapplication.xiangq.ZixunAdapter;

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

import static com.example.jinkejinfuapplication.utils.DisplayUtil.dip2px;

public class SeachActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private EditText ed_search;
    private TextView btn_sousuo;
    private List<String> mListString =new ArrayList<>();
    private List<Map<String,String>> myouxiList =new ArrayList<>();
    private List<Map<String,String>> mzixunList =new ArrayList<>();
    private List<Map<String,String>> mshipingList =new ArrayList<>();
    private Search_youxiAdapter search_youxiAdapter;
    private ZixunAdapter zixunAdapter;
    private Shiping_zhuyeAdapter shiping_zhuyeAdapter;
    private RadioGroup rgroup;
    private RecyclerView rec_sousuo1,rec_sousuo2;
    private TextView kong_jieguo;
    private int sousuotype=1;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_seach);
        setTitle("搜索");
        btn_sousuo=findView(R.id.btn_sousuo);
        ed_search=findView(R.id.ed_search);
        kong_jieguo=findView(R.id.kong_jieguo);
        rgroup=findView(R.id.group_sousuo);
        rgroup.setOnCheckedChangeListener(this);
        rec_sousuo1=findView(R.id.rec_jieguo1);
        rec_sousuo2=findView(R.id.rec_jieguo2);
        btn_sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initsearch();
            }
        });

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rec_sousuo1.setLayoutManager(layoutManager2);
        rec_sousuo1.setHasFixedSize(true);
        search_youxiAdapter=new Search_youxiAdapter(this,myouxiList);
        rec_sousuo1.setAdapter(search_youxiAdapter);
        zixunAdapter=new ZixunAdapter(this,mzixunList);

        GridLayoutManager linManager1=new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL,false);
        rec_sousuo2.setLayoutManager(linManager1);
        rec_sousuo2.setHasFixedSize(true);
        shiping_zhuyeAdapter=new Shiping_zhuyeAdapter(this,mshipingList);
        rec_sousuo2.setAdapter(shiping_zhuyeAdapter);
    }

    private void initsearch() {
        myouxiList.clear();
        mzixunList.clear();
        mshipingList.clear();
        String sousuo=ed_search.getText().toString();
        String url1= AppBaseUrl.SEARCH;
        RequestParams params1 = new RequestParams(url1);
        params1.addBodyParameter("search",sousuo);
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("搜索结果",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    jObj=jObj.getJSONObject("search");
                    JSONArray jrry=jObj.getJSONArray("game");
                    for (int i=0;i<jrry.length();i++) {
                        Map<String,String> map=new HashMap<String, String>();
                        JSONObject temp = jrry.getJSONObject(i);
                        map.put("game_name",temp.getString("game_name"));
                        map.put("id",temp.getString("id"));
                        map.put("type",temp.getString("type"));
                        map.put("game_type",temp.getString("game_type"));
                        map.put("game_pic",AppBaseUrl.BASEURL+temp.getString("game_pic"));
                        myouxiList.add(map);
                    }
                    search_youxiAdapter.setmList(myouxiList);

                    JSONArray jrry2=jObj.getJSONArray("video");
                    for (int i=0;i<jrry2.length();i++) {
                        Map<String,String> map=new HashMap<String, String>();
                        JSONObject temp = jrry2.getJSONObject(i);
                        map.put("gameId",temp.getString("gameId"));
                        map.put("id",temp.getString("id"));
                        map.put("background",AppBaseUrl.BASEURL+temp.getString("background"));
                        map.put("title",temp.getString("title"));
                        mshipingList.add(map);
                    }
                    shiping_zhuyeAdapter.setmList(mshipingList);

                    JSONArray jrry3=jObj.getJSONArray("article");
                    for (int i=0;i<jrry3.length();i++) {
                        Map<String,String> map=new HashMap<String, String>();
                        JSONObject temp = jrry3.getJSONObject(i);
                        map.put("gameId",temp.getString("gameId"));
                        map.put("id",temp.getString("id"));
                        map.put("background",AppBaseUrl.BASEURL+temp.getString("background"));
                        map.put("title",temp.getString("title"));
                        map.put("datetime",temp.getString("datetime"));
                        mzixunList.add(map);
                    }
                    zixunAdapter.setmList(mzixunList);
                    rgroup.setVisibility(View.VISIBLE);

                    switch (sousuotype){
                        case 1:
                            if (myouxiList.size()==0){
                                kong_jieguo.setVisibility(View.VISIBLE);
                                rec_sousuo1.setVisibility(View.GONE);
                            }else {
                                kong_jieguo.setVisibility(View.GONE);
                                rec_sousuo1.setVisibility(View.VISIBLE);
                                rec_sousuo1.setAdapter(search_youxiAdapter);
                                search_youxiAdapter.notifyDataSetChanged();
                            }
                            break;
                        case 2:
                            if (mzixunList.size()==0){
                                kong_jieguo.setVisibility(View.VISIBLE);
                                rec_sousuo1.setVisibility(View.GONE);
                            }else {
                                kong_jieguo.setVisibility(View.GONE);
                                rec_sousuo1.setVisibility(View.VISIBLE);
                                rec_sousuo1.setAdapter(zixunAdapter);
                                zixunAdapter.notifyDataSetChanged();
                            }
                            break;
                        case 3:
                            if (mshipingList.size()==0){
                                kong_jieguo.setVisibility(View.VISIBLE);
                                rec_sousuo2.setVisibility(View.GONE);
                            }else {
                                kong_jieguo.setVisibility(View.GONE);
                                rec_sousuo2.setVisibility(View.VISIBLE);
                                rec_sousuo2.setAdapter(shiping_zhuyeAdapter);
                                shiping_zhuyeAdapter.notifyDataSetChanged();
                            }
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(SeachActivity.this,"网络发生错误!");
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
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.btn_youxi:
                sousuotype=1;
                if (myouxiList.size()==0){
                    rec_sousuo1.setVisibility(View.GONE);
                    rec_sousuo2.setVisibility(View.GONE);
                    kong_jieguo.setVisibility(View.VISIBLE);
                }else{
                    kong_jieguo.setVisibility(View.GONE);
                    rec_sousuo1.setAdapter(search_youxiAdapter);
                    rec_sousuo1.setVisibility(View.VISIBLE);
                    rec_sousuo2.setVisibility(View.GONE);
                    search_youxiAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.btn_zixun:
                sousuotype=2;
                if (mzixunList.size()==0){
                    rec_sousuo1.setVisibility(View.GONE);
                    rec_sousuo2.setVisibility(View.GONE);
                    kong_jieguo.setVisibility(View.VISIBLE);
                }else{
                    kong_jieguo.setVisibility(View.GONE);
                    rec_sousuo1.setAdapter(zixunAdapter);
                    rec_sousuo1.setVisibility(View.VISIBLE);
                    rec_sousuo2.setVisibility(View.GONE);
                    zixunAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.btn_shiping:
                sousuotype=3;
                if (mshipingList.size()==0){
                    rec_sousuo1.setVisibility(View.GONE);
                    rec_sousuo2.setVisibility(View.GONE);
                    kong_jieguo.setVisibility(View.VISIBLE);
                }else{
                    kong_jieguo.setVisibility(View.GONE);
                    rec_sousuo1.setVisibility(View.GONE);
                    rec_sousuo2.setVisibility(View.VISIBLE);
                    shiping_zhuyeAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
}
