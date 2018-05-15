package com.example.jinkejinfuapplication.xiangq;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jinkejinfuapplication.MainActivity;
import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.shouye.Shouye_gameAdapter;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.MyLinearManager;
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

public class YeyouActivity extends BaseActivity implements View.OnClickListener {
    private String mId;
    private ImageView back,game_icon,more;
    private LinearLayout rel_zhankai,rel_zhankai1;
    private TextView zhankai_text,zhankai_text2;
    private ImageView zhankai_arrow,zhankai_arrow2;
    private TextView game_name,game_biaoq,jieshao,zhinan;
    private boolean flag=true,flag1=true;
    private RecyclerView rec_img,rec_zixun,rec_xiangsi,rec_pinglun;
    private Map<String,String> map_xiangq=new HashMap<>();
    private List<Map<String,String>> mNewslist=new ArrayList<>();
    private List<Map<String,String>> mXiangsilist=new ArrayList<>();
    private ImgAdapter imgAdapter;
    private ZixunAdapter zixunAdapter;
    private Shouye_gameAdapter gameAdapter;

    private PinglunAdapter pinglunAdapter;
    private List<Map<String, String>> mPinglunlist=new ArrayList<>();
    private ImageView pinglun;
    private LinearLayout lin_pinglun_kong;
    private EditText ed_pinglun;
    private PtrClassicFrameLayout ptrFrameLayout;
    private int page=0;
    private TextView num_dianzan;
    private ImageView xin_dianzan;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_yeyou);
        back=findView(R.id.back);
        back.setOnClickListener(this);
        more=findView(R.id.more);
        more.setOnClickListener(this);
        jieshao =findView(R.id.jieshao);
        zhinan=findView(R.id.zhinan);
        rel_zhankai=findView(R.id.zhankai);
        rel_zhankai.setOnClickListener(this);
        rel_zhankai1=findView(R.id.zhankai2);
        rel_zhankai1.setOnClickListener(this);
        zhankai_text=findView(R.id.zhankai_text);
        zhankai_arrow=findView(R.id.zhankai_arrow);
        zhankai_text2=findView(R.id.zhankai_text2);
        zhankai_arrow2=findView(R.id.zhankai_arrow2);
        game_icon=findView(R.id.img_icon);
        game_name=findView(R.id.game_name);
        game_biaoq=findView(R.id.game_biaoq);
        pinglun=findView(R.id.pinglun);
        pinglun.setOnClickListener(this);
        lin_pinglun_kong=findView(R.id.lin_pinglun_kong);
        ed_pinglun=findView(R.id.ed_pinglun);
        num_dianzan=findView(R.id.num_dianzan);
        xin_dianzan=findView(R.id.xin_dianzan);
        xin_dianzan.setOnClickListener(this);

        rec_img=findView(R.id.rec_img);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rec_img.setLayoutManager(layoutManager);
        rec_img.addItemDecoration(new SpaceItemDecoration(20,false));
        rec_img.setHasFixedSize(true);

        rec_zixun=findView(R.id.rec_zixun);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rec_zixun.setLayoutManager(layoutManager1);
        zixunAdapter=new ZixunAdapter(this,mNewslist);
        rec_zixun.setAdapter(zixunAdapter);

        rec_xiangsi=findView(R.id.rec_xiangsi);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rec_xiangsi.setLayoutManager(layoutManager4);
        rec_xiangsi.addItemDecoration(new SpaceItemDecoration(20,false));

        rec_pinglun=findView(R.id.rec_pinglun);
        MyLinearManager layoutManager5 = new MyLinearManager(this, LinearLayoutManager.VERTICAL, false);
        rec_pinglun.setLayoutManager(layoutManager5);
        rec_pinglun.setHasFixedSize(true);
        pinglunAdapter=new PinglunAdapter(this,mPinglunlist);
        rec_pinglun.setAdapter(pinglunAdapter);
        rec_pinglun.setNestedScrollingEnabled(false);

        ptrFrameLayout= (PtrClassicFrameLayout) findViewById(R.id.prtframelayout);
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page+=1;
                        moredate();
                        ptrFrameLayout.refreshComplete();
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
        String url= AppBaseUrl.YOUXI_XIANGQ;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("id",mId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("游戏",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONObject temp = jObj.getJSONObject("game");
                    map_xiangq.put("keywords",temp.getString("keywords"));
                    map_xiangq.put("Experience_posture",temp.getString("Experience_posture"));
                    map_xiangq.put("num",temp.getString("num"));
                    map_xiangq.put("android",temp.getString("android"));
                    map_xiangq.put("language",temp.getString("language"));
                    /*map_xiangq.put("videoId",temp.getString("videoId"));*/
                    map_xiangq.put("game_type",temp.getString("game_type"));
                   /* map_xiangq.put("video",AppBaseUrl.BASEURL+temp.getString("video"));*/
                    map_xiangq.put("ios",temp.getString("ios"));
                    map_xiangq.put("type",temp.getString("type"));
                    /*map_xiangq.put("title",temp.getString("title"));*/
                    map_xiangq.put("game_name",temp.getString("game_name"));
                    map_xiangq.put("datetime",temp.getString("datetime"));
                    map_xiangq.put("control_equipment",temp.getString("control_equipment"));
                    map_xiangq.put("id",temp.getString("id"));
                    map_xiangq.put("Wearing_equipment",temp.getString("Wearing_equipment"));
                    map_xiangq.put("introduce_text",temp.getString("introduce_text"));
                    map_xiangq.put("sort",temp.getString("sort"));
                    map_xiangq.put("support_system",temp.getString("support_system"));
                    map_xiangq.put("pc",temp.getString("pc"));
                    map_xiangq.put("players_note",temp.getString("players_note"));
                    map_xiangq.put("developer",temp.getString("developer"));
                   /* map_xiangq.put("video_background",AppBaseUrl.BASEURL+temp.getString("video_background"));*/
                    map_xiangq.put("game_pic",AppBaseUrl.BASEURL+temp.getString("game_pic"));
                    map_xiangq.put("game_photo1",AppBaseUrl.BASEURL+temp.getString("game_photo1"));
                    map_xiangq.put("likenum",temp.getString("likenum"));
                    map_xiangq.put("clicknum",temp.getString("clicknum"));
                    init();
                    List<String> imglist=new ArrayList<String>();
                    for (int i=0;i<5;i++){
                        if (!temp.getString("game_photo"+(i+1)).isEmpty()){
                            imglist.add(AppBaseUrl.BASEURL+temp.getString("game_photo"+(i+1)));
                        }
                    }
                    imgAdapter = new ImgAdapter(YeyouActivity.this,imglist);
                    rec_img.setAdapter(imgAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(YeyouActivity.this,"网络发生错误!");
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
        initzixun();
        initgame();
        initpinglun();
    }
    private void initgame() {
        String url1= AppBaseUrl.YOUXI_XIANGSI;
        RequestParams params1 = new RequestParams(url1);
        params1.addBodyParameter("id",mId);
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("更多游戏",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("game");
                    for (int i=0;i<jrry.length();i++) {
                        Map<String,String> map=new HashMap<String, String>();
                        JSONObject temp = jrry.getJSONObject(i);
                        map.put("gameId",temp.getString("gameId"));
                        map.put("id",temp.getString("id"));
                        map.put("game_name",temp.getString("game_name"));
                        map.put("game_pic",AppBaseUrl.BASEURL+temp.getString("game_pic"));
                        map.put("background",AppBaseUrl.BASEURL+temp.getString("background"));
                        map.put("developer",temp.getString("developer"));
                        map.put("game_type",temp.getString("game_type"));
                        map.put("type",temp.getString("type"));
                        mXiangsilist.add(map);
                    }
                    gameAdapter=new Shouye_gameAdapter(YeyouActivity.this,mXiangsilist);
                    rec_xiangsi.setAdapter(gameAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(YeyouActivity.this,"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }
    private void initzixun() {
        String url1= AppBaseUrl.YOUXI_ZIXUN;
        RequestParams params1 = new RequestParams(url1);
        params1.addBodyParameter("id",mId);
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("更多资讯",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("news");
                    for (int i=0;i<jrry.length();i++) {
                        Map<String,String> map=new HashMap<String, String>();
                        JSONObject temp = jrry.getJSONObject(i);
                        map.put("datetime",temp.getString("datetime"));
                        map.put("title",temp.getString("title"));
                        map.put("background",AppBaseUrl.BASEURL+temp.getString("background"));
                        map.put("id",temp.getString("id"));
                        map.put("gameId",temp.getString("gameId"));
                        mNewslist.add(map);
                    }
                    zixunAdapter.setmList(mNewslist);
                    zixunAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(YeyouActivity.this,"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }
    private void initpinglun(){
        page=0;
        mPinglunlist.clear();
        String url2= AppBaseUrl.PINGLUN_LIST;
        RequestParams params2 = new RequestParams(url2);
        params2.addBodyParameter("id",mId);
        params2.addBodyParameter("sort","game");
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
                        map.put("pic",AppBaseUrl.BASEURL+temp.getString("pic"));
                        map.put("name",temp.getString("name"));
                        map.put("replynum",temp.getString("replynum"));
                        map.put("commentId",temp.getString("commentId"));
                        map.put("sort",temp.getString("sort"));
                        map.put("content",temp.getString("content"));
                        mPinglunlist.add(map);
                    }
                    pinglunAdapter.setmList(mPinglunlist);
                    pinglunAdapter.notifyDataSetChanged();
                    if (mPinglunlist.size()>0){
                        lin_pinglun_kong.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(YeyouActivity.this,"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }
    private void moredate(){
        String url= AppBaseUrl.PINGLUN_LIST;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("id",mId);
        params.addBodyParameter("sort","game");
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
                        ToastUtil.show(YeyouActivity.this,"没有更多数据了");
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
                ToastUtil.show(YeyouActivity.this,"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }
    private void init() {
        x.image().bind(game_icon,map_xiangq.get("game_pic"), ImageOptions.getimageOptions_img());
        game_name.setText(map_xiangq.get("game_name"));
        game_biaoq.setText(map_xiangq.get("keywords"));
        jieshao.setText(map_xiangq.get("introduce_text"));
        if (map_xiangq.get("introduce_text").isEmpty()){
            rel_zhankai.setVisibility(View.GONE);
        }
        zhinan.setText(map_xiangq.get("players_note"));
        if (map_xiangq.get("players_note").isEmpty()){
            rel_zhankai1.setVisibility(View.GONE);
        }
        num_dianzan.setText(map_xiangq.get("likenum"));
    }
    @Override
    protected void initListener() {

    }

    @Override
    protected boolean isShowToolBar() {
        return false;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.more:
                ShouyouActivity.shareGameWindow(this,more,map_xiangq);
                break;
            case R.id.zhankai:
                if(flag){
                    flag = false;
                    jieshao.setEllipsize(null); // 展开
                    jieshao.setSingleLine(flag);
                    zhankai_text.setText("收缩");
                    zhankai_arrow.setImageResource(R.drawable.arrow_up);
                }else{
                    flag = true;
                    jieshao.setMaxLines(2);
                    jieshao.setEllipsize(TextUtils.TruncateAt.END); // 收缩
                    zhankai_text.setText("展开");
                    zhankai_arrow.setImageResource(R.drawable.arrow_bttom);
                }
                break;
            case R.id.zhankai2:
                if(flag1){
                    flag1 = false;
                    zhinan.setEllipsize(null); // 展开
                    zhinan.setSingleLine(flag1);
                    zhankai_text2.setText("收缩");
                    zhankai_arrow2.setImageResource(R.drawable.arrow_up);
                }else{
                    flag1 = true;
                    zhinan.setMaxLines(2);
                    zhinan.setEllipsize(TextUtils.TruncateAt.END); // 收缩
                    zhankai_text2.setText("展开");
                    zhankai_arrow2.setImageResource(R.drawable.arrow_bttom);
                }
                break;
            case R.id.pinglun:
                if (ed_pinglun.getText().toString().isEmpty()){
                    showToastMsg("评论内容不能为空！");
                    return;
                }
                String url2= AppBaseUrl.PINGLUN;
                RequestParams params1 = new RequestParams(url2);
                params1.addBodyParameter("id",mId);
                params1.addBodyParameter("sort","game");
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
                                lin_pinglun_kong.setVisibility(View.GONE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        LalaLog.d("错误详情",ex.toString());
                        ToastUtil.show(YeyouActivity.this,"网络发生错误!");
                    }
                    @Override
                    public void onCancelled(CancelledException cex) {
                    }
                    @Override
                    public void onFinished() {
                    }
                });
                break;
            case R.id.xin_dianzan:
                String url= AppBaseUrl.DIANZAN;
                RequestParams params = new RequestParams(url);
                params.addBodyParameter("id",map_xiangq.get("id"));
                params.addBodyParameter("sort","game");
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        LalaLog.i("点赞",result);
                        xin_dianzan.setImageResource(R.drawable.ic_hongxin);
                        try {
                            JSONObject jObj = new JSONObject(result);
                            jObj=jObj.getJSONObject("likenum");
                            num_dianzan.setText(jObj.getString("likenum"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        LalaLog.d("错误详情",ex.toString());
                        ToastUtil.show(YeyouActivity.this,"网络发生错误!");
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
