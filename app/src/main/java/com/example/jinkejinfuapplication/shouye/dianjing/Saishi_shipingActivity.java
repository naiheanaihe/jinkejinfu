package com.example.jinkejinfuapplication.shouye.dianjing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.bean.TextBean;
import com.example.jinkejinfuapplication.shouye.TestNormalAdapter;
import com.example.jinkejinfuapplication.utils.DeviceUtil;
import com.example.jinkejinfuapplication.utils.DisplayUtil;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.SpaceItemDecoration;
import com.example.jinkejinfuapplication.utils.ToastUtil;
import com.example.jinkejinfuapplication.view.Mybanner;

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

public class Saishi_shipingActivity extends BaseActivity {
    private  Sel_youxiAdapter sel_youxiAdapter;
    private RecyclerView rec,rec_youxi;
    private Saishi_shipingAdapter shipingAdapter;

    int page=0;
    private List<TextBean> mtextList=new ArrayList<>();
    private List<Map<String, String>> mList=new ArrayList<>();
    private PtrClassicFrameLayout ptrFrameLayout;
    public String type;
    private String myurl=AppBaseUrl.BASEURL+"app/gaming/video";


    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_saishi_shiping);
        setTitle("赛事视频");



        rec=findView(R.id.rec);
        LinearLayoutManager linManager1=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rec.setLayoutManager(linManager1);
        rec.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(this,7),true));
        rec.setHasFixedSize(true);
        shipingAdapter =new Saishi_shipingAdapter(this,mList);
        rec.setAdapter(shipingAdapter);

        rec_youxi=findView(R.id.rec_youxi);
        LinearLayoutManager linManager2=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rec_youxi.setLayoutManager(linManager2);
        rec_youxi.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(this,10),false));
        rec_youxi.setHasFixedSize(true);
        sel_youxiAdapter =new Sel_youxiAdapter(mtextList,this,rec_youxi);
        rec_youxi.setAdapter(sel_youxiAdapter);

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


        RequestParams params = new RequestParams(AppBaseUrl.BASEURL+"app/gaming/video/sort");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("赛事游戏",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("sort");
                    for (int i=0;i<jrry.length();i++)
                    {
                        JSONObject ob=jrry.getJSONObject(i);
                        mtextList.add(new TextBean(ob.getString("sort")));
                    }
                    sel_youxiAdapter.setmName(mtextList);
                    sel_youxiAdapter.notifyDataSetChanged();
                    type=mtextList.get(0).getName();
                    redate();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.i("错误信息",ex.toString());
                ToastUtil.show(Saishi_shipingActivity.this,"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }
    public void redate() {
        page=0;
        mList.clear();
        RequestParams params = new RequestParams(myurl);
        params.addBodyParameter("str","0");
        params.addBodyParameter("sort",type);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("视频",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("video");
                    for (int i=0;i<jrry.length();i++)
                    {
                        JSONObject ob=jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("background",AppBaseUrl.isHttp(ob.getString("background")));
                        map.put("id",ob.getString("id"));
                        map.put("title",ob.getString("title"));
                        map.put("datetime",ob.getString("datetime"));
                        map.put("duration",ob.getString("duration"));
                        map.put("type",ob.getString("type"));
                        map.put("sort",ob.getString("sort"));
                        mList.add(map);
                    }
                    shipingAdapter.setmList(mList);
                    shipingAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.i("错误信息",ex.toString());
                ToastUtil.show(Saishi_shipingActivity.this,"网络发生错误!");
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
        params.addBodyParameter("sort",type);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("更多视频",result);
                try {
                    ArrayList<Map<String,String>> mNewslist=new ArrayList<>();
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("video");
                    for (int i=0;i<jrry.length();i++)
                    {
                        JSONObject ob=jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("background",AppBaseUrl.isHttp(ob.getString("background")));
                        map.put("id",ob.getString("id"));
                        map.put("title",ob.getString("title"));
                        map.put("datetime",ob.getString("datetime"));
                        map.put("duration",ob.getString("duration"));
                        map.put("type",ob.getString("type"));
                        map.put("sort",ob.getString("sort"));
                        mNewslist.add(map);
                    }
                    if (mNewslist.size()==0)
                    {
                        ptrFrameLayout.refreshComplete();
                        ToastUtil.show(Saishi_shipingActivity.this,"没有更多数据了");
                    }else
                    {
                        mList.addAll(mNewslist);
                        ptrFrameLayout.refreshComplete();
                        shipingAdapter.notifyItemRangeInserted(mList.size()-mNewslist.size(),mNewslist.size());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.i("错误信息",ex.toString());
                ToastUtil.show(Saishi_shipingActivity.this,"网络发生错误!");
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
