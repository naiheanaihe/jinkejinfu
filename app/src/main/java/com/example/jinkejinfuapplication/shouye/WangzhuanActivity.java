package com.example.jinkejinfuapplication.shouye;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.shouye.dianjing.Saishi_xinwenAdapter;
import com.example.jinkejinfuapplication.shouye.dianjing.XinwenActivity;
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

public class WangzhuanActivity extends BaseActivity {
    private RecyclerView rec;
    private WangzhuanAdapter wangzhuanAdapter;

    private int page=0;
    private List<Map<String, String>> mList=new ArrayList<>();
    private PtrClassicFrameLayout ptrFrameLayout;
    String myurl= AppBaseUrl.BASEURL+"app/make/money";
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_wangzhuan);
        setTitle("网赚攻略");

        rec=findView(R.id.rec);
        LinearLayoutManager linManager1=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rec.setLayoutManager(linManager1);
        rec.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(this,7),true));
        rec.setHasFixedSize(true);
        wangzhuanAdapter =new WangzhuanAdapter(this,mList);
        rec.setAdapter(wangzhuanAdapter);

        ptrFrameLayout= (PtrClassicFrameLayout) findViewById(R.id.prtframelayout);
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page+=1;
                        moredate();
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
    protected void initData() {
        redate();
    }
    private void redate() {
        page=0;
        mList.clear();
        RequestParams params = new RequestParams(myurl);
        params.addBodyParameter("str","0");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("新闻",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("money");
                    for (int i=0;i<jrry.length();i++)
                    {
                        JSONObject ob=jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("background",AppBaseUrl.isHttp(ob.getString("background")));
                        map.put("id",ob.getString("id"));
                        map.put("title",ob.getString("title"));
                        map.put("datetime",ob.getString("datetime"));
                        map.put("abstracts",ob.getString("abstracts"));
                        map.put("likenum",ob.getString("likenum"));
                        map.put("clicknum",ob.getString("clicknum"));
                        mList.add(map);
                    }
                    wangzhuanAdapter.setmList(mList);
                    wangzhuanAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.i("错误信息",ex.toString());
                ToastUtil.show(WangzhuanActivity.this,"网络发生错误!");
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

        RequestParams params = new RequestParams(myurl);
        params.addBodyParameter("str",page+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("更多新闻",result);
                try {
                    ArrayList<Map<String,String>> mNewslist=new ArrayList<>();
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("money");
                    for (int i=0;i<jrry.length();i++)
                    {
                        JSONObject ob=jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("background",AppBaseUrl.isHttp(ob.getString("background")));
                        map.put("id",ob.getString("id"));
                        map.put("title",ob.getString("title"));
                        map.put("datetime",ob.getString("datetime"));
                        map.put("abstracts",ob.getString("abstracts"));
                        map.put("likenum",ob.getString("likenum"));
                        map.put("clicknum",ob.getString("clicknum"));
                        mNewslist.add(map);
                    }
                    if (mNewslist.size()==0)
                    {
                        ptrFrameLayout.refreshComplete();
                        ToastUtil.show(WangzhuanActivity.this,"没有更多数据了");
                    }else
                    {
                        mList.addAll(mNewslist);
                        ptrFrameLayout.refreshComplete();
                        wangzhuanAdapter.notifyItemRangeInserted(mList.size()-mNewslist.size(),mNewslist.size());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.i("错误信息",ex.toString());
                ToastUtil.show(WangzhuanActivity.this,"网络发生错误!");
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
