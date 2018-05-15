package com.example.jinkejinfuapplication.shouye.hudong;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseFragment;
import com.example.jinkejinfuapplication.utils.DisplayUtil;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.SpaceItemDecoration;
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

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by naihe on 2017/12/4.
 */

public class WendaFragment extends BaseFragment implements View.OnClickListener {
    private RecyclerView rec_huati;
    private Hudong_wendaAdapter hudong_wendaAdapter;
    private List<Map<String, String>> mHuatiList=new ArrayList<>();
    private PtrClassicFrameLayout ptrFrameLayout;
    private Spinner spinner_paixu;
    private String[] jines=new String[]{"按时间排序","按浏览量排序","按点赞数排序"};
    private int page=0;
    private String paixu="1";
    private TextView num_huati,num_jinri;
    @Override
    public void initData(Bundle savedInstanceState) {
        redate();
    }
    private void redate() {
        page=0;
        String url= AppBaseUrl.HUATI;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        params.addBodyParameter("sort", "topic1");
        params.addBodyParameter("str", page+"");
        params.addBodyParameter("str1", paixu);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("话题",result);
                mHuatiList.clear();
                try {
                    JSONObject jObj = new JSONObject(result);
                    jObj=jObj.getJSONObject("topic");
                    num_huati.setText(jObj.getString("num"));
                    num_jinri.setText(jObj.getString("num1"));
                    JSONArray jrry=jObj.getJSONArray("topic_list");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject ob = jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("datetime",ob.getString("datetime"));
                        map.put("name",ob.getString("name"));
                        map.put("clicknum",ob.getString("clicknum"));
                        map.put("id",ob.getString("id"));
                        map.put("title",ob.getString("title"));
                        map.put("likenum",ob.getString("likenum"));
                        map.put("commentnum",ob.getString("commentnum"));
                        map.put("hot",ob.getString("hot"));
                        map.put("content",ob.getString("content"));
                        map.put("img1",ob.getString("img1"));
                        map.put("img2",ob.getString("img2"));
                        map.put("state",ob.getString("state"));
                        if (ob.getString("pic").substring(0,4).equals("http")){
                            map.put("pic",ob.getString("pic"));
                        }else
                        {
                            map.put("pic",AppBaseUrl.BASEURL+ob.getString("pic"));
                        }
                        mHuatiList.add(map);
                    }
                    hudong_wendaAdapter.setmList(mHuatiList);
                    hudong_wendaAdapter.notifyDataSetChanged();
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
    private void moredate() {
        String url= AppBaseUrl.HUATI;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        params.addBodyParameter("sort", "topic1");
        params.addBodyParameter("str", page+"");
        params.addBodyParameter("str1", paixu);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("话题",result);
                try {
                    List<Map<String,String>> mNewslist=new ArrayList<>();
                    JSONObject jObj = new JSONObject(result);
                    jObj=jObj.getJSONObject("topic");
                    JSONArray jrry=jObj.getJSONArray("topic_list");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject ob = jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("datetime",ob.getString("datetime"));
                        map.put("name",ob.getString("name"));
                        map.put("clicknum",ob.getString("clicknum"));
                        if (ob.getString("pic").substring(0,4).equals("http")){
                            map.put("pic",ob.getString("pic"));
                        }else
                        {
                            map.put("pic",AppBaseUrl.BASEURL+ob.getString("pic"));
                        }
                        map.put("id",ob.getString("id"));
                        map.put("title",ob.getString("title"));
                        map.put("likenum",ob.getString("likenum"));
                        map.put("hot",ob.getString("hot"));
                        map.put("content",ob.getString("content"));
                        map.put("img1",ob.getString("img1"));
                        map.put("img2",ob.getString("img2"));
                        map.put("state",ob.getString("state"));
                        map.put("commentnum",ob.getString("commentnum"));
                        mNewslist.add(map);
                    }
                    if (mNewslist.size()==0)
                    {
                        ptrFrameLayout.refreshComplete();
                        ToastUtil.show(getContext(),"没有更多数据了");
                    }else
                    {
                        mHuatiList.addAll(mNewslist);
                        ptrFrameLayout.refreshComplete();
                        hudong_wendaAdapter.notifyItemRangeInserted(page*10,mNewslist.size());
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
        rec_huati= (RecyclerView) view.findViewById(R.id.rec_huati);
        LinearLayoutManager linManager1=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rec_huati.setLayoutManager(linManager1);
        rec_huati.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(getContext(),7),true));
        rec_huati.setHasFixedSize(true);
        hudong_wendaAdapter=new Hudong_wendaAdapter(getContext(),mHuatiList);
        rec_huati.setAdapter(hudong_wendaAdapter);

        num_huati= (TextView) view.findViewById(R.id.num_huati);
        num_jinri= (TextView) view.findViewById(R.id.num_jinri);
        view.findViewById(R.id.btn_huati_fenxiang).setOnClickListener(this);
        view.findViewById(R.id.btn_huati_myhuati).setOnClickListener(this);


        spinner_paixu= (Spinner) view.findViewById(R.id.spinner_paixu);
        Spinner_huatiAdapater spinnerAdapater=new Spinner_huatiAdapater(getContext(),jines);
        spinner_paixu .setAdapter(spinnerAdapater);
        spinner_paixu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                paixu=(i+1)+"";
                redate();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        view.findViewById(R.id.add).setOnClickListener(this);

        ptrFrameLayout= (PtrClassicFrameLayout) view.findViewById(R.id.prtframelayout);
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page+=1;
                        moredate();
                        ptrFrameLayout.refreshComplete();
                    }
                },500);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        redate();
                        ptrFrameLayout.refreshComplete();
                    }
                },500);
            }
        });
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_wenda;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add:
                Intent intent=new Intent(getContext(),AddWendaActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.btn_huati_fenxiang:
                intent=new Intent(getContext(),MyhuifuAndWendaActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.btn_huati_myhuati:
                intent=new Intent(getContext(),MyhuifuAndWendaActivity.class);
                getContext().startActivity(intent);
                break;
        }
    }
}
