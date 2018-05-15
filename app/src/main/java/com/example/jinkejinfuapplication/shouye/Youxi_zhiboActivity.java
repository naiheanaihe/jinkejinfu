package com.example.jinkejinfuapplication.shouye;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.utils.DeviceUtil;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.ToastUtil;
import com.example.jinkejinfuapplication.view.Mybanner;
import com.jude.rollviewpager.hintview.ColorPointHintView;

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

public class Youxi_zhiboActivity extends BaseActivity {
    private Youxi_zhiboAdapter zhiboAdapter;
    private RecyclerView rec;

    int page=0;
    private List<Map<String, String>> mList=new ArrayList<>();
    private PtrClassicFrameLayout ptrFrameLayout;
    public String type;
    private String myurl= AppBaseUrl.BASEURL+"app/gaming/live";
    private Mybanner mRollViewPager;
    private TestNormalAdapter testNormalAdapter;
    private List<Map<String,String>> LEADING_IMAGE_RESOURCES=new ArrayList<>();
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_youxi_zhibo);
        setTitle("游戏直播");

        mRollViewPager= (Mybanner) findViewById(R.id.inforpager);
        testNormalAdapter=new TestNormalAdapter(LEADING_IMAGE_RESOURCES,this);
        mRollViewPager.setAdapter(testNormalAdapter);
        int height= DeviceUtil.deviceWidth(this)*420/1080;
        ViewGroup.LayoutParams params=mRollViewPager.getLayoutParams();
        params.height=height;
        mRollViewPager.setLayoutParams(params);

        rec=findView(R.id.rec);
        GridLayoutManager linManager1=new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL,false);
        rec.setLayoutManager(linManager1);
        //rec.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(this,7),true));
        rec.setHasFixedSize(true);
        zhiboAdapter =new Youxi_zhiboAdapter(this,mList);
        rec.setAdapter(zhiboAdapter);

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
        ptrFrameLayout.disableWhenHorizontalMove(true);
    }

    @Override
    protected void initData() {
        String url3= AppBaseUrl.BANNER_SHOUYE;
        RequestParams params3 = new RequestParams(url3);
        params3.addBodyParameter("sort","10");
        x.http().get(params3, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("广告",result);
                LEADING_IMAGE_RESOURCES.clear();
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("banner");
                    for (int i=0;i<jrry.length();i++)
                    {
                        JSONObject temp=jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("id",temp.getString("ID"));
                        map.put("url", AppBaseUrl.BASEURL+temp.getString("img"));
                        map.put("sort",temp.getString("sort"));
                        map.put("type",temp.getString("type"));
                        map.put("links",temp.getString("links"));
                        LEADING_IMAGE_RESOURCES.add(map);
                    }
                    testNormalAdapter.setPhotoPaths(LEADING_IMAGE_RESOURCES);
                    testNormalAdapter.notifyDataSetChanged();
                    mRollViewPager.setHintView(new ColorPointHintView(Youxi_zhiboActivity.this, Color.YELLOW,Color.WHITE));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.show(Youxi_zhiboActivity.this,"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });

        redate();
    }
    public void redate() {
        page=0;
        mList.clear();
        RequestParams params = new RequestParams(myurl);
        params.addBodyParameter("str","0");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("直播",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("live");
                    for (int i=0;i<jrry.length();i++)
                    {
                        JSONObject ob=jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("img",AppBaseUrl.isHttp(ob.getString("img")));
                        map.put("anchor_name",ob.getString("anchor_name"));
                        map.put("hot_num",ob.getString("hot_num"));
                        map.put("city",ob.getString("city"));
                        map.put("links",ob.getString("links"));
                        map.put("id",ob.getString("id"));
                        map.put("label",ob.getString("label"));
                        mList.add(map);
                    }
                    zhiboAdapter.setmList(mList);
                    zhiboAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.i("错误信息",ex.toString());
                ToastUtil.show(Youxi_zhiboActivity.this,"网络发生错误!");
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
                LalaLog.i("更多直播",result);
                try {
                    ArrayList<Map<String,String>> mNewslist=new ArrayList<>();
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("live");
                    for (int i=0;i<jrry.length();i++)
                    {
                        JSONObject ob=jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("img",AppBaseUrl.isHttp(ob.getString("img")));
                        map.put("anchor_name",ob.getString("anchor_name"));
                        map.put("hot_num",ob.getString("hot_num"));
                        map.put("city",ob.getString("city"));
                        map.put("links",ob.getString("links"));
                        map.put("id",ob.getString("id"));
                        map.put("label",ob.getString("label"));
                        mNewslist.add(map);
                    }
                    if (mNewslist.size()==0)
                    {
                        ptrFrameLayout.refreshComplete();
                        ToastUtil.show(Youxi_zhiboActivity.this,"没有更多数据了");
                    }else
                    {
                        mList.addAll(mNewslist);
                        ptrFrameLayout.refreshComplete();
                        zhiboAdapter.notifyItemRangeInserted(mList.size()-mNewslist.size(),mNewslist.size());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.i("错误信息",ex.toString());
                ToastUtil.show(Youxi_zhiboActivity.this,"网络发生错误!");
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
