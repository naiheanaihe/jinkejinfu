package com.example.jinkejinfuapplication.shouye;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.taojinke.TuijianAdapter;
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

import cn.appsdream.nestrefresh.base.AbsRefreshLayout;
import cn.appsdream.nestrefresh.base.OnPullListener;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;

public class More_zixunActivity extends BaseActivity implements OnPullListener {
    private RecyclerView rec_tuijian;
    private Shouye_zixunAdapter shouye_zixunAdapter;
    private List<Map<String,String>> mList=new ArrayList<>();
    private NestRefreshLayout nestRefreshLayout;
    private int page=0;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_more_zixun);
        setTitle("更多资讯");
        rec_tuijian= (RecyclerView) findViewById(R.id.rec_zixun);
        LinearLayoutManager linManager1=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        rec_tuijian.setLayoutManager(linManager1);
        rec_tuijian.setHasFixedSize(true);
        shouye_zixunAdapter =new Shouye_zixunAdapter(this,mList);
        rec_tuijian.setAdapter(shouye_zixunAdapter);
        nestRefreshLayout= (NestRefreshLayout) findViewById(R.id.nestlayou);
        nestRefreshLayout.setOnLoadingListener(this);
    }

    @Override
    protected void initData() {
        redate();
    }
    private void redate() {
        page=0;
        mList.clear();
        String url= AppBaseUrl.MORE_ZIXUN;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("str","0");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("更多资讯",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("news");
                    for (int i=0;i<jrry.length();i++)
                    {
                        JSONObject ob=jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("gameId",ob.getString("gameId"));
                        map.put("background",AppBaseUrl.BASEURL+ob.getString("background"));
                        map.put("id",ob.getString("id"));
                        map.put("title",ob.getString("title"));
                        map.put("datetime",ob.getString("datetime"));
                        map.put("name",ob.getString("name"));
                        map.put("likenum",ob.getString("likenum"));
                        map.put("clicknum",ob.getString("clicknum"));
                        mList.add(map);
                    }
                    shouye_zixunAdapter.setmList(mList);
                    shouye_zixunAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.i("错误信息",ex.toString());
                ToastUtil.show(More_zixunActivity.this,"网络发生错误!");
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
        String url= AppBaseUrl.MORE_ZIXUN;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("str",page+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("更多资讯",result);
                try {
                    ArrayList<Map<String,String>> mNewslist=new ArrayList<>();
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("news");
                    for (int i=0;i<jrry.length();i++)
                    {
                        JSONObject ob=jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("gameId",ob.getString("gameId"));
                        map.put("background",AppBaseUrl.BASEURL+ob.getString("background"));
                        map.put("id",ob.getString("id"));
                        map.put("title",ob.getString("title"));
                        map.put("datetime",ob.getString("datetime"));
                        map.put("name",ob.getString("name"));
                        map.put("likenum",ob.getString("likenum"));
                        map.put("clicknum",ob.getString("clicknum"));
                        mNewslist.add(map);
                    }
                    if (mNewslist.size()==0)
                    {
                        nestRefreshLayout.onLoadFinished();
                        ToastUtil.show(More_zixunActivity.this,"没有更多数据了");
                    }else
                    {
                        mList.addAll(mNewslist);
                        nestRefreshLayout.onLoadFinished();
                        shouye_zixunAdapter.notifyItemRangeInserted(page*12,mNewslist.size());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.i("错误信息",ex.toString());
                ToastUtil.show(More_zixunActivity.this,"网络发生错误!");
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

    @Override
    public void onRefresh(AbsRefreshLayout listLoader) {
        nestRefreshLayout.onLoadFinished();
        page=0;
        redate();
    }

    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
        page+=1;
        moredate();
    }
}
