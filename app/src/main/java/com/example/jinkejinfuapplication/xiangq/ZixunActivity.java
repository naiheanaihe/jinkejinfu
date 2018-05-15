package com.example.jinkejinfuapplication.xiangq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinkejinfuapplication.MainActivity;
import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.bean.Shouye_gameBean;
import com.example.jinkejinfuapplication.mine.LoginActivity;
import com.example.jinkejinfuapplication.shouye.More_zixunActivity;
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.MyLinearManager;
import com.example.jinkejinfuapplication.utils.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

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

public class ZixunActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back,img_icon,more;
    private WebView webView;
    private Map<String,String> map_xiangq=new HashMap<>();
    private List<Map<String,String>> mNewslist=new ArrayList<Map<String, String>>();
    private String mId,gameId;
    private TextView game_name,game_type,title,zuozhe,time;
    private ZixunAdapter zixunAdapter;
    private RecyclerView rec_zixun,rec_pinglun;
    private PinglunAdapter pinglunAdapter;
    private Button xiazai;
    private List<Map<String, String>> mPinglunlist=new ArrayList<>();
    private ImageView pinglun;
    private LinearLayout lin_pinglun_kong;
    private EditText ed_pinglun;
    private PtrClassicFrameLayout ptrFrameLayout;
    private int page=0;
    private TextView num_dianzan;
    private ImageView xin_dianzan;
    private LinearLayout lin_pinglun;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_zixun);
        back=findView(R.id.back);
        back.setOnClickListener(this);
        img_icon=findView(R.id.img_icon);
        game_name=findView(R.id.name);
        game_type=findView(R.id.type);
        title=findView(R.id.zixun_title);
        zuozhe=findView(R.id.zuozhe);
        time=findView(R.id.time);
        webView=findView(R.id.webview);
        xiazai=findView(R.id.xiazai);
        xiazai.setOnClickListener(this);
        pinglun=findView(R.id.pinglun);
        pinglun.setOnClickListener(this);
        lin_pinglun_kong=findView(R.id.lin_pinglun_kong);
        ed_pinglun=findView(R.id.ed_pinglun);
        more=findView(R.id.more);
        more.setOnClickListener(this);
        num_dianzan=findView(R.id.num_dianzan);
        xin_dianzan=findView(R.id.xin_dianzan);
        xin_dianzan.setOnClickListener(this);

        rec_zixun=findView(R.id.rec_zixun);
        MyLinearManager linManager1=new MyLinearManager(this,LinearLayoutManager.VERTICAL,false);
        rec_zixun.setLayoutManager(linManager1);
        zixunAdapter=new ZixunAdapter(this,mNewslist);
        rec_zixun.setAdapter(zixunAdapter);

        rec_pinglun=findView(R.id.rec_pinglun);
        final MyLinearManager linManager2=new MyLinearManager(this,LinearLayoutManager.VERTICAL,false);
        rec_pinglun.setLayoutManager(linManager2);
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
        gameId=getIntent().getStringExtra("gameId");

        redate();

    }

    private void redate() {
        String url= AppBaseUrl.ZIXUN_XIANGQ;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("id",mId);
        params.addBodyParameter("gameId",gameId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("资讯",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONObject temp = jObj.getJSONObject("article");
                    map_xiangq.put("game_name",temp.getString("game_name"));
                    map_xiangq.put("datetime",temp.getString("datetime"));
                    map_xiangq.put("background",AppBaseUrl.BASEURL+temp.getString("background"));
                    map_xiangq.put("abstracts",temp.getString("abstracts"));
                    map_xiangq.put("author",temp.getString("author"));
                    map_xiangq.put("content",AppBaseUrl.WENZHANG+temp.getString("content"));
                    map_xiangq.put("articleId",temp.getString("articleId"));
                    map_xiangq.put("game_type",temp.getString("game_type"));
                    map_xiangq.put("title",temp.getString("title"));
                    map_xiangq.put("game_pic",AppBaseUrl.BASEURL+temp.getString("game_pic"));
                    map_xiangq.put("game_background",AppBaseUrl.BASEURL+temp.getString("game_background"));
                    map_xiangq.put("game_id",temp.getString("game_id"));
                    map_xiangq.put("type",temp.getString("type"));
                    map_xiangq.put("likenum",temp.getString("likenum"));
                    map_xiangq.put("clicknum",temp.getString("clicknum"));
                    if (temp.getString("game_id").isEmpty()){
                        xiazai.setVisibility(View.INVISIBLE);
                    }
                    init();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(ZixunActivity.this,"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });

        mNewslist.clear();
        String url1= AppBaseUrl.MOREZIXUN_ZIXUN;
        RequestParams params1 = new RequestParams(url1);
        params1.addBodyParameter("id",mId);
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("更多资讯",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("more");
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
                ToastUtil.show(ZixunActivity.this,"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });

        page=0;
        mPinglunlist.clear();
        String url2= AppBaseUrl.PINGLUN_LIST;
        RequestParams params2 = new RequestParams(url2);
        params2.addBodyParameter("id",mId);
        params2.addBodyParameter("sort","article");
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
                ToastUtil.show(ZixunActivity.this,"网络发生错误!");
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
        params.addBodyParameter("sort","article");
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
                        ToastUtil.show(ZixunActivity.this,"没有更多数据了");
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
                ToastUtil.show(ZixunActivity.this,"网络发生错误!");
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
        x.image().bind(img_icon,map_xiangq.get("game_pic"), ImageOptions.getimageOptions_touming());
        game_name.setText(map_xiangq.get("game_name"));
        game_type.setText(map_xiangq.get("game_type"));
        title.setText(map_xiangq.get("title"));
        zuozhe.setText(map_xiangq.get("author"));
        time.setText(map_xiangq.get("datetime"));

        //设置要加载的网页
        webView.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        webView.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webView.loadUrl(map_xiangq.get("content"));

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
            case R.id.xiazai:
                if (map_xiangq.get("type").equals("手游")){
                    Intent intent=new Intent(this, ShouyouActivity.class);
                    intent.putExtra("id",map_xiangq.get("game_id"));
                    startActivity(intent);
                }else if (map_xiangq.get("type").equals("页游")){
                    Intent intent=new Intent(this, YeyouActivity.class);
                    intent.putExtra("id",map_xiangq.get("game_id"));
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(this, VRActivity.class);
                    intent.putExtra("id",map_xiangq.get("game_id"));
                    startActivity(intent);
                }
                break;
            case R.id.pinglun:
                if (ed_pinglun.getText().toString().isEmpty()){
                    showToastMsg("评论内容不能为空！");
                    return;
                }
                String url1= AppBaseUrl.PINGLUN;
                RequestParams params1 = new RequestParams(url1);
                params1.addBodyParameter("id",mId);
                params1.addBodyParameter("sort","article");
                params1.addBodyParameter("content",ed_pinglun.getText().toString());
                params1.addBodyParameter("userId", MainActivity.LOGINSTATE==MainActivity.LOGINSTATE_LOGIN?MyApplication.getInstance().getYonghuBean().getUserId():"");
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
                        ToastUtil.show(ZixunActivity.this,"网络发生错误!");
                    }
                    @Override
                    public void onCancelled(CancelledException cex) {
                    }
                    @Override
                    public void onFinished() {
                    }
                });
                break;
            case R.id.more:
                shareZixunWindow(more);
                break;
            case R.id.xin_dianzan:
                String url= AppBaseUrl.DIANZAN;
                RequestParams params = new RequestParams(url);
                params.addBodyParameter("id",mId);
                params.addBodyParameter("sort","article");
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
                        ToastUtil.show(ZixunActivity.this,"网络发生错误!");
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

    private void shareZixunWindow( ImageView imageView)
    {
       /* View popupView = View.inflate(this,R.layout.share_poupwindow,null);
        //创建PopupWindow ,指定它的显示的视图，以及它本身的宽度和高度
        final PopupWindow poupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置它获得焦点
        poupWindow.setFocusable(true);
        //设置背景
        poupWindow.setBackgroundDrawable(new BitmapDrawable());
        //动画效果
        ScaleAnimation animation = new ScaleAnimation(0f,1f,0f,1f, Animation.RELATIVE_TO_SELF,0.8f,Animation.RELATIVE_TO_SELF,0f);
        animation.setDuration(500);
        popupView.setAnimation(animation);
        //设置位置
        poupWindow.showAsDropDown(imageView);

        LinearLayout lin1= (LinearLayout) popupView.findViewById(R.id.share);
        lin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poupWindow.dismiss();

            }
        });*/

        if (MainActivity.LOGINSTATE==MainActivity.LOGINSTATE_NOTLOGIN){
            Intent intent=new Intent(ZixunActivity.this,LoginActivity.class);
            startActivity(intent);
            return;
        }
        UMWeb web = null;
        String gameid=map_xiangq.get("game_id").isEmpty()?"0":map_xiangq.get("game_id");
        web = new UMWeb("http://www.goldcs.net/news/" + map_xiangq.get("articleId")+"-"+ gameid);
        web.setTitle(map_xiangq.get("title"));
        web.setDescription(map_xiangq.get("abstracts"));

        UMImage thumb =  new UMImage(ZixunActivity.this, map_xiangq.get("background"));
        web.setThumb(thumb);  //缩略图
                /*web.setDescription("my description");//描述*/
        new ShareAction(ZixunActivity.this)
                .withMedia(web)
                .setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.SINA,SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }
                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        Toast.makeText(ZixunActivity.this,share_media + " 分享成功啦", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        LalaLog.d("分享失败",throwable.getMessage().toString());
                        Toast.makeText(ZixunActivity.this,share_media + " 分享失败啦", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                    }
                }).open();
    }
}
