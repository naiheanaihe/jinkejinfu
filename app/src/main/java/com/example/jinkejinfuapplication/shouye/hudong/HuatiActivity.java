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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jinkejinfuapplication.MainActivity;
import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.ToastUtil;
import com.example.jinkejinfuapplication.xiangq.ImgAdapter;
import com.example.jinkejinfuapplication.xiangq.PinglunAdapter;
import com.example.jinkejinfuapplication.xiangq.ShouyouActivity;

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

public class HuatiActivity extends BaseActivity implements View.OnClickListener {
    private ImageView myicon,img1,img2,fabu;
    private TextView mytitle,zuozhe,mytime,neirong,num_liuyin,num_chakan,num_pinglun,num_zhuanfa,num_liulan;
    private LinearLayout lin_img;
    private EditText ed_pinglun;
    private RecyclerView rec;
    private PinglunAdapter pinglunAdapter;
    private String mId;
    private List<Map<String, String>> mPinglunlist=new ArrayList<>();
    private PtrClassicFrameLayout ptrFrameLayout;
    private int page=0;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_huati);
        setTitle("话题详情");

        myicon=findView(R.id.myicon);
        img1=findView(R.id.img1);
        img2=findView(R.id.img2);
        fabu=findView(R.id.pinglun);
        fabu.setOnClickListener(this);
        mytitle=findView(R.id.mytitle);
        zuozhe=findView(R.id.zuozhe);
        mytime=findView(R.id.mytime);
        neirong=findView(R.id.neirong);
        num_liuyin=findView(R.id.num_liuyin);
        num_chakan=findView(R.id.num_chakan);
        num_pinglun=findView(R.id.num_pinglun);

        lin_img=findView(R.id.lin_img);
        ed_pinglun=findView(R.id.ed_pinglun);

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
        String url= AppBaseUrl.HUDONG_XIANGQ;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("id",mId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("详情",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONObject temp = jObj.getJSONObject("topic");
                    x.image().bind(myicon,AppBaseUrl.isHttp(temp.getString("pic")), ImageOptions.getimageOptions());
                    if (temp.getString("img1").isEmpty()&&temp.getString("img2").isEmpty()){
                        lin_img.setVisibility(View.GONE);
                    }
                    LalaLog.d("图片",temp.getString("img1"));
                    x.image().bind(img1,temp.getString("img1"), ImageOptions.getimageOptions_touming());
                    x.image().bind(img2,temp.getString("img2"), ImageOptions.getimageOptions_touming());
                    mytitle.setText(temp.getString("title"));
                    zuozhe.setText(temp.getString("name"));
                    mytime.setText(temp.getString("datetime"));
                    neirong.setText(temp.getString("content"));
                    zuozhe.setText(temp.getString("name"));
                    num_liuyin.setText(temp.getString("commentnum"));
                    num_pinglun.setText(temp.getString("commentnum"));
                    num_chakan.setText(temp.getString("clicknum"));



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(HuatiActivity.this,"网络发生错误!");
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
        String url2= AppBaseUrl.HUDONG_HUOQUPINGLUN;
        RequestParams params2 = new RequestParams(url2);
        params2.addBodyParameter("id",mId);
        params2.addBodyParameter("sort","topic");
        params2.addBodyParameter("str","0");
        x.http().post(params2, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("评论",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    jObj=jObj.getJSONObject("comment_list");
                    JSONArray jrry=jObj.getJSONArray("comment");
                    for (int i=0;i<jrry.length();i++) {
                        Map<String,String> map=new HashMap<String, String>();
                        JSONObject temp = jrry.getJSONObject(i);
                        map.put("datetime",temp.getString("datetime"));
                        map.put("like",temp.getString("like"));
                        map.put("pic",AppBaseUrl.isHttp(temp.getString("pic")));
                        map.put("name",temp.getString("name"));
                        map.put("replynum",temp.getString("replynum"));
                        map.put("commentId",temp.getString("commentId"));
                        map.put("sort",temp.getString("sort"));
                        map.put("content",temp.getString("content"));
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
                ToastUtil.show(HuatiActivity.this,"网络发生错误!");
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
        String url= AppBaseUrl.HUDONG_HUOQUPINGLUN;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("id",mId);
        params.addBodyParameter("sort","topic");
        params.addBodyParameter("str",page+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("更多评论",result);
                try {
                    ArrayList<Map<String,String>> mNewslist=new ArrayList<>();
                    JSONObject jObj = new JSONObject(result);
                    jObj=jObj.getJSONObject("comment_list");
                    JSONArray jrry=jObj.getJSONArray("comment");
                    for (int i=0;i<jrry.length();i++)
                    {
                        JSONObject temp=jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("datetime",temp.getString("datetime"));
                        map.put("like",temp.getString("like"));
                        map.put("pic",AppBaseUrl.BASEURL+temp.getString("pic"));
                        map.put("name",temp.getString("name"));
                        map.put("replynum",temp.getString("replynum"));
                        map.put("commentId",temp.getString("commentId"));
                        map.put("sort",temp.getString("sort"));
                        map.put("content",temp.getString("content"));
                        mNewslist.add(map);
                    }
                    if (mNewslist.size()==0)
                    {
                        ptrFrameLayout.refreshComplete();
                        ToastUtil.show(HuatiActivity.this,"没有更多数据了");
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
                ToastUtil.show(HuatiActivity.this,"网络发生错误!");
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
                String url2= AppBaseUrl.HUDONG_PINGLUN;
                RequestParams params1 = new RequestParams(url2);
                params1.addBodyParameter("id",mId);
                params1.addBodyParameter("sort","topic");
                params1.addBodyParameter("content",ed_pinglun.getText().toString());
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
                                JSONObject temp = jObj.getJSONObject("comment");
                                Map<String,String> map=new HashMap<String, String>();
                                map.put("datetime",temp.getString("datetime"));
                                map.put("like",temp.getString("like"));
                                map.put("pic",AppBaseUrl.BASEURL+temp.getString("photo"));
                                map.put("name",temp.getString("name"));
                                map.put("replynum",temp.getString("replynum"));
                                map.put("commentId",temp.getString("commentId"));
                                map.put("sort",temp.getString("sort"));
                                map.put("content",temp.getString("content"));
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
                        ToastUtil.show(HuatiActivity.this,"网络发生错误!");
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
