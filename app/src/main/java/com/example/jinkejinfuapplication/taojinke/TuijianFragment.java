package com.example.jinkejinfuapplication.taojinke;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioGroup;

import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseFragment;
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

/**
 * Created by naihe on 2017/12/4.
 */

public class TuijianFragment extends BaseFragment implements OnPullListener {
    private RadioGroup radioGroup;
    private RecyclerView rec_tuijian;
    private TuijianAdapter tuijianAdapter;
    private List<Map<String,String>> mList=new ArrayList<>();
    private NestRefreshLayout nestRefreshLayout;
    private int page=0;
    private String sort="2";

    @Override
    public void initData(Bundle savedInstanceState) {
        redate();
    }

    @Override
    public void initView(View view) {
        radioGroup= (RadioGroup) view.findViewById(R.id.group_tuijian);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.btn_type1:
                        sort="0";
                        redate();
                        break;
                    case R.id.btn_type2:
                        sort="1";
                        redate();
                        break;
                    case R.id.btn_type3:
                        sort="2";
                        redate();
                        break;
                    case R.id.btn_type4:
                        sort="3";
                        redate();
                        break;
                }
            }
        });

        rec_tuijian= (RecyclerView) view.findViewById(R.id.rec_tuijian);
        GridLayoutManager linManager1=new GridLayoutManager(getContext(),2 ,LinearLayoutManager.VERTICAL,false);
        rec_tuijian.setLayoutManager(linManager1);
        rec_tuijian.setHasFixedSize(true);
        tuijianAdapter=new TuijianAdapter(getContext(),mList);
        rec_tuijian.setAdapter(tuijianAdapter);
        nestRefreshLayout= (NestRefreshLayout) view.findViewById(R.id.nestlayou);
        nestRefreshLayout.setOnLoadingListener(this);
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_tuijian;
    }
    private void redate() {
        page=0;
        mList.clear();
        String url= AppBaseUrl.TAOJINKE_FENXIANG;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("str","0");
        params.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        params.addBodyParameter("sort",sort);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("分享游戏",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("game");
                    for (int i=0;i<jrry.length();i++)
                    {
                        JSONObject temp=jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("id",temp.getString("id"));
                        map.put("game_name",temp.getString("game_name"));
                        map.put("background",AppBaseUrl.BASEURL+temp.getString("background"));
                        map.put("code",temp.getString("code"));
                        map.put("game_type",temp.getString("game_type"));
                        map.put("game_pic",AppBaseUrl.BASEURL+temp.getString("game_pic"));
                        map.put("type",temp.getString("type"));
                        map.put("discount",temp.getString("discount"));
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
                LalaLog.i("错误信息",ex.toString());
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
        String url= AppBaseUrl.TAOJINKE_FENXIANG;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("str",page+"");
        params.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        params.addBodyParameter("sort",sort);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("更多分享",result);
                try {
                    ArrayList<Map<String,String>> mNewslist=new ArrayList<>();
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("game");
                    for (int i=0;i<jrry.length();i++)
                    {
                        JSONObject temp=jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("id",temp.getString("id"));
                        map.put("game_name",temp.getString("game_name"));
                        map.put("background",AppBaseUrl.BASEURL+temp.getString("background"));
                        map.put("code",temp.getString("code"));
                        map.put("game_type",temp.getString("game_type"));
                        map.put("game_pic",AppBaseUrl.BASEURL+temp.getString("game_pic"));
                        map.put("type",temp.getString("type"));
                        map.put("discount",temp.getString("discount"));
                        mNewslist.add(map);
                    }
                    if (mNewslist.size()==0)
                    {
                        nestRefreshLayout.onLoadFinished();
                        ToastUtil.show(getContext(),"没有更多数据了");
                    }else
                    {
                        mList.addAll(mNewslist);
                        nestRefreshLayout.onLoadFinished();
                        tuijianAdapter.notifyItemRangeInserted(page*20,mNewslist.size());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.i("错误信息",ex.toString());
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
