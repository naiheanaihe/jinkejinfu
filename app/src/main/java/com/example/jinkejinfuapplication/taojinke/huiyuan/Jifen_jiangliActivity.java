package com.example.jinkejinfuapplication.taojinke.huiyuan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.jinkejinfuapplication.MyApplication;
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

public class Jifen_jiangliActivity extends BaseActivity implements OnPullListener {
    private RadioGroup radioGroup;
    private RecyclerView rec_jifen;
    private LinearLayout lin_jifen;
    private TextView jifen_all;
    private JifenAdapter jifenAdapter;
    private String sort="";
    private List<Map<String,String>> mList=new ArrayList<>();
    private NestRefreshLayout nestRefreshLayout;
    private int page=0;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_jifen_jiangli);
        setTitle("积分奖励");
        lin_jifen=findView(R.id.lin_jifen);
        jifen_all=findView(R.id.jifen_all);
        radioGroup=findView(R.id.group_jifen);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.btn_type1:
                        sort="";
                        lin_jifen.setVisibility(View.VISIBLE);
                        redate();
                        break;
                    case R.id.btn_type2:
                        sort="0";
                        lin_jifen.setVisibility(View.GONE);
                        redate();
                        break;
                    case R.id.btn_type3:
                        sort="1";
                        lin_jifen.setVisibility(View.GONE);
                        redate();
                        break;
                    case R.id.btn_type4:
                        sort="2";
                        lin_jifen.setVisibility(View.GONE);
                        redate();
                        break;
                    case R.id.btn_type5:
                        sort="3";
                        lin_jifen.setVisibility(View.GONE);
                        redate();
                        break;
                }
            }
        });

        rec_jifen= (RecyclerView) findViewById(R.id.rec_jifen);
        LinearLayoutManager linManager1=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        rec_jifen.setLayoutManager(linManager1);
        rec_jifen.setHasFixedSize(true);
        jifenAdapter=new JifenAdapter(this,mList);
        rec_jifen.setAdapter(jifenAdapter);
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
        String url= AppBaseUrl.JIFEN_JIANGLI;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("str",page+"");
        params.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        params.addBodyParameter("sort",sort);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("积分",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    jObj=jObj.getJSONObject("integral");
                    jifen_all.setText(jObj.getString("integral")+"分");
                    JSONArray jrry=jObj.getJSONArray("integral_list");
                    for (int i=0;i<jrry.length();i++)
                    {
                        JSONObject temp=jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("datetime",temp.getString("datetime"));
                        map.put("describe",temp.getString("describe"));
                        map.put("sort",temp.getString("sort"));
                        mList.add(map);
                    }
                    jifenAdapter.setmList(mList);
                    jifenAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.i("错误信息",ex.toString());
                ToastUtil.show(Jifen_jiangliActivity.this,"网络发生错误!");
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
        String url= AppBaseUrl.JIFEN_JIANGLI;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("str",page+"");
        params.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        params.addBodyParameter("sort",sort);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("更多积分",result);
                try {
                    ArrayList<Map<String,String>> mNewslist=new ArrayList<>();
                    JSONObject jObj = new JSONObject(result);
                    jObj=jObj.getJSONObject("integral");
                    JSONArray jrry=jObj.getJSONArray("integral_list");
                    for (int i=0;i<jrry.length();i++)
                    {
                        JSONObject temp=jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("datetime",temp.getString("datetime"));
                        map.put("describe",temp.getString("describe"));
                        map.put("sort",temp.getString("sort"));
                        mNewslist.add(map);
                    }
                    if (mNewslist.size()==0)
                    {
                        nestRefreshLayout.onLoadFinished();
                        ToastUtil.show(Jifen_jiangliActivity.this,"没有更多数据了");
                    }else
                    {
                        mList.addAll(mNewslist);
                        nestRefreshLayout.onLoadFinished();
                        jifenAdapter.notifyItemRangeInserted(page*20,mNewslist.size());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.i("错误信息",ex.toString());
                ToastUtil.show(Jifen_jiangliActivity.this,"网络发生错误!");
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
