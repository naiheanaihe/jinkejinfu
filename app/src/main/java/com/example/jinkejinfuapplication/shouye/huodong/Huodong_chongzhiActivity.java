package com.example.jinkejinfuapplication.shouye.huodong;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
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

public class Huodong_chongzhiActivity extends BaseActivity {
    private TextView feiyong;
    private RecyclerView rec;
    private Huodong_chongzhiAdapter chongzhiAdapter;

    int page=0;
    private List<Map<String, String>> mList=new ArrayList<>();
    private PtrClassicFrameLayout ptrFrameLayout;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_huodong_chongzhi);
        setTitle("充值详情");
        feiyong=findView(R.id.feiyong);
        rec=findView(R.id.rec);
        LinearLayoutManager linManager1=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rec.setLayoutManager(linManager1);
        rec.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(this,1),true));
        rec.setHasFixedSize(true);
        chongzhiAdapter =new Huodong_chongzhiAdapter(this,mList);
        rec.setAdapter(chongzhiAdapter);

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
    private void moredate() {
        String url= AppBaseUrl.BASEURL+"app/activity/game/cost";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        params.addBodyParameter("str", page+"");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("推荐",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    jObj=jObj.getJSONObject("game");
                    List<Map<String,String>> mNewslist=new ArrayList<>();
                    JSONArray jrry=jObj.getJSONArray("game_list");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject ob = jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("datetime",ob.getString("datetime"));
                        map.put("game_name",ob.getString("game_name"));
                        map.put("amount",ob.getString("amount"));
                        if (ob.getString("game_pic").substring(0,4).equals("game_pic")){
                            map.put("game_pic",ob.getString("game_pic"));
                        }else
                        {
                            map.put("game_pic",AppBaseUrl.BASEURL+ob.getString("game_pic"));
                        }

                        mNewslist.add(map);
                    }
                    if (mNewslist.size()==0)
                    {
                        ptrFrameLayout.refreshComplete();
                        ToastUtil.show(Huodong_chongzhiActivity.this,"没有更多数据了");
                    }else
                    {
                        mList.addAll(mNewslist);
                        ptrFrameLayout.refreshComplete();
                        chongzhiAdapter.notifyItemRangeInserted(page*10,mNewslist.size());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(Huodong_chongzhiActivity.this,"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }
    private void redate() {
        page=0;
        String url= AppBaseUrl.BASEURL+"app/activity/game/cost";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        params.addBodyParameter("str", page+"");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("推荐",result);
                mList.clear();
                try {
                    JSONObject jObj = new JSONObject(result);
                    jObj=jObj.getJSONObject("game");
                    feiyong.setText(jObj.getString("all_cost")+"元");
                    JSONArray jrry=jObj.getJSONArray("game_list");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject ob = jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("datetime",ob.getString("datetime"));
                        map.put("game_name",ob.getString("game_name"));
                        map.put("amount",ob.getString("amount"));
                        if (ob.getString("game_pic").substring(0,4).equals("game_pic")){
                            map.put("game_pic",ob.getString("game_pic"));
                        }else
                        {
                            map.put("game_pic",AppBaseUrl.BASEURL+ob.getString("game_pic"));
                        }
                        mList.add(map);
                    }
                    chongzhiAdapter.setmList(mList);
                    chongzhiAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(Huodong_chongzhiActivity.this,"网络发生错误!");
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
