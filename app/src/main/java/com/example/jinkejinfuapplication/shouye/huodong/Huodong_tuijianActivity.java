package com.example.jinkejinfuapplication.shouye.huodong;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
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

public class Huodong_tuijianActivity extends BaseActivity {
    private TextView feiyong;
    private ImageButton lingjiang;
    private RecyclerView rec;
    private Huodong_tuijianAdapter tuijianAdapter;
    int page=0;
    private List<Map<String, String>> mList=new ArrayList<>();
    private PtrClassicFrameLayout ptrFrameLayout;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_huodong_tuijian);
        setTitle("推荐详情");
        feiyong=findView(R.id.feiyong);
        lingjiang=findView(R.id.lingjiang);
        rec=findView(R.id.rec);
        LinearLayoutManager linManager1=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rec.setLayoutManager(linManager1);
        rec.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(this,1),true));
        rec.setHasFixedSize(true);
        tuijianAdapter=new Huodong_tuijianAdapter(this,mList);
        rec.setAdapter(tuijianAdapter);

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

    private void moredate() {
        String url= AppBaseUrl.BASEURL+"app/activity/member/income";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        params.addBodyParameter("str", page+"");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("推荐",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    jObj=jObj.getJSONObject("member");
                    List<Map<String,String>> mNewslist=new ArrayList<>();
                    JSONArray jrry=jObj.getJSONArray("member_list");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject ob = jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("datetime",ob.getString("datetime"));
                        map.put("name",ob.getString("name"));
                        map.put("money",ob.getString("money"));
                        LalaLog.d("头像",AppBaseUrl.BASEURL+ob.getString("photo"));
                        if (ob.getString("photo").substring(0,4).equals("http")){
                            map.put("photo",ob.getString("photo"));
                        }else
                        {
                            map.put("photo",AppBaseUrl.BASEURL+ob.getString("photo"));
                        }

                        map.put("rank",ob.getString("rank"));
                        mNewslist.add(map);
                    }
                    if (mNewslist.size()==0)
                    {
                        ptrFrameLayout.refreshComplete();
                        ToastUtil.show(Huodong_tuijianActivity.this,"没有更多数据了");
                    }else
                    {
                        mList.addAll(mNewslist);
                        ptrFrameLayout.refreshComplete();
                        tuijianAdapter.notifyItemRangeInserted(page*10,mNewslist.size());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(Huodong_tuijianActivity.this,"网络发生错误!");
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
        String url= AppBaseUrl.BASEURL+"app/activity/member/income";
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
                    jObj=jObj.getJSONObject("member");
                    feiyong.setText(jObj.getString("all_income")+"元");
                    JSONArray jrry=jObj.getJSONArray("member_list");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject ob = jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("datetime",ob.getString("datetime"));
                        map.put("name",ob.getString("name"));
                        map.put("money",ob.getString("money"));
                        if (ob.getString("photo").substring(0,4).equals("http")){
                            map.put("photo",ob.getString("photo"));
                        }else
                        {
                            map.put("photo",AppBaseUrl.BASEURL+ob.getString("photo"));
                        }

                        map.put("rank",ob.getString("rank"));
                        mList.add(map);
                    }
                    tuijianAdapter.setmList(mList);
                    tuijianAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(Huodong_tuijianActivity.this,"网络发生错误!");
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
        redate();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected boolean isShowToolBar() {
        return true;
    }


}
