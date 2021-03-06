package com.example.jinkejinfuapplication.shouye.hudong;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinkejinfuapplication.MainActivity;
import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.ToastUtil;
import com.example.jinkejinfuapplication.xiangq.PinglunAdapter;

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

public class LiuyinActivity extends BaseActivity implements View.OnClickListener {
    private TextView mytitle,neirong,chakan_number,pinglun_number,mytime;
    private EditText ed_pinglun;
    private ImageView fabu,myicon;
    private RecyclerView rec;
    private PinglunAdapter pinglunAdapter;
    private String mId;
    private List<Map<String, String>> mPinglunlist=new ArrayList<>();
    private PtrClassicFrameLayout ptrFrameLayout;
    private int page=0;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_liuyin);
        setTitle("留言详情");

        fabu=findView(R.id.pinglun);
        fabu.setOnClickListener(this);
        mytitle=findView(R.id.myname);
        neirong=findView(R.id.neirong);
        chakan_number=findView(R.id.chakan_number);
        pinglun_number=findView(R.id.pinglun_number);
        ed_pinglun=findView(R.id.ed_pinglun);
        myicon=findView(R.id.myicon);
        mytime=findView(R.id.mytime);

        rec=findView(R.id.rec);
        LinearLayoutManager layoutManager5 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rec.setLayoutManager(layoutManager5);
        rec.setHasFixedSize(true);
        pinglunAdapter=new PinglunAdapter(this,mPinglunlist);
        rec.setAdapter(pinglunAdapter);
        rec.setNestedScrollingEnabled(false);

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
                },100);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page=0;
                        redate();
                        ptrFrameLayout.refreshComplete();
                    }
                },1000);
            }
        });
    }

    @Override
    protected void initData() {
        mId=getIntent().getStringExtra("id");
        String url= AppBaseUrl.LIUYIN_XIANGQ;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("id",mId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("详情",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONObject temp = jObj.getJSONObject("comment");
                    if (temp.getString("photo").substring(0,4).equals("http")){
                        x.image().bind(myicon,temp.getString("photo"), ImageOptions.getimageOptions());
                    }else
                    {
                        x.image().bind(myicon,AppBaseUrl.BASEURL+temp.getString("photo"), ImageOptions.getimageOptions());
                    }
                    mytitle.setText(temp.getString("name")+":");
                    mytime.setText(temp.getString("datetime"));
                    neirong.setText(temp.getString("message"));
                    pinglun_number.setText(temp.getString("commentnum"));
                    chakan_number.setText(temp.getString("clicknum"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(LiuyinActivity.this,"网络发生错误!");
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
    private void redate(){
        page=0;
        mPinglunlist.clear();
        String url2= AppBaseUrl.BASEURL+"app/message/reply";
        RequestParams params2 = new RequestParams(url2);
        params2.addBodyParameter("id",mId);
        /*params2.addBodyParameter("sort","message");*/
        params2.addBodyParameter("str","0");
        x.http().post(params2, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("评论",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("reply");
                    for (int i=0;i<jrry.length();i++) {
                        Map<String,String> map=new HashMap<String, String>();
                        JSONObject temp = jrry.getJSONObject(i);
                        map.put("datetime",temp.getString("datetime"));
                        map.put("like",temp.getString("like"));
                        if (temp.getString("reply_photo").substring(0,4).equals("http")){
                            map.put("pic",temp.getString("reply_photo"));
                        }else
                        {
                            map.put("pic",AppBaseUrl.BASEURL+temp.getString("reply_photo"));
                        }
                        map.put("name",temp.getString("replyname"));
                        map.put("commentId",temp.getString("replyId"));
                        map.put("content",temp.getString("reply"));
                        mPinglunlist.add(map);
                    }
                    pinglunAdapter.setmList(mPinglunlist);
                    pinglunAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(LiuyinActivity.this,"网络发生错误!");
            }
            @Override
            public void onCancelled(Callback.CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }
    private void moredate(){
        String url= AppBaseUrl.BASEURL+"app/message/reply";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("id",mId);
      /*  params.addBodyParameter("sort","message");*/
        params.addBodyParameter("str",page+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("更多评论",result);
                try {
                    ArrayList<Map<String,String>> mNewslist=new ArrayList<>();
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("reply");
                    for (int i=0;i<jrry.length();i++)
                    {
                        JSONObject temp=jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("datetime",temp.getString("datetime"));
                        map.put("like",temp.getString("like"));
                        if (temp.getString("reply_photo").substring(0,4).equals("http")){
                            map.put("pic",temp.getString("reply_photo"));
                        }else
                        {
                            map.put("pic",AppBaseUrl.BASEURL+temp.getString("reply_photo"));
                        }
                        map.put("name",temp.getString("replyname"));
                        map.put("commentId",temp.getString("replyId"));
                        map.put("content",temp.getString("reply"));
                        mNewslist.add(map);
                    }
                    if (mNewslist.size()==0)
                    {
                        ptrFrameLayout.refreshComplete();
                        ToastUtil.show(LiuyinActivity.this,"没有更多数据了");
                    }else
                    {
                        mPinglunlist.addAll(mNewslist);
                        ptrFrameLayout.refreshComplete();
                        pinglunAdapter.notifyItemRangeInserted(mPinglunlist.size()-mNewslist.size(),mNewslist.size());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.i("错误信息",ex.toString());
                ToastUtil.show(LiuyinActivity.this,"网络发生错误!");
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pinglun:
                if (ed_pinglun.getText().toString().isEmpty()){
                    showToastMsg("评论内容不能为空！");
                    return;
                }
                String url2= AppBaseUrl.BASEURL+"app/reply";
                RequestParams params1 = new RequestParams(url2);
                params1.addBodyParameter("id",mId);
                /*params1.addBodyParameter("sort","message");*/
                params1.addBodyParameter("reply",ed_pinglun.getText().toString());
                params1.addBodyParameter("userId", MainActivity.LOGINSTATE==MainActivity.LOGINSTATE_LOGIN? MyApplication.getInstance().getYonghuBean().getUserId():"");
                x.http().post(params1, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        LalaLog.i("评论",result);
                        ed_pinglun.setText("");
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        showToastMsg("评论成功！");
                        if (mPinglunlist.size()<10){
                            try {
                                JSONObject jObj = new JSONObject(result);
                                JSONObject temp = jObj.getJSONObject("reply");
                                Map<String,String> map=new HashMap<String, String>();
                                map.put("datetime",temp.getString("replytime"));
                                map.put("like",temp.getString("like"));
                                if (temp.getString("pic").substring(0,4).equals("http")){
                                    map.put("pic",temp.getString("pic"));
                                }else
                                {
                                    map.put("pic",AppBaseUrl.BASEURL+temp.getString("pic"));
                                }
                                map.put("name",temp.getString("replyname"));
                                map.put("commentId",temp.getString("commentId"));
                                map.put("content",temp.getString("reply"));
                                mPinglunlist.add(0,map);
                                pinglunAdapter.notifyItemRangeInserted(0,1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        LalaLog.d("错误详情",ex.toString());
                        ToastUtil.show(LiuyinActivity.this,"网络发生错误!");
                    }
                    @Override
                    public void onCancelled(CancelledException cex) {
                    }
                    @Override
                    public void onFinished() {
                    }
                });
                break;
        }
    }
}
